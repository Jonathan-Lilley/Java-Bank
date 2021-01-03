import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;

// Client is a class that inherits the base template of User
public class Client extends User {	
	
/***********************************************************************
**************** CORE VARIABLES AND CONSTRUCTOR ************************
***********************************************************************/
	
	// Base variables and stuff
	// Balance of Client's account; private for obvious reasons
	private double balance = 0.0d;
	
	// Constructor
	// Sets up password, interest, balance
	public Client(long id, String usernm, String pw, double bal) {
		userName = usernm;
		IDNum = id;
		password = pw;
		balance = bal;
	}

/***********************************************************************
*********************** CORE METHODS ***********************************
***********************************************************************/
	
	// checks balance
	public void checkbal(double bal) {
		String balstr = String.valueOf(bal);
		System.out.println("Your balance is: $" + bal);
	}
	
	// deposits money, then returns current bal
	private double deposit(double bal) {
		Scanner mons = new Scanner(System.in);
		try {
			// Calculate balance
			double depos = mons.nextDouble();
			bal += depos;
			// Set strings
			String monsies = String.valueOf(depos);
			String balnc = String.valueOf(bal);
			// Output result
			System.out.println("You added " + monsies + 
					". Your balance is $" + balnc);
		}
		// Catches invalid entries
		catch (Exception e) {
			System.out.println("That is not a valid amount.");
		}
		return bal;
	}
	
	// withdraws money, then returns current bal
	private double withdraw(double bal) {
		Scanner mons = new Scanner(System.in);
		try {
			// calculate withdrawal
			double withdr = mons.nextDouble();
			bal -= withdr;
			// Checks if balance < 0; sends an alert if it is
			if (bal < 0) {
				System.out.println("ALERT: Your balance is now below $0.");
			}
			// make strings
			String monsies = String.valueOf(withdr);
			String balnc = String.valueOf(bal);
			// talk
			System.out.println("You took out" + monsies + 
					". Your balance is $" + balnc);
		}
		// Catches invalid entries
		catch (Exception e) {
			System.out.println("That is not a valid amount.");
		}
		return bal;
	}
	
	
	// Save data
	private void saveData(String usrnm, double bal) throws IOException {
		// opens file and file scanner
		File database = new File(dataBaseFile);
		Scanner dataScan = new Scanner(database);
		// Initializes a string for the file
		String alldata = "";
		String curLine;
		// Loops through lines in file
		while (dataScan.hasNextLine()) {
			// splits up line so it can identify IDNum
			curLine = dataScan.nextLine();
			String[] lineSplit = curLine.split(",");
			long id = Long.valueOf(lineSplit[0]);
			// If the IDNum isn't the current user...
			if (id != IDNum) {
				// append alldata and continue
				alldata += '\n' + curLine;
				continue;
			}
			// If not, make current line updated value
			curLine = lineSplit[0]+","+usrnm+","+String.valueOf(bal);
			// and add it to alldata
			alldata += '\n' + curLine;
		}
		// close reader
		dataScan.close();
		//open writer, write, close writer
		FileWriter dataWriter = new FileWriter(dataBaseFile);
		dataWriter.write(alldata);
		dataWriter.close();
	}
	
/***********************************************************************
*********************** MAIN RUN METHOD ********************************
***********************************************************************/
	
	// Menu!
	// Full menu, inherited from User
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
			System.out.println("1. Check balance\n2. Deposit"
					+ "\n3. Withdraw\n4. Change Username\n5. Log out");
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
					checkbal(balance);
					break;
				// Case 2: deposit
				case 2:
					balance = deposit(balance);
					break;
				// Case 3: withdraw
				case 3:
					balance = withdraw(balance);
					break;
				// Case 4: change username
				case 4:
					userName = nameChange();
					System.out.println("Name changed.");
					break;
				// Case 5: log out
				case 5:
					try {
						saveData(userName, balance);
					} 
					// File IO exception catcher
					catch (IOException e) {
						e.printStackTrace();
					}
					System.out.println("Thank you for using Bank.");
					return;
				default:
					// Catches if invalid int input (not 1-5)
					System.out.println("Please enter 1, 2, 3, 4, or 5.");
					break;
			}
		} while (choice != 5);
	}
}







