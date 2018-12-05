import java.util.Scanner;

public class EgccApplication {
	public static void main(String[] args){
		//some initialization
		Scanner scnr = new Scanner(System.in);
		boolean running = true;
		int command;
		
		//logging in
		System.out.println("Welcome to eGCC\n~~~~~~~~~~~~~~~~~~~~~~~~~~\nPlease enter your login credentials:");
		System.out.println("Username: ");
		String userNm = scnr.next();
		System.out.println("Password: ");
		String psswd = scnr.next();
		System.out.println("Schema: ");
		String schema = scnr.next();
		
		EgccConnector connect = new EgccConnector(userNm, psswd, schema);
		
		while(running){
			//Get user input
			System.out.println("Please enter one of the following commands:\n0: Change my password\n1: View items I bid on\n2: View my items\n3: View my purchases\n4: Search by keyword\n5: View seller rating\n6: View my purchases\n7: Put item up for auction\n8: Ship an item\n9: View highest bid\n10: Place a bid\n11: Rate a seller\n12: Close an auction\n13: Exit eGCC\nInput a command: ");
			command = scnr.nextInt();
			
			//execute command
			if(command == 13){
				running = false;
				connect.closeConnection();
			}else if(command == 0){
				System.out.println("Please enter the user's username: ");
				userNm = scnr.next();
				System.out.println("Password: ");
				psswd = scnr.next();
				connect.login(userNm, psswd);
			}else if(command == 1){
				System.out.println("Please enter your username: ");
				userNm = scnr.next();
				System.out.println("Please enter your new password");
				psswd = scnr.next();
				connect.changePassword(userNm, psswd);
			}
		}
		
		System.out.println("Thank you for using eGCC!");
		scnr.close();
	}
}
