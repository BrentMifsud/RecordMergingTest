package com.brentmifsud.domain;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class FileTypeTest {

    @ParameterizedTest(name = "{displayName} [{index}] {arguments}")
    @ArgumentsSource(FileTypeArguments.class)
    void isSupportedFileType(FileTypeArgument args) {
        assertThat(FileType.isSupportedFileType(args.fileName)).isEqualTo(args.expectedResult);
    }

    static class FileTypeArgument {
        String testDescription;
        String fileName;
        Boolean expectedResult;

        FileTypeArgument(String testDescription, String fileName, Boolean expectedResult) {
            this.testDescription = testDescription;
            this.fileName = fileName;
            this.expectedResult = expectedResult;
        }
    }

    static class FileTypeArguments implements ArgumentsProvider {
        public FileTypeArguments() {
        }

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
            return Stream.of(
                    Arguments.of(
                            new FileTypeArgument(
                                    "Valid FileType with single extension",
                                    "test.html",
                                    true
                            )
                    ),
                    Arguments.of(
                            new FileTypeArgument(
                                    "Valid FileType with multiple extensions",
                                    "test.abc.html",
                                    true
                            )
                    ),
                    Arguments.of(
                            new FileTypeArgument(
                                    "File with no extension",
                                    "test",
                                    false
                            )
                    ),
                    Arguments.of(
                            new FileTypeArgument(
                                    "File with invalid extension",
                                    "test.mp3",
                                    false
                            )
                    )
            );
        }
    }
}