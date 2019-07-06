package com.brentmifsud;

import com.brentmifsud.domain.SupportedFileTypes;
import com.brentmifsud.merger.Merger;

public class RecordMerger {

	private static final Merger merger = new Merger();

	public static final String FILENAME_COMBINED = "combined.csv";

	/**
	 * Entry point of this test.
	 *
	 * @param args command line arguments: first.html and second.csv.
	 * @throws Exception bad things had happened.
	 */
	public static void main(final String[] args) throws IllegalArgumentException {

		if (args.length == 0) {
			System.err.println("Usage: java RecordMerger file1 [ file2 [...] ]");
			System.exit(1);
		}

		// your code starts here.

		/*
		* ASSUMPTION:
		* file path is passed as argument into program rather than just file name.
		*
		* example mac usage: java RecordMerger /Users/<username>/Desktop/file1.html .../file2.csv
		* example windows usage; java RecordMerger C:/Users/<username>/Desktop/file1.html .../file2.csv
		*/

        // Ensure all input files are supported file types
		for (String arg : args){
			if (!SupportedFileTypes.isSupportedFileType(arg)){
			    System.out.println("Input file extension is not supported.");
			    System.out.println("Please input one of the following file types:");
			    for(SupportedFileTypes fileExtension : SupportedFileTypes.values()){
			        System.out.println("- " + fileExtension.name());
                }
            }
		}

        merger.prepareFiles(args);
	}
}
