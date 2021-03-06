import java.util.Scanner; // Import Scanner class for user input
import java.util.ArrayList; // Import ArrayList utility for array lists

public class CustManagement { // Open class
  
  /* ********************************************************************
   * Author: Rachel Joy Copreros   Date: December 07, 2020
   * CustManagement.java
   * Description: Class with methods to manipulate and process data for
   * objects of Customer class and ArrayList of objects, customers.
   * ******************************************************************** */
  
  ArrayList<Customer> customers = new ArrayList<Customer>(); // Instantiate new ArrayList of type Customer object called customers (for holding customers list)
  Scanner input = new Scanner(System.in); // Instantiate new Scanner for user input called input
  ErrorGuard check = new ErrorGuard(); // Create instance of ErrorGuard class for checking for invalid input and general methods called check
  
  // Declare and initialize private class variables
  private String firstName, lastName, phone;
  private int cID;
  
  public CustManagement (ArrayList<Customer> cust) { // Class constructor which takes ArrayList of type Customer object as parameter
    customers = cust; // Assign passed ArrayList to class ArrayList customers
  } // Close constructor
  
  public void askID (String msg) { // Open askID method
    
  /* ********************************************************************
   * Author: Rachel Joy Copreros   Date: December 07, 2020
   * Prompts user to enter Customer ID until it is valid input.
   * @param: String msg, for tailored prompts
   * ******************************************************************** */
    
    do { // Do while loop executes following statements before checking while condition
      System.out.println("Enter the Customer ID of the customer you wish to " + msg ); // Prompt using to enter Customer ID
      cID = check.minimum(); // Assign value returned by minimum() of ErrorGuard class to class variable cID
      if (!check.verify(customers, cID)) // If the value returned by verify() of ErrorGuard class is false, statements execute
        System.out.println("This Customer ID does not exist. Please enter a valid ID. "); // Display warning message of invalid input
    } while (!check.verify(customers, cID)); // While condition of do while loop which continues iteration while value returned by verify() is false
  } // Close askID()
  
  public void addCustomer () { // Open addCustomer()
    
  /* ********************************************************************
   * Author: Rachel Joy Copreros   Date: December 07, 2020
   * Prompts user to enter data needed to add new customer to database and
   * adds new customer object to customers arraylist.
   * ******************************************************************** */
    
    System.out.println("Adding New Customer"); // Display message informing user of method's purpose
    do { // Do while loop executes following statements before checking while condition
      System.out.println("Enter First Name: "); // Prompt user to input first name
      firstName = input.nextLine(); // Assign String value input to variable firstName
    } while (check.whitespace(firstName)); // While condition continues do while loop while whitespace() of ErrorGuard class returns true as variable is passed
    do { // Do while loop executes following statements before checking while condition
      System.out.println("Enter Last Name: "); // Prompt user to input last name
      lastName = input.nextLine(); // Assign String value input to variable lastName
    } while (check.whitespace(lastName)); // While condition continues do while loop while whitespace() of ErrorGuard class returns true as variable is passed
    System.out.println("Enter Phone Number: "); // Prompts user to enter phone numbers
    phone = check.phone(); // Assigns value returned by phone() of ErrorGuard class to variable phone
    cID = customers.get(customers.size() - 1).getID() + 1; // Assigns value of last customer's ID plus one to variable cID to simulate generating a new unique customer id
    customers.add(new Customer (cID, firstName, lastName, phone, "")); // Uses ArrayList to add new Customer to Array of objects, customers
    System.out.printf("Welcome, %s %s! ", firstName, lastName ); // Display Welcome message to new customer with full name using printf()
    System.out.printf("Your Generated Customer ID: %d\n",  cID); // Display customer's new generated customer id using printf()
    System.out.println("Customer Successfully Added."); // Display message stating customer was added to database
  } // Close addCustomer() method
  
  public void removeCustomer (ArrayList<Product> inventory) { // Open removeCustomer()
    
  /* ********************************************************************
   * Author: Rachel Joy Copreros   Date: December 07, 2020
   * Prompts user to enter data needed to remove a customer from database
   * and removes customer's ID from inventory of backordered items and 
   * removes entire customer from customers arraylist.
   * @param: ArrayList of type Product (the inventory ArrayList) - to
   * process the removal of customer ID's in inventory database.
   * ******************************************************************** */
    
    InvManagement iMgt = new InvManagement(inventory); // Create instance of InvManagement class and pass inventory ArrayList to class constructor
    System.out.println("Removing a Customer"); // Display message describing action being performed - possibly remove this?
    askID("remove."); // Call askID() and pass String for tailored prompt
    int index = check.findIndex(customers, cID); // Assign value returned by findIndex of ErrorGuard class - when customers and ciD are passed - to variable int index
    for (int i = 0; i < customers.get(index).bOrders.size(); i++){ // Open for loop which executes as many iterations as there are elements in the given customer's backorders list
      int pNum = customers.get(index).bOrders.get(i); // Assign value of element at index i of bOrders ArrayList for given customer to int variable pNum
      iMgt.removeBItem(cID, pNum); // Call removeBItem() of InvManagement class and pass customer ID and product number of backordered item
    } // Close for loop
    System.out.printf("Customer %d, %s", cID, customers.get(index).getName()); // Use printf() to display the customer ID and full name of customer that will be removed
    customers.remove(index); // Use ArrayList utility to remove customer at given index from array of objects, customers
    System.out.println(" Successfully Removed."); // Display that the customer has been removed
  } // Close removeCustomer()
  
  public void addBItems (int id, int bPNum) { // Open addBItems()
    
  /* ********************************************************************
   * Author: Rachel Joy Copreros   Date: December 07, 2020
   * Calls addB() of Customer class for given Customer object and passes
   * product number of backordered item to add the product number of an item
   * that a customer has backordered to the customer's data.
   * @param: int id, to find index of given customer and int bPNum, to pass
   * to addB() method
   * ******************************************************************** */
    customers.get(check.findIndex(customers, id)).addB(bPNum); // Calls addB() for customer of passed ID
  } // Close addBItems()
  
  public void removeBItems (int id, int bPNum) {
  /* ********************************************************************
   * Author: Rachel Joy Copreros   Date: December 07, 2020
   * Calls removeB() for given Customer object and passes
   * product number of backordered item to remove the product number of an
   * item that a customer has backordered from the customer's data.
   * @param: int id, to find index of given customer and int bPNum, to pass
   * to removeB() method
   * ******************************************************************** */
    customers.get(check.findIndex(customers, id)).removeB(bPNum); // Calls removeB() for customer of passed ID
  } // Close removeBItems()
  
  public void displayBackorders (ArrayList<Product> inventory) { // Open displayBackorders()
  /* ********************************************************************
   * Author: Rachel Joy Copreros   Date: December 07, 2020
   * Displays the product info of backordered items for a given customer.
   * @param: ArrayList of type Product object, to access inventorial data
   * while processing backorder information.
   * ******************************************************************** */
    
    InvManagement iMgt = new InvManagement(inventory); // Create new instance of InvManagement class called iMgt and pass inventory ArrayList
    askID("display backordered items."); // Call askID() and pass String for tailored prompt when requresting iD
    int index = check.findIndex(customers, cID); // Assign value returned by findIndex() - index of customer object in arraylist - of ErrorGuard class to method variable index
    System.out.println("Backordered Items for Customer " + cID + ", " + customers.get(index).getName() + ":"); // Display ID and full name of customer
    String backorders  = customers.get(index).displayBO(); // Declare new String called backorders to hold value returned by displayBO() of given customer object
    if (backorders.isEmpty()) // If the String backorders is empty, customer has no backorders, statements execute
      System.out.println("This customer has no backordered items."); // Display messagfe informing user
    else { // If the conditions are not satisfied above, statements exceute
      int numOfItems = backorders.length()/7; // Declare new int variable called numOfItems and assign value of length of backorders String divided by 7
      int first = 0; // Decalre and initialize int variable first as 0 for substring() of wrapper class
      int last = 6; // Decalre and initialize int variable last as 6 for substring() of wrapper class
      System.out.printf("%-11s %-7s %-4s %-27s %-6s %s\n", "ProductNum", "Price", "Qty", "Desc", "QtyBO", "CustomerBOLinks"); // Use printf) to display headers when displaying backorder information
      for (int i = 0; i < numOfItems; i++){ // For loop to execute as many iterations as there are backordered items in customer's info
        int backPNum = Integer.parseInt(backorders.substring(first, last)); // Use wrapper class to parse substring into Integer type and assign to variable backPNum
        int invIndex = check.findIndex(inventory, backPNum); // Assign value returned by findIndex() to invIndex - index of product with given backordered product number
        System.out.print(iMgt.displayProduct(invIndex)); // Print value returned by displayProduct() of InvManagement class
        first += 7; // Increment variable first by 7
        last += 7; // Increment variable last by 7
      } // Close for loop
    } // Close else statement
  } // Close displayBackorders()
  
  @Override // Notation to tell Java Compiler that this method is meant to override
  public String toString () { // Open toString() override
    
  /* ********************************************************************
   * Author: Rachel Joy Copreros   Date: December 07, 2020
   * Returns string of collective customer data with each attribute of
   * each customer object in customers arraylist
   * @return: String customerData, holds all formatted customer data.
   * ******************************************************************** */
    
    String customerData = ""; // Decalre and initialize string variable customerData
    for (int i = 0; i < customers.size(); i++){ // For loop to execute as many iterations as there are customer objects in customers list
      customerData += String.format("%-7d", customers.get(i).getID()); // Add formatted id of customer at index i to customerData String
      customerData += String.format("%-11s", customers.get(i).getFN().toUpperCase()); // Add formatted firstName of customer at index i to customerData String
      customerData += String.format("%-11s", customers.get(i).getLN().toUpperCase()); // Add formatted lastName of customer at index i to customerData String
      customerData += String.format("%-11s", customers.get(i).getPhone()); // Add formatted phone of customer at index i to customerData String
      customerData += customers.get(i).displayBO() + "\n"; // Add value returned by displayBO() of customer at index i to customerData String
    } // Close for loop
    return customerData; // Return customerData
  } // Close toString()
} // Close CustManagement Class