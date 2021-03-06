import java.util.ArrayList; // Import ArrayList utility for ArrayLists
import java.util.Scanner; // Import Scanner class for hasNext()

public class Customer { // Open Customer Class
  
  /* ********************************************************************
   * Author: Rachel Joy Copreros   Date: December 07, 2020
   * Customer.java
   * Description: Class that acts as a container for customer information
   * with accessor methods for processing and manipuating customer object attributes.
   * ******************************************************************** */
  
  // Declare class variables
  private String firstName, lastName, phone;
  private int id;
  
  ErrorGuard check = new ErrorGuard(); // Create instance of ErrorGuard class called check for general methods
  ArrayList<Integer> bOrders = new ArrayList<Integer>(); // Instantiate ArrayList of Integer type called bOrders - to hold product numbers of items customer has backordered

  public Customer (int cID, String fn, String ln, String pNum, String bItm) { // Open class constructor
    
    id = cID; // Assign value of cID to id, customer ID
    firstName = fn; // Assign value of fn to firstName, first name of customer
    lastName = ln; // Assign value of ln to lastName, last name of customer
    phone = pNum; // Assign value of pNum to phone, customer's phone number
    
    Scanner backorders = new Scanner (bItm); // Create new scanner called backorders and pass String bItm
    while (backorders.hasNext()){ // While backorders scanner has next token, statements continue to execute
      Integer bItem = new Integer(backorders.next()); // Use wrapper class to create integer object called bItem of each nect token in backorders scanner
      bOrders.add(bItem); // Add bItem to class ArrayList bOrders
    } // Close while loop
  } // Close class constructor
  
  // Getter methods for private class varibles
  public int getID () { return id; }
  public String getFN () { return firstName; }
  public String getLN () { return lastName; }
  public String getPhone () { return phone;}
  
  public String getName () { // Getter method to return full name of given customer
    String fullname = firstName + " " + lastName; // Declare string variable and assign String value of first name plus last name with space
    return fullname; // Return value of fullname
  } // Close accessor method getName()
  
  public void addB (int pNum) { // Open addB()
    bOrders.add(pNum); // Add passed product number to customer's backordered items list
  } // Close addB()
  
  public void removeB (int pNum) { // Open removeB()
    bOrders.remove(check.findIndex(bOrders, pNum)); // Remove the product number passed from the customer's backordered items list
  } // Close removeB()
  
  public String displayBO () { // Open displayBO()
    
  /* ********************************************************************
   * Author: Rachel Joy Copreros   Date: December 07, 2020
   * Sorts customer's bOrders list from least to greatest using bubble
   * sort algorithim and returns the sorted list.
   * @return: String items, a string of the product numbers of the customer's
   * backordered items.
   * ******************************************************************** */
    
    // SORTING THE ARRAYLIST
    int temp = 0; // Declare and initialize temp int variable to equal 0
    for (int i = 0; i < (bOrders.size() - 1); i++) { // Outer for loop with condition size of array minus one
      for (int j = 1; j < (bOrders.size() - i); j++) { // Inner for loop to continue to iterate while j is less than the length of bOrders minus the value of i
        if (bOrders.get(j - 1) > bOrders.get(j)) { // If the the element preceding index j is greater than the element at index j, statements execute
          temp = bOrders.get(j - 1); // Assign value of element at preceding index to variable temp
          bOrders.set((j - 1), bOrders.get(j)); // Set the value of the element at the preceding index to the value of the element at index j
          bOrders.set(j, temp); // Set the value of the element at index j to the value of temp
        } // Close if statement
      } // Close inner for loop
    } // Close outer for loop
    
    // DISPLAYING THE ARRAYLIST
    String items = ""; // Declare and initialize String variable items to hold the product numbers of backordered items
    for (int i = 0; i < bOrders.size(); i++){ // For loop to execute as many iterations as the are elements in bOrders
      items += String.valueOf(bOrders.get(i)) + " "; // Add the String value of the elment at index i of bOrders with space to items
    } // Close for loop
    return items; // Return items
  } // Close displayBO
} // Close Customer class