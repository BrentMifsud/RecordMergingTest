package com.brentmifsud.domain;

import org.apache.commons.io.FilenameUtils;

public enum FileType {
    HTML,
    CSV;

    public static Boolean isSupportedFileType(String fileName) {
        try {
            FileType.valueOf(FilenameUtils.getExtension(fileName).toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
