package com.atul.core;

import com.atul.config.Configuration;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.services.drive.DriveScopes;

import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by atul on 18/05/16.
 */
public class Authorization {

    /** Authorizes the installed application to access user's protected data. */
    private Credential authorize() throws Exception {
        // load client secrets
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(Configuration.JSON_FACTORY,
                new InputStreamReader(Main.class.getResourceAsStream("/client_secrets/client_secrets.json")));
        if (clientSecrets.getDetails().getClientId().startsWith("Enter")
                || clientSecrets.getDetails().getClientSecret().startsWith("Enter ")) {
            System.out.println(
                    "Enter Client ID and Secret from https://code.google.com/apis/console/?api=drive "
                            + "into src/main/resources/client_secrets.json");
            System.exit(1);
        }

        // set up authorization code flow
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                Configuration.httpTransport, Configuration.JSON_FACTORY, clientSecrets,DriveScopes.all()
                ).setDataStoreFactory(Configuration.dataStoreFactory)
                .build();
        // authorize
        return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
    }

    public Credential getCredential() {
        Credential credential = null;
        try {
            credential = authorize();
        } catch (Exception ex) {
            System.out.println("Problem while authorizing.");
            ex.printStackTrace();
        }
        return credential;
    }
}
