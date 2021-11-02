import java.util.Scanner;

import Classes.*;

/**
 * RRPSS_Login_Boundary
 */
public class RRPSS_Login_Boundary {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        DataBaseHandler db = new AllAccountDetails();
        DataBaseHandler db2 = new Menu();
        DataBaseHandler db3 = new Menu();
        db.deserializeFromFile();
        db2.deserializeFromFile();
        db3.deserializeFromFile();
        Login_Controller loginController = new Login_Controller(((AllAccountDetails)db).getAccounts());
        
       
        System.out.println("Log in Domain: ");
        System.out.println("1. Staff");
        System.out.println("2. Admin");

        boolean flg = true;

        while(flg){
            System.out.print("Select an option: (0 to stop) ");
            int num= sc.nextInt();

            if (num == 0) break;

            if (num !=1 && num != 2) {
                System.out.println("Invalid input");
                continue;
            }

            System.out.print("Network username or new username: ");
            String userName = sc.next();
            System.out.print("Password: ");
            String password = sc.next();

            boolean logInTry = loginController.checkPassword(userName, password, num);

            if (logInTry == false){
                System.out.println("Incorrect password/ username/ domain details.");
            }
            else{
                flg = false;
                if (num == 2){
                     AdminBoundary adminBoundary = new AdminBoundary(((Menu)db2).getmenu(),((Menu)db3).getpromo());
                     adminBoundary.printOptions();
                }
                else{

                    //StaffBoundaryClass

                }











        }
    }
}
}