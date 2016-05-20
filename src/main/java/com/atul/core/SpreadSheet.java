package com.atul.core;

import com.atul.config.Configuration;
import com.google.api.client.googleapis.media.MediaHttpDownloader;
import com.google.api.client.googleapis.media.MediaHttpUploader;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.*;

import javax.activation.MimetypesFileTypeMap;
import java.io.*;
import java.io.File;
import java.util.Collections;
import java.util.Scanner;

/**
 * Created by atul on 20/05/16.
 */
public class SpreadSheet {
    public static boolean convertToSpreedSheet(Drive drive,
                                               String path) {
        com.google.api.services.drive.model.File fileMetaData = new com.google.api.services.drive.model.File();
        java.io.File file = new java.io.File(path);
        MimetypesFileTypeMap mimeTypesMap = new MimetypesFileTypeMap();

        fileMetaData.setName(file.getName());
        String googleSheetMimeType = "application/vnd.google-apps.spreadsheet";
        fileMetaData.setMimeType(googleSheetMimeType);

        FileContent mediaContent = new FileContent(mimeTypesMap.getContentType(file), file);

        com.google.api.services.drive.model.File f = null;
        Drive.Files.Create create = null;

        try {
            System.out.println("Uploading file " + file.getName());
            create = drive.files().create(fileMetaData, mediaContent)
                    .setFields("id, parents, mimeType");
            create.getMediaHttpUploader().setProgressListener(new FileUpdateProgressListener());

            // Using default chunk size of 10MB.
            create.getMediaHttpUploader().setChunkSize(MediaHttpUploader.DEFAULT_CHUNK_SIZE);
            f = create.execute();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        System.out.println("File ID: " + f.getId() + " Parent: " + f.getParents().toString()
        + " MimeType: " + f.getMimeType());
        return true;

    }

}
