package fr.thegostsniperfr.arffornia_boostrap.api;

import com.google.gson.JsonObject;
import fr.thegostsniperfr.arffornia_boostrap.ArfforniaBootstrap;
import fr.thegostsniperfr.java_toolbox.json.LoadJson;

import java.net.MalformedURLException;
import java.net.URL;

public class ArfforniaAPI {
    private static final String ARFFORNIA_ENDPOINT = "http://localhost/api/";

    public static String getLauncherHash() {
        try {
            JsonObject apiResponse = LoadJson.loadJsonFromUrl(new URL(ARFFORNIA_ENDPOINT + "launcherVersionInfo"));

            return apiResponse.get("hash").getAsString();
        } catch (MalformedURLException e) {
            ArfforniaBootstrap.getInstance().getLogger().err(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static URL getLauncherJarURL() {
        try {
            System.out.println("URL : " + new URL(ARFFORNIA_ENDPOINT + "download/launcher"));
            return new URL(ARFFORNIA_ENDPOINT + "download/launcher");
        } catch (MalformedURLException e) {
            ArfforniaBootstrap.getInstance().getLogger().err(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
