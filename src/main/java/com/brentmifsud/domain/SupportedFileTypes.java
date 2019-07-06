package com.brentmifsud.domain;

import org.apache.commons.io.FilenameUtils;

/**
 * This enum contains all the supported file types.
 * As you add more support for different file types, add them here.
 */
public enum SupportedFileTypes {
    HTML,
    CSV;

    public static Boolean isSupportedFileType(String fileName) {
        try {
            SupportedFileTypes.valueOf(FilenameUtils.getExtension(fileName).toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
