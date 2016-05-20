package com.atul.core;

import com.google.api.client.googleapis.media.MediaHttpUploader;
import com.google.api.client.googleapis.media.MediaHttpUploaderProgressListener;

import java.io.IOException;
import java.text.NumberFormat;

/**
 * Created by atul on 19/05/16.
 */
public class FileUpdateProgressListener implements MediaHttpUploaderProgressListener {
    @Override
    public void progressChanged(MediaHttpUploader mediaHttpUploader) throws IOException {
        switch (mediaHttpUploader.getUploadState()) {
            case INITIATION_STARTED:
                System.out.println("Initializing.");
                break;
            case INITIATION_COMPLETE:
                System.out.println("Initiation Completed.");
                break;
            case MEDIA_IN_PROGRESS:
                System.out.println("Upload in progress " +
                        NumberFormat.getPercentInstance().format(mediaHttpUploader.getProgress()) +
                " uploaded!");
                break;
            case MEDIA_COMPLETE:
                System.out.println("Upload Completed!");
                break;
        }
    }
}
