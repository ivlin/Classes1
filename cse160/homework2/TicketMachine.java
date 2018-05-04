/**
 * TicketMachine models a ticket machine that issues
 * flat-fare tickets.
 * The price of a ticket is specified via the constructor.
 * Instances will check to ensure that a user only enters
 * sensible amounts of money, and will only print a ticket
 * if enough money has been input.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2011.07.31
 * @modified Ivan Lin
 */
public class TicketMachine
{
    public static final int DISCOUNT_TICKET = 0;
    public static final int REGULAR_TICKET = 1;
    public static final int HIGHEST_TICKET = 2;

    // The price of a ticket from this machine.
    private int priceDiscounted, priceRegular, priceHighest;
    // The amount of money entered by a customer so far.
    private int balance;
    // The total amount of money collected by this machine.
    private int total;

    /**
     * Create a machine that issues tickets of the given price.
     */
    public TicketMachine(int discount, int regular, int highest)
    {
        priceDiscounted = discount;
        priceRegular = regular;
        priceHighest = highest;
        balance = 0;
        total = 0;
    }

    /**
     * ticketType either TicketMachine.DISCOUNT_TICKET, TicketMachine.REGULAR_TICKET, or TicketMachine.HIGHEST_TICKET
     * @Return The price of a ticket of the given type.
     */
    public int getPrice(int ticketType)
    {
        if (ticketType == DISCOUNT_TICKET){
            return priceDiscounted;
        }
        if (ticketType == HIGHEST_TICKET){
            return priceHighest;
        }
        return priceRegular;
    }

    /**
     * Return The amount of money already inserted for the
     * next ticket.
     */
    public int getBalance()
    {
        return balance;
    }

    /**
     * Receive an amount of money from a customer.
     * Check that the amount is sensible.
     */
    public void insertMoney(int amount)
    {
        if(amount > 0) {
            balance = balance + amount;
        }
        else {
            System.out.println("Use a positive amount rather than: " +
                amount);
        }
    }

    /**
     * Print a ticket if enough money has been inserted, and
     * reduce the current balance by the ticket price. Print
     * an error message if more money is required. 
     * ticketType either TicketMachine.DISCOUNT_TICKET, TicketMachine.REGULAR_TICKET, or TicketMachine.HIGHEST_TICKET.
     */
    public void printTicket(int ticketType)
    {
        int amountLeftToPlay;
        if (ticketType == DISCOUNT_TICKET){
            amountLeftToPlay = priceDiscounted - balance;
        }
        else if (ticketType == HIGHEST_TICKET){
            amountLeftToPlay = priceHighest - balance;
        }
        else{
            amountLeftToPlay = priceRegular - balance;
        }
        if(amountLeftToPlay <= 0) {
            // Simulate the printing of a ticket.
            System.out.println("##################");
            System.out.println("# The BlueJ Line");
            System.out.println("# Ticket");

            // Update the total collected with the price.
            // Reduce the balance by the prince.
            if (ticketType == DISCOUNT_TICKET){
                total=total+priceDiscounted;
                balance = balance-priceDiscounted;
                System.out.println("# " + priceDiscounted + " cents.");
            }
            else if (ticketType == HIGHEST_TICKET){
                total=total+priceHighest;
                balance = balance-priceHighest;
                System.out.println("# " + priceHighest + " cents.");
            }
            else{

                total=total+priceRegular;
                balance = balance-priceRegular;
                System.out.println("# " + priceRegular + " cents.");
            } 
            System.out.println("##################");
            System.out.println();
        }
        else {
            System.out.println("You must insert at least: " +
                (amountLeftToPlay) + " more cents.");

        }
    }

    /**
     * Return the money in the balance.
     * The balance is cleared.
     */
    public int refundBalance()
    {
        int amountToRefund;
        amountToRefund = balance;
        balance = 0;
        return amountToRefund;
    }

    /**
     * Empties the machine, returning the total
     */
    public int emptyMachine(){
        int originalTotal = total;
        total = 0;
        return originalTotal;
    }
}
