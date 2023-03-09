package com.techelevator.view;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class Menu {

	//used to output the menu options to the user
	private PrintWriter out;
	//reads the user's input.
	private Scanner in;

	//stores the balance of the user before they made a purchase
	private double prevBalance;
	//stores the balance of the user before they inserted any money
	private double nonpreBalance;
	//stores the current balance of the user.
	private double balance;

	/*A constructor that takes in an InputStream and an
	OutputStream as arguments.
	These streams are used to create the Scanner and PrintWriter
	objects, respectively.
	 */
	public Menu(InputStream input, OutputStream output) {
		this.out = new PrintWriter(output);
		this.in = new Scanner(input);
	}
	/*Takes an array of objects as an argument and returns an object.
	Displays the menu options to the user and waits for their input.
	It continues to do so until the user provides a valid input.
	 */
	public Object getChoiceFromOptions(Object[] options) {
		Object choice = null;
		while (choice == null) {
			displayMenuOptions(options);
			choice = getChoiceFromUserInput(options);
		}
		return choice;
	}
	/*takes an array of objects as an argument and returns an object.
	It reads the user's input, converts it to an integer,
	and checks if it is a valid menu option. If it is,
	it returns the corresponding object from the options array.
	If it is not a valid option, it prints an error message and returns
	null.
	 */
	private Object getChoiceFromUserInput(Object[] options) {
		Object choice = null;
		String userInput = in.nextLine();
		try {
			int selectedOption = Integer.valueOf(userInput);
			if (selectedOption > 0 && selectedOption <= options.length) {
				choice = options[selectedOption - 1];
			}
		} catch (NumberFormatException e) {
			// eat the exception, an error message will be displayed below since choice will be null
		}
		if (choice == null) {
			out.println(System.lineSeparator() + "*** " + userInput + " is not a valid option ***" + System.lineSeparator());
		}
		return choice;
	}
	/*The method displayMenuOptions takes an array of objects as an
	argument and displays the options to the user. It loops through
	the options array and prints each option with a number
	preceding it. It then prompts the user to make a choice.
	 */
	private void displayMenuOptions(Object[] options) {
		out.println();
		for (int i = 0; i < options.length; i++) {
			int optionNum = i+1;
			out.println(optionNum + ") " + options[i]);
		}
		out.println("**************************************");
		out.print(System.lineSeparator() + "Please choose an option >>> ");
		out.flush();
	}
	public double feedMoney(){
		System.out.println("\r\nInsert whole dollar amounts. Does not Accept $50 or $100 bills.");
		Scanner userFeed = new Scanner(System.in);
		String feed = userFeed.nextLine();
		feedBalance(Integer.parseInt(feed));

		System.out.printf("\r\nBalance is now: $ %.2f" + System.lineSeparator(), getBalance());
		return Double.parseDouble(feed);

	}
	/* adds the specified amount to the user's balance
    if it is a valid whole dollar amount.
    It also has several other methods which allow the user to make
    a purchase, view their current balance, and view their
    transaction history.
     */
	public double feedBalance(int feed){
		//only want to intake amounts of 1, 2, 5, 10, 20
		if(feed == 1 || feed == 2 || feed == 5 || feed == 10||feed == 20){
			System.out.println("\r\nAdding $" + feed + " to your balance.");
			//adjusts balance and adjusted to use in future calculations
			this.balance = getBalance() + feed;
			this.prevBalance = balance;
			//sets unaltered to balance for future calculations
			this.nonpreBalance = getBalance();
			if(getNonpreBalance()==0){
				//if balance is 0 -> unaltered is also 0
				this.nonpreBalance = 0;
			}else{
				//is "a step behind" so if getBalance(0) + feed(5) = getBalance(5), then unaltered = 0
				this.nonpreBalance = getBalance() - feed;
			}
		}else {
			System.out.println("\r\nSorry, this machine only accepts whole dollar amounts in the form of $1, $2, $5, $10 and $20. Please try again.\r\n");
			//catches if dollar amount cannot be accepted and keeps balances unaffected
			this.balance = getBalance();
			this.nonpreBalance = getBalance();
		}

		return this.balance;
	}
	/*calculates the number of quarters, dimes, and nickels needed
    to return the user's balance to them and displays the amounts to
    the user. It also resets the user's balance to 0.
     */
	public double returnChange() {
		//statement to calculate quarters, nickles and dimes
		//define coin amounts
		double quarter = 0.25;
		double nickle = 0.05;
		double dime = 0.10;

		//round balance down
		double modQuarters = ((double) (int) Math.round((prevBalance % quarter) * 100) / 100.0);
		double modDimes = ((double) (int) Math.round((modQuarters % dime) * 100) / 100.0);
		double modNickles = ((double) (int) Math.round((modQuarters) % nickle) * 100) / 100.0;

		//count coins up
		int numQuarters = (int) ((prevBalance - modQuarters) / (quarter));
		int numDimes = (int) ((modQuarters - modDimes) / (dime));
		int numNickles = (int) ((modDimes - modNickles) / (nickle));

		System.out.println("\r\nCalculating your change, please wait...");
		//display amounts to user
		System.out.printf(System.lineSeparator() + "$ %.2f returning to you in the form of: " + numQuarters + " quarters, " + numDimes + " dimes, and " + numNickles + " nickles." + System.lineSeparator(), getprevBalance());
		//reset balance
		this.prevBalance = 0.00;
		this.balance = 0.00;

		return this.balance;
	}
	public double removeBalance(double cost) {

		if (cost == 0) {
			//if nothing is bought then balance remains the same. Needed for sold out inventory/or item not found
			this.prevBalance = getBalance();
			this.nonpreBalance = getBalance();
		} else {
			//removes cost from balance
			this.prevBalance = getBalance() - cost;
			this.balance = prevBalance;
			//adds cost to unaltered, keeping it "a step behind"
			this.nonpreBalance = getBalance() + cost;
		}

		return getprevBalance();


	}
	private double getprevBalance() {
		return balance;
	}

	public double getPrevBalance() {
		return prevBalance;
	}

	public double getNonpreBalance() {
		return nonpreBalance;
	}
	public double getBalance() {
		return balance;
	}
}
