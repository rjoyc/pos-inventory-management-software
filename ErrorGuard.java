import java.util.ArrayList; // Import ArrayList utility for arraylists
import java.util.Scanner; // Import scanner class for user input

public class ErrorGuard {
  
  /* ********************************************************************
   * Author: Rachel Joy Copreros   Date: December 07, 2020
   * ErrorGuard.java
   * Description: Class with methods to check for valid user input and
   * general methods to verify items or find indexes.
   * ******************************************************************** */
  
  // Declare class variables
  int value;
  double dVal;
  
  ArrayList<Product> cart = new ArrayList<Product>(); // Instantiate ArrayList of Product object type called cart
  ArrayList<Customer> customers = new ArrayList<Customer>(); // Instantiate ArrayList of Customer object type called customers
  ArrayList<Product> inventory = new ArrayList<Product>(); // Instantiate ArrayList of Product object type called inventory
  ArrayList<Integer> backorderData = new ArrayList<Integer>(); // Instantiate Integer ArrayList called backorderData
  
  Scanner input = new Scanner(System.in); // Instatiate new Scanner called input for user input
  
  public boolean verify (ArrayList list, int code) { // Open verify()
    
  /* ********************************************************************
   * Author: Rachel Joy Copreros   Date: December 07, 2020
   * Description: Verifies that a given value exists within the software's 
   * database.
   * @param: generic ArrayList, method checks for item in a given arraylist
   * @param: int code, the value that needs to be verified
   * @return: boolean exists
   * ******************************************************************** */
    
    // Declare and initialize method boolean variable exists to false
    Boolean exists = false;
    
    if (list.get(0) instanceof Customer) { // If the first element of the passed list is an instance of Customer class, statements execute
      customers = list; // Assign list to class ArrayList customers
      for (int i = 0; i < customers.size(); i++){ // For loop to execute as many iterations as there are elements in the passed arraylist
        if (code == customers.get(i).getID()){ // If the passed value is equal to the value returned by getID() of the element at index i of customers arraylist, statements execute
          exists = true; // Set exists to true
          break; // Break out of for loop
        } // Close if statement
      } // Close for loop
    } else { // If the conditions above are not satisfied, list is an instance of Product Class and statements execute
      inventory = list; // Assign list to class ArrayList inventory
      for (int i = 0; i < inventory.size(); i++){ // For loop to execute as many iterations as there are elements in the passed arraylist
        if (code == inventory.get(i).getPNum()){ // If the passed value is equal to the product number of the element at index i of inventory arraylist, statements execute
          exists = true; // Set exists to true
        } // Close if statement
      } // Close for loop
    } // Close if statement
    return exists; // Return exists
  } // Close verify()
  
  public int findIndex (ArrayList list, int num) { // Open findIndex()
    
  /* ********************************************************************
   * Author: Rachel Joy Copreros   Date: December 07, 2020
   * Description: Determines the index of a given value in a given arraylist.
   * @param: generic ArrayList, method checks for item in a given arraylist
   * @param: int num, the value which the index needs to be determined
   * @return: int index, the index of the element
   * ******************************************************************** */
    
    int index = 0; // Declare and initialize int variable index to 0
    
    if (list.get(0) instanceof Customer) { // If the first element of the passed list is an instance of Customer class, statements execute
      customers = list; // Assign list to class arraylist customers
      for (int i = 0; i < customers.size(); i++){ // For loop to execute as many iterations as there are elements in customers
        if (customers.get(i).getID() == num) { // If the passed value is equal to the id of customer at index i of customers, statements execute
          index = i; // Assign value of i to index
          break; // Break out of for loop
        } // Close if statement
      } // Close for loop
    } else if (list.get(0) instanceof Product) { // If the first element of the passed list is an instance of Product class, statements execute
      inventory = list; // Assign list to class arraylist inventory
      for (int i = 0; i < inventory.size(); i++){ // For loop to execute as many iterations as there are elements in inventory
        if (inventory.get(i).getPNum() == num) { // If the passed value is equal to the product number of product at index i of inventory, statements execute
          index = i; // Assign value of i to index
          break; // Break out of for loop
        } // Close if statement
      } // Close for loop
    } else if (list.get(0) instanceof Integer) { // If the first element of the passed list is an instance of Integer, statements execute
      backorderData = list; // Assign list to class arraylist backorderData
      for (int i = 0; i < backorderData.size(); i++){ // For loop to execute as many iterations as there are elements in backorderData
        if (backorderData.get(i) == num) { // If the passed value is equal to the element at index i of backorderData, statements execute
          index = i; // Assign value of i to index
          break; // Break out of for loop
        } // Close if statement
      } // Close for loop
    } // Close else if
    return index; // return value of index
  } // Open findIndex()
  
  // Method to check if input is the correct data type (int), and if it is in the given range
  public int range(String msg, int min, int max) { // Open range()
    
  /* ********************************************************************
   * Author: Rachel Joy Copreros   Date: December 07, 2020
   * Description: Continues to prompt user for input until it is valid in 
   * that it meets range conditions.
   * @param: String msg, to tailor the prompts 
   * @param: int min, the minimum value the input can equal
   * @param: int max, the maximum value the input can equal
   * ******************************************************************** */
    
    do { // Do while loop to continue prompting user for input while input is not within range
      while (!input.hasNextInt()) { // While loop to continue asking user for input while input is not an integer
        System.out.println("Invalid input. Please enter an integer option."); // Display a message warning of invalid input
        input.next(); // Call Scanner class next() to return next complete token from scanner
      } // Close while loop
      value = input.nextInt(); // Assign user input to variable value
      if (value < min || value > max) // If value of variable value is less than min or greater than max, statements execute
        System.out.printf("Invalid input. %s. Please input a value from %d - %d.\n", msg, min, max); // Display tailored message warning of invalid input using printf()
      // Do while condition to continue while value of variable value is less than min or greater than max
    } while (value < min || value > max); // While condition to continue loop while value inputted it less than min or greater than max
    return value; // Return value of variable value
  } // Close range()
  
  public int minimum () { // Open minimum()
    
  /* ********************************************************************
   * Author: Rachel Joy Copreros   Date: December 07, 2020
   * Description: Continues to prompt user for input until it is valid in 
   * that it meets minimum conditions.
   * @return: int value, the final valid value
   * ******************************************************************** */
    
    do { // Do while loop to continue promptinf for input while input is less than 0
     while (!input.hasNextInt()) { // While loop to continue asking user for input while input is not an integer
        System.out.println("Invalid input. Enter an integer."); // Display a message warning of invalid input
        input.next(); // Call Scanner class next() to return next complete token from scanner
      } // Close while loop
      value = input.nextInt(); // Assign user input to variable value
      if (value < 0) // If value of variable value is less than 0, statements execute
        System.out.print("Invalid input. Please enter a positive integer value.\n"); // Display a message warning of invalid input
    } while (value < 0); // While condition of do while loop to continue while value of variable value is less than 0
    return value; // Return value variavle
  } // Close minimum()
  
  public double price () { // Open price()
    
  /* ********************************************************************
   * Author: Rachel Joy Copreros   Date: December 07, 2020
   * Description: Continues to prompt user for input until it is valid in 
   * that it is a positive double.
   * @return: double dVal, the final valid value
   * ******************************************************************** */
    
    do { // Open do while loop
      while (!input.hasNextDouble()) { // While loop to continue asking user for input while input is not an integer
        System.out.println("Invalid input. Enter a double value."); // Display a message warning of invalid input
        input.next(); // Call Scanner class next() to return next complete token from scanner
      } // Close while loop
      dVal = input.nextDouble(); // Assign user input to double variable dVal
      if (dVal < 0) // If value of variable value is less than 0, statements execute
        System.out.println("Invalid input. Prices must be positive."); // Display a message warning of invalid input
    } while (dVal < 0); // Do while condition to continue while value of dVal value is less than 0
    return dVal; // Return dVal
  } // Close price()
  
  public boolean whitespace(String str){ // Method to check that String input does not include whitespace
    
  /* ********************************************************************
   * Author: Rachel Joy Copreros   Date: December 07, 2020
   * Description: Continues to prompt user for input until it is valid in 
   * that it is a string value with no whitespace (not more than one word).
   * @return: boolean isSpace, to idicate that String has whitespace
   * ******************************************************************** */
    
    boolean isSpace = false; // Declare boolean variable for checking if there are spaces in String
    int spaces = 0; // Declare and initialize int variable spaces
    for (int i = 0; i < str.length(); i++){ // For loop to execute as many interation as the length of the passed String
      if (Character.isWhitespace(str.charAt(i))) // If isWhitespace() returns true for char at index i of the String, statements execute
      spaces++; // Increment spaces by one
    } // Close for loop
    if (spaces > 0){ // If value of spaces is greater than 0, statements execute
      isSpace = true; // Assign value true to variable isSpace
      System.out.println("Invalid input. Please enter a single entry with no spaces."); // Display message warning of invalid input
    } // Close if statement
    return isSpace; // Return value of variable isSpace
  } // Close whitespace()
  
  // ADD METHODS FOR CHECKING THAT USER ID OR PRODUCT NUM IS 6 DIGITS - i mean the verify already does that but we will see (NO STRING!) 
  // fix this pls!
  public String phone () { // Open phone()
    
   /* ********************************************************************
   * Author: Rachel Joy Copreros   Date: December 07, 2020
   * Description: Continues to prompt user for input until it is valid in 
   * that it is a String that consists of 10 digits.
   * @return: String num, the final valid phone number
   * ******************************************************************** */
    
    // Declare and initialize method variables
    boolean ten = true;
    int digits = 0;
    boolean numbers = true;
    String num;
 
    do { // Open do while loop
      digits = 0; // Reset value of digits to 0 at beginning of do while loop
      num = input.nextLine(); // Assign user input to variable num
      if (num.length() != 10) { // If the length of String num does not equal 10, statement executes
        ten = false; // Set value of ten to false
      } else { // If the conditions above are not satisfied, statements execute
        for (int i = 0; i < num.length(); i ++){ // For loop to execute as many iterations as there are characters in String num
          if (Character.isDigit(num.charAt(i))) // If the character at index i of String num is a digit, statement executes
            digits++; // Increment value of digits up by one
        } // Close for loop
        if (digits == 10){ // If digits equals 10, statements execute
          numbers = true; // Set numbers to true
          break; // Break out of do while loop
        } else { // If the conditions above are not satisfied, statement executes
          numbers = false; // Set numvers to false
        } // Close else statement
      } // Close else statment
      if (!ten || !numbers) // If ten is false or numbers is false, statements execute
        System.out.println("Invalid phone number. Phone Numbers must consist of 10 numbers with no spaces/dashes."); // Display message warning od invalid input
    } while (!ten || !numbers); // While condition of do while to continue execute while ten is false or numbers is false
    return num; // Return value of num
  } // Close phone()
} // Close ErrorGuard class