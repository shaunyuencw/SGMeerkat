import java.util.*;
import Classes.*;


public class StaffBoundary {
    /*
        1. Menu Management
        2. Order Management
        3. Reservation Management
        4. Reports

        1.1 - 3 CUD menu_items
        1.4 - 6 CUD promo_items

        2.1 Seat guest -> (Create order)
        2.2-3 UD items in order
        
        3.1 Create Reservation
        3.2 - 3 RD Reservation
        12 Check if table is available
        13 Print Order invoice (Order will be closed)
        14 Print Sales report
    */

    public static void main(String[] args) {

        Menu menu = new Menu();
        menu.deserializeFromFile();
        AllStaff allStaff = new AllStaff();
        allStaff.deserializeFromFile();
        List<Staff> staffs = allStaff.getstaffList();

        Scanner sc = new Scanner(System.in);
        boolean login = false;
        System.out.println("Good Morning Please Enter StaffID");

        while(true)
        {
            System.out.println("Enter a valid StaffID");
            int in = sc.nextInt();

            for (Object pass: staffs){

                Staff s = (Staff) pass;
                if (s.getEmployeeID() == in)
                    login = true;
            }

            if(login)
                break;
        }


        while(true)
        {
            RRPSS.printOptions();
    
            int ch = sc.nextInt();
            sc.nextLine();
            if (ch == 0) {
                break;
            }

            switch(ch){
                case 1:
                //1. Menu Management
            
                     break;

                 case 2:
                 //2. Order Management
                     
                     break;

                 case 3:
                 //3. Reservation Management
                   
                     break;

                 case 4:
                 //4. Reports   
                    
                 default:
                     System.out.println("Invalid Options");
                     break; 
             }
 



















        }







    }

}
