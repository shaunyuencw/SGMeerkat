package Classes;
import java.util.*;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Date;

public class Reservation {

    SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
    Date dateTime = new Date(System.currentTimeMillis());
    private int NumberofPax;
    private ArrayList<Table> tablesReserved;
    private String CustomerName;
    private int CustomerContact;


    public Reservation(int NumberofPax, ArrayList<Table> tablesReserved, String CustomerName, int CustomerContact)
    {
        this.NumberofPax = NumberofPax;
        this.tablesReserved = tablesReserved;
        this.CustomerName = CustomerName;
        this.CustomerContact = CustomerContact;
    }

    public ArrayList<Table> getTablesReserved() {
        return this.tablesReserved;
    }

    public void getCustomerNameANDContact(){
        System.out.println("Custome Name: "+ this.CustomerName);
        System.out.println("Contact: "+ this.CustomerContact);
    }



    

}
