import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
	private static String targetDir = "";
	private static String destinationDir = "";
	private static final StringBuilder contentFile = new StringBuilder();
	private static final StringBuilder importFile = new StringBuilder();

	public static void main(String[] args) {
		targetDir = args[0];
		destinationDir = args[1];

		System.out.println("targetDir : " + targetDir);
		System.out.println("destinationDir : " + destinationDir);
		
		listFilesByFolder(new File(targetDir));
		
		writeFile();

	}
	
	private static void listFilesByFolder(final File folder) {
	    for (final File fileEntry : folder.listFiles()) {
	    	readFile(fileEntry);
	    }
	}

	/**
	 * Read the content from file.
	 * @param file
	 */
	private static void readFile(final File file) {
		System.out.println(file.getAbsolutePath());
		try(BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
			
		    String line = skipLines(bufferedReader).readLine();
		    while(line != null) {
		    	if(line.contains("import")) {
		    		importFile.append(line).append("\n");
		    	} else {
			    	contentFile.append(line).append("\n");
		    	}
		    	
		        line = bufferedReader.readLine();
		    }
		} catch (Exception e) {
		    System.out.println(e);
		}
	}
	
	/**
	 * Skip the two first line.
	 * @param bufferedReader
	 * @return
	 * @throws IOException
	 */
	private static BufferedReader skipLines(final BufferedReader bufferedReader) throws IOException {
		for (int i = 0; i < 2; i++) {
			bufferedReader.readLine();
		}
		
		return bufferedReader;
		
	}
	
	/**
	 * Write the content in file.
	 */
	private static void writeFile() {
		try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(destinationDir))) {
		    bufferedWriter.write(importFile.append("\n").append(contentFile).toString());
		} catch (IOException e) {
		    // Exception handling
		}
	}
}
