// import java.util.*;
// import Classes.*;

// public class StaffBoundary {
//     private Scanner sc;

//     public StaffBoundary(){
//         this.sc = new Scanner(System.in);
//     }

//     public void printOptions(){
//         System.out.println("-------------------\n    Staff Menu     \n-------------------");
//         System.out.println("1. Create Reservation");
//         System.out.println("2. Remove Rervation");
//         System.out.println("3. Order");
//         System.out.println("4. Cancel item from Order");
//         System.out.println("5. Print Order Invoice");
//         System.out.println("6. View Order");

//         while (true){
//             System.out.println("----------------------------------------");
//             System.out.println("Enter choice (Staff): (Enter 0 to stop)");
//             int ch = sc.nextInt();
//             sc.nextLine();
//             if (ch == 0){
//                 break;
//             }
//             switch(ch){
//                 case 1: this.addMenuItem();
//                         break;
//                 case 2: this.removeMenuItem();
//                         break;
//                 //case 3: this.createNewPromo();
//                         //break;
//                // case 4: this.removePromo();
//                        // break;
//                // case 5: this.editMenu();
//                         //break;
//                 case 6: this.viewMenu();
//                         break;
//                 default: System.out.println("Invalid choice");
//             }

//         }
    
// }
// }
