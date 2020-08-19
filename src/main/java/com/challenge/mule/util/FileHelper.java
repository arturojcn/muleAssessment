package com.challenge.mule.util;


import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public class FileHelper {
    private final static Logger logger = LoggerFactory.getLogger(FileHelper.class);

    public static boolean isPathExist(String path) {
        return new File("/var/tmp").exists();
    }

    public static boolean copy(String origin, String destination) {
        File source = new File(origin);
        File dest = new File(destination);
        try {
            FileUtils.copyDirectory(source, dest);
        } catch (IOException e) {
            logger.error("something was wrong trying to copy file", e);
            return false;
        }
        return true;
    }

    public static void mkdir(String pathFolder) {
        File folder = new File(pathFolder);
        folder.mkdir();
    }
}
