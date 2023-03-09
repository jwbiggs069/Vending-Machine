package com.techelevator;

import com.techelevator.view.Menu;

public class VendingMachineCLI {
	/*has a main method that creates instances of the Menu and
	PurchaseMenu classes, as well as the Transaction and Inventory
	classes. It also creates an instance of itself,
	VendingMachineCLI.*/
	public static void main (String[]args){
		Menu menu = new Menu(System.in, System.out);
		Transaction transaction = new Transaction();
		Inventory inventory = new Inventory();
		VendingMachineCLI cli = new VendingMachineCLI(menu, inventory, transaction);
		cli.run();
	}

	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
	private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
	private static final String MAIN_MENU_EXIT = "Exit";
	private static final String[] MAIN_MENU_OPTIONS = {MAIN_MENU_OPTION_DISPLAY_ITEMS,
			MAIN_MENU_OPTION_PURCHASE, MAIN_MENU_EXIT};

	//added purchase menu options
	private static final String PURCHASE_MENU_FEED_MONEY = "Feed Money";
	private static final String PURCHASE_MENU_SELECT_PRODUCT = "Select Product";
	private static final String PURCHASE_MENU_FINISH_TRANSACTION = "Finish Transaction";
	private static final String[] PURCHASE_MENU_OPTIONS = {PURCHASE_MENU_FEED_MONEY,
			PURCHASE_MENU_SELECT_PRODUCT, PURCHASE_MENU_FINISH_TRANSACTION};

	//CLI Variables
	private Menu menu;

	private Transaction transaction;


	public VendingMachineCLI(Menu menu, Inventory inventory,  Transaction transaction) {
		this.inventory = inventory;
		this.menu = menu;
		this.transaction = transaction;

	}

	private Inventory inventory = new Inventory();


	public void run() {
		inventory.InventoryStock();
		// add load or restock method

		while (true) {
			String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);

			if (choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {
				inventory.displayInventory();
				System.out.println(System.lineSeparator());
				// display vending machine items (Currently returning gibberish)
			} else if (choice.equals(MAIN_MENU_OPTION_PURCHASE)) {
				while (true) {
					String nextChoice = (String) menu.getChoiceFromOptions(PURCHASE_MENU_OPTIONS);

					/*If the user selects the "Feed Money" option,
					the program updates the balance and logs the
					transaction.*/
					if (nextChoice.equals(PURCHASE_MENU_FEED_MONEY)) {
						menu.feedMoney();
						// feed money method, updates balance, logs
						VMLog.logTransactions("FEED MONEY: $" +menu.getNonpreBalance() +" $"+ menu.getPrevBalance());

						/*f the user selects the "Select Product" option, the program
						gets the user's input for which product they want to purchase
						and dispenses the product if it is available.
						 */
					} else if (nextChoice.equals(PURCHASE_MENU_SELECT_PRODUCT)) {
						//display products
						inventory.displayInventory();
						//allow input of which 'key' to buy
						transaction.transaction();

						if (!inventory.getSnacks().containsKey(transaction.getTempkey())) {

							//catches if key does not exist and returns to menu
							System.out.println("Item UNAVAILABLE, returning to purchase menu: ");
						} else if((inventory.getSnacks().get(transaction.getTempkey()).getQty() == 0)){
							//catches if that item is sold out and informs customer, returning to menu
							System.out.println("\r\n******* SOLD OUT *******: ");
						}else{
							//dispenses item, adjusts inventory and balances, then logs
							System.out.println("\r\nItem purchased, dispensing " + inventory.getSnacks().get(transaction.getTempkey()).getName() + ": " + inventory.getSnacks().get(transaction.getTempkey()).getMessage() + System.lineSeparator());
							menu.removeBalance(inventory.getSnacks().get(transaction.getTempkey()).getPrice());
							inventory.getSnacks().get(transaction.getTempkey()).getInventoryTakeAway(true);
							VMLog.logTransactions(inventory.getSnacks().get(transaction.getTempkey()).getName() + " " + inventory.getSnacks().get(transaction.getTempkey()).getSnackType() + " $" + menu.getNonpreBalance() + " $" + menu.getPrevBalance());
						}
						/*If the user selects the "Finish Transaction"
						option, the program returns the user's
						remaining balance and logs the transaction.
						 */
						} else if (nextChoice.equals(PURCHASE_MENU_FINISH_TRANSACTION)) {
							menu.returnChange();
							VMLog.logTransactions("CHANGE RETURNED: $" + menu.getNonpreBalance() + "$" + menu.getBalance());
							break;
						/*If the user selects the "Exit" option
						from the main menu, the program terminates.
						 */
					} else if (choice.equals(MAIN_MENU_EXIT)) {
							System.out.println("Thanks, ENJOY.");
							System.exit(0);
					} else if (choice.equals(MAIN_MENU_EXIT)) {
						System.out.println("Thanks, ENJOY.");
					}
				}

			}
		}
	}
}