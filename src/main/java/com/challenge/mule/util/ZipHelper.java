package com.challenge.mule.util;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZipHelper {

    private static final int BUFFER_SIZE = 4096;
    private static final String META_COUNTRY = "Metadata_Country";
    private static final String META_INDICATOR = "Metadata_Indicator";
    private static final String INDICATOR_DATA = "API_Download";
    private static final String CSV = ".csv";

    public static void unzip(byte[] data, String dirName) throws IOException {
        File destDir = new File(dirName);
        if (!destDir.exists()) {
            destDir.mkdir();
        }
        ZipInputStream zipIn = new ZipInputStream(new ByteArrayInputStream(data));
        ZipEntry entry = zipIn.getNextEntry();

        while (entry != null) {

            String filePath = dirName + File.separator;
            if (!entry.isDirectory()) {
                // if the entry is a file, extracts it
                String fileName = getNameFile(entry.getName());
                extractFile(zipIn, filePath+fileName);
            } else {
                // if the entry is a directory, make the directory
                File dir = new File(filePath+entry.getName());
                dir.mkdir();
            }
            zipIn.closeEntry();
            entry = zipIn.getNextEntry();
        }
        zipIn.close();
    }

    private static void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
        byte[] bytesIn = new byte[BUFFER_SIZE];
        int read;
        while ((read = zipIn.read(bytesIn)) != -1) {
            bos.write(bytesIn, 0, read);
        }
        bos.close();
    }

    private static String getNameFile(String fileName) {
        String fileNameResponse = fileName;
        if (fileName.contains(META_COUNTRY)) {
            fileNameResponse = META_COUNTRY+CSV;
        } else if (fileName.contains(META_INDICATOR)) {
            fileNameResponse = META_INDICATOR+CSV;
        } else if (fileName.contains(INDICATOR_DATA)) {
            fileNameResponse = INDICATOR_DATA+CSV;
        }
        return  fileNameResponse;

    }
}
