package Classes;

import java.util.*;

public class Table {
    private static final double GST_CHARGE = 0.07; // in percentage
    private static final double SERVICE_CHARGE = 0.10; // in percentage

    private int table_id;
    private int num_seats;
    private boolean occupied;
    private HashMap<Integer, String> reservationMap;

    public Table(int table_id, int num_seats){
        this.table_id = table_id;
        this.num_seats = num_seats;

        // Table is not occupied nor reserved on init
        this.occupied = false;
        reservationMap = new HashMap<Integer, String>();
        
        // Restaurant opening hours is 8am - 8pm, but last reservation is 7pm
        for (int i = 8; i < 20; i++){
            
            reservationMap.put(i, null);
        }
    }

    public int getTable_id() {
        return this.table_id;
    }

    public void setTable_id(int table_id) {
        this.table_id = table_id;
    }

    public int getNum_seats() {
        return this.num_seats;
    }

    public void setNum_seats(int num_seats) {
        this.num_seats = num_seats;
    }

    public void reserve(){
        
    }

    public boolean isReserved() {
        return false;
    }

    public void seatGuest(String staff_name){

    }

    public boolean isOccupied(){
        return this.occupied;
    }

    public void printInvoice(){
        // TODO Some calculation and output
        System.out.println(GST_CHARGE + SERVICE_CHARGE);
        this.occupied = false;
    }
}