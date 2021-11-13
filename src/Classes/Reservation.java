package Classes;

import java.io.Serializable;

//import java.util.*; // Not used

/**
 Represents a reservation for a table
 */
public class Reservation implements Serializable{
    private static final long serialVersionUID = 1L;
    private String reservationDate;
    private String timeSlot;
    private String customerName;
    private String customerContact;
    private int numPax;

    /**
     * Constructor for Reservation
     *@param reservationDate Reservation date
     *@param timeSlot Reservation time
     *@param customerName Customer name under reservation
     *@param customerContact Customer contact number under reservation
     * @param numPax Number of customer dining
     */
    public Reservation(String reservationDate, String timeSlot, String customerName, String customerContact, int numPax)
    {
        this.reservationDate = reservationDate;
        this.timeSlot = timeSlot;
        this.customerName = customerName;
        this.customerContact = customerContact;
        this.numPax = numPax;
    }

    /**
     * @return String
     * Returns the reservation date
     */
    public String getReservationDate(){
        return this.reservationDate;
    }
    /**
     * @return String
     * Returns the reservation time
     */
    public String getReservationTimeSlot(){
        return this.timeSlot;
    }
    /**
     * @return String
     * Returns the reservation name
     */
    public String getCustomerName(){
        return this.customerName;
    }
    /**
     * @return String
     * Returns the reservation contact number
     */
    public String getCustomerContact(){
        return this.customerContact;
    }
    /**
     * @return int
     * Returns the number of customer dining
     */
    public int getNumPax(){ 
        return this.numPax;
    }

    /**
     * Method to print out customer name and contact number
     */
    public void getCustomerNameANDContact(){
        System.out.println("Custome Name: "+ this.customerName);
        System.out.println("Contact: "+ this.customerContact);
    }
}
