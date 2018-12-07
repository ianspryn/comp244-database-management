import java.util.Scanner;

public class EgccApplication {
	public static void main(String[] args){
		//some initialization
		Scanner scnr = new Scanner(System.in);
		boolean running = true;
		int command;
		boolean success;
		String searchTerm;
		int itemID;
		int sellerID;
		double outcome;
		double input;
		String[] categories;
		
		//logging in
		System.out.println("Username: (example: 123456) Much apprec");
		String userNm = scnr.next();
		String schema = "schema" + userNm;
		userNm = "u" + userNm;
		System.out.println("Password: ");
		String psswd = scnr.next();
//		System.out.println("Schema: ");
//		String schema = scnr.next();
		
		EgccConnector connect = new EgccConnector(userNm, psswd, schema);
		
		System.out.println("Welcome to eGCC\n~~~~~~~~~~~~~~~~~~~~~~~~~~\nPlease enter your login credentials:");
		
		//Login as member of database
		System.out.println("LOGIN\n\nPlease enter your username: ");
		userNm = scnr.next();
		System.out.println("Password: ");
		psswd = scnr.next();
		success = connect.login(userNm, psswd);
		
		if(success){
			System.out.println("\nLogin successful, welcome "+userNm+"!");
		}else{
			System.out.println("\nWrong username/password, please try again.");
		}
		
		while(running){
			//Get user input
			System.out.println(
					"\nPlease enter one of the following commands:\n"
					+ "0: Change my password\n"
					+ "1: View items I bid on\n"
					+ "2: View my items\n"
					+ "3: View my purchases\n"
					+ "4: Search by keyword\n"
					+ "5: View seller rating\n"
					+ "6: Put item up for auction\n"
					+ "7: Ship an item\n"
					+ "8: View highest bid\n"
					+ "9: Place a bid\n"
					+ "10: Rate a seller\n"
					+ "11: Close an auction\n"
					+ "12: Exit eGCC\n"
					+ "Input a command: "
				);
			command = scnr.nextInt();
			
			//execute command
			if(command == 0){				//change password
				System.out.println("Please enter your username: ");
				userNm = scnr.next();
				System.out.println("Please enter your new password");
				psswd = scnr.next();
				connect.changePassword(userNm, psswd);
			}
			
			else if(command == 1){				//view items I bid on
				connect.viewMyBiddingItems();
			}
			
			else if(command == 2){				//view my items
				connect.viewMyItems();
			}
			
			else if(command == 3){				//view my purchases
				connect.viewMyPurchases();
			}
			
			else if(command == 4){				//search by keyword
				System.out.println("Please enter the word to search for: ");
				searchTerm = scnr.next();
				connect.searchByKeyword(searchTerm);
			}
			
			else if(command == 5){				//view seller rating
				System.out.println("Please enter the item ID whose seller's rating you want to display: ");
				itemID = scnr.nextInt();
				outcome = connect.viewSellerRating(itemID);
				System.out.println("Rating: "+outcome);
			}
			
			else if(command == 6){				//put item up for auction
				System.out.println("Please enter the title: ");
				String title = scnr.next();
				System.out.println("Please enter the starting bid: ");
				input = scnr.nextDouble();
				System.out.println("Please enter the end date (YYYY-MM-DD): ");
				String date = scnr.next();
				System.out.println("How many categories?");
				int a = scnr.nextInt();
				categories = new String[a-1];
				for(int i = 0; i < a; i++){
					System.out.println("Please enter category "+(i+1)+"/"+a+": ");
					categories[i] = scnr.next();
				}
				
				
				success = connect.putItem(title, input, date, categories);
				
				if(success){
					System.out.println("Put item up for auction successfully.");
				}else{
					System.out.println("Could not put item up for auction.");
				}
			}
			
			else if(command == 7){				//ship item
				System.out.println("Please enter the ID of the item to be shipped: ");
				itemID = scnr.nextInt();
				success = connect.shipItem(itemID);
				if(success){
					System.out.println("Item shipped successfully.");
				}else{
					System.out.println("Could not ship.");
				}
			}
			
			else if(command == 8){				//view highest bid
				System.out.println("Please enter the ID of the item: ");
				itemID = scnr.nextInt();
				outcome = connect.viewHighestBid(itemID);
				System.out.println("Highest bid: " + outcome);
			}
			
			else if(command == 9){			//place bid
				System.out.println("Please enter the ID of the item: ");
				itemID = scnr.nextInt();
				System.out.println("Please enter the bid: ");
				input = scnr.nextDouble();
				success = connect.placeBid(itemID, input);
				
				if(success){
					System.out.println("Bid successfully placed.");
				}else{
					System.out.println("Could not place bid.");
				}
			}
			
			else if(command == 10){			//rate seller
				System.out.println("Please enter the seller's ID: ");
				sellerID = scnr.nextInt();
				System.out.println("Please enter the rating: ");
				input = scnr.nextDouble();
				success = connect.rateSeller(sellerID, input);
				
				if(success){
					System.out.println("Seller successfully rated.");
				}else{
					System.out.println("Could not rate Seller.");
				}
			}
			
			else if(command == 11){			//close auction
				System.out.println("Please enter the ID of the item: ");
				itemID = scnr.nextInt();
				success = connect.closeAuction(itemID);
				
				if(success){
					System.out.println("Auction closed.");
				}else{
					System.out.println("Could not close auction.");
				}
			}
			
			else if(command == 12){					//exiting the application
				running = false;
				connect.closeConnection();
			}
		}
		
		System.out.println("You have logged out. Thank you for using eGCC!");
		scnr.close();
	}
}