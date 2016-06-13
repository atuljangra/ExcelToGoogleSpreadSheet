package com.atul.core;

import com.atul.config.Configuration;
import com.google.api.services.drive.Drive;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by atul on 20/05/16.
 */
public class Main {
    public static void main(String [] args) {
        System.out.println("Enter the path:");
        Scanner scanner = new Scanner(System.in);
        String path = scanner.nextLine();
        Drive drive = null;
        try {
            drive = Configuration.getDriveService();
            SpreadSheet.convertToSpreedSheet(drive, path);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
