import java.util.Scanner; // Import Scanner class for user input
import java.util.ArrayList; // Import ArrayList utility for array lists

public class InvManagement { // Open InvManagement Class
  
  /* ********************************************************************
   * Author: Rachel Joy Copreros   Date: December 07, 2020
   * InvManagement.java
   * Description: Class with methods to manipulate and process data for
   * objects of product class and ArrayList of objects, inventory.
   * ******************************************************************** */
  
  ArrayList<Product> inventory = new ArrayList<Product>(); // Instantiate ArrayList of type Product called inventory
  ErrorGuard check = new ErrorGuard(); // Create instance of ErrorGuard class called check
  Scanner input = new Scanner(System.in); // Instantiate new Scanner called input for user inpit
  
  // Declare class variables
  int pNum, qty;
  double price;
  String desc;
  
  public InvManagement (ArrayList<Product> inv) { // Open class constructor
    inventory = inv; // Assign passed ArrayList inv to inventory
  }
  
  public void askPN (String msg) { // Open askPN()
    
  /* ********************************************************************
   * Author: Rachel Joy Copreros   Date: December 07, 2020
   * Description: Prompts user to input product number to perform a certain
   * option until it is a verified and valid number.
   * @param: String msg, for tailored prompt
   * ******************************************************************** */
    
    do { // Open do while loop
      System.out.println("Enter the Product Number of the product you wish to " + msg + ":"); // Display tailored prompt for user input
      pNum = check.minimum(); // Assign value returned by minimum() of ErrorGuard Class to pNum
      if (!check.verify(inventory, pNum)) // If the value returned by verify() is false, statement executes
        System.out.println("This Product Number does not exist. Please enter a valid Product Number. "); // Diplay message warning of invalid product number    
    } while (!check.verify(inventory, pNum)); // While coniditon to continue looping while verify() returns false for product number
  } // Close askPN()
  
  public int getPN () { // Open getter getPN()
    return pNum; // Return the value of class variable pNum
  } // Close getter getPN()
  
  public void addItem (int pn) { // Open addItem()
    pNum = pn;
    inventory.get(check.findIndex(inventory, pNum)).quantity++; // Increment the quantity of the product at index of the given product number by 1
  } // Close addItem()

  public void addBItem (int id, int pN) { // Open addBItem()
  /* ********************************************************************
   * Author: Rachel Joy Copreros   Date: December 07, 2020
   * Description: Increments quantity on backorder of the backordered item 
   * by one and adds customer id of customer who backordered the item to
   * the product's customer links list when a customer backorders an item.
   * @param: int id, customer id and int pN, product number
   * ******************************************************************** */
    int index = check.findIndex(inventory, pN); // Assign value returned by findIndex() to index
    inventory.get(index).bQty++; // Increment bQty of product at given index by one
    inventory.get(index).addBID(id); // Add customer id to customer links list of product at given index
  } // Close addBItem()
  
  public void removeBItem (int id, int pN) { // Open removeBItem()
  /* ********************************************************************
   * Author: Rachel Joy Copreros   Date: December 07, 2020
   * Description: Decrease quantity on backorder of the backordered item 
   * by one and remove customer id of customer who backordered the item from
   * the product's customer links list when a customer returns.
   * @param: int id, customer id and int pN, product number
   * ******************************************************************** */
    int index = check.findIndex(inventory, pN); // Assign value returned by findIndex() to index - the index of give product number
    inventory.get(index).bQty--; // Decrease quantity on backorder for given product by 1
    inventory.get(index).removeBID(id); // Remove customer id on product's backorder link list
  } // Close removeBItem()
  
  public void addProduct () { // Open addProduct()
    
  /* ********************************************************************
   * Author: Rachel Joy Copreros   Date: December 07, 2020
   * Description: Prompts user to enter required info and adds new 
   * Product object to inventory ArrayList.
   * ******************************************************************** */
    
    System.out.println("Adding a Product to Catalgue"); // Display description of method's functions
    System.out.println("Enter Price of Product: "); // Prompt user to input price
    price = check.price(); // Assign value returned by price() to price
    System.out.println("Enter Quantity of Product: "); // Prompt user to input quantity
    qty = check.minimum(); // Assign value returned by minimum() to qty
    do { // Open do while loop
      System.out.println("Enter the Name of Product (For descriptions longer than one word, use '_' instead of space): "); // Prompt user for description of product
      desc = input.nextLine(); // Assign user input to variable desc
    } while (check.whitespace(desc)); // While condition of do while to continue while whitespace() returns true as desc is passed
    pNum = inventory.get(inventory.size() - 1).getPNum() + 1; // Simulate generating new product number by adding 1 to product number of last product on list
    inventory.add(new Product (pNum, price, qty, desc, 0, "")); // Add new Product object to inventory list
    System.out.printf("Product %d Successfully Added.\n", pNum); // Display message stating new product was added
  } // Open addProduct()
  
  public void removeProduct (ArrayList<Customer> customers){ // Open removeProduct()
    
  /* ********************************************************************
   * Author: Rachel Joy Copreros   Date: December 07, 2020
   * Description: Removes the the product num of the product being removed
   * from the backordered items list of each customer listed on the product's
   * customer links list, then removes entire product from inventory arraylist.
   * @param: ArrayList of Customer type
   * ******************************************************************** */
    
    CustManagement cMgt = new CustManagement(customers); // Create instance of CustManagement class called cMgt and pass customers ArrayList
    System.out.println("Removing a Product"); // Display description of method functionality
    askPN("remove"); // Call askPN() and pass String "remove" for tailored prompt
    int index = check.findIndex(inventory, pNum); // Assign value returned by findIndex() to index - the index of the product w/ that product number
    for (int i = 0; i < inventory.get(index).customerLinks.size(); i++){ // For loop to execute as many iteratios as there are elements in the given product's customer links list
      int id = inventory.get(index).customerLinks.get(i); // Assign value (customer id) of element at index i of product's customerLinks list to int id
      cMgt.removeBItems(id, pNum); // Call removeBItems of CustManagemenr class and pass each id and the product num of the product being removed
    } // Close for loop
    inventory.remove(index); // Remove the product at index from inventory arraylist
    System.out.printf("Product %d Successfully Removed.\n", pNum); // Display message stating specific product has been removed
  } // Open removeProduct()
  
  public void removeItem (int pNum) { // Open removeItem()
    if (inventory.get(check.findIndex(inventory, pNum)).quantity > 0) { // If the quantity of the given product is greater than 0, statement executes
      inventory.get(check.findIndex(inventory, pNum)).quantity--; // Decrement quantity in stock of product by 1
    } else // If the conditions above are not satisfied, statement executes
      System.out.println("This product is out of stock. Item cannot be removed."); // Display message stating that the item cannot be removed
  } // Close removeItem()
  
  public void editQty () { // Open editQty()
    System.out.println("Editing # of Items in Inventory"); // Display description of method's function
    askPN("edit # of items"); // Call askPN and pass String for tailored prompt
    System.out.println("Enter the # of Items you would like the set the Quantity to: "); // Prompt user to input the new value of items 
    int newVal = check.minimum(); // Assign value returned by minimum() of ErrorGuard to newVal
    inventory.get(check.findIndex(inventory, pNum)).quantity = newVal; // Set quantity of given product to the value of newVal
    System.out.println("Inventory Successfully Edited."); // Display message stating method is complete
  } // Close editQty()
  
  public String displayProduct (int i) { // Open displayProduct()
    
  /* ********************************************************************
   * Author: Rachel Joy Copreros   Date: December 07, 2020
   * Description: Display the product info of a given product.
   * @param: int i, the index of the product to be displayed
   * @return: String product, holds formatted data for a product.
   * ******************************************************************** */
    
    String product = ""; // Declare and initialize String product
    product += String.format("%-11d ", inventory.get(i).getPNum()); // Add formatted product number of product at index i to product String
    product += String.format("%-7.2f ", inventory.get(i).getPrice()); // Add formatted price of product at index i to product String
    product += String.format("%-4d ", inventory.get(i).quantity); // Add formatted quantity of product at index i to product String
    product += String.format("%-27s ", inventory.get(i).getDescr().toUpperCase()); // Add formatted description of product at index i to product String
    product += String.format("%-6d ", inventory.get(i).bQty); // Add formatted quantity on backorder of product at index i to product String
    product += inventory.get(i).displayBID() + "\n"; // Add customer ids of customers who backordered the product at index i to product String
    return product; // Return product
  } // Close displayProduct()
  
  public String toString(int displayInProgram) {
    
  /* ********************************************************************
   * Author: Rachel Joy Copreros   Date: December 07, 2020
   * Description: Method to overload toString() override when the catalogue
   * is being displayed within the program in the interactions pane.
   * @param: int displayInProgram, used to overload toString() override
   * @return: String catalogue, holds all formatted data products in database.
   * ******************************************************************** */
    
    String catalogue = ""; // Declare and initialize String called catalogue
    catalogue += String.format("%-11s %-7s %-4s %-27s %-6s %s\n", "ProductNum", "Price", "Qty", "Desc", "QtyBO", "CustomerBOLinks"); // Add formatted headings to catalogue
    for (int i = 0; i < inventory.size(); i++){ // For loop to execute as many iterations as there are items in the inventory
      catalogue += String.format("%-11d ", inventory.get(i).getPNum()); // Add formatted product number of product at index i to String catalogue
      catalogue += String.format("%-7.2f ", inventory.get(i).getPrice()); // Add formatted price of product at index i to String catalogue
      catalogue += String.format("%-4d ", inventory.get(i).quantity); // Add formatted quantity of product at index i to String catalogue
      catalogue += String.format("%-27s ", inventory.get(i).getDescr().toUpperCase()); // Add formatted description of product at index i to String catalogue
      catalogue += String.format("%-6d ", inventory.get(i).bQty); // Add formatted quantity on backorder of product at index i to String catalogue
      catalogue += inventory.get(i).displayBID() + "\n"; // Add customer ids of customer who have backordered product at index i to String catalogue
    } // Close for loop
    return catalogue; // Return catalogue
  } // Close toString(int)
  
  @Override // Notation to tell Java this method it meant to override default toString()
  public String toString() { // Open toString()
    
  /* ********************************************************************
   * Author: Rachel Joy Copreros   Date: December 07, 2020
   * Description: Method to return formatted string of entire catalogue
   * with no headings.
   * @return: String catalogue, holds all formatted data products in database.
   * ******************************************************************** */
    
    String catalogue = ""; // Declare and initialize String variable catalogue
    for (int i = 0; i < inventory.size(); i++){ // For loop to execute as many iterations as there are items in the inventory
      catalogue += String.format("%-11d ", inventory.get(i).getPNum()); // Add formatted product number of product at index i to String catalogue
      catalogue += String.format("%-7.2f ", inventory.get(i).getPrice()); // Add formatted price of product at index i to String catalogue
      catalogue += String.format("%-4d ", inventory.get(i).quantity); // Add formatted quantity of product at index i to String catalogue
      catalogue += String.format("%-27s ", inventory.get(i).getDescr().toUpperCase()); // Add formatted description of product at index i to String catalogue
      catalogue += String.format("%-6d ", inventory.get(i).bQty); // Add formatted quantity on backorder of product at index i to String catalogue
      catalogue += inventory.get(i).displayBID() + "\n"; // Add customer ids of customer who have backordered product at index i to String catalogue
    } // Close for loop
    return catalogue; // Return catalogue
  } // Close toString()
} // Close InvManagement Class