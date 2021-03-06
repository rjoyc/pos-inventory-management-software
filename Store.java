import java.util.Scanner; // Import Scanner class to read information from files
import java.io.File; // Import file utility to open and work with files on disk
import java.io.FileNotFoundException; // Import to manage exceptions regarding files not found
import java.io.PrintWriter; // Import utility to write to a file
import java.util.ArrayList; // Import ArrayList utility for array lists

public class Store { // Open Store class
  
  /* ********************************************************************
   * Author: Rachel Joy Copreros   Date: December 07, 2020
   * Store.java
   * Description: Class which reads, writes, and appends to a store catalogue
   * and a customer data file. The class calls various methods of different
   * classes to process file and inputted data with success.
   * ******************************************************************** */
  
  public static void main (String [] args) { // Open main() method
    
    ArrayList<Product> inventory = new ArrayList<Product>(); // Instantiate ArrayList of Product type called inventory
    ArrayList<Product> cart = new ArrayList<Product>(); // Instantiate ArrayList of Product type called cart
    ArrayList<Customer> customers = new ArrayList<Customer>(); // Instantiate ArrayList of Product type called customers
    ErrorGuard check = new ErrorGuard(); // Create instance of ErrorGuard class called check
    
    // Declare and initialize class variables
    int choice;
    int menuChoice;
    
    try { // Start of try catch to throw an exception and display error message if file is not found
      
      /********** READING FROM FIRST FILE (CATALOGUE) **********/
      // Instantiate new file object that points to file named StoreCatalogue.txt
      File catalogue = new File ("StoreCatalogue.txt"); 
      // Instatiate new Scanner object called scanner1 and pass file at catalogue address
      Scanner scanner1 = new Scanner (catalogue);
      while (scanner1.hasNext()) { // While loop to continue executing code block while scanner1 has next token (reads up to space)
        inventory.add(new Product (scanner1.nextInt(), scanner1.nextDouble(), scanner1.nextInt(), scanner1.next(), scanner1.nextInt(), scanner1.nextLine()));
      } // Close while loop
      scanner1.close(); // Close first scanner
      
      /********** READING FROM SECOND FILE (CUSTOMER DATA) **********/
      // Instantiate new file object that points to file named CustomerData.txt
      File customerData = new File ("CustomerData.txt");
      // Instatiate new Scanner object called scanner1 and pass file at customerData address
      Scanner scanner2 = new Scanner (customerData); 
      while (scanner2.hasNext()) { // While loop to continue executing code block while scanner2 has next token (reads up to space)
        customers.add(new Customer (scanner2.nextInt(), scanner2.next(), scanner2.next(), scanner2.next(), scanner2.nextLine())); // Add value returned by formatNum() when nextLine of diskScanner is passed to method
      } // Close while loop
      scanner2.close(); // Close second scanner
      
      /********** PROGRAM MENU SYSTEM **********/
      InvManagement iMgt = new InvManagement(inventory); // Create instance of InvManagement class called iMgt and pass inventory arraylist
      CustManagement cMgt = new CustManagement(customers); // Create instance of CustManagement class called cMgt and pass customers arraylist
      POS pos = new POS(cart, inventory, customers); // Create instance of POS class called pos and pass all 3 class arraylists
      
      do { // Open do while loop for Program Menu System
      System.out.println("Consumers Distributing Point of Sales & Inventory Management Software"); // Display title of software
      // Prompt user to select menu choice
      System.out.println("Welcome! What would you like to access today?\n"
                        + "1 - Store Management Menu\n"
                        + "2 - Point of Sales Menu\n"
                        + "3 - Exit");
      menuChoice = check.range("This is not one of the menu options", 1, 3); // Assign the value returned by range() of ErrorGuard class to menuChoice
      switch (menuChoice){ // Open switch statement to evaluate value of menuChoice
        case 1: // If menuChoice equals 1, user selected Store Management Menu
          System.out.println("Store Management Menu"); // Display title of menu
          // Prompt user to select an option
          System.out.println("Choose an option:\n"
                            + "1 - Inventory Management\n"
                            + "2 - Customer Management");
          int smChoice = check.range("This is not one of the menu options", 1, 2); // Assign value returned by range() of ErrorGuard class to smChoice
          switch (smChoice) { // Open switch statement to evaluate value of smChoice
            case 1: // If smChoice equals 1, user selected Inventory Management
              System.out.println("Inventory Management"); // Display title of menu
              // Prompt user to select menu option
              System.out.println("Choose an option:\n"
                               + "1 - Edit Products\n"
                               + "2 - Edit Inventory (Qty)\n"
                               + "3 - Display Catalogue");
              int imChoice = check.range("This is not one of the menu options", 1, 3); // Assign value returned by range() of ErrorGuard class to imChoice
              switch (imChoice) { // Open switch statement to evaluate value of imChoice
                case 1: // If imChoice equals 1, user selected to Edit Products
                  // Prompt user to select option
                  System.out.println("Edit Products\n"
                                   + "1 - Add a Product\n"
                                   + "2 - Remove a Product");
                  int imChoice1 = check.range("This is not one of the menu options", 1, 2); // Assign value returned by range() of ErrorGuard class to imChoice1
                  switch (imChoice1) { // Open switch statement to evaluate value of imChoice1
                    case 1: iMgt.addProduct(); break; // If imChoice1 equals 1, user chose to Add Product, call addProduct() of InvManagement class
                    case 2: iMgt.removeProduct(customers); break; // If imChoice1 equals 2, user chose to Remove Product, call removeProduct() of InvManagement class
                  } // Close switch statement for imChoice1
                  break; // Break out of case 1 of switch statement
                case 2: // If imChoice equals 2, user selected to Edit Inventory(Qty)
                  // Prompt user to select option
                  System.out.println("Edit Inventory (Qty)\n" 
                                   + "1 - Add Item(s)\n"
                                   + "2 - Remove Item(s)\n"
                                   + "3 - Edit # of Items");
                  int imChoice2 = check.range("This is not one of the menu options", 1, 3); // Assign value returned by range() of ErrorGuard class to imChoice2
                  switch (imChoice2) { // Open switch statement to evaluate value of imChoice2
                    case 1: // If imChoice2 equals 1, user chose to add item(s)
                      iMgt.askPN("add item(s) to inventory"); // Call askPN() of iMgt object and pass String for tailored prompt
                      System.out.println("How many items of this product would you like to add? "); // Prompt user to input # of items
                      int numOfItemsAdd = check.minimum(); // Assign value returned by minimum() to numOfItemsAdd
                      for (int i = 0; i < numOfItemsAdd; i++){ // For loop to execute as many iterations as there are items to be added
                        iMgt.addItem(iMgt.getPN()); // Call addItem() of iMgt and pass value returned by getPN() of iMgt
                      } // Close for loop
                      System.out.println("Item(s) Successfully Added to Product " + iMgt.getPN()); // Displaye message stating success of update
                      break; // Break out of case 1 of switch statement
                    case 2: // If imChoice2 equals 2, user chose to remove item(s)
                      iMgt.askPN("remove an item"); // Call askPN() of iMgt object and pass String for tailored prompt
                      System.out.println("How many items of this product would you like to remove? "); // Prompt user to input # of items
                      int numOfItemsRemove = check.minimum(); // Assign value returned by minimum() to numOfItemsRemove
                      for (int i = 0; i < numOfItemsRemove; i++){ // For loop to execute as many iterations as there are items to be removed
                        iMgt.removeItem(iMgt.getPN());  // Call removeItem() of iMgt and pass value returned by getPN() of iMgt
                      } // Close for loop
                      break; // Break out of case 2 of switch statement
                    case 3: iMgt.editQty(); break; // If imChoice2 equals 3, user chose to edit # of item(s), call editQty() of iMgt
                  } // Close switch stateent for imChoice2
                  break; // Breat out of case 2 of switch statement
                case 3: System.out.print(iMgt.toString(1)); break; // If imChoice equals 3, user chose to display catalogue, display value returned by toString(int)
              } // Close switch stateent for imChoice
              break; // Break out of case 1 of switch statement
            case 2: // If smChoice equals 2, user selected Customer Management
              System.out.println("Customer Management"); // Display title of menu
              // Prompt user to select option
              System.out.println("Choose an option:\n"
                               + "1 - Add Customer\n"
                               + "2 - Remove Customer\n"
                               + "3 - Display Backordered Items");
              int cmChoice = check.range("This is not one of the menu options", 1, 3); // Assign value returned by range() of ErrorGuard class to cmChoice
              switch (cmChoice) { // Open switch statement to evaluate cmChoice
                case 1: cMgt.addCustomer(); break; // If cmChoice equals 1, call addCustomer() of cMgt
                case 2: cMgt.removeCustomer(inventory); break; // If cmChoice equals 2, call removeCustomer() of cMgt
                case 3: cMgt.displayBackorders(inventory); break; // If cmChoice equals 3, call displayBackorders() and pass inventory
              } // Close switch statement
              break; // Break out of case 2 of switch statement
          } // Close switch statement for smChoice
          break; // Break out of case 1 of first switch statement
       case 2: // If menuChoice equals 2, user selected Point of Sales Menu
         System.out.println("Point of Sales Menu"); // Display title of menue
         System.out.println("Step 1 - Order Desk"); // Display title of first step
         pos.orderDeskC(); // Call orderDeskC() of pos to collect User/Customer information
         do { // Open do while loop
           pos.orderDeskP(); // Call orderDeskP() of pos to collect Order/Product information
           // Prompt user to select option
           System.out.println("Would you like to order another item?\n"
                         + "1 - Yes\n"
                         + "2 - No");
           choice = check.range("This is not one of the menu options", 1, 2); // Assign value returned by range() of ErrorGuard class to choice
         } while (choice != 2); // Condition to continue do while loop while choice does not equal 2(user does not select no)
         System.out.println("\nStep 2 - Order Pick Display"); // Display title for second step
         pos.displayOrderPick(); // Call displayOrderPick() of pos
         System.out.println("\nStep 3 - Check Out"); // Display title for third step
         pos.checkOut(); // Call checkOut() of pos
      } // Close switch statement for menuChoice
      System.out.println(); // For adding a space before printing welcome title again
      } while (menuChoice != 3); // Condition of do while loop to continue while user does not exit program
      
      /********** WRITING TO FIRST TMP FILE (CATALOGUE) **********/
      // Instatiate a new Printwriter object called writer1 that createS file called StoreCatalogueTmp.txt 
      PrintWriter writer1 = new PrintWriter ("StoreCatalogueTmp.txt"); 
      // Instatiate a new file object that points to file names StoreCatalogueTmp.txt
      File updatedCatalogue = new File ("StoreCatalogueTmp.txt");
      writer1.print(iMgt); // Print toString() of iMgt object to file
      writer1.close(); // Close first writer
      /********** WRITING TO SECOND TMP FILE (CUSTOMER DATA) **********/
      // Instatiate a new Printwriter object called writer2 that createS file called CustomerDataTmp.txt 
      PrintWriter writer2 = new PrintWriter ("CustomerDataTmp.txt"); 
      // Instatiate a new file object that points to file names CustomerDataTmp.txt
      File updatedCustomers = new File ("CustomerDataTmp.txt");
      writer2.print(cMgt); // Print toString() of cMgt object to file
      writer2.close(); // Close print writer
      
      /********** RENAMING TMP FILES **********/
      catalogue.delete(); // Delete the file saved at catalogue, StoreCatalogue.txt
      customerData.delete(); // Delete the file saved at customerData, CustomerData.txt
      
      // If renameTo() returns boolean value true for renaming tmp files to name of old files, statements execute
      if (updatedCatalogue.renameTo(catalogue) && updatedCustomers.renameTo(customerData)){ 
        // Display a message stating that txt files have been updated
        System.out.println("Inventory and Customer Data Updated.");
      } else { // If the value returned by renameTo() is false, statements execute
        System.out.println("Inventory and Customer Data Update Unsuccessful."); // Display message stating that txts were not successfully updated
      } // Close if statement
      
      } catch (FileNotFoundException e){ // Catch statement of try catch to catch exception if file it not found
        System.err.print(e); // Print tailored error message for file not found exception
        System.out.println("\nThis file was not found. Ensure the name of the file is correct. Thank you."); // Display additional message stating error
      } // Close catch statement
  } // Close main() method
} // Close Store class