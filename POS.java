import java.util.ArrayList; // Import ArrayList utility for arraylists

public class POS {
  
   /* ********************************************************************
   * Author: Rachel Joy Copreros   Date: December 07, 2020
   * POS.java
   * Description: Class with methods to simulate a point of sales software.
   * Includes methods for order desk, order pick display, and check out.
   * ******************************************************************** */
  
  ArrayList<Product> cart = new ArrayList<Product>(); // Instantiate ArrayList type Product called cart (to hold customer's on hand orders)
  ArrayList<Customer> customers = new ArrayList<Customer>(); // Instantiate ArrayList type Customer called customers (to hold customer data)
  ArrayList<Product> inventory = new ArrayList<Product>(); // Instantiate ArrayList type Product called inventory (to hold catalogue info)
  ArrayList<Product> backorders = new ArrayList<Product>(); // Instantiate ArrayList type Product called backorders (to hold customer's back orders)
  ArrayList<Product> backorderPick = new ArrayList<Product>(); // Instantiate ArrayList type Product called backorderPick (to hold backorders customer is picking up)
  ErrorGuard check = new ErrorGuard(); // Create instance of ErrorGuard called check (for error guards and general methods)
  
  // Declare and initialize class variables
  int boQty;
  int id, productNum, choice;
  String fn, ln, phone;
  final double HST = 0.13;
  
  public POS (ArrayList<Product> purchases, ArrayList<Product> inv, ArrayList<Customer> cus) { // Open class constructor
    cart = purchases; // Assign Product ArrayList purchases to cart
    inventory = inv; // Assign Product ArrayList inv to inventory
    customers = cus; // Assign Product ArrayList cus to customers
  } // Close constructor 
  
  public void backorderPickUp (int id) { // Open backorderPickUp()
    
  /* ********************************************************************
   * Author: Rachel Joy Copreros   Date: December 07, 2020
   * Description: Adjusts and updates inventory and customer data when a customer with 
   * backordered items returns and their backordered items are now in stock.
   * @param: int id, customers id
   * ******************************************************************** */
    
    InvManagement iMgt = new InvManagement(inventory); // Create instance of InvManagement class called iMgt
    CustManagement cMgt = new CustManagement(customers); // Create instance of CustManagement class called cMgt

    int index = check.findIndex(customers, id); // Assign value returned by findIndex() to index (index of the customer with that id in customers list)
    if (!(customers.get(index).bOrders.isEmpty())){ // If the customer at this index does not have an empty bOrders arraylist (customer has backordered items), statements execute
      for (int i = (customers.get(index).bOrders.size() - 1); i >= 0; i--) { // For loop to iterate backwards since the size of bOrders array os adjusted within loop
        int pNum = customers.get(index).bOrders.get(i); // Assign the value of the element at index i of the customer's bOrders list to pNum
        int invIndex = check.findIndex(inventory, pNum); // Assign value returned by findIndex() to invIndex (index of backordered item product in inventory)
        if (inventory.get(invIndex).quantity >= 1) { // If the quantity (in stock) of the product is greater than or equal to 1, statements execute
          System.out.print("Customer's Backordered Item is ready for pick up: " + iMgt.displayProduct(invIndex)); // Display product information of the backordered item now ready for pick up
          backorderPick.add(inventory.get(invIndex)); // Add this product to the class backorderPick arraylist for displaying at order pick
          cMgt.removeBItems(id, pNum); // Call removeBItems() of cMgt and pass id and pNum (removing the product number from the customer's data)
          iMgt.removeBItem(id, pNum); // Call removeBItem() of iMgt and pass id and pNum (removing customer id from product's data)
          iMgt.removeItem(pNum); // Call removeItem() of iMgt and pass pNum (to decrease quantity in stock for product)
        } else { // If conditions above are not satisfied, statement executes
          System.out.print("Customer's Backordered Item is not ready for pick up: " + iMgt.displayProduct(invIndex)); // Display product info for backordered item that is not ready for pick up
          continue; // Continue in for loop
        } // Close else statement 
      } // Close for loop
    } // Close if statement
  } // Close backorderPickUp()
  
  public void orderDeskC () { // Open orderDeskC()
    
  /* ********************************************************************
   * Author: Rachel Joy Copreros   Date: December 07, 2020
   * Description: Collects user information (customer data).
   * ******************************************************************** */
    
    CustManagement cMgt = new CustManagement(customers); // Create instance of CustManagement called cMgt and pass class arraylist customers
    System.out.println("Customer Information"); // Display title
    do { // Open do while loop
      System.out.println("Enter Customer ID: (Enter 000000 for New Customers)"); // Prompt user to enter customer id
      id = check.minimum(); // Assign value returned by minimum() of ErrorGuard class to id
      if (check.verify(customers, id)) { // If the value returned by verify() is true, this id exists in the customer database, statements execute
        System.out.println("Welcome Back, " + customers.get(check.findIndex(customers, id)).getName() + "! "); // Display welcome message with full name
        backorderPickUp(id); // Call backorderPickUp and pass the id of the customer - method deals with backordered items
      } // Close if statement
      else { // If the conditions above are not satisfied, statements execute
        if (id == 000000) { // If the value of id is equal to 000000, statements execute
          System.out.println("New customer! Let's add you to our database!"); // Display message ackowleding new customer
          cMgt.addCustomer(); // Call addCustomer method of cMgt class
          id = customers.get(customers.size() - 1).getID(); // Assign the id of the last customer on customers arraylist to id (will be the newly added customer's id)
          break; // Break out of do while loop
        } else // If the conditions above are not satisfied, statement executes
          System.out.println("This ID does not exist. Please enter a valid Customer ID."); // Display message warning of invalid input
      } // Close else statement
    } while (!(check.verify(customers, id))); // Condition of do while loop to continue while verify() returns false when customers and id are passed
  } // Close orderDeskC()
  
  public void orderDeskP () { // Open orderDeskP()
    
  /* ********************************************************************
   * Author: Rachel Joy Copreros   Date: December 07, 2020
   * Description: Collects and prcoesses order information (purchased 
   * product data).
   * ******************************************************************** */
    
    int qty = 0; // Declare and initialize int qty to 0
    InvManagement iMgt = new InvManagement(inventory); // Create instance of InvManagement called iMgt and pass inventory arraylist
    CustManagement cMgt = new CustManagement(customers); // Create instance of CustManagement called cMgt and pass customers arraylist
    
    System.out.println("\nOrder Information"); // Display title for order information
    iMgt.askPN("to purchase"); // Call askPN() of iMgt and pass string for tailored prompt
    productNum = iMgt.getPN(); // Assign value returned by getPN() to productNum
    int index = check.findIndex(inventory, productNum); // Assign value returned by findIndex() to index (the index of the product in inventory with that product number)
    
    if (check.verify(inventory, productNum)) { // If value returned by verify() is true for given arguments, statements execute
      System.out.print("This product exists."); // Display message stating product exists
      if (!(inventory.get(index).inStock())){ // If value of inStock returns false, statements execute (inStock returns true if available stock > 0)
        // Display message stating out of stock and available stock value and prompt user to select option for backordering
        System.out.println(" But it is out of stock. Available Stock: " + inventory.get(index).getAvailStock() + "\nWould you like to backorder this item?\n"
                          + "1 - Yes\n"
                          + "2 - No");
        int bChoice = check.range("This is not one of the menu options", 1, 2); // Assign value returned by range() to bChoice
        switch (bChoice){ // Switch statement to evaluate bChoice
          case 1: // If bChoice equals 1, user chose to backorder
            System.out.println("How many items would you like to backorder? "); // Prompt user to enter number of items to backorder
            boQty = check.minimum(); // Assign value returned by minimum() to boQty (# of items being backordered)
            for (int i = 0; i < boQty; i++){ // For loop to execute as many iterations as there are items to be backordered
              iMgt.addBItem(id, productNum); // Call addBItem() of iMgt and pass id and product num (increase quantity on backorder and add customer id)
              cMgt.addBItems(id, productNum); // Call addBItems() of cMgt and pass if and product num (list product number on customer's bOders list)
              backorders.add(inventory.get(index)); // Add product to backorders "cart" arraylist
            } // Close for loop
            break; // Break out of case 1
          case 2: break; // If bChoice equals 1, user chose not to backorder, nothing further occurs until next method call
        } // Close switch statement
      } else { // If the conditions above are not satisfied, statements execute (product is in stock)
        System.out.println(" It is also in stock. Available Stock: " + inventory.get(index).getAvailStock()); // Display message stating product is in stock, and displays available stock
        System.out.println("How many items of this product would you like? "); // Prompt user to input number of items they would like to purchase
        qty = check.minimum(); // Assign value returned by minimum() to qty
        int avail = inventory.get(index).getAvailStock(); // Assign value returned by getAvailStock() to avail (represents quantity - quantity on backorder)
        if (qty > avail){ // If qty(to be ordered) is greater than the value of avail (quantity in stock - quanity on backorder), statements execute
          boQty = qty - avail; // Set boQty(qty to be backordered) equal to difference of qty and avail
          // Prompt user to select option
          System.out.println("There are only " + inventory.get(index).getAvailStock() + " item(s) available in stock for this product.\n" // Display available stock and remaining unavailable quantity
                           + "Would you like to backorder the other " + boQty + " item(s)?\n"
                           + "1 - Yes\n"
                           + "2 - No");
          int bOption = check.range("This is not one of the menu options", 1, 2); // Assign value returned by range() to bOption
          switch (bOption) { // Open switch statement to evaluate bOption
            case 1: // If bOption equals 1, user chose to backorder remaining items
              qty = qty - boQty; // Set qty equal to qty minus the qty to be backordered (when adding to cart, backorders will be excluded)
              for (int i = 0; i < boQty; i++){ // For loop to execute as many iterations as there are items to be backordered
              iMgt.addBItem(id, productNum); // Call addBItem() of iMgt and pass id and product num 
              cMgt.addBItems(id, productNum); // Call addBItem() of cMgt and pass id and product num 
              backorders.add(inventory.get(index)); // Add product to backorders "cart" to be displayed on order pick
              } // Close for loop
              break; // Break out of case 1
            case 2: qty = qty - boQty; break; // If bOption equals 2, make no other adjustments, but set qty equal to qty minus the qty to be backordered
          } // Close switch statement
        } // Close if statement
        for (int i = 0; i < qty; i++){ // For loop to iterate as many times as there are items to be added to cart
          cart.add(inventory.get(index)); // Add ordered product to cart arraylist
          // might change this its unecceary - working with one product at a time
          int cartIndex = check.findIndex(cart, inventory.get(index).getPNum()); // Assign value returned by findIndex() to cartIndex (index of the product number in the cart)
          iMgt.removeItem(cart.get(cartIndex).getPNum()); // Call removeItem() and pass the product number of the element at cartIndex
        } // Close for loop
        System.out.println("Purchase(s) added to cart."); // Display message stating purchases were added to cart 
      } // Close else statement
    } // Close if statement
  } // Close orderDeskP()

  public void displayOrderPick () { // Open displayOrderPick()
    
  /* ********************************************************************
   * Author: Rachel Joy Copreros   Date: December 07, 2020
   * Description: Displays order pick with customer name, and information
   * of products to pick (including qty in stock and qty on backorder),
   * also shows customer's backorders.
   * ******************************************************************** */
    
    System.out.println("Customer " + id + ", " + customers.get(check.findIndex(customers, id)).getName()); // Display customer id and full name
    InvManagement purchases = new InvManagement(cart); // Create instance of InvManagement class called purchases and pass cart arraylist
    System.out.println("------------------------------ Products to Pick ------------------------------"); // Display header
    System.out.println("Customer Orders"); // Display title
    if (cart.isEmpty()) // If cart arraylist is empty, execute statement
      System.out.println("Customer has no product orders.\n"); // Display message stating customer has not made any orders
    else // If the conditions above are not satisfied, statement executes
      System.out.println(purchases.toString(1)); // Display string returned by toString(int)
    InvManagement backOPick = new InvManagement(backorderPick); // Create instance of InvManagement class called backOPick and pass backorderPick arraylist
    System.out.println("Backordered Items For Pickup"); // Display title
    if (backorderPick.isEmpty()) // If backorderPick arraylist is empty, statement executes 
      System.out.println("Customer has no backordered items to pick up.\n"); // Display message stating customer has no backordered items to pick up
    else // If the conditions above are not satisfied, statement executes
      System.out.println(backOPick.toString(1)); // Display string returned by toString(int)
    InvManagement customerBackorders = new InvManagement(backorders); // Create instance of InvManagement class called customerBackorders and pass backorders arraylist
    System.out.println("--------------------------- Customer's Backorders ----------------------------"); // Display header
    if (backorders.isEmpty()) // If backorders arraylist is empty, statement executes
      System.out.println("Customer has not made any backorders."); // Display message stating customer has not made any backorders
    else // If the conditions above are not satisfied, statement executes
      System.out.println(customerBackorders.toString(1)); // Display string returned by toString(int)
  } // Close displayOrderPick()
  
  public void checkOut () { // Open checkOut()
    
  /* ********************************************************************
   * Author: Rachel Joy Copreros   Date: December 07, 2020
   * Description: Calculates the cost of customer's purchase and reverse
   * and updates/edits to inventory is customer cancels.
   * ******************************************************************** */
    
    InvManagement purchases = new InvManagement(inventory); // Create instance of InvManagement class called purchases and pass inventory
    CustManagement cMgt = new CustManagement(customers); // Create instance of CustManagement class called purchases and pass customers
    double subtotal = 0; // Declare and initialize subtotal variable
    for (int i = 0; i < cart.size(); i++){ // For loop to iterate as many times as there are items in the cart
      subtotal += cart.get(i).getPrice(); // Add value returned by getPrice() for item at index i to subtotal
    } // End for loop
    for (int i = 0; i < backorders.size(); i++){ // For loop to iterate as many times as there are items in backorder arraylist
      subtotal += backorders.get(i).getPrice(); // Add value returned by getPrice() for item at index i to subtotal
    } // End for loop
    double tax = subtotal * HST; // Assign product of subtotal and final variable HST to double variable tax
    double total = subtotal + tax; // Assign sum of subtotal and tax to total
    System.out.printf("Subtotal: $%.2f\nTax: $%.2f\nTotal: $%.2f\n\n", subtotal, tax, total); // Use printf to display cost calculation at checkout
    System.out.println("Confirm purchase?\n" // Prompt user to select option
                     + "1 - Yes, I confirm.\n"
                     + "2 - No, cancel please.");
    choice = check.range("This is not one of the menu options", 1, 3); // Assign value returned by range() to choice
    switch (choice) { // Open swtich statement for evaluating choice
      case 1: // If choice equals 1, user chose to confirm purchase
        System.out.println("Purchase Confirmed."); // Display message stating purchase confirmed
        break; // Break out of case 1
      case 2: // If choice equals 2, user chose to cancel purchase
        for (int i = 0; i < backorders.size(); i++) { // For loop to execute as many iterations as there are items in backorders arraylist
          productNum = backorders.get(i).getPNum(); // Assign value returned by getPNum() for item at index i to productNum
          purchases.removeBItem(id, productNum); // Call removeBItem() of InvManagement and pass id and product num (remove customer id from products customer links list)
          cMgt.removeBItems(id, productNum); // Call removeBItems() of CustManagement and pass if and product num (remove product number from custoemrs bOrders list)
      } // Close for loop
        for (int i = 0; i < cart.size(); i++){ // For loop to iterate as many times as there are items in cart arraylist
          purchases.addItem(cart.get(i).getPNum()); // Call addItem() of InvManagement and pass product number of element at index i of cart
        } // Close for loop
        System.out.println("Purchase Cancelled."); // Display message stating purchase cancelled
        break; // Break out of case 2 
    } // Close switch statement
    cart.clear(); // Clear cart arraylist
    backorders.clear(); // Clear backorders arraylist
    backorderPick.clear(); // Clear backorderPick arraylist
  } // Close checkOut()
} // Close class