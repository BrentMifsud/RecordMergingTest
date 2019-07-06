package com.brentmifsud.domain;

import org.apache.commons.lang3.StringUtils;

public enum FileType {
    HTML,
    CSV;

    public static Boolean isSupportedFileType(String fileName) throws IllegalArgumentException {
        //Split file name and extension
        String[] splitString = StringUtils.split(fileName, '.');

        //Return false if file has no extension
        if(splitString.length == 1) return false;

        //Grab the last extension (this is for edge cases where a file has multiple '.' in its name)
        String extension = splitString[splitString.length - 1];

        try {
            FileType.valueOf(extension.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
