import java.text.*;
import java.util.*;

import Classes.*;

public class StaffBoundary {
    // Jumpstart
    
    private HashMap<Integer, Table> all_tables;
    private Staff curStaff = null;
    private OrderList orderList = new OrderList();
    private String currentDate = "12-11-2021";
    private String currentTime = "1048";

    public static Scanner sc = new Scanner(System.in);
    /** 
     * openRestaurant() will initialize the following: 5 2-seats, 4 4-seats, 3
     * 6-seats, 2 8-seats, 1 10-seats
     */

    public void openRestaurant() {
        all_tables = new HashMap<Integer, Table>();
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

        return hrs + "" + mins;
    }

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

    private void reserveTable(){
        // This method will get infomation required from the user to make a reservation
        // name, numPax, and the timeslot of their reservation. 
        String reservationName, reservationContact;
        String dateStr, reserveTimeslot;
        int reservePax;

        System.out.println("Can I get a name for the reservation?: ");
        reservationName = sc.next();

        System.out.println("Contact number for the reservation?: ");
        reservationContact = sc.next();

        while (true){
            System.out.println("How many persons will be dining? (Max 10): ");
            try {
                reservePax = sc.nextInt();

                if (reservePax < 0 || reservePax > 10){
                    System.out.println("We do not have a table that sits that number of guest.");
                    continue;
                }
                break;
                
            } catch (Exception e) {
                System.out.println("Invalid input, only numbers allowed");
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

    public int reservationAllocator(String reservationKey, Reservation toReserve) {
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

    

    public void cleanupReservations(){
        String date = inputDate();
        int reservationsCleared = 0;

        System.out.println("Cleaning up reservations before " + date);

        for (int tableid : all_tables.keySet()){
            Table tempTable = all_tables.get(tableid);
            reservationsCleared += tempTable.cleanupReservations(date);
        }

        System.out.printf("%d old reservations cleared. \n", reservationsCleared);
    }

    public void print_allTables(){
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

    // DIEGO


    // Checks the reservation and returns the table id if valid reservation, -1 if not.
    private int checkReservation(String action){
        String reserveName;
        int reservedTableId;
        String dateStr, reserveTimeslot;

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

        if (action.toLowerCase().equals("remove")){
            dateStr = inputDate();
            reserveTimeslot = getRoundedTimeslot();
        }
        else{
            dateStr = currentDate;
            reserveTimeslot = getRoundedTimeslot(currentTime);

            if (reserveTimeslot.equals("invalid")){
                return -1; // ! something went wrong...
            }
        }

        String reserveKey = dateStr + "," + reserveTimeslot;

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
                // TODO check if reservation is expired
                int expire = isExpired(toCheck, tempTable, reserveKey, false);
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

    private int isExpired(Reservation toCheck, Table tableToCheck, String reserveKey, boolean retry){
        String timeToCheckAgainst;
        if (!retry){
            timeToCheckAgainst = currentTime;
        }
        else{
            timeToCheckAgainst = getRoundedTimeslot();
        }

        if(currentDate.equals(toCheck.getReservationDate())){
            int timeDiff = calcTimeDiff(toCheck.getReservationTimeSlot(), timeToCheckAgainst);

            System.out.println("Time difference is " + timeDiff); // DEBUG
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
    
            else if (timeDiff >= 0 && timeDiff <= -15){
                // * perfect time, chase away previous guest if table is not ready...
                if (tableToCheck.isOccupied()){
                    // * Table is ready
                    // TODO chase previous guest away and seat new guest.
                    tableToCheck.occupySwitch(); // Table is not longer occupied
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
            System.out.println("Its the wrong date of your reservation...");
            
            // ! Wrong date
        }
        
        
        return 1;
    }

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

    private void welcomeGuest() {
        int tableAssigned = -1;
        char option;
        boolean hasReservation = false;

        System.out.println("Welcome to RRPSS!");
        System.out.println("Do you have a reservation? (Y/N)");
        option = sc.next().charAt(0);

        if (option == 'Y' || option == 'y'){
            tableAssigned = checkReservation("check");
            if (tableAssigned != -1){
                hasReservation = true;
            }
            else{
                return;
            }
        }

        if (!hasReservation){
            // Guest does not have a reservtion, proceed too allocate
            // TODO Ask how many people
        }
        else{
            System.out.println("Your table number is " +tableAssigned+ ", this way please.");
        }
    }

    private void changeCurrentDate(){
        // TODO Change this.currentDate
    }

    private void changeCurrentTime(){
        // TODO Change this.currentTime
    }

    /*
     * 1. Menu Management 2. Order Management 3. Reservation Management 4. Reports
     * 
     * 1.1 - 3 CUD menu_items 1.4 - 6 CUD promo_items
     * 
     * 2.1 Seat guest -> (Create order) 2.2-3 UD items in order
     * 
     * 3.1 Create Reservation 3.2 - 3 RD Reservation 12 Check if table is available
     * 13 Print Order invoice (Order will be closed) 14 Print Sales report
     */

    public static void main(String[] args) {
        StaffBoundary staffBoundary = new StaffBoundary();
        Menu menu = new Menu();
        menu.deserializeFromFile();
        OrderList orderList = new OrderList();
        AllStaff allStaff = new AllStaff();
        allStaff.deserializeFromFile();
        List<Staff> staffs = allStaff.getstaffList();

        boolean login = false;

        System.out.println("Good Morning, please enter your StaffID");

        while (true) {
            System.out.println("Enter a valid StaffID");
            int in;

            try{
                in = sc.nextInt();
            } catch (Exception e){
                sc.nextLine();
                continue;
            }

            for (Object pass : staffs) {
                Staff s = (Staff) pass;
                if (s.getEmployeeID() == in){
                    staffBoundary.curStaff = s;
                    login = true;
                }
            }

            if (login)
                break;
        }

        staffBoundary.openRestaurant(); // Initialize tables
        staffBoundary.print_allTables();

        while (true) {
            staffBoundary.printOptions();

            int ch = sc.nextInt();
            sc.nextLine();
            if (ch == 0) {
                break;
            }

            switch (ch) {
            case 1:
                // 1. Menu Management
                outerwhile: while (true) {
                    staffBoundary.printMenuOptions();
                    int ch2 = sc.nextInt();
                    switch (ch2) {
                    case 1:
                        menu.addMenuItem();
                        break;
                    case 2:
                        menu.removeMenuItem();
                        break;
                    case 3:
                        // TODO EDIT MENU
                        break;
                    case 4:
                        menu.createNewPromo();
                        break;
                    case 5:
                        menu.removePromo();
                        break;
                    case 6:
                        // TODO EDIT PROMO
                        break;
                    case 7:
                        menu.viewMenu();
                        break;
                    case 0:
                        break outerwhile;
                    default: {
                        System.out.println("Invalid Options ");
                        break;
                    }
                    }
                }
                break;

            case 2:
                outerwhile: while(true) {
                    staffBoundary.printOrderOptions();
                    int ch3 = sc.nextInt();
                    int tableNo = 0;
                    switch (ch3) {
                        case 1:
                        case 2:
                            if(ch3 == 1){
                                // DORA
                                staffBoundary.welcomeGuest();
                                
                            } else {
                                orderList.viewAllOrder();
                                tableNo = sc.nextInt();
                                if(tableNo == 0) break;
                            }
                            innerwhile: while(true){
                                staffBoundary.printNewOrderOptions();
                                int ch31 = sc.nextInt();
                                switch (ch31){
                                    case 1:
                                        orderList.orderAlaCarte(menu, tableNo);
                                        break;
                                    case 2:
                                        orderList.removeAlaCarte(tableNo);
                                        break;
                                    case 3:
                                        orderList.orderSetPackage(menu, tableNo);
                                        break;
                                    case 4:
                                        orderList.removeSetPackage(tableNo);
                                        break;
                                    case 5:
                                        orderList.viewCurrentOrder(tableNo);
                                        break;
                                    case 0:
                                        break innerwhile;
                                    default:
                                        System.out.println("Invalid Options");
                                        break;
                                }
                            }
                            break;
                        case 3:
                            orderList.viewAllOrder();
                            tableNo = sc.nextInt();
                            if(tableNo == 0) break;
                            orderList.viewCurrentOrder(tableNo);
                            break;
                        case 4:
                            orderList.viewAllOrder();
                            tableNo = sc.nextInt();
                            if(tableNo == 0) break;
                            orderList.generateInvoice(tableNo);
                            break;
                        case 0:
                            break outerwhile;
                        default:
                            System.out.println("Invalid Options");
                            break;
                    }
                }
                break;
            case 3:
                staffBoundary.printReservationOptions();
                int ch4 = sc.nextInt();
                switch(ch4){
                    case 1:
                        // Making a reservation
                        // Asks for reservation name, pax and time
                        staffBoundary.reserveTable();
                        break;
                    case 2:
                        // Removing a reservation
                        // Asks for reservation name, pax and time
                        staffBoundary.checkReservation("remove");
                        break;
                    case 3:
                        staffBoundary.print_allTables();
                        break;
                    case 4:
                        staffBoundary.cleanupReservations();
                        break;
                    default:{
                        System.out.println("Invalid Options");
                        break;
                    }
                }
                break;

            case 4:
                // TODO print revenue
                break;

            case 5:
                staffBoundary.printCurrentDateTimeOptions();
                int ch5 = sc.nextInt();

                switch(ch5){
                    case 1:
                        staffBoundary.changeCurrentDate();
                        break;
                    case 2:
                        staffBoundary.changeCurrentTime();
                        break;
                }
                // TODO admin functions to change currentDate and currentTime
                break;
            }     
        }
        sc.close();
        menu.serializeToFile();
    }

    // * MENU ITEMS START 
        
    

    public void printMenuOptions() {
        System.out.println("-------Select Menu Options--------");
        System.out.println("1. Create Menu Item");
        System.out.println("2. Delete Menu Item");
        System.out.println("3. Update Menu Item");
        System.out.println("4. Create Promo Item");
        System.out.println("5. Delete Promo Item");
        System.out.println("6. Update Promo Item");
        System.out.println("7. View Menu");
        System.out.println("0. Exit Menu Options");
        System.out.println("-----------------------------------");
    }

    public void printOrderOptions(){
        System.out.println("---------------Select Order Options---------------");
        System.out.println("1. Create New Order");
        System.out.println("2. Edit Order");
        System.out.println("3. View Order");
        System.out.println("4. Print Order Invoice (this will close the order)");
        System.out.println("0. Exit Menu Options");
        System.out.println("--------------------------------------------------");
    }

    public void printNewOrderOptions(){
        System.out.println("---------------Order---------------");
        System.out.println("1. Order Ala Carte");
        System.out.println("2. Remove Ala Carte");
        System.out.println("3. Order Set Package");
        System.out.println("4. Remove Set Package");
        System.out.println("5. View Order");
        System.out.println("0. Complete Order");
        System.out.println("-----------------------------------");
    }

    public void printReservationOptions(){
        System.out.println("-----------Reservation------------");
        System.out.println("1. Create Reservation");
        System.out.println("2. Remove Reservation");
        System.out.println("3. View Reservation");
        System.out.println("4. Cleanup Reservations");
        System.out.println("-----------------------------------");
    }

    public void printOptions() {
        System.out.println("-----------------------------------");
        System.out.println("1. Menu Management");
        System.out.println("2. Order Management");
        System.out.println("3. Reservation Management");
        System.out.println("4. Reports");
        System.out.println("5. Change current date or time");
        System.out.println("0. Exit RRPSS");
        System.out.println("-----------------------------------");
    }

    public void printCurrentDateTimeOptions(){
        System.out.println("-----------------------------------");
        System.out.println("1. Change Date");
        System.out.println("2. Change Time");
        System.out.println("0. Back to RRPSS");
        System.out.println("-----------------------------------");
    }

    // * MENU ITEMS END

}