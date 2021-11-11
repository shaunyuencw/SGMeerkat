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

    private String showFullTime(float timeslot){
        int hrs = (int) (timeslot / 1);
        float mins = timeslot % 1;
        
        String fullTime = ((hrs < 10)? "0" + hrs : String.valueOf(hrs)) + ((mins == 0.5) ? "30" : "00");
        return fullTime;
    }

    private float getRoundedTimeslot(){
        String time;
        int timeInt, hrs = 0, mins = 0;
        
        float reserveTimeFloat;

        while(true) {
            System.out.println("What time? (Hourly Slots 24hours clock): ");
            try {
                time = sc.next();
                timeInt = Integer.parseInt(time);

                if (timeInt == -1){
                    System.out.println("Going back to main menu...");
                    return -1;
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
        reserveTimeFloat = (float) (hrs + ((mins == 30)? 0.5 : 0));

        return reserveTimeFloat;
    }

    private void reserveTable(){
        // This method will get infomation required from the user to make a reservation
        // name, numPax, and the timeslot of their reservation. 
        String reserveName;
        int reservePax;
        float reserveTimeslot;

        System.out.println("Can I get a name for the reservation?: ");
        reserveName = sc.next();
        while (true){
            System.out.println("How many persons will be dining? (Max 10): ");
            try {
                reservePax = sc.nextInt();

                if (reservePax < 0 || reservePax > 10){
                    System.out.println("We do not have a table that sits that number of guest.");
                }
                else{
                    if (reservePax % 2 == 1)    reservePax++;
                    break;
                }
                
            } catch (Exception e) {
                System.out.println("Invalid input, only numbers allowed");
            }
        }
   
        reserveTimeslot = getRoundedTimeslot();

        if (reserveTimeslot != -1){
            int tableReserved = reservationAllocator(reserveName, reservePax, reserveTimeslot);
            if (tableReserved != -1){
                // Successful reservation
                Table tableView = all_tables.get(tableReserved);
                System.out.printf("Table %d (size %d) booked under the name %s for ", tableView.getTable_id(), tableView.getNum_seats(), reserveName);
                System.out.println(showFullTime(reserveTimeslot));

                tableView.showReservations();
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
        float reserveTimeslot;

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
            
        reserveTimeslot = getRoundedTimeslot();

        

        if (reserveTimeslot != -1){
            Table tempTable = all_tables.get(reservedTableId);
            System.out.println("tableid is " + tempTable.getTable_id()); // DEBUG CHECK NULL

            if (tempTable.isReserved(reserveTimeslot)){
                String checkReservedName = tempTable.getReservedName(reserveTimeslot);
                if (checkReservedName.equals(reserveName)){
                    if (tempTable.removeReservation(reserveTimeslot)){
                        System.out.println("Your reservation has been cancelled, hope to see you again in the future!");
                    }
                    else{
                        System.out.println("Something went wrong, please try again");
                    }
                }
                else{
                    System.out.println("Your reservation does not exist, please try again if you think there is a mistake.");
                }
            }
            else{
                System.out.println("Your reservation does not exist, please try again if you think there is a mistake.");
            }
        }
        else{
            return;
        }
    }


    public int reservationAllocator(String reserveName, int reservePax, float reserveTimeslot) {
        // Finds the next available table for reservation, upsize table if needed.
        // Tables are assumed to be sorted.
        Table tempTable;
        for (int key : all_tables.keySet()){
            tempTable = all_tables.get(key);
            // System.out.printf("Table: %d, size: %d, reservePax: %d\n", key, tempTable.getNum_seats(), reservePax); //DEBUG
            if (tempTable.getNum_seats() >= reservePax && !tempTable.isReserved(reserveTimeslot)){
                tempTable.reserve(reserveTimeslot, reserveName.toLowerCase()); // Reservation name will always be lower case
                return key;
            }
        }

        return -1; // Return allocation table, - 1 if no tables available
    }

    public void print_allTables(){
        Table temp;
        for (int key : all_tables.keySet()){
            temp = all_tables.get(key);
            // System.out.println("key is " + key);
            System.out.println("Table id: " + temp.getTable_id() + ", Size: " + temp.getNum_seats() + " seats.");
            temp.showReservations();
            System.out.println();
        }
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
        orderList.deserializeFromFile();
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
            RRPSS.printOptions();

            int ch = sc.nextInt();
            sc.nextLine();
            if (ch == 0) {
                break;
            }

            switch (ch) {
            case 1:
                // 1. Menu Management
                outerwhile: while (true) {
                    RRPSS.printMenuOptions();
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
                // TODO ASK IF MADE RESERVATION?
                System.out.println("Welcome, table for: ");
                int noOfCust = sc.nextInt();
                // TODO GET TABLE ID FROM NUMBER OF CUST
                int tableNo = 1;
                orderList.createNewOrder(menu, curStaff, tableNo, noOfCust);
                break;

            case 3:
                RRPSS.printReservationOptions();
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
                    default:{
                         // TODO Reask for sub options(ZW)
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
        orderList.serializeToFile();
    }


}