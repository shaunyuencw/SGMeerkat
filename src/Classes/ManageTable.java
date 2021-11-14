package Classes;

import java.io.*;
import java.text.*;
import java.util.*;

/**
 * Control class for tables containing a list of all tables
 */
public class ManageTable implements DatabaseHandler, Serializable {
    private static final long serialVersionUID = 1L;
    private HashMap<Integer, Table> all_tables;
    private OrderList orderList;
    private Staff curStaff;
    private static Scanner sc = new Scanner(System.in);
    private String currentDate = "12-11-2021";
    private String currentTime = "1030";

    public ManageTable(){
        all_tables = new HashMap<Integer, Table>();
    }

    public String getCurrentDate() { return this.currentDate; }
    public String getCurrentTime() { return this.currentTime; }

    public void tableOccupySwitch(int tableNo){
        all_tables.get(tableNo).occupySwitch();
    }

    /**
     * openRestaurant() will initialize the following: 5 2-seats, 4 4-seats, 3
     * 6-seats, 2 8-seats, 1 10-seats
     */
    public void openRestaurant(){
        int tid_counter = 1;
        int[] num_of_each = { 5, 4, 3, 2, 1 };

        for (int i = 2; i <= 10; i += 2) {
            for (int j = num_of_each[i / 2 - 1]; j > 0; j--) {
                Table t = new Table(tid_counter, i);
                all_tables.put(tid_counter, t);
                tid_counter++;
            }
        }
    }

    /**
     * @param orderList List of current existing orders
     * @param curStaff The staff that is using the system currently
     * Method to control how to manage the walk-in customer
     */
    public void welcomeGuest(OrderList orderList, Staff curStaff) {
        this.orderList = orderList;
        this.curStaff = curStaff;
        int tableAssigned = -1;
        boolean hasReservation = false;
        char option;

        System.out.println("Welcome to SGMeerkat!");
        System.out.println("Do you have a reservation? (Y/N)");
        option = sc.next().charAt(0);

        if (option == 'Y' || option == 'y'){
            // check if reservation is legit
            tableAssigned = checkReservation("check");
            hasReservation = true;
        }
        else{
            // No reservation, get number of pax
            tableAssigned = walkinAllocator();
        }

        if (tableAssigned != -1){
            System.out.println("Your table number is " +tableAssigned+ ", this way please.");
        }
        else{
            if (!hasReservation)
                System.out.println("No tables available currently, try again later.");
        }
    }

    /**
     * @return String The rounded off time
     * Method to get time and round off time to nearest 30th mins
     */
    private String getRoundedTimeslot(){
        String time;
        int timeInt, hrs = 0, mins = 0;

        while(true) {
            System.out.println("What time? (Hourly Slots 24hours clock): ");
            try {
                time = sc.next();
                timeInt = Integer.parseInt(time);

                if (timeInt == -1){
                    System.out.println("Going back to main menu...");
                    return "invalid";
                }

                hrs = timeInt / 100;
                mins = timeInt % 100;

                // Check
                if (hrs < 0 || hrs > 23 || mins < 0 || mins > 59){
                    System.out.println("Invalid time value...");
                }
                else if (hrs < 8) {
                    System.out.println("We are not yet opened.");
                }
                else if (hrs > 19){
                    System.out.println("We are not longer taking reservation at that time.");
                }
                else {
                    if (hrs == 19 && mins > 30){
                        System.out.println("We are not longer taking reservation at that time.");
                        continue;
                    }

                    if (mins >= 46) {
                        hrs++;
                    } else if (mins >= 16 && mins <= 45){
                        mins = 30;
                    } else if (mins > 0 && mins <= 15){
                        mins = 0;
                    }
                    break;
                }
            }
            catch (Exception e){
                System.out.println("Invalid time value...");
            }
        }

        String returnStr = "";
        if (hrs < 10){
            returnStr += "0" + hrs;
        }
        else{
            returnStr += hrs;
        }

        if (mins < 10){
            returnStr += "00";
        }
        else{
            returnStr += mins;
        }

        return returnStr;
    }

    /**
     * @param currentTime The current time
     * @return String The rounded off time
     * Method to round off given current time to nearest 30th mins
     */
    private String getRoundedTimeslot(String currentTime){
        int hrs = 0, mins = 0;
        int timeInt;
        try {
            timeInt = Integer.parseInt(currentTime);

            if (timeInt == -1){
                System.out.println("Going back to main menu...");
                return "invalid";
            }

            hrs = timeInt / 100;
            mins = timeInt % 100;

            // Check
            if (hrs < 0 || hrs > 23 || mins < 0 || mins > 59){
                System.out.println("Invalid time value...");
                return "invalid";
            }
            else if (hrs < 8) {
                System.out.println("We are not yet opened.");
                return "invalid";
            }
            else if (hrs > 19){
                System.out.println("We are not longer taking reservation at that time.");
                return "invalid";
            }
            else {
                if (hrs == 19 && mins > 30){
                    System.out.println("We are not longer taking reservation at that time.");
                    return "invalid";
                }

                if (mins >= 46) {
                    hrs++;
                    mins = 0;
                } else if (mins >= 16 && mins <= 45){
                    mins = 30;
                } else if (mins > 0 && mins <= 15){
                    mins = 0;
                }
            }
        }

        catch (Exception e){
            System.out.println("Invalid time input");
        }

        String returnStr = "";
        if (hrs < 10){
            returnStr += "0" + hrs;
        }
        else{
            returnStr += hrs;
        }

        if (mins < 10){
            returnStr += "00";
        }
        else{
            returnStr += mins;
        }

        return returnStr;
    }

    /**
     * Method to get contact details for reservation
     */
    public void reserveTable(){
        // This method will get infomation required from the user to make a reservation
        // name, numPax, and the timeslot of their reservation.
        String reservationName, reservationContact;
        String dateStr, reserveTimeslot;
        int reservePax = -1;

        System.out.println("Can I get a name for the reservation?: ");
        reservationName = sc.next();

        System.out.println("Contact number for the reservation?: ");
        reservationContact = sc.next();

        while(true) {
            try {
                System.out.println("How many persons will be dining? (Max 10): ");
                reservePax = sc.nextInt();

                if (reservePax < 0 || reservePax > 10){
                    System.out.println("We do not have a table that sits that number of guest.");
                }
                else{
                    break;
                }
                
            } catch (Exception e) {
                System.out.println("Invalid input, only numbers allowed");
                sc.next();
            }   
        }

        dateStr = inputDate();
        reserveTimeslot = getRoundedTimeslot();

        String reserveKey = dateStr + "," + reserveTimeslot;

        System.out.println("Date: " + dateStr + ", timeSlot: " + reserveTimeslot);

        Reservation toReserve = new Reservation(dateStr, reserveTimeslot, reservationName.toLowerCase(), reservationContact, reservePax);

        if (!reserveTimeslot.equals("invalid")){
            int tableReserved = reservationAllocator(reserveKey, toReserve);
            if (tableReserved != -1){
                // Successful reservation
                Table tableView = all_tables.get(tableReserved);
                System.out.printf("Table %d (size %d) booked under the name %s for ", tableView.getTable_id(), tableView.getNum_seats(), reservationName);
                System.out.println(dateStr + ", " + reserveTimeslot);

                //tableView.showReservations();
            }
            else{
                System.out.println("No table matched to fit your reservation.");
                return;
            }
        }
        else{
            return;
        }
    }

    /**
     * @return String
     * Method to format the date
     */
    public String inputDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String returnStr = "";

        while(true){
            System.out.println("Which date? (dd-MM-yyyy): ");
            returnStr = sc.next();
            try {
                return (sdf.format(sdf.parse(returnStr)));
            }
            catch (ParseException e){
                System.out.println("Invalid date format.");
            }
        }
    }

    /**
     * @param reservationKey
     * @param toReserve
     * @return int
     * Method to find the next available table for reservation
     */
    private int reservationAllocator(String reservationKey, Reservation toReserve) {
        // Finds the next available table for reservation, upsize table if needed.
        // Tables are assumed to be sorted.
        Table tempTable;
        for (int key : all_tables.keySet()){
            tempTable = all_tables.get(key);
            // System.out.printf("Table: %d, size: %d, reservePax: %d\n", key, tempTable.getNum_seats(), reservePax); //DEBUG
            if (tempTable.getNum_seats() >= toReserve.getNumPax() && !tempTable.isReserved(reservationKey)){
                tempTable.reserve(reservationKey, toReserve); // Reservation name will always be lower case
                return key;
            }
        }

        return -1; // Return allocation table, - 1 if no tables available
    }

    /**
     * @return int
     * Method to allocate walk in customer to a seat
     */
    private int walkinAllocator(){
        int numPax = -1;

        while (true){
            try {
                System.out.println("How many persons will be dining? (Max 10): ");
                numPax = sc.nextInt();

                if (numPax == -1 ){
                    return -1; // Terminate allocation process
                }

                if (numPax < 0 || numPax > 10){
                    System.out.println("We do not have a table that sits that number of guest.");
                    continue;
                }
                break;
            }
            catch (Exception e){
                System.out.println("Invalid input, only integers allowed.");
            }
        }

        String reserveKey = currentDate + "," + getRoundedTimeslot(currentTime);

        for (int tableid : all_tables.keySet()){
            Table tempTable = all_tables.get(tableid);

            if (!tempTable.isReserved(reserveKey) && !tempTable.isOccupied() && tempTable.getNum_seats() >= numPax){
                // Table is not reserved, not occupied, and can fit the group.
                orderList.createNewOrder(curStaff, tempTable.getTable_id(), numPax);
                tempTable.occupySwitch();
                return tempTable.getTable_id();
            }
        }

        return -1;
    }

    /**
     * Method to print current date and time
     */
    public void displayCurDateTime(){
        System.out.println("System date is " + this.currentDate + ", time is " + this.currentTime);
    }
    /**
     * Method to change current date
     */
    public void changeCurrentDate(){
        this.currentDate = inputDate();
    }
    /**
     * Method to change current time
     */
    public void changeCurrentTime(){
        System.out.println("What time?: ");
        this.currentTime = sc.next();
    }
    /**
     * Method to clear all old reservations
     */
    public void cleanupReservations(){
        //String date = inputDate();
        int reservationsCleared = 0;

        System.out.println("Cleaning up reservations before " + currentDate);

        for (int tableid : all_tables.keySet()){
            Table tempTable = all_tables.get(tableid);
            reservationsCleared += tempTable.cleanupReservations(currentDate);
        }

        System.out.printf("%d old reservations cleared. \n", reservationsCleared);
    }
    /**
     * @param reservation
     * Method to print all tables info
     */
    public void print_allTables(boolean reservation){

        if (reservation){
            boolean hasReservation = false;
            Table temp;
            for (int key : all_tables.keySet()){
                temp = all_tables.get(key);

                if (temp.getNumberOfReservations() > 0){
                    System.out.printf("Table %d (%d seats)\n", temp.getTable_id(), temp.getNum_seats());
                    temp.showReservations();
                    System.out.println();
                    hasReservation = true;
                }
            }

            if (!hasReservation){
                System.out.println("No reservations :(");
            }
        }
        else{
            Table temp;
            for (int key : all_tables.keySet()){
                temp = all_tables.get(key);
                System.out.printf("Table %d (%d seats)\n", temp.getTable_id(), temp.getNum_seats());
                if (temp.isOccupied()){
                    System.out.println("Occupied");
                }
                else{
                    System.out.println("Not Occupied");
                }
            }
        }
    }

    /**
     * 
     * @param action
     * @return String
     */
    private String getReserveKey(String action){
        String dateStr, reserveTimeslot;
        if (action.toLowerCase().equals("remove")){
            dateStr = inputDate();
            reserveTimeslot = getRoundedTimeslot();
        }
        else{
            dateStr = currentDate;
            reserveTimeslot = getRoundedTimeslot(currentTime);

            if (reserveTimeslot.equals("invalid")){
                return "invalid"; // ! something went wrong...
            }
        }
        return dateStr + "," + reserveTimeslot;
    }

    /**
     * @param action
     * @return int
     * Method to check the reservation and returns the table id if valid reservation, -1 if not.
     */
    public int checkReservation(String action){
        String reserveName;
        int reservedTableId;

        System.out.println("Under what name was the reservation made under?: ");
        reserveName = sc.next();
        while (true){
            System.out.println("Which table id?: ");
            try {
                reservedTableId = sc.nextInt();

                if (reservedTableId > 0 && reservedTableId < all_tables.size()){
                    // Valid tableIds
                    break;
                }
                else{
                    if (reservedTableId == - 1){
                        return -1; // Force break.
                    }
                    System.out.println("Invalid table id");
                }
            } catch (Exception e) {
                System.out.println("Invalid input, only numbers allowed");
            }
        }

        Table tempTable = all_tables.get(reservedTableId);

        String reserveKey = getReserveKey(action); // Returns a fully constructed key

        Reservation toCheck = tempTable.getReservation(reserveKey);

        if (toCheck != null && toCheck.getCustomerName().equals(reserveName)){
            // Actions here
            if (action.toLowerCase().equals("remove")){
                if (tempTable.removeReservation(reserveKey)){
                    // Reservation removed
                    System.out.println("Your reservation has been cancelled, hope to see you again in the future!");
                    return reservedTableId;
                }
                else{
                    // ! Failed to remove reservation
                    System.out.println("Failed to remove reservation, please try again.");
                    return -1;
                }
            }
            else{
                int expire = isExpired(toCheck, tempTable, reserveKey);
                if (expire == 0){
                    // Valid reservation (within 15 minutes of reservation) + table is ready.
                    // return assigned table id
                    tempTable.occupySwitch(); // Table is now occupied
                    orderList.createNewOrder(curStaff, reservedTableId, toCheck.getNumPax());
                    return reservedTableId;
                }
                return -1; // Either too early or late...
            }
        }

        else{
            // ! Reservation not found.
            // Ask for reservation time, if expired, remove it

            String retryTime = getRoundedTimeslot();

            if (calcTimeDiff(retryTime, currentTime) < -15){
                String removeReserveKey = currentDate + "," + retryTime;
                if (tempTable.isReserved(removeReserveKey) && tempTable.getReservation(removeReserveKey).getCustomerName().equals(reserveName)){
                    System.out.println("Your reservation has expired.");
                    tempTable.removeReservation(removeReserveKey);
                }

            }

            else{
                System.out.println("Reservation not found, if you think this is a mistake, please try again.");
            }
            return -1;
        }
    }

    /**
     * @param toCheck
     * @param tableToCheck
     * @param reserveKey
     * @return int
     * Method to check if the reservation under the table is expired
     */
    private int isExpired(Reservation toCheck, Table tableToCheck, String reserveKey){
        String timeToCheckAgainst = currentTime;

        if(currentDate.equals(toCheck.getReservationDate())){
            int timeDiff = calcTimeDiff(toCheck.getReservationTimeSlot(), timeToCheckAgainst);

            if (timeDiff > 15 ){
                // Way too early...
                System.out.println("Your reservation is still being held but you are too early...");
                System.out.println("Come again 15min of your reservation.");

                return 1; // ! Too early, do nothing
            }
            else if (timeDiff <= 15 && timeDiff > 0){
                // ? check if table is ready, return 0 if ready, 1 if not ready.

                if (!tableToCheck.isOccupied()){
                    // * Table is ready
                    System.out.println("Your table is ready! :)");
                    return 0; // No issues
                }

                else{
                    // ! Table is not ready.
                    System.out.println("Your reservation is still valid, but the previous guest has not left. Please try again later");
                    return 1; // ! Too early and table is not ready.
                }

            }
            else if (timeDiff <= 0 && timeDiff >= -15){
                // * perfect time, chase away previous guest if table is not ready...
                if (tableToCheck.isOccupied()){
                    // * Table is ready
                    System.out.println("We will chase the person out...");
                    orderList.generateInvoice(tableToCheck.getTable_id(), currentDate, currentTime);
                    tableToCheck.occupySwitch();
                    System.out.println("Your table is ready! :)");
                }

                return 0; // * Table WILL be ready
            }
            else{
                // ! expired >:(, remove reservation...");
                System.out.println("Your reservation has expired and is not longer valid.");
                tableToCheck.removeReservation(reserveKey);
                return -1; // ! Too late
            }
        }

        else{
            System.out.println("Its the wrong date of your reservation..."); // ! Wrong date
        }
        return 1;
    }

    /**
     * @param reservationTime Time of reservation
     * @param entryTime Time of entry
     * @return int
     * Method to return the time difference between time of reservation and time of entry
     */
    private static int calcTimeDiff(String reservationTime, String entryTime){
        int time1 = Integer.valueOf(reservationTime);
        int time2 = Integer.valueOf(entryTime);

        int hr1 = time1 / 100;
        int min1 = time1 % 100;

        int hr2 = time2 / 100;
        int min2 = time2 % 100;

        int totalMin1 = hr1 * 60 + min1;
        int totalMin2 = hr2 * 60 + min2;

        return totalMin1 - totalMin2;
    }

    /**
     * Method to serialize table list
     */
    public void serializeToFile() {
         
        // ? serialize menu to menu.dat
        try {      
            File tables_file = new File("tables.dat");
            ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(tables_file));

            output.writeObject(all_tables);
            output.flush();
            output.close();

            //System.out.println("Tables updated.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to deserialize table list
     */
    @SuppressWarnings("unchecked")
    public void deserializeFromFile() {
        // ? get menu from menu.dat
        try {
            File tables_file = new File("tables.dat");
            ObjectInputStream input = new ObjectInputStream(new FileInputStream(tables_file));

            //Reads the first object in
            Object readObject = input.readObject();
            input.close();
        
            if(!(readObject instanceof HashMap)) throw new IOException("Data is not a hashmap");
            this.all_tables = (HashMap<Integer, Table>) readObject;

            //System.out.println("Tables loaded.");
            
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
