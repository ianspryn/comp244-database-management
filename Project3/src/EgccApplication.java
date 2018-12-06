import java.util.Scanner;

public class EgccApplication {
	public static void main(String[] args){
		//some initialization
		Scanner scnr = new Scanner(System.in);
		boolean running = true;
		int command;
		boolean success;
		
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
			System.out.println("Please enter one of the following commands:\n0: Login\n1: Change my password\n2: View items I bid on\n3: View my items\n4: View my purchases\n5: Search by keyword\n6: View seller rating\n7: Put item up for auction\n8: Ship an item\n9: View highest bid\n10: Place a bid\n11: Rate a seller\n12: Close an auction\n13: Exit eGCC\nInput a command: ");
			command = scnr.nextInt();
			
			//execute command
			if(command == 13){					//exiting the application
				running = false;
				connect.closeConnection();
			}
			
			else if(command == 0){				//login
				System.out.println("Please enter your username: ");
				userNm = scnr.next();
				System.out.println("Password: ");
				psswd = scnr.next();
				success = connect.login(userNm, psswd);
				
				if(success){
					System.out.println("Login successful, welcome "+userNm+"!");
				}else{
					System.out.println("Wrong username/password, please try again.");
				}
			}
			
			else if(command == 1){				//change password
				System.out.println("Please enter your username: ");
				userNm = scnr.next();
				System.out.println("Please enter your new password");
				psswd = scnr.next();
				connect.changePassword(userNm, psswd);
			}
			
			else if(command == 2){				//view items I bid on
				connect.viewMyBiddingItems();
			}
			
			else if(command == 3){				//view my items
				//TODO: fill in
			}
			
			else if(command == 4){				//view my purchases
				connect.viewMyPurchases();
			}
			
			else if(command == 5){				//search by keyword
				//TODO: fill in
			}
			
			else if(command == 6){				//view seller rating
				//TODO: fill in
			}
			
			else if(command == 7){				//put item up for auction
				//TODO: fill in
			}
			
			else if(command == 8){				//ship item
				//TODO: fill in
			}
			
			else if(command == 9){				//view highest bid
				//TODO: fill in
			}
			
			else if(command == 10){			//place bid
				//TODO: fill in
			}
			
			else if(command == 11){			//rate seller
				//TODO: fill in
			}
			
			else if(command == 12){			//close auction
				//TODO: fill in
			}
		}
		
		System.out.println("Thank you for using eGCC!");
		scnr.close();
	}
}
