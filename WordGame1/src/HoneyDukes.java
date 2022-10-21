import java.util.*;
import javax.swing.JOptionPane;

/**
 * @author 21gjulio30
 * Gianna Julio
 * period 4 - Java
 * Honeydukes shop - allows customer to buy snacks from Honeydukes
 */
public class HoneyDukes 
{
    public static void main(String[] args) 
    {
        try
        {
            Scanner input = new Scanner(System.in);
            
            // {Acid Pops, Pumpkin Pasties, small bean bags, large bean bags}
            int[] cart = {0, 0, 0, 0};
            
            String customer = getCustomer(input);
            
            if (customer.equals("y")) // there is a customer
            {
                boolean discount = getPassword(input);
                showPriceList(discount);
                loopOptions(input, discount, cart);
                showSavings(discount, cart);
            }
            
            else if (customer.equals("n")) // input is n, there is no customer
                System.out.println("Thank you for visiting Honeydukes! Please come again soon!");
        }
        catch (InputMismatchException ex)
        {
            System.out.println("Invalid value. Please try again.");
        }
    }
    
    /**
     * @param discount boolean if true the user receives a discount on their purchase
     * @param cart integer array of how many products the user has bought
     * 
     * shows total savings in Knuts and % using JOptionPane
     */
    public static void showSavings(boolean discount, int[] cart)
    {
        if(discount) // only executes if the user knows the password and is a honeydukes member
        {
            int nonMemberTotal = findTotal(false, cart); // finds total WITHOUT discount
            int memberTotal = findTotal(true, cart); // finds total WITH discount
            if (memberTotal > 290) // orders greater than 10 Sickles get additional 10% off
                memberTotal -= memberTotal/10; // applies 10% discount if member
            int diff = nonMemberTotal - memberTotal; // finds difference between regular price and discount price
            
            if(memberTotal > 0) // executes only if the user had bought any products
                // formats receipt showing savings
                JOptionPane.showMessageDialog(null, "As a Honeydukes member, you can save money!\nToday's Savings:"
                        + "\n       " + "Total with regular prices:                      " + nonMemberTotal + " Knuts"
                        + "\n       " + "Total with membership discount:        " + memberTotal + " Knuts"
                        + "\n                                                                           ------------------"
                        + "\n       Total Savings:                                           " + diff + " Knuts"
                        + "\n\nYou've saved " + (int)(((double)diff/nonMemberTotal)*100) + "% with your Honeydukes membership!"
                        + "\nThank you for shopping with us!");
        }
    }
    
    /**
     * @param input Scanner to get user input
     * @return int 1 if there is a customer, 0 if there is no customer
     */
    public static String getCustomer(Scanner input)
    {
        // prints heading
        System.out.println("Welcome to Honeydukes!");
        System.out.println("---------------------");
        
        String customer = null; // initialize customer so loop executes
        
        do
        {
            System.out.print("Is there a customer in line? (n / y): ");
            customer = input.nextLine();
            
            if(customer.equals("y"))
            	return "y";
            else if (customer.equals("n"))
            	return "n";
            else
            	System.out.println("Invalid value. Please try again.\n");
            
        } while(!(customer.equals("y")) || !(customer.equals("n")));
        
        return customer;
    }

    /**
     * @param input Scanner to get user input
     * @return discount boolean if true the user receives a discount on their purchase
     */
    public static boolean getPassword(Scanner input)
    {
        // initialize guess to empty string
        String guess = "";
        
        // loop twice - user gets two guesses
        for (int i = 0; i < 2; i++)
        {
            System.out.print("Enter the password: ");
            guess = input.next().toLowerCase();
            input.nextLine(); // flush input stream
            
            if(guess.equals(PASSWORD)) // if user knows the password
            {
                System.out.println("\nWelcome Honeydukes member!");
                System.out.println("Enjoy these special discounts on our products at Honeydukes!");
                printDiscountList(); // prints out the list of items that are discounted
                return true;
            }
            else
            {
                if(i == 0) // first guess
                    System.out.println("Sorry, but that password is incorrect. You get one more chance.");
                else // second guess
                    System.out.println("Sorry, but that password is incorrect. Please enjoy our products at the regular price.");
                    
            }
        }
        
        return false;
    }
    
    /**
     * prints the list of items that the user receives a discount on if they know the password
     */
    public static void printDiscountList()
    {
        System.out.println("\tDiscount on Acid Pops");
        System.out.println("\tDiscount on Pumpkin Pasties");
        System.out.println("\tDiscount on Large Every Flavour Beans");
        System.out.println("\n\t10% additional discount on orders of 10 Sickles or more");
    }
    
    /**
     * @param discount boolean if true the user receives a discount on their purchase
     * prints out the price of each item
     */
    public static void showPriceList(boolean discount)
    {
        // initialize prices for each item
        int acidPops, acidPopsBag, pumpkinPasties, smallBeans, bigBeans;
        
        if (discount)
        {
            acidPops = 11;
            acidPopsBag = 50;
            pumpkinPasties = 100;
            smallBeans = 50;
            bigBeans = 58;
        }
        
        else // no discount - regular prices
        {
            acidPops = 12;
            acidPopsBag = 58;
            pumpkinPasties = 116;
            smallBeans = 50;
            bigBeans = 70;
        }
        
        // prints out nicely formatted prices
        String fmt = "%-55s%-10s%n";
        System.out.println("\nHere is our price list:");
        System.out.printf(fmt, "\tAcid Pops (each)", (acidPops + " Knuts"));
        System.out.printf(fmt, "\tAcid Pops (bag of 5)", (acidPopsBag + " Knuts"));
        System.out.printf(fmt, "\tPumpkin Pasties (each)", (pumpkinPasties + " Knuts"));
        System.out.printf(fmt, "\tEvery Flavour Beans (small bag)", (smallBeans + " Knuts"));
        System.out.printf(fmt, "\tEvery Flavour Beans (large bag)", (bigBeans + " Knuts"));
    }
    
    /**
     * @param input Scanner to get user input
     * @return int option on the menu that the user chose to do
     */
    public static int getOption(Scanner input)
    {
        // prints list of options
        System.out.println("\nOption Menu:");
        System.out.println("\t1) Update Acid Pops Order");
        System.out.println("\t2) Update Pumpkin Pasties Order");
        System.out.println("\t3) Update Every Flavour Beans Order");
        System.out.println("\t4) Show Price List");
        System.out.println("\t5) Check Out");
        System.out.print("\nPlease choose an option: ");
        
        return input.nextInt(); // user's choice
    }
    
    /**
     * @param input Scanner to get user input
     * @param discount boolean if true the user receives a discount on their purchase
     * @param cart int array that holds the amount of various products purchased
     */
    public static void loopOptions(Scanner input, boolean discount, int[] cart)
    {
        int option = getOption(input);
        
        if(option == 1) // user wants to buy Acid Pops
        {
            updateAcidPops(input, discount, cart);
            loopOptions(input, discount, cart);
        }
        else if(option == 2) // user wants to buy Pumpkin Pasties
        {
            updatePumpkinPasties(input, discount, cart);
            loopOptions(input, discount, cart);
        }
        else if(option == 3) // user wants to buy Every Flavor Beans
        {
            updateBeans(input, discount, cart);
            loopOptions(input, discount, cart);
        }
        else if(option == 4) // prints price list and loops again
        {
            showPriceList(discount);
            loopOptions(input, discount, cart);
        }
        else if(option == 5) // user is done shopping, do not loop again
            checkOut(input, discount, cart);
        else // input is less than 1 or greater than 5
        {
            System.out.println("Invalid option. Try again.");
            loopOptions(input, discount, cart); // loop again
        }
            
    }
    
    /**
     * @param cart int array that holds the amount of various products purchased
     */
    public static void printAcidPops(int[] cart)
    {
        System.out.println("Current order:");
        int bags = cart[0]/5; // total bags of acid pops bought
        int pops = cart[0]%5; // singular acid pops bought
        
        // prints # of acid pops ordered
        if (bags == 0 && pops == 0) // none
            System.out.println("\tNo Acid Pops ordered");
        else if (bags > 0 && pops == 0) // just bags bought
            System.out.println("\t" + bags + " bags of Acid Pops");
        else if (bags == 0 && pops > 0) // just singular pops bought
            System.out.println("\t" + pops + " Acid Pops");
        else // both bags and singular pops bought
        {
            System.out.println("\t" + bags + " bags of Acid Pops ordered");
            System.out.println("\t" + pops + " Acid Pops ordered");
        }
    }
    
    /**
     * @param input Scanner to get user input
     * @param discount boolean if true the user receives a discount on their purchase
     * @param cart int array that holds the amount of various products purchased
     */
    public static void updateAcidPops(Scanner input, boolean discount, int[] cart)
    {
        printAcidPops(cart); //prints # of acid pops bought
        System.out.println("\nAcid Pops cost");
        
        // prints cost of pops with or without discount
        if(discount)
        {
            System.out.println("\t11 Knuts per pop");
            System.out.println("\t50 Knuts per bag of 5");
        }
        
        else
        {
            System.out.println("\t12 Knuts per pop");
            System.out.println("\t58 Knuts per bag of 5");
        }
        
        System.out.print("\nEnter the total # of Acid Pops you would like to order: ");
        int newOrder = input.nextInt(); // gets new order
        
        if(newOrder < 0)
            System.out.println("Negative number accepted as 0.");
        else
        {
            cart[0] += newOrder; // add new order of pops to total in cart
            System.out.println("\nTransaction complete!");
            printAcidPops(cart); // prints updated # of acid pops bought
        }
    }
    
    /**
     * @param cart int array that holds the amount of various products purchased
     */
    public static void printPumpkinPasties(int[] cart)
    {
        // prints total # of pumpkin pasties ordered
        System.out.println("Current order:");
        int pasties = cart[1];
        
        if (pasties == 0) // none ordered
            System.out.println("\tNo Pumpkin Pasties ordered");
        else
            System.out.println("\t" + pasties + " Pumpkin Pasties ordered");
    }
    
    /**
     * @param input Scanner to get user input
     * @param discount boolean if true the user receives a discount on their purchase
     * @param cart int array that holds the amount of various products purchased
     */
    public static void updatePumpkinPasties(Scanner input, boolean discount, int[] cart)
    {
        printPumpkinPasties(cart);
        
        if(discount)
            System.out.print("Enter the # of Pumpkin Pasties you would like to order for 100 Knuts each: ");
        else
            System.out.print("Enter the # of Pumpkin Pasties you would like to order for 116 Knuts each: ");
        
        int newOrder = input.nextInt();
        
        if(newOrder < 0)
            System.out.println("Negative number accepted as 0.");
        else
        {
            cart[1] += newOrder; // add new order to cart
            System.out.println("\nTransaction complete!");
            printPumpkinPasties(cart); // print updated # of pasties bought
        }
    }
    
    /**
     * @param cart int array that holds the amount of various products purchased
     */
    public static void printBeans(int[] cart)
    {
        System.out.println("Current order:");
        int small = cart[2];
        int large = cart[3];
        
        if (large == 0 && small == 0) // no bags of beans ordered
            System.out.println("\tNo Every Flavour Beans ordered");
        else if (large > 0 && small == 0) // only large bags ordered
            System.out.println("\t" + large + " large bags of Every Flavour Beans ordered");
        else if (large == 0 && small > 0) // only small bags ordered
            System.out.println("\t" + small + " small bags of Every Flavour Beans ordered");
        else // both bags ordered
        {
            System.out.println("\t" + small + " small bags of Every Flavour Beans ordered");
            System.out.println("\t" + large + " large bags of Every Flavour Beans ordered");
        }
    }
    
    /**
     * @param input Scanner to get user input
     * @param discount boolean if true the user receives a discount on their purchase
     * @param cart int array that holds the amount of various products purchased
     */
    public static void updateBeans(Scanner input, boolean discount, int[] cart)
    {
        printBeans(cart);
        
        System.out.println("\nEvery Flavor Beans cost");
        
        if(discount)
        {
            System.out.println("\t50 Knuts per small bag");
            System.out.println("\t58 Knuts per large bag");
        }
        else
        {
            System.out.println("\t50 Knuts per small bag");
            System.out.println("\t70 Knuts per large bag");
        }
        
        // small bags
        System.out.print("\nEnter the total # of small bags you would like to order: ");
        int newSmall = input.nextInt();
        
        if(newSmall < 0)
            System.out.println("Negative number accepted as 0.");
        else
            cart[2] += newSmall; // add new order to cart
        
        // large bags
        System.out.print("Enter the total # of large bags you would like to order: ");
        int newLarge = input.nextInt();
        
        if(newLarge < 0)
            System.out.println("Negative number accepted as 0.");
        else
            cart[3] += newLarge; // add new order to cart
        
        System.out.println("\nTransaction complete!");
        printBeans(cart); // print updated # of bean bags bought
    }
    
    /**
     * @param discount boolean if true the user receives a discount on their purchase
     * @param cart int array that holds the amount of various products purchased
     * @return returns total cost of items in cart (in Knuts)
     */
    public static int findTotal(boolean discount, int[] cart)
    {
        // initialize total and prices of products in cart
        int popBags, pops, pasties, smallBags, largeBags;
        
        if(discount)
        {
            popBags = (cart[0]/5) * 50;
            pops = (cart[0]%5) * 11;
            pasties = cart[1] * 100;
            smallBags = cart[2] * 50;
            largeBags = cart[3] * 58;
        }
        
        else
        {
            popBags = (cart[0]/5) * 58;
            pops = (cart[0]%5) * 12;
            pasties = cart[1] * 116;
            smallBags = cart[2] * 50;
            largeBags = cart[3] * 70;
        }
        
        // total
        return popBags + pops + pasties + smallBags + largeBags;
    }
    
    /**
     * @param discount boolean if true the user receives a discount on their purchase
     * @param cart int array that holds the amount of various products purchased
     * @return total after the 10% discount has been applied (if applicable)
     */
    public static int printTotal(boolean discount, int[] cart)
    {
        int total = findTotal(discount, cart);
        int pops, popBags, pasties, smallBags, largeBags;
        
        if(total > 0) // of any products have been bought
            System.out.println("Subtotal:");
        
        if (discount)
        {
            pops = 11;
            popBags = 50;
            pasties = 100;
            smallBags = 50;
            largeBags = 58;
        }
        
        else
        {
            pops = 12;
            popBags = 58;
            pasties = 116;
            smallBags = 50;
            largeBags = 70;
        }
        
        // prints out nicely formatted receipt with total
        String fmt = "%-55s%-10s%n";
        
        for (int i = 0; i < cart.length; i++)
        {
            if(cart[i] != 0)
            {
                switch(i)
                {
                    case 0: if(cart[0]/5 != 0)
                            System.out.printf(fmt, "\t" + cart[0]/5 + " Bags of Acid Pops at " + popBags + " Knuts ea.:", popBags);
                            System.out.printf(fmt, "\t" + cart[0]%5 + " Acid Pops at " + pops + " Knuts ea.: ", pops); 
                            break;
                    case 1: System.out.printf(fmt, "\t" + cart[1] + " Pumpkin Pasties at " + pasties + " Knuts ea.: ", pasties); break;
                    case 2: System.out.printf(fmt, "\t" + cart[2] + " Small Every Flavor Beans at 50 Knuts ea.: ", smallBags); break;
                    case 3: System.out.printf(fmt, "\t" + cart[3] + " Large Every Flavor Beans at " + largeBags + " Knuts ea.: ", largeBags); break;
                }
            }
        }
        
        if (total > 0) // if any products have been bought
        {
            System.out.printf(fmt, "\t", "----");
            System.out.printf(fmt, "\tTotal: ", total);
            
            // 10 % discount only applied if order is greater than 10 Sickles (290 Knuts)
            if(discount && total > 290)
            {
                System.out.printf(fmt, "\tBonus discount of 10%: ", "-" + total/10);
                total -= total/10;
                System.out.printf(fmt, "\t", "----");
                System.out.printf(fmt, "\tNew total: ", total);
            }
        }
        
        return total;
    }
    
    /**
     * prints the instructions for how to enter a payment
     */
    public static void printPayInfo()
    {
        System.out.println("\nPlease enter a payment amount in the following format:");
        System.out.println("\t<amount><space><currency>");
        System.out.println("\t\tWhere <amount> = an integer");
        System.out.println("\t\tWhere <space> = a blank space");
        System.out.println("\t\tWhere <currency> = {Knuts, Sickles, Galleons}");
        System.out.println("\nYou may enter as many times as you like. Each entry will be");
        System.out.println("added to your total until sufficient funds have been obtained.");
        System.out.println("\nCurrency exchange rates:\n\t29 Knuts = 1 Sickle\n\t493 Knuts = 17 Sickles = 1 Galleon");
    }
    
    /**
     * @param numCoins the number of coins that the user has entered
     * @param type the type of coin that the user has entered (Knuts, Sickles, Galleons)
     * @return the amount of Knuts the user needs in change (converted from Sickles and Galleons)
     */
    public static int convertCoins(int numCoins, String type)
    {
        if (type.toLowerCase().equals("sickle") || type.toLowerCase().equals("sickles"))
            return numCoins * 29; // Sickles to Knuts
        else if (type.toLowerCase().equals("galleon") || type.toLowerCase().equals("galleons"))
            return numCoins * 493; // Galleons to Knuts
        else if (type.toLowerCase().equals("knut") || type.toLowerCase().equals("knuts"))
            return numCoins; // Knuts
        else
            return -2; // invalid type
    }
    
    /**
     * @param change the amount of change (in Knuts) that the user gets back
     */
    public static void getChange(int change)
    {
        int[] payList = new int[3]; // galleons, sickles, knuts
        payList[0] = change/493; // change in galleons
        change -= payList[0] * 493;     
        payList[1] = change/29; // change in sickles
        change -= payList[1] * 29;
        payList[2] = change; // remaining change (in knuts)
        
        // loops through types of change and prints amounts
        for(int i = 0; i < payList.length; i++)
        {
            if(payList[i] != 0)
            {
                if(i == 0)
                    System.out.println("\t\t" + payList[i] + " Galleons (= " + payList[i]*493 + " Knuts)");
                else if(i == 1)
                    System.out.println("\t\t" + payList[i] + " Sickles (= " + payList[i]*29 + " Knuts)");
                else if(i == 2)
                    System.out.println("\t\t" + payList[i] + " Knuts");
            }
        }
        
        System.out.println("\nThank you for shopping at Honeydukes!");
    }
    
    /**
     * @param input Scanner to get user input
     * @param totalCost how much the user still has to pay to reach the total
     * @param TOTAL total amount that the user owes
     * @param sum the sum of all previous payments that the user has entered
     */
    public static void getPayment(Scanner input, int totalCost, int TOTAL, int sum)
    {
        try
        {
            System.out.print("\n\tPayment > ");
            int numCoins = input.nextInt(); // amount of coins
            String type = input.next(); // type of coins (galleons, sickles, knuts)
            int knutsPaid = convertCoins(numCoins, type); // convert to Knuts
            int change = knutsPaid - totalCost;
            
            if(change > (50*493))
            {
                System.out.println("Sorry, we can't provide enough change for that payment. Please try again.");
                numCoins = -1;
                knutsPaid = -1;
                type = null;
                getPayment(input, totalCost, TOTAL, sum);
            }
            
            if(knutsPaid == -2) // invalid input
            {
                System.out.println("\tInvalid payment. Try again.");
                getPayment(input, totalCost, TOTAL, sum);
            }
            
            else if (numCoins != -1 && knutsPaid != -1)// valid input
            {
                sum += knutsPaid; // keeps running total of payment
                
                System.out.println("\t\tYou have added " + knutsPaid + " Knuts to your total");
                System.out.println("\t\tYou have paid " + sum + " out of " + TOTAL);
                
                if(knutsPaid > totalCost) // user has overpaid and needs change
                {
                    System.out.println("\tYou have overpaid by " + change + " Knuts");
                    System.out.println("\n\tHere is your change: ");
                    getChange(change);
                }
                
                else if (knutsPaid == totalCost) // user has paid perfect amount
                {
                    System.out.println("\tThank you!");
                    System.out.println("\nThank you for shopping at Honeydukes!");
                }
                
                else // user needs to pay more
                {
                    int newTotal = totalCost - knutsPaid;
                    System.out.println("\tYou still owe " + newTotal + " Knuts");
                    getPayment(input, newTotal, TOTAL, sum);
                }
            }
        }
        catch (InputMismatchException ex)
        {
            System.out.println("Invalid Payment. Try again.");
            input.next(); // flush input stream
            getPayment(input, totalCost, TOTAL, sum);
        }
    }
    
    /**
     * @param input Scanner to get user input
     * @param discount boolean if true the user receives a discount on their purchase
     * @param cart int array that holds the amount of various products purchased
     */
    public static void checkOut(Scanner input, boolean discount, int[] cart)
    {
        int totalCost = findTotal(discount, cart);
        totalCost = printTotal(discount, cart);
        
        if(totalCost > 0) // user has bought something
        {
            printPayInfo();
            getPayment(input, totalCost, totalCost, 0);
        }
        
        else // no items purchased
            System.out.println("\tNo items purchased. Thanks for stopping by anyway!");
    }
    
    // secret password for members only discount
    static final String PASSWORD = "alohomora";
}