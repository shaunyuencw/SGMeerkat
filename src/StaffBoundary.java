import java.util.*;
import Classes.*;

public class StaffBoundary {
    private ArrayList<Table> all_tables;
    public static Scanner sc = new Scanner(System.in);
    /** 
     * openRestaurant() will initialize the following: 5 2-seats, 4 4-seats, 3
     * 6-seats, 2 8-seats, 1 10-seats
     */

    public int reserveTable(){
        // This 
        String time;
        int timeInt, hrs, mins;
        boolean validReservation = false;
        String reserveName;
        int reservePax;

        System.out.println("Can I get a name for the reservation?: ");
        reserveName = sc.nextLine();
        while (true){
            System.out.println("How many persons will be dining? (Max 10): ");
            try {
                reservePax = sc.nextInt();
                sc.nextLine();

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
        
        // No more walk in after 7.30pm
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
                    if (mins >= 30) {
                        hrs++;
                        if (hrs > 19){
                            validReservation = false;
                            System.out.println("We are not longer taking reservation at that time.");
                        }
                    }     
                    
                    if (mins != 0){
                        System.out.printf("We only allow reservations at XX00, your reservation will be booked at ");
                        if (hrs < 10)   System.out.printf("0" + hrs + "00.\n");
                        else            System.out.printf(hrs + "00.\n");
                    }
                }

                if (validReservation){
                    // TODO get next available table that a) is not booked, b) is not occupied
                    System.out.printf("Table i (size %d) booked under the name %s for ", reservePax, reserveName);
                    if (hrs < 10)   System.out.printf("0" + hrs + "00.\n");
                    else            System.out.printf(hrs + "00.\n");
                    return hrs;
                }

            } catch(Exception e) {
                System.out.println("Invalid time value...");
            }
        } while (!validReservation);

        return -1;
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
        Menu menu = new Menu();
        menu.deserializeFromFile();
        AllStaff allStaff = new AllStaff();
        allStaff.deserializeFromFile();
        List<Staff> staffs = allStaff.getstaffList();

        boolean login = false;
        System.out.printf("%d\n", staffBoundary.reserveTable());

        staffBoundary.openRestaurant();
        System.out.println("There are " + staffBoundary.all_tables.size() + " tables.");

        System.out.println("Good Morning, please enter your StaffID");

        while (true) {
            System.out.println("Enter a valid StaffID");
            int in = sc.nextInt();

            for (Object pass : staffs) {
                Staff s = (Staff) pass;
                if (s.getEmployeeID() == in)
                    login = true;
            }

            if (login)
                break;
        }

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
                        menu.updateMenu(RRPSS.addMenuItem(menu));
                        break;
                    case 2:
                        menu.updateMenu(RRPSS.removeMenuItem(menu));
                        break;
                    case 3:
                        // TODO EDIT MENU
                        break;
                    case 4:
                        menu.updatePromoMenu(RRPSS.createNewPromo(menu));
                        break;
                    case 5:
                        menu.updatePromoMenu(RRPSS.removePromo(menu));
                        break;
                    case 6:
                        // TODO EDIT PROMO
                        break;
                    case 7:
                        menu.viewMenu();
                        break;
                    case 8:
                        break outerwhile;
                    default: {
                        System.out.println("Invalid Options ");
                        break;
                    }
                    }
                }
                break;

            case 2:
            RRPSS.printOrderOptions();
            int ch3 = sc.nextInt();

            switch(ch3)
            {
                case 1:
                    //TODO Order (Each Order should contain a list of menuitems and promoitems, Change the order class)
                    break;
                case 2:
                    //TODO Remove item from Order
                    break;
                case 3: 
                    //TODO View Order
                    break;
                case 4: 
                    //TODO Calculate the total bill and Print Order Invoice(closing the order)
                    break;
                default:{
                    // TODO Reask for sub options(ZW)
                    System.out.println("Invalid Options");
                    break;
                }
            }
                break;

            case 3:
                RRPSS.printReservationOptions();
                int ch4 = sc.nextInt();
                switch(ch4){
                    case 1:
                        //TODO Create Reservation
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
            //TODO Sales Report
                break;

            }

        }
        
        sc.close();
        menu.serializeToFile();
    }


}