
import java.util.*;
import java.text.ParseException;

import Classes.*;


/**
 * AdminBoundary
 */
public class AdminBoundary {

    private Scanner sc;
    private ArrayList<MenuItem> menu;
    private ArrayList<PromoItems> promomenu;


    public AdminBoundary(ArrayList<MenuItem> menu, ArrayList<PromoItems> promomenu){
        this.sc = new Scanner(System.in);
        this.menu = menu;
        this.promomenu = promomenu;
    }


    public void printOptions(){
        System.out.println(promomenu.size());
        System.out.println("-------------------\n    Admin Menu     \n-------------------");
        System.out.println("1. Add Item to Menu");
        System.out.println("2. Remove Item from Menu");
        System.out.println("3. Create new Promotion Set");
        System.out.println("4. Remove Promotion Set");
        System.out.println("5. Edit Menu");
        System.out.println("6. View Menu");
        //KIV
        //System.out.println("6. Add Staff");
        //System.out.println("7. Remove Staff");
        while (true){
            System.out.println("----------------------------------------");
            System.out.println("Enter choice (Admin): (Enter 0 to stop)");
            int ch = sc.nextInt();
            sc.nextLine();
            if (ch == 0){
                break;
            }
            switch(ch){
                case 1: this.addMenuItem();
                        break;
                case 2: this.removeMenuItem();
                        break;
                case 3: this.createNewPromo();
                        //break;
               // case 4: this.removePromo();
                       // break;
               // case 5: this.editMenu();
                        //break;
                case 6: this.viewMenu();
                        break;
                default: System.out.println("Invalid choice");
            }

        }
    }


    public void addMenuItem(){
      
            System.out.println ("---------------Adding a new MenuItem---------------");
            System.out.println("Enter the new item name:");
            String name = sc.nextLine();
            System.out.println("Enter the new item description:");
            String desc = sc.nextLine();
            System.out.println("Enter the price of new item:");
            double price = sc.nextDouble();
            
            boolean exist = false;
            for (Object pass: menu){
                MenuItem m = (MenuItem) pass;
                if (m.getname() == name)
                    {System.out.println("Another item with the same name already exists");
                    exist = true;}
                
            }
        
            if(exist == false){
            MenuItem newitem = new MenuItem(desc, name, price);
            menu.add(newitem);
            System.out.println("Menu item added");
            }
        
    }

    public void viewMenu(){
        System.out.println("-------------\n         Menu:\n-------------");
        for(int i =0; i<menu.size();i++)
        {
            System.out.println((i+1) + ": " + menu.get(i).getname() +"------ $"+ menu.get(i).getprice());
            System.out.println(menu.get(i).getdesc());
        }
        System.out.println("-------------\n    Promotion Sets:\n-------------");
        for(int i =0; i<promomenu.size();i++)
        {
            System.out.println((i+1) + ": " + promomenu.get(i).getname() +"------ $"+ promomenu.get(i).getprice());
            System.out.println(promomenu.get(i).getdesc());
            for(int y =0; y< promomenu.get(i).getpromoitems().size(); y++)
            {
                System.out.println("-" +promomenu.get(i).getpromoitems().get(y).getname());
            }
            System.out.println("---------------------------------");
        }

    }

    public void removeMenuItem(){
      
        System.out.println ("---------------Removing a new MenuItem---------------");
        System.out.println("Enter the item name to remove:");
        String name = sc.nextLine();
        
        boolean removed  = false;

        for (Object pass: menu){
            MenuItem m = (MenuItem) pass;
            System.out.println("Item in Menu:" + m.getname());
            System.out.println("Item tryign to delete:"+ name);
            if (m.getname() == name)
                {  menu.remove(m);
                    removed = true;
                }
        }
     
        if(removed == false){
        System.out.println("Item does not exist");
        }
    
}

public void createNewPromo(){
      
    System.out.println ("---------------Adding a new Promotion set---------------");
    System.out.println("Enter the new set name:");
    String name = sc.nextLine();
    System.out.println("Enter the new set description:");
    String desc = sc.nextLine();
    System.out.println("Enter the price of new set:");
    double price = sc.nextDouble();
    
    boolean exist = false;
    for (Object pass: menu){
        MenuItem m = (MenuItem) pass;
        if (m.getname() == name)
            {System.out.println("Another item with the same name already exists");
            exist = true;}
        
    }

    if(exist == false){
    MenuItem newitem = new MenuItem(desc, name, price);
    menu.add(newitem);
    System.out.println("Menu item added");
    }

}

}