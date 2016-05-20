package com.atul.config;

import com.atul.core.Authorization;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;

import java.io.File;
import java.io.IOException;

/**
 * Created by atul on 18/05/16.
 */
public class Configuration {

    public static final long DEFAULT_FREE_DRIVE_SPACE = 1000000000L; // 1GB Free space
    /**
     * Global instance of the {@link com.google.api.client.util.store.DataStoreFactory}. The best practice is to make it a single
     * globally shared instance across your application.
     */
    public static FileDataStoreFactory dataStoreFactory;

    /** Global instance of the HTTP transport. */
    public static HttpTransport httpTransport;

    /** Global instance of the JSON factory. */
    public static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    /** Directory to store user credentials. */
    public static final File DATA_STORE_DIR =
            new File(System.getProperty("user.home"), ".store/drive_sample");

    /** Global Drive API client. */
    public static Drive drive;

    private static final String APPLICATION_NAME="unlimitedGoogleDrive";

    static {
        try {
            httpTransport = GoogleNetHttpTransport.newTrustedTransport();
            dataStoreFactory = new FileDataStoreFactory(DATA_STORE_DIR);
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }



    /**
     * Build and return an authorized Drive client service.
     * @return an authorized Drive client service
     * @throws java.io.IOException
     */
    public static Drive getDriveService() throws IOException {
        System.out.println("Getting the credentials.");
        Authorization auth = new Authorization();
        Credential credential = auth.getCredential();

        return new Drive.Builder(
                httpTransport, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }
}
