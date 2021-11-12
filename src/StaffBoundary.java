import java.text.*;
import java.util.*;

import Classes.*;

public class StaffBoundary {
    private HashMap<Integer, Table> all_tables;
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
            System.out.println("DEBUG 1 " + reserveKey); // DEBUG 
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

    private void removeReservation(){
        // This method will get infomation required from the user to make a reservation
        // name, numPax, and the timeslot of their reservation. 
        String reserveName;
        int reservedTableId;
        String dateStr, reserveTimeslot;

        System.out.println("Under what name was the reservation made under?: ");
        reserveName = sc.next();
        while (true){
            System.out.println("Which table id?: ");
            try {
                reservedTableId = sc.nextInt();

                if (reservedTableId < 1 || reservedTableId > all_tables.size()){
                    if (reservedTableId == -1){
                        System.out.println("Stopping reservation process...");
                        return;
                    }

                    System.out.println("No such table");
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

        if (!reserveTimeslot.equals("invalid")){
            Table tempTable = all_tables.get(reservedTableId);
            System.out.println("DEBUG 1 " + reserveKey);
            if (tempTable.isReserved(reserveKey)){
                Reservation toCheck = tempTable.getReservation(reserveKey);

                System.out.printf("toCheckName: %s, reserveName: %s\n", toCheck.getCustomerName(), reserveName.toLowerCase()); //DEBUG 2
                if (toCheck != null && toCheck.getCustomerName().equals(reserveName.toLowerCase())){
                    // Reservation found, proceed to remove
                    if (tempTable.removeReservation(reserveKey)){
                        System.out.println("Your reservation has been cancelled, hope to see you again in the future!");
                        return;
                    }

                    System.out.println("Something went wrong, please try again... "); // Should never happen?
                    return;
                }
            }
            System.out.println("Your reservation does not exist, please try again if you think there is a mistake."); 
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
        System.out.println("0. Exit RRPSS");
        System.out.println("-----------------------------------");
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
        Staff curStaff = null;
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
                    curStaff = s;
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
                    int tableNo;
                    switch (ch3) {
                        case 1:
                        case 2:
                            if(ch3 == 1){
                                // TODO ASK IF MADE RESERVATION?
                                System.out.println("Welcome, table for: ");
                                int noOfCust = sc.nextInt();
                                // TODO GET TABLE ID FROM NUMBER OF CUST
                                tableNo = noOfCust;
                                System.out.println("Your table number is " +tableNo+ ", this way please.");
                                orderList.createNewOrder(menu, curStaff, tableNo, noOfCust);
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
                        staffBoundary.removeReservation();
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

            }

        }
        
        sc.close();
        menu.serializeToFile();
    }


}