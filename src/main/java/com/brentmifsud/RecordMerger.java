package com.brentmifsud;

import com.brentmifsud.domain.SupportedFileTypes;
import com.brentmifsud.merger.FileMerger;

public class RecordMerger {

	private static final FileMerger merger = new FileMerger();

	public static final String FILENAME_COMBINED = "combined.csv";

	/**
	 * Entry point of this test.
	 *
	 * @param args command line arguments: first.html and second.csv.
	 * @throws Exception bad things had happened.
	 */
	public static void main(final String[] args) throws IllegalArgumentException {

		if (args.length == 0) {
			System.err.println("Usage: java -jar RecordMerger/build/libs/RecordMerger-1.0-SNAPSHOT.jar file1 ...fileN");
			System.exit(1);
		}

		// your code starts here.

		/*
		* ASSUMPTION 1:
		* file path is passed as argument into program rather than just file name.
		* This allows the application to use files from anywhere rather than just the resources folder.
		*
		* example mac usage: java -jar RecordMerger/build/libs/RecordMerger-1.0-SNAPSHOT.jar /Users/<username>/Desktop/file1.html .../file2.csv
		* example windows usage: -jar java RecordMerger/build/libs/RecordMerger-1.0-SNAPSHOT.jar C:/Users/<username>/Desktop/file1.html .../file2.csv
		*
		* ASSUMPTION 2:
		* All records are to be included in the output file.
		* The record IDs that only exist in a single file will have some fields as empty strings.
		*
		* Example output: Jane Doe is the only person that exists in both files:
		* "Occupation","Address","PhoneNum","ID","Gender","Name"
		* "","123 Apple Street","555-1234","1111","","John Smith"
		* "Doctor","","","3333","Female","Mary Phil"
		* "Teacher","456 Orange Street","555-5678","5555","Female","Jane Doe"
		* "Pilot","","","6666","Male","Jerry Springfield"
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

        merger.prepareFilesForMerging(args);
	}
}
