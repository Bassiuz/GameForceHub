package com.bassiuz.gameforcehub.WERFiles;

import com.bassiuz.gameforcehub.Player.Player;
import com.bassiuz.gameforcehub.Player.PlayerRepository;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.quarkus.scheduler.Scheduled;
import okhttp3.*;
import org.xml.sax.SAXException;

import javax.enterprise.context.ApplicationScoped;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLOutput;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

@ApplicationScoped
public class WerFilesScheduler {

    private final OkHttpClient httpClient = new OkHttpClient();
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    private final String otherBackendUrl = getBackendUrl();
    private final boolean enableSyncing = true;

    private String getBackendUrl() {
        if (System.getenv("OTHER_BACKEND_URL") != null)
            return System.getenv("OTHER_BACKEND_URL").replace("\"", "").replace(" ", "");
        else
        {
            return System.getProperty("OTHER_BACKEND_URL").replace("\"", "").replace(" ", "");
        }
    }

    private boolean backendUrlAvailable()
    {
        return otherBackendUrl != null && !otherBackendUrl.equals("") && enableSyncing;
    }

    private static boolean warnedAboutNoEnv = false;
    private static final Gson gson = new Gson();

    @Scheduled(every="30s")
    void syncBackends() throws IOException, SAXException {
        if (!backendUrlAvailable() && !warnedAboutNoEnv) {
            warnedAboutNoEnv = true;
            System.out.println("No Environment Variable Found for other Backend Url.");
        }
        pushUpdates();
        pullUpdates();
    }

    private void pullUpdates() throws IOException, SAXException {
        if (backendUrlAvailable()) {
            // code request code here
                Request request = new Request.Builder()
                        .url(otherBackendUrl + "WerFiles")
                        .build();

                Response response = httpClient.newCall(request).execute();
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            String responseString = response.body().string();
            if (responseString.length() > 2)
            {

                JsonArray unsynced = gson.fromJson(responseString, JsonArray.class);
                for (JsonElement pa : unsynced) {
                    {

                        if (new WerFileRepository().getByWerFileByFileName(pa.getAsJsonObject().get("fileName").getAsString()) == null) {
                            WerFile werFile = new WerFile();
                            werFile.setXmlValue(pa.getAsJsonObject().get("xmlValue").getAsString());
                            werFile.setFileName(pa.getAsJsonObject().get("fileName").getAsString());
                            werFile.setUploadDate(LocalDate.now());
                            werFile.parseObjectFromXML(new PlayerRepository());
                            new WerFileRepository().save(werFile);

                            System.out.println(pa.getAsJsonObject().get("fileName").getAsString() + " downloaded");
                        }
                    }
                }

            }

        }
    }

    private void pushUpdates() {
        if (backendUrlAvailable())
        {
            ArrayList<String> names = new ArrayList<>();
            for (WerFile werFile : new WerFileRepository().findAll())
            {
                names.add(werFile.getFileName());
            }

            // form parameters
            RequestBody body = RequestBody.create(gson.toJson(names), JSON);

            Request request = new Request.Builder()
                    .url(otherBackendUrl + "WerFiles/postList")
                    .addHeader("User-Agent", "OkHttp Bot")
                    .post(body)
                    .build();


            try (Response response = httpClient.newCall(request).execute()) {
                    if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                    // Get response body
                    String responseString = response.body().string();
                    if (request.body().contentLength() > 2)
                    {
                        String[] unsynced = gson.fromJson(responseString, String[].class);
                        for (String unsyncedName : unsynced)
                        {
                            postWerFileToOtherBackend(unsyncedName);
                        }
                    }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void postWerFileToOtherBackend(String werFileName)
    {
        // form parameters
        HashMap<String, String> object = new HashMap<>();
        object.put("fileName", werFileName);
        object.put("xmlValue", new WerFileRepository().getByWerFileByFileName(werFileName).getXmlValue());
        String jsonObj = gson.toJson(object);

        RequestBody requestBody = RequestBody.create(jsonObj,JSON);

        Request request = new Request.Builder()
                .url(otherBackendUrl + "WerFiles/postFile")
                .addHeader("User-Agent", "OkHttp Bot")
                .post(requestBody)
                .build();

        try (Response response = httpClient.newCall(request).execute()) {

            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            System.out.println(werFileName + " uploaded");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
