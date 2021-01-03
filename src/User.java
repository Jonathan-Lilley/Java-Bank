import java.util.Scanner;

// User class is designed to be a basis for both Admin and Client classes
// Includes:
// - Password
// - Password checker (for login)
// - Abstract menu
// - Final file path for database file
abstract class User {
	// Password usable for both Admin and Client
	protected String password; // required to initiate program
	protected long IDNum; // used to locate user in database
	protected String userName; // used at login to identify user
	// final file path to database for all classes to access
	public static final String dataBaseFile = "database.txt"; 
	
	// checkPassword allows 3 tries to enter correct string password;
	// Success returns true, three failures returns false
	// Used to initiate Menu
	protected boolean checkPassword() {
		// Password scanner
		Scanner PWScan = new Scanner(System.in);
		
		// Login loop; escapes after 3 tries
		for (int i=0;i<3;i++) {
			System.out.println("Please enter your password: ");
			String pw = PWScan.nextLine();
			
			// If correct password ...
			if (pw == password) {
				System.out.println("Login successful. Welcome, " + userName);
				// return true
				return true;
			}
			// Otherwise keep going
			else {
				System.out.println("Incorrect password. Please try again");
			}
		}
		// 3 failures = failed login
		System.out.println("Password attempts exceeded. Login denied.");
		return false;
	}
	
	// Username change function
	public String nameChange() {
		// Gets scanner
		Scanner nameScan = new Scanner(System.in);
		System.out.println("Please enter a new name.");
		// Scans for name and sets it, then returns it
		String newName = nameScan.nextLine();
		return newName;
	}
	
	// Menu
	// This is the main program and loop of the subclasses Admin and Client, 
	// so this is defined as an abstract method.
	// In those classes it should contain:
	// - checkPassword (break out of the menu function if it returns false)
	// - Basic actions
	// - Subclass specific actions
	public abstract void menu();
	
}
