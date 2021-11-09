import java.util.*;

import Classes.*;

/**
 * RRPSS_Login_Boundary
 */
public class RRPSS_Login_Boundary {
    private ArrayList<Table> all_tables;

    /* 
        openRestaurant() will initialize the following: 
        5 2-seats, 4 4-seats, 3 6-seats, 2 8-seats, 1 10-seats
    */
    public void openRestaurant(){
        all_tables = new ArrayList<Table>(); 
        int tid_counter = 1;
        int[] num_of_each = {5, 4, 3, 2, 1};

        for (int i = 2; i <= 10; i += 2){
            for (int j = num_of_each[i/2 - 1]; j > 0; j--){
                Table t = new Table(tid_counter, i);
                tid_counter++;
                all_tables.add(t);
            }
        }
    }

    public static void main(String[] args) {

        RRPSS_Login_Boundary rrpss = new RRPSS_Login_Boundary();
        // Ah Huang was here!
        rrpss.openRestaurant();
        System.out.print("There are " + rrpss.all_tables.size() + " tables.");
        System.out.println();
        Scanner sc = new Scanner(System.in);
        Menu menu = new Menu();
        AllAccountDetails accounts = new AllAccountDetails();
        

        accounts.deserializeFromFile();
        menu.deserializeFromFile();
        Login_Controller loginController = new Login_Controller(((AllAccountDetails) accounts).getAccounts());

        System.out.println("Log in Domain: ");
        System.out.println("1. Staff");
        System.out.println("2. Admin");

        while (true) {
            System.out.print("Enter EmployeeID(0 to stop) ");
            
            int num = sc.nextInt();

            if (num == 0){
                break;
            }

            else if (num == 1 || num == 2){
                System.out.print("Network username or new username: ");
                String userName = sc.next();
                System.out.print("Password: ");
                String password = sc.next();

                boolean logInTry = loginController.checkPassword(userName, password, num);

                if (logInTry == false) {
                    System.out.println("Incorrect password/ username/ domain details.");
                } 
                else {
                    if (num == 1) {
                        // ? StaffBoundaryClass
                        while (true){
                            // TODO Somehow get and retrieve past orders
                            StaffBoundary.printOptions();
                            int ch = sc.nextInt();
                            sc.nextLine();
                            if (ch == 0){
                                break;
                            }
    
                            switch(ch){
                               case 1:
                                    // Create Reservation
                                    StaffBoundary.createReservation();
                                    break;

                                case 2:
                                    // Remove Reservation
                                    StaffBoundary.removeReservation();
                                    break;

                                case 3:
                                    // Create order
                                    StaffBoundary.createOrder();
                                    break;

                                case 4:
                                    // Cancel item from Order
                                    StaffBoundary.cancelOrder();
                                    break;
                                
                                case 5:
                                    // Print Order invoice
                                    StaffBoundary.printInvoice();
                                    break;

                                case 6:
                                    // View Order
                                    StaffBoundary.viewOrder();
                                    break;

                                case 7:
                                    //view menu
                                    menu.viewMenu();
                                    break;

                                default:
                                    System.out.println("Invalid Options");
                                    break; 
                            }
                
                        }
                    } 
                    else {
                         // ? Admin features
                         do {
                            AdminBoundary.displayOptions();
                            int ch = sc.nextInt();
                            sc.nextLine();
                            if (ch == 0) {
                                break;
                            }
                            switch (ch) {
                            case 1:
                                // Adding items to menu
                                menu.updateMenu(AdminBoundary.addMenuItem(menu));
                                break;

                            case 2:
                                // Removing items from menu
                                menu.updateMenu(AdminBoundary.removeMenuItem(menu));
                                break;

                            case 3:
                                // Create new Promo set
                                menu.updatePromoMenu(AdminBoundary.createNewPromo(menu));
                                break;

                            case 4:
                                // Delete a promo set
                                menu.updatePromoMenu(AdminBoundary.removePromo(menu));
                                break;

                            case 5:
                                menu.viewMenu();
                                break;

                            default:
                                System.out.println("Invalid choice");
                            }
                
                        } while (true);
                    }
                }
            }
            
            else{
                System.out.println("Invalid input");
                continue;
            }
        }
        sc.close();
        menu.serializeToFile();
    }
    

}