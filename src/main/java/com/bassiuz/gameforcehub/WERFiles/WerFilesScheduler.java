package com.bassiuz.gameforcehub.WERFiles;

import com.google.gson.Gson;
import io.quarkus.scheduler.Scheduled;
import okhttp3.*;

import javax.enterprise.context.ApplicationScoped;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.ArrayList;

@ApplicationScoped
public class WerFilesScheduler {

    private final OkHttpClient httpClient = new OkHttpClient();
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    private final String otherBackendUrl = System.getProperty("WSNSHELL_HOME");

    @Scheduled(every="30s")
    void syncBackends() {
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
                .url(otherBackendUrl + "/WerFiles/postList")
                .addHeader("User-Agent", "OkHttp Bot")
                .post(body)
                .build();

        try (Response response = httpClient.newCall(request).execute()) {

            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            // Get response body
            String[] unsynced = gson.fromJson(response.body().string(), String[].class);
            for (String unsyncedName : unsynced)
            {
                postWerFileToOtherBackend(unsyncedName);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void postWerFileToOtherBackend(String werFileName)
    {
        // form parameters
        Gson gson = new Gson();
        RequestBody body = RequestBody.create(gson.toJson(new WerFileRepository().getByWerFileByFileName(werFileName)), JSON);

        Request request = new Request.Builder()
                .url(otherBackendUrl + "/WerFiles/postFile")
                .addHeader("User-Agent", "OkHttp Bot")
                .post(body)
                .build();

        try (Response response = httpClient.newCall(request).execute()) {

            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            System.out.println(werFileName + " uploaded");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
