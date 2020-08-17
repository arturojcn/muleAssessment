package com.challenge.mule.util;


import org.apache.maven.shared.utils.io.DirectoryScanner;

import java.util.Arrays;
import java.util.List;

public class NameFileHelper {

    public static String getFileName(String path, String prefixNameFile) {
        DirectoryScanner scanner = new DirectoryScanner();
        scanner.setIncludes(prefixNameFile);
        scanner.setBasedir(path);
        scanner.setCaseSensitive(false);
        scanner.scan();
        List<String> files = Arrays.asList(scanner.getIncludedFiles());
        return path + "/" + files.get(0);
    }
}
