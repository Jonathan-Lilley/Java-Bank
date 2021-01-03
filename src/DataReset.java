import java.io.FileWriter;
import java.io.IOException;
import java.io.File;

public class DataReset {
	public static void resetFile(String[] args) throws IOException {
		File dataBase = new File(User.dataBaseFile);
		if (dataBase.createNewFile()) {
			System.out.println("Database initiated.");
			System.out.println(User.dataBaseFile + " created");
		}
		FileWriter dataBaseWriter = new FileWriter(User.dataBaseFile);
		dataBaseWriter.write("0,#Jonathan,pass");
		System.out.println("Database contents: 0,#Jonathan,pass");
		dataBaseWriter.close();
		System.out.println("Database Closed.");
	}
}
