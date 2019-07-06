package com.brentmifsud.parser;

import java.io.File;
import java.util.List;

public interface IParser {
    <T> List<T> parseFileToPojo(File file, Class<T> schema);
}
