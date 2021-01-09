import java.io.FileWriter;
import java.io.IOException;
import java.io.File;

// Resets database file for testing purposes
public class DataReset {
	public static void resetFile(String[] args) throws IOException {
		File dataBase = new File(User.dataBaseFile);
		// creates database if not already created
		if (dataBase.createNewFile()) {
			System.out.println("Database initiated.");
			System.out.println(User.dataBaseFile + " created");
		}
		// Opens the file for writing
		FileWriter dataBaseWriter = new FileWriter(User.dataBaseFile);
		// Write single admin with ID 0 and password "pass"
		// Then verbose that, then close the file, then verbose the close
		dataBaseWriter.write("0,#Jonathan,pass");
		System.out.println("Database contents: 0,#Jonathan,pass");
		dataBaseWriter.close();
		System.out.println("Database Closed.");
	}
}
