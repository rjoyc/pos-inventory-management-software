import java.util.ArrayList; // Import ArrayList utility for arraylists
import java.util.Scanner; // Import Scanner class for user input

public class Product { // Open Product class
  
  /* ********************************************************************
   * Author: Rachel Joy Copreros   Date: December 07, 2020
   * Product.java
   * Description: Class that acts as a container for product information
   * with accessor methods for processing and manipuating product object attributes.
   * ******************************************************************** */
  
  // Decalre class variables
  private int productNum;
  private double price;
  private String descr;
  private boolean inStock;
  int quantity, bQty;
  
  ArrayList<Integer> customerLinks = new ArrayList<Integer> (); // Instantiate Integer ArrayList called customerLinks
  ErrorGuard check = new ErrorGuard(); // Create instance of class ErrorGuard called check
  
  public Product (int pNum, double pr, int qty, String des, int bQ, String bC) { // Open class constructor
    productNum = pNum; // Assign value of pNum to productNum
    price = pr; // Assign value ofpr to price
    quantity = qty; // Assign value of qty to quantity
    descr = des; // Assign value of des to descr
    bQty = bQ; // Assign value of bQ to productNum
    
    Scanner customerIDs = new Scanner (bC); // Instantiate new Scanner called customerIDs and pass String bC as argument
    while (customerIDs.hasNext()){ // While customerIDs has next token, loop continues
      Integer bID = new Integer(customerIDs.next()); // Use wrapper class to create new Integer object called bID of each next token in scanner
      customerLinks.add(bID); // Add bID to customerLinks arraylist
    } // Close while loo
  } // Close class constructor
  
  // Getter methods for private class varibles
  public int getPNum() { return productNum; }
  public double getPrice() { return price; }
  public String getDescr() { return descr; }
  
  public int getAvailStock () { // Open getter getAvailStock()
    
  /* ********************************************************************
   * Author: Rachel Joy Copreros   Date: December 07, 2020
   * Description: Returns the available stock of product.
   * @return: integer of available stock (quantity - bQty)
   * ******************************************************************** */
    
    if ((quantity - bQty) < 0) // If the difference of quantity in stock and quantity on backorder is less than 0, statement executes
      return 0; // Return value of 0 to avoid displaying negative value to user
    else // If the conditions above are not satisfied, statement executes
      return quantity - bQty; // Return the difference of quantity in stock and quantity on backorder
  } // Close getter getAvailStock()
  
  public boolean inStock (){ // Open inStock()
    
  /* ********************************************************************
   * Author: Rachel Joy Copreros   Date: December 07, 2020
   * Description: Indicates whether a product is "in stock", meaning its
   * available stock is greater than 0.
   * @return: boolean inStock
   * ******************************************************************** */
    
    if ((quantity - bQty) > 0) // If the available stock is greater than 0, statement executes
      inStock = true; // Set inStock to true
    else // If the conditions above are not satisfied, statement executes
      inStock = false; // Set value of inStock to fase
    return inStock; // Return value of inStock
  } // Close inStock()
  
  public void addBID (int id) { // Open addBID()
    
  /* ********************************************************************
   * Author: Rachel Joy Copreros   Date: December 07, 2020
   * Description: Adds customer id of a customer who backorders an item of
   * the given product to product's customerLinks list.
   * @param: int id, the customer id
   * ******************************************************************** */
    customerLinks.add(id); // Add passed value to customerLinks arraylist
  } // Close addBID()
  
  public void removeBID (int id) { // Open removeBID()
    
  /* ********************************************************************
   * Author: Rachel Joy Copreros   Date: December 07, 2020
   * Description: Removes a given customer id of a customer who backordered an 
   * item of the given product from product's customerLinks list.
   * @param: int id, the customer id
   * ******************************************************************** */
    customerLinks.remove(check.findIndex(customerLinks, id)); // Remove element at index returned by findIndex of customerLinks
  } // Close removeBID()
  
  public String displayBID () { // Open displayBID()
    
  /* ********************************************************************
   * Author: Rachel Joy Copreros   Date: December 07, 2020
   * Sorts products's customerLinks list from least to greatest using bubble
   * sort algorithim and returns the sorted list.
   * @return: String ids, a string of the customer id's of customers who have
   * backordered an item of the given product.
   * ******************************************************************** */
    
    // SORTING THE ARRAYLIST
    int temp = 0; // Declare and initialize temp int variable to equal 0
    for (int i = 0; i < (customerLinks.size() - 1); i++) { // Outer for loop with condition size of array minus one
      for (int j = 1; j < (customerLinks.size() - i); j++) { // Inner for loop to continue to iterate while j is less than the length of bOrders minus the value of i
        if (customerLinks.get(j - 1) > customerLinks.get(j)) { // If the the element preceding index j is greater than the element at index j, statements execute
          temp = customerLinks.get(j - 1); // Assign value of element at preceding index to variable temp
          customerLinks.set((j - 1), customerLinks.get(j)); // Set the value of the element at the preceding index to the value of the element at index j
          customerLinks.set(j, temp); // Set the value of the element at index j to the value of temp
        } // Close if statement
      } // Close inner for loop
    } // Close outer for loop
    
    // DISPLAYING THE ARRAYLIST
    String ids = ""; // Declare and initialize string variable ids
    for (int i = 0; i < customerLinks.size(); i++){ // For loop to execute as many iterations as there are elements in customerLinks arraylist
      ids += String.valueOf(customerLinks.get(i)) + " "; // Add the string value of element at index i of customerLinks with space to ids
    } // Close for loop
    return ids; // Return ids
  } // Close displayBID()
} // Close Product class