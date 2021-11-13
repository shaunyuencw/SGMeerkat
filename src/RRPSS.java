import java.util.*;

import Classes.*;

public class RRPSS {
    // Jumpstart
    public static Scanner sc = new Scanner(System.in);

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
    /**
     * Main User Interface 
     * @param args
     */
    public static void main(String[] args) {
        RRPSS RRPSS = new RRPSS();
        Menu menu = new Menu();
        menu.deserializeFromFile();
        AllStaff allStaff = new AllStaff();
        allStaff.deserializeFromFile();
        List<Staff> staffs = allStaff.getstaffList();
        Staff curStaff = null;
        OrderList orderList = new OrderList();
        ManageTable manageTable = new ManageTable();
        manageTable.deserializeFromFile();


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

        //manageTable.openRestaurant(); // Initialize tables

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
                    int ch1 = sc.nextInt();
                    switch (ch1) {
                    case 1:
                        menu.addMenuItem();
                        break;
                    case 2:
                        menu.removeMenuItem();
                        break;
                    case 3:
                        menu.editMenuItem();
                        break;
                    case 4:
                        menu.createNewPromo();
                        break;
                    case 5:
                        menu.removePromo();
                        break;
                    case 6:
                        menu.editPromo();
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
                // Assign Table
                // DORA
                manageTable.welcomeGuest(orderList, curStaff);
                break;
            case 3:
                // Order Management
                outerwhile: while(true) {
                    RRPSS.printOrderOptions();
                    int ch3 = sc.nextInt();
                    int tableNo = 0;
                    switch (ch3) {
                        case 1:
                            orderList.viewAllOrder();
                            tableNo = sc.nextInt();
                            if(tableNo == 0) break;
                            innerwhile: while(true){
                                RRPSS.printNewOrderOptions();
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
                        case 2:
                            orderList.viewAllOrder();
                            tableNo = sc.nextInt();
                            if(tableNo == 0) break;
                            orderList.viewCurrentOrder(tableNo);
                            break;
                        case 3:
                            orderList.viewAllOrder();
                            tableNo = sc.nextInt();
                            if(tableNo == 0) break;
                            orderList.generateInvoice(tableNo, manageTable.getCurrentDate(), manageTable.getCurrentTime());
                            manageTable.tableOccupySwitch(tableNo);
                            break;
                        case 0:
                            break outerwhile;
                        default:
                            System.out.println("Invalid Options");
                            break;
                    }
                }
                break;
            case 4:
                // Reservation Management
                RRPSS.printReservationOptions();
                int ch4 = sc.nextInt();
                switch(ch4){
                    case 1:
                        // Making a reservation
                        // Asks for reservation name, pax and time
                        manageTable.reserveTable();
                        break;
                    case 2:
                        // Removing a reservation
                        // Asks for reservation name, pax and time
                        manageTable.checkReservation("remove");
                        break;
                    case 3:
                        // TODO print all reservation today.
                        manageTable.print_allTables(true);
                        break;
                    case 4:
                        manageTable.cleanupReservations();
                        break;
                    case 5:
                        manageTable.print_allTables(false);
                        break;
                    default:{
                        System.out.println("Invalid Options");
                        break;
                    }
                }
                break;

            case 5:
                outerwhile: while(true){
                    RRPSS.printReport();
                    int ch5 = sc.nextInt();
                    switch(ch5){
                        case 1:
                            orderList.printInvoice(true);
                            break;
                        case 2:
                            orderList.printInvoice(false);
                            break;
                        case 0:
                            break outerwhile;
                        default:
                            System.out.println("Invalid Options");
                            break;
                    }
                }
                break;
            case 6:
                RRPSS.printCurrentDateTimeOptions();
                int ch6 = sc.nextInt();

                switch(ch6){
                    case 1:
                        manageTable.displayCurDateTime();
                        break;
                    case 2:
                        manageTable.changeCurrentDate();
                        break;
                    case 3:
                        manageTable.changeCurrentTime();
                        break;
                }
                break;
            }     
        }
        sc.close();
        manageTable.cleanupReservations();
        manageTable.serializeToFile();
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
        System.out.println("0. Exit to Main");
        System.out.println("-----------------------------------");
    }

    public void printOrderOptions(){
        System.out.println("---------------Select Order Options---------------");
        System.out.println("1. Create/Edit Order");
        System.out.println("2. View Order");
        System.out.println("3. Print Order Invoice (this will close the order)");
        System.out.println("0. Exit to Main");
        System.out.println("--------------------------------------------------");
    }

    public void printNewOrderOptions(){
        System.out.println("---------------Order---------------");
        System.out.println("1. Order Ala Carte");
        System.out.println("2. Remove Ala Carte");
        System.out.println("3. Order Set Package");
        System.out.println("4. Remove Set Package");
        System.out.println("5. View Order");
        System.out.println("0. End Order");
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
        System.out.println("2. Assign Table");
        System.out.println("3. Order Management");
        System.out.println("4. Reservation Management");
        System.out.println("5. Reports");
        System.out.println("6. Change Current Date or Time");
        System.out.println("0. Close Restaurant");
        System.out.println("-----------------------------------");
    }

    public void printCurrentDateTimeOptions(){
        System.out.println("-----------------------------------");
        System.out.println("1. Display Current Date and Time");
        System.out.println("2. Change Date");
        System.out.println("3. Change Time");
        System.out.println("0. Exit to Main");
        System.out.println("-----------------------------------");
    }

    public void printReport(){
        System.out.println("-----------------------------------");
        System.out.println("1. Print by Day");
        System.out.println("2. Print by Month");
        System.out.println("0. Exit to Main");
        System.out.println("-----------------------------------");
    }

    // * MENU ITEMS END

}