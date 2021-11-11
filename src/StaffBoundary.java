import java.util.*;
import Classes.*;

public class StaffBoundary {
    private ArrayList<Table> all_tables;
    public static Scanner sc = new Scanner(System.in);
    /** 
     * openRestaurant() will initialize the following: 5 2-seats, 4 4-seats, 3
     * 6-seats, 2 8-seats, 1 10-seats
     */

    public void reserveTable(){
        // This method will get infomation required from the user to make a reservation
        // name, numPax, and the timeslot of their reservation. 
        String time;
        int timeInt, hrs = 0, mins = 0;
        boolean validReservation = false;
        String reserveName;
        String reserveTime;
        float reserveTimeFloat;
        int reservePax;

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
        
        do {
            System.out.println("What time would you like your reservation to be? (Hourly Slots 24hours clock): ");
            try {
                time = sc.next();
                timeInt = Integer.parseInt(time);

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
                    validReservation = true;

                    if (hrs == 19 && mins > 30){
                        validReservation = false;
                        System.out.println("We are not longer taking reservation at that time.");
                    }

                    if (mins >= 46) {
                        hrs++;
                    } else if (mins >= 16 && mins <= 45){
                        mins = 30;
                    } else if (mins > 0 && mins <= 15){
                        mins = 0;
                    }
                }
            }
            catch (Exception e){
                System.out.println("Invalid time value...");
            }
        } while (!validReservation);

        if (validReservation){
            reserveTime = ((hrs < 10)? "0"+hrs : String.valueOf(hrs)) + ((mins == 0)? "00" : String.valueOf(mins));
            reserveTimeFloat = (float) (hrs + ((mins == 30)? 0.5 : 0));

            // TODO get next available table that a) is not booked, b) is not occupied
            int tableReserved = reservationAllocator(reserveName, reservePax, reserveTimeFloat);
            if (tableReserved != -1){
                // Successful reservation
                System.out.printf("Table %d (size %d) booked under the name %s for ", tableReserved, reservePax, reserveName);
                System.out.println(reserveTime);
            }
            else{
                System.out.println("No table matched to fit your reservation.");
            }
        }
    }

    public int reservationAllocator(String reserveName, int reservePax, float reserveTimeslot) {
        
        Table tempTable;
        for (int i = 0; i < all_tables.size(); i++){
            tempTable = all_tables.get(i);
            if (tempTable.getNum_seats() >= reservePax && !tempTable.isReserved(reserveTimeslot)){
                tempTable.reserve(reserveTimeslot, reserveName);
                return tempTable.getTable_id();
            }
        }

        return -1; // Return allocation talbe, - 1 if no tables available
    }

    public void print_allTables(){
        Table temp;
        for (int i = 0; i < all_tables.size(); i++){
            temp = all_tables.get(i);

            System.out.printf("Table id: %d, size: %d\n", temp.getTable_id(), temp.getNum_seats());
        }
    }


    public void openRestaurant() {
        all_tables = new ArrayList<Table>();
        int tid_counter = 1;
        int[] num_of_each = { 5, 4, 3, 2, 1 };

        for (int i = 2; i <= 10; i += 2) {
            for (int j = num_of_each[i / 2 - 1]; j > 0; j--) {
                Table t = new Table(tid_counter, i);
                tid_counter++;
                all_tables.add(t);
            }
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
        //staffBoundary.print_allTables();

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
                orderList.createNewOrder(menu, curStaff);
                break;

            case 3:
                RRPSS.printReservationOptions();
                int ch4 = sc.nextInt();
                switch(ch4){
                    case 1:
                        //TODO Create Reservation
                        staffBoundary.reserveTable();
                        break;
                    case 2:
                        //TODO Remove Reservation
                        break;
                    case 3:
                        //TODO View Reservation
                        break;
                    default:{
                         // TODO Reask for sub options(ZW)
                         System.out.println("Invalid Options");
                        break;
                    }
                      

                }
                break;

            case 4:
                System.out.println("");
                break;

            }

        }
        
        sc.close();
        menu.serializeToFile();
        orderList.serializeToFile();
    }


}