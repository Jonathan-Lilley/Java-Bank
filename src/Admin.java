import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;

// Admin class (from User)
// This class can access Clients and perform non-transaction operations
public class Admin extends User {
	
/***********************************************************************
**************** CORE VARIABLES AND CONSTRUCTOR ************************
***********************************************************************/
	
	// Constructor
	public Admin(long id, String usernm, String pw) {
		userName = usernm;
		IDNum = id;
		password = pw;
	}
	
/***********************************************************************
*********************** BASIC METHODS 1 ********************************
***********************************************************************/
	
	
	
	// Method to check if input is admin
	private boolean checkAdmin(Scanner inScan) {
		String adminyn = "x"; // gets the input for checking admin
		System.out.println("Is the user an admin? (\"yes\" or \"no\")");
		// Loops until input is "yes" or "no"
		while (adminyn != "yes" && adminyn != "no") {
			adminyn = inScan.nextLine();
			// Confirms is admin
			if (adminyn == "yes") {
				System.out.println("User is an admin.");
				return true;
			}
			// Confirms is not admin
			else if (adminyn == "no"){
				System.out.println("User is not an admin.");
				return false;
			}
			// requires re-attempt
			else {
				System.out.println("Please enter \"yes\" or \"no\"");
			}
		}
		// default; should not reach this
		System.out.println("Error with code: checkAdmin function failed.");
		return false;
	}
	
	
	
	
	// gets username
	private String getUsernm(boolean admin, Scanner inScan) {
		String usernm;
		System.out.println("Enter username for the new user.");
		usernm = inScan.nextLine();
		if (admin) {
			usernm = "#" + usernm;
		}
		return usernm;
	}
	
	
	
	
	// gets balance for non-admin user
	private double getBal(Scanner inScan) {
		System.out.println("Enter a starting balance.");
		// Sets up a checker to see if balance was successfully retrieved
		double bal = 0.0d;
		boolean gotBal = false;
		while (!gotBal) {
			try {
				bal = Double.valueOf(inScan.nextLine());
				gotBal = true;
			}
			// Catches an invalid balance
			catch (Exception e) {
				System.out.println("Invalid balance. Please enter a valid"
						+ " balance.");
			}
		}
		return bal;
	}
	
	
	
	
	
	// saves to file
	private void saveData(String usernm, String pw, double bal) 
			throws IOException {
		// Add to file
		File dataBase = new File(dataBaseFile);
		Scanner fileScanner = new Scanner(dataBase);
		// Sets temp variables
		String alldata = "";
		String curLine = ""; // current like temp var
		long currid = -1; // current id recorder
		// Loops through file to collect each line
		while (fileScanner.hasNextLine()) {
			curLine = fileScanner.nextLine(); // gets line
			alldata += '\n' + curLine; // adds line
		}
		String[] lineSplit = curLine.split(","); // Splits curLine to get id
		long curid = Long.parseLong(lineSplit[0]); // gets id
		long id = ++curid; // increments id and creates new id
		// concatentates data
		String newdata = String.valueOf(id) + usernm + pw + String.valueOf(bal); 
		alldata += '\n' + newdata; // adds new data to full file data
		FileWriter writeData = new FileWriter(dataBaseFile); // opens and
		writeData.write(alldata); // writes and
		writeData.close(); // closes
		// confirm Update
		System.out.println("System updated. New data: " + alldata);
	}
	
	
	
	
	// gets ID or UN
	private boolean idorun(Scanner inScan, String inpt) {
		while (inpt != "ID" && inpt != "UN") {
			System.out.println("Find by ID or Username? "
					+ "Enter ID for ID or UN for Username.");
			inpt = inScan.nextLine();
			if (inpt == "ID") {
				System.out.println("You chose ID");
				return true;
			}
			else if (inpt == "UN") {
				System.out.println("You chose Username");
				return false;
			}
			else {
				System.out.println("Please enter ID or UN");
			}
		}
		System.out.println("Code error in idorun()");
		return false;
	}
	
	
	
	
	// getID
	// gets long id from string
	private long getID(Scanner inScan) {
		// default string
		String stringid = "abc";
		// Sets it basically infinite loop? until a valid id is entered or QUIT
		boolean islong = false;
		while (!islong) {
			// Instructions
			System.out.println("Please enter an ID (or QUIT to quit)");
			// scans input
			stringid = inScan.nextLine();
			// Quits if required
			if (stringid == "QUIT") {
				return -1;
			}
			// Tries to see if it's valid
			try {
				return Long.valueOf(stringid);
			}
			// Sends an error and loops if not
			catch (Exception e) {
				System.out.println("Please enter a valid number.");
			}
		}
		System.out.println("Error in code, Admin.java, getID");
		return -1;
	}

	
	
	
	// finds Client in databse and creates Client object
	private Client findClient() throws FileNotFoundException {
		// file stuff
		File dataFile = new File(dataBaseFile);
		Scanner fileScan = new Scanner(dataFile);
		String line = "";
		
		// input stuff
		Scanner inputScan = new Scanner(System.in);
		String inp = "";
		boolean IDtUNf = false; // ID = true, UN = false
		long inpID = -1;
		String inpName = "";
		
		System.out.println("ID or UN (username)? Enter QUIT to quit.");
		// Gets ID, UN or QUIT
		while (inp != "ID" && inp != "UN") {
			inp = inputScan.nextLine();
			// IF ID get the id
			if (inp == "ID") {
				System.out.println("You chose ID,");
				inpID = getID(inputScan);
				if (inpID == -1) {
					continue;
				}
			}
			// IF UN get the username
			else if (inp == "UN") {
				System.out.println("You chose UN");
				System.out.println("Enter a username.");
				inpName = inputScan.nextLine();
				if (inpName == "QUIT") {
					continue;
				}
			}
			// IF QUIT break out of method
			else if (inp == "QUIT") {
				return null;	
			}
			// Throws error and requests retry
			else {
				System.out.println("Please choose ID or UN");
			}
		}
		// Go through file to find the user
		while (fileScan.hasNextLine()) {
			line = fileScan.nextLine();
			String[] lineSplit = line.split(",");
			// If its an id number
			if (IDtUNf) {
				// get the id num and name
				long id = Long.valueOf(lineSplit[0]);
				String name = lineSplit[1];
				// match id and its not admin name?
				if (name.charAt(0) != '#' && id == inpID) {
					// put it all together
					String pw = lineSplit[2];
					double bal = Double.valueOf(lineSplit[3]);
					return new Client(id,name,pw,bal);
				}
			}
			// if its a name
			else { 
				// get name
				String name = lineSplit[1];
				// match name?
				if (name == inpName) {
					// put it all together
					long id = Long.valueOf(lineSplit[0]);
					String pw = lineSplit[2];
					double bal = Double.valueOf(lineSplit[3]);
					return new Client(id,name,pw,bal);
				}
			}
		}
		System.out.println("Something went horriblyw rong. Check line 246");
		return null;
	}
	
	
	
	// Adds user to database
	private void addToDatabase(boolean ad, String name, String pass, double bal) throws Exception {
		// open the file as both read and write
		File dataFile = new File(dataBaseFile);
		FileWriter dataFileIn = new FileWriter(dataBaseFile);
		Scanner scanFile = new Scanner(dataFile);
		String alldata = ""; // string for data to write
		long usrid = 0; // Checks the current user id -- if it does not exist, insert the user here
		boolean inserted = false; // checks to see if user has been inserted or not
		String curline; // temp var for line catching
		// loop through lines
		while(scanFile.hasNextLine()) {
			// line catching and splitting
			curline = scanFile.nextLine();
			String[] lineSplit = curline.split(",");
			long id = Long.valueOf(lineSplit[0]);
			// if the current id is not the same value as usrid, insert the user here
			if(id != usrid) {
				if(ad) {
					alldata += Long.toString(usrid) + "," + name + "," + pass + '\n';
				}
				else {
					alldata += Long.toString(usrid) + "," + name + "," + pass + Double.toString(bal) + '\n';
				}
				inserted = true;
			}
			// Otherwise just insert the next file
			else {
				alldata += curline + "\n";
			}
			usrid++;
		}
		// Insert at the end if an empty slot wasnt found in the file
		if(!inserted) {
			if(ad) {
				alldata += Long.toString(usrid) + "," + name + "," + pass + '\n';
			}
			else {
				alldata += Long.toString(usrid) + "," + name + "," + pass + Double.toString(bal) + '\n';
			}			
		}
		//write
		dataFileIn.write(alldata);
	}
	
/***********************************************************************
*********************** BASIC METHODS 2 ********************************
***********************************************************************/
	
	// Create user
	public void createUser() throws Exception {
		System.out.println("Creating user.");
		// Initialize scanner
		Scanner inputScan = new Scanner(System.in);
		// initialize variables
		boolean admin = false; // is the user an admin
		String usernm = ""; // username
		String pw = ""; // password
		double bal = -1.0d; // balance (for clients only)
		
		//// Run through options for inputs
		
		// Is admin? gets admin as boolean
		admin = checkAdmin(inputScan);
		
		// Gets username
		usernm = getUsernm(admin, inputScan);
		
		// Gets password
		System.out.println("Enter a password for the new user.");
		pw = inputScan.nextLine();
		
		// Get balance if not admin
		if (!admin) {
			bal = getBal(inputScan);
		}
		
		// Creates user in database file
		addToDatabase(admin,usernm,pw,bal);
		
	}
	
	
	
	
	
	// Edits client by opening client menu
	public void editUser() throws FileNotFoundException {
		System.out.println("Editing user.");
		Client client = findClient();
		client.menu();
	}

/***********************************************************************
*********************** MAIN RUN METHOD ********************************
***********************************************************************/
	
	
	// Menu
	// runs through run options for admins
	public void menu() {
		// Start login process
		System.out.println("Please enter password to log in.");
		boolean corrPw = false;
		corrPw = checkPassword();
		// End menu if login failed
		if (corrPw == false) {
			return;
		}
		
		// Main loop of menu
		// Sets up choice var and scanner for choice var
		int choice = 0;
		Scanner choiceScan = new Scanner(System.in);
		do {
			// Intro text
			System.out.println("Please enter an operation from "
					+ "the following list:");
			System.out.println("1. Create User\n2. Delete User"
					+ "\n3. Change Username\n4. Edit User\n5. Log Out");
			// Catches if input is anything other than an int
			try {
				choice = choiceScan.nextInt();
			}
			catch (Exception e) {
				System.out.println("Please enter 1, 2, 3, 4, or 5.");
				continue;
			}
			
			// Starts the options switch
			// Each switch option uses a method -- refer to them for details
			String bal;
			// Switches on choice var
			switch (choice) {
				// Case 1: check bal
				case 1:
					createUser();
					break;
				// Case 2: deposit
				case 2:
					deleteUser();
					break;
				// Case 3: withdraw
				case 3:
					editUser();
					break;
				// Case 4: change username
				case 4:
					userName = nameChange();
					System.out.println("Name changed.");
					break;
				// Case 5: log out
				case 5:
					System.out.println("Thank you for using Bank.");
					return;
				default:
					// Catches if invalid int input (not 1-5)
					System.out.println("Please enter 1, 2, 3, 4, or 5.");
					break;
			}
		} while (choice != 4);
	}
	
}
