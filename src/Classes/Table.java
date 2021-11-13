package Classes;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Represents a table in the restaurant
 */
public class Table implements Serializable{
    private static final long serialVersionUID = 1L;
    private int table_id;
    private int num_seats;
    private boolean occupied;
    private HashMap<String, Reservation> reservationMap;

    /**
     * Constructor for Table
     *@param table_id The table number
     *@param num_seats The number of seats in the table
     */
    public Table(int table_id, int num_seats){
        this.table_id = table_id;
        this.num_seats = num_seats;

        // Table is not occupied nor reserved on init
        this.occupied = false;
        reservationMap = new HashMap<String, Reservation>();
        
        // Restaurant opening hours is 8am - 8pm, but last reservation is 7pm

    }

    /**
     * @return int
     * Returns the table number
     */
    public int getTable_id() {
        return this.table_id;
    }
    /**
     * @param table_id the table number
     * Method set the table number to the table_id
     */
    public void setTable_id(int table_id) {
        this.table_id = table_id;
    }
    /**
     * @return int
     * Returns the number of seats
     */
    public int getNum_seats() {
        return this.num_seats;
    }
    /**
     * @param num_seats
     * Method sets the number of seats to num_seats
     */
    public void setNum_seats(int num_seats) {
        this.num_seats = num_seats;
    }
    /**
     * @param reserveKey
     * @param toReserve reservation object
     * @return boolean
     * Method to check if reservation exists
     */
    public boolean reserve(String reserveKey, Reservation toReserve) {
        if (!reservationMap.containsKey(reserveKey)){
            reservationMap.put(reserveKey, toReserve);
            return true;
        }
        return false; // ! Something went wrong
    }

    /**
     * @param reserveKey
     * @return Reservation
     * Method to get the Reservation object
     */
    public Reservation getReservation(String reserveKey) {
        if (reservationMap.containsKey(reserveKey)){
            return reservationMap.get(reserveKey);
        }

        return null; // ! Something went wrong...
    }

    /**
     * @param reserveKey
     * @return boolean
     * Method to remove reservation
     */
    public boolean removeReservation(String reserveKey) {
        if (reservationMap.containsKey(reserveKey)){
            reservationMap.remove(reserveKey);
            return true;
        }

        return false; // ! Something went wrong
    }

    /**
     * Method to show list of reservations
     */
    public void showReservations(){
        for (String key : reservationMap.keySet()){
            Reservation reservation = reservationMap.get(key);
            System.out.printf("ReserveKey: %s, Date: %s, Time: %s, Name: %s, Contact: %s, numPax: %d\n", key, reservation.getReservationDate()
                            , reservation.getReservationTimeSlot(), reservation.getCustomerName(), reservation.getCustomerContact(), reservation.getNumPax());
        }
    }

    /**
     * @return int
     * Method to return number of reservation
     */
    public int getNumberOfReservations(){ 
        return reservationMap.size();
    }

    /**
     * @param reserveKey
     * @return boolean
     * Method to check if table is reserved
     */
    public boolean isReserved(String reserveKey) {
        if (reservationMap.containsKey(reserveKey)){
            //System.out.println(reserveKey + " is reserved!"); // DEBUG
            return true;
        }

        return false; // No such reservation.
    }

    /**
     * @return boolean
     * Method to change the occupied status
     */
    public boolean occupySwitch(){
        if (!this.occupied){
            this.occupied = true;
            return true;
        }
        else{
            this.occupied = false;
            return false;
        }
    }

    /**
     * @return boolean
     * Method to check if table is occupied
     */
    public boolean isOccupied(){
        return this.occupied;
    }

    /**
     * @param cleanupDate reservation date to be removed
     * @return int
     * Method clear up the reservation date
     */
    public int cleanupReservations(String cleanupDate) {
        int reservationCleared = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        if (reservationMap.size() > 0){
            for (String reserveKey : reservationMap.keySet()) {
                Reservation toCheck = reservationMap.get(reserveKey);
                try{
                    if (sdf.parse(toCheck.getReservationDate()).before(sdf.parse(cleanupDate)) || toCheck.getReservationDate().equals(cleanupDate)){
                        reservationMap.remove(reserveKey);
                        reservationCleared++;
                    }
                }
                catch (ParseException e){
                    System.out.println("Something went wrong");
                }
            }
        }
    
        return reservationCleared;
    }
}