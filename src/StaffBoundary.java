import java.util.*;
import Classes.*;


public class StaffBoundary {
    private ArrayList<Table> all_tables;

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
        StaffBoundary staffBoundary = new StaffBoundary();
        Menu menu = new Menu();
        menu.deserializeFromFile();
        AllStaff allStaff = new AllStaff();
        allStaff.deserializeFromFile();
        List<Staff> staffs = allStaff.getstaffList();

        Scanner sc = new Scanner(System.in);
        boolean login = false;

        staffBoundary.openRestaurant();
        System.out.println("There are " + staffBoundary.all_tables.size() + " tables.");
        
        System.out.println("Good Morning, please enter your StaffID");

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
                System.out.println("-----------------------------------");
                System.out.println("1. Create Menu Item");
                System.out.println("2. Delete Menu Item");
                System.out.println("3. Update Menu Item");
                System.out.println("4. Create Promo Item");
                System.out.println("5. Delete Promo Item");
                System.out.println("6. Update Promo Item");
                System.out.println("-----------------------------------");

                int ch2 = sc.nextInt();

                switch(ch2)
                {

                }


            
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






/* 
    openRestaurant() will initialize the following: 
    5 2-seats, 4 4-seats, 3 6-seats, 2 8-seats, 1 10-seats
*/

}