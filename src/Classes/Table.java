package Classes;

import java.util.*;

public class Table {
    private static final double GST_CHARGE = 0.07; // in percentage
    private static final double SERVICE_CHARGE = 0.10; // in percentage

    private int table_id;
    private int num_seats;
    private boolean occupied;
    private HashMap<String, Reservation> reservationMap;

    public Table(int table_id, int num_seats){
        this.table_id = table_id;
        this.num_seats = num_seats;

        // Table is not occupied nor reserved on init
        this.occupied = false;
        reservationMap = new HashMap<String, Reservation>();
        
        // Restaurant opening hours is 8am - 8pm, but last reservation is 7pm

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

    public boolean reserve(String reserveKey, Reservation toReserve) {
        if (!reservationMap.containsKey(reserveKey)){
            reservationMap.put(reserveKey, toReserve);
            return true;
        }
        return false; // ! Something went wrong
    }

    public Reservation getReservation(String reserveKey) {
        if (reservationMap.containsKey(reserveKey)){
            return reservationMap.get(reserveKey);
        }

        return null; // ! Something went wrong...
    }

    public boolean removeReservation(String reserveKey) {
        if (reservationMap.containsKey(reserveKey)){
            reservationMap.remove(reserveKey);
            return true;
        }

        return false; // ! Something went wrong
    }

    public void showReservations(){
        for (String key : reservationMap.keySet()){
            Reservation reservation = reservationMap.get(key);
            System.out.printf("ReserveKey: %s, Date: %s, Time: %s, Name: %s, Contact: %s, numPax: %d\n", key, reservation.getReservationDate()
                            , reservation.getReservationTimeSlot(), reservation.getCustomerName(), reservation.getCustomerContact(), reservation.getNumPax());
        }
    }

    public int getNumberOfReservations(){ 
        return reservationMap.size();
    }

    public boolean isReserved(String reserveKey) {
        if (reservationMap.containsKey(reserveKey)){
            System.out.println(reserveKey + " is reserved!");
            return true;
        }

        return false; // No such reservation.
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