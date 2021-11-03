import java.util.*;

import Classes.*;

/**
 * RRPSS_Login_Boundary
 */
public class RRPSS_Login_Boundary {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        DataBaseHandler acccount_db = new AllAccountDetails();
        DataBaseHandler db2 = new Menu();
        acccount_db.deserializeFromFile();
        db2.deserializeFromFile();
        Login_Controller loginController = new Login_Controller(((AllAccountDetails) acccount_db).getAccounts());

        System.out.println("Log in Domain: ");
        System.out.println("1. Staff");
        System.out.println("2. Admin");

        while (true) {
            System.out.print("Select an option: (0 to stop) ");
            
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
                    } 
                    else {
                         // ? AdminBoundaryClass
                         AdminBoundary adminBoundary = new AdminBoundary(((Menu) db2).getmenu(), ((Menu) db2).getpromo());
                         adminBoundary.printOptions();
                        

                    }
                    break;
                }
            }
            
            else{
                System.out.println("Invalid input");
                continue;
            }
        }

        db2.serializeToFile();
    }
}