package com.bassiuz.gameforcehub.WERFiles;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.quarkus.scheduler.Scheduled;
import okhttp3.*;

import javax.enterprise.context.ApplicationScoped;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;

@ApplicationScoped
public class WerFilesScheduler {

    private final OkHttpClient httpClient = new OkHttpClient();
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    private final String otherBackendUrl = System.getenv("OTHER_BACKEND_URL");

    @Scheduled(every="30s")
    void syncBackends() {
        if (otherBackendUrl != null)
        {
            System.out.println("Synchronizing Backends with " + otherBackendUrl);

            Gson gson = new Gson();

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

            int i = 0;

            try (Response response = httpClient.newCall(request).execute()) {
                if (i< 1)
                {
                    if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                    // Get response body
                    String responseString = response.body().string();
                    System.out.println(responseString);
                    if (request.body().contentLength() > 2)
                    {
                        String[] unsynced = gson.fromJson(responseString, String[].class);
                        for (String unsyncedName : unsynced)
                        {
                            postWerFileToOtherBackend(unsyncedName);
                        }
                    }
                    i++;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void postWerFileToOtherBackend(String werFileName)
    {
        // form parameters
        Gson gson = new Gson();
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
