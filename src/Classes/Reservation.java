package Classes;
import java.util.*;

public class Reservation {
    private String reservationDate;
    private String timeSlot;
    private String customerName;
    private String customerContact;
    private int numPax;

    public Reservation(String reservationDate, String timeSlot, String customerName, String customerContact, int numPax)
    {
        this.reservationDate = reservationDate;
        this.timeSlot = timeSlot;
        this.customerName = customerName;
        this.customerContact = customerContact;
        this.numPax = numPax;
    }

    public String getReservationDate(){
        return this.reservationDate;
    }

    public String getReservationTimeSlot(){
        return this.timeSlot;
    }

    public String getCustomerName(){
        return this.customerName;
    }

    public String getCustomerContact(){
        return this.customerContact;
    }

    public int getNumPax(){ 
        return this.numPax;
    }

    public void getCustomerNameANDContact(){
        System.out.println("Custome Name: "+ this.customerName);
        System.out.println("Contact: "+ this.customerContact);
    }
}
