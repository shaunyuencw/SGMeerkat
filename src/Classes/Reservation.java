package Classes;
import java.util.*;

public class Reservation {

    private int NumberofPax;
    private ArrayList<Table> tablesReserved;

    //TODO Date and Time attributes

    public Reservation(int NumberofPax, ArrayList<Table> tablesReserved)
    {
        this.NumberofPax = NumberofPax;
        this.tablesReserved = tablesReserved;
    }

    public ArrayList<Table> getTablesReserved() {
        return this.tablesReserved;
    }



    

}
