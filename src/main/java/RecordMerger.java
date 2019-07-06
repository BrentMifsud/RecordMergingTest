import com.brentmifsud.domain.FileType;

public class RecordMerger {

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

        // Ensure all input files are supported file types
        for (String arg : args){
			if (!FileType.isSupportedFileType(arg)){
			    System.out.println("Input file extension is not supported.");
			    System.out.println("Please input one of the following file types:");
			    for(FileType fileExtension : FileType.values()){
			        System.out.println(fileExtension.name());
                }
			    System.exit(1);
            }
		}


	}
}
