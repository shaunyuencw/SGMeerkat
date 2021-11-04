
import java.util.*; //lololol

import Classes.*;

/**
 * AdminBoundary
 */
public class AdminBoundary {
    public AdminBoundary() {
    }

    public static void displayOptions() {
        System.out.println("-------------------\n    Admin Menu     \n-------------------");
        System.out.println("1. Add Item to Menu");
        System.out.println("2. Remove Item from Menu");
        System.out.println("3. Create new Promotion Set");
        System.out.println("4. Remove Promotion Set");
        System.out.println("5. View Menu");
        System.out.println("----------------------------------------");
        System.out.println("Enter choice (Admin): (Enter 0 to stop)");
        
    }

    @SuppressWarnings("resource")
    public static HashMap<String, MenuItem> addMenuItem(Menu menuObj) {
        HashMap<String, MenuItem> menu = menuObj.getMenu();
        //HashMap<String, PromoItems> promo_menu = menuObj.getPromo();
        Scanner sc = new Scanner(System.in);
        String name = "";
        System.out.println("---------------Adding a new MenuItem---------------");

        while (true){
            System.out.println("Enter the new item name:");
            name = sc.nextLine();
            if (menu.containsKey(name.toLowerCase())){
                System.out.println("Another item with the same name already exists");
            }
            else{
                break;
            }
        }
        
        System.out.println("Enter the new item description:");
        String desc = sc.nextLine();
        System.out.println("Enter the price of new item:");
        double price = sc.nextDouble();

        menu.put(name.toLowerCase(), new MenuItem(name, desc, price));
        return menu;
    }

    @SuppressWarnings("resource")
    public static HashMap<String, MenuItem> removeMenuItem(Menu menuObj) {
        menuObj.displayMenu();
        HashMap<String, MenuItem> menu = menuObj.getMenu();
        //HashMap<String, PromoItems> promo_menu = menuObj.getPromo();
        Scanner sc = new Scanner(System.in);
        System.out.println("---------------Removing a MenuItem---------------");
        System.out.println("Enter the item name to remove:");

        String nameToRemove = sc.nextLine();

        if (menu.containsKey(nameToRemove.toLowerCase())){
            menu.remove(nameToRemove);
            System.out.println(nameToRemove + " removed.");
        }
        else if (menu.containsKey(menuObj.getMenu_key(Integer.parseInt(nameToRemove) - 1))){
            nameToRemove = menuObj.getMenu_key(Integer.parseInt(nameToRemove) - 1);
            menu.remove(nameToRemove);
            System.out.println(nameToRemove + " removed.");
        }
        else{
            System.out.println("No such items exist");
        }
            
        return menu;
    }

    @SuppressWarnings("resource")
    public static HashMap<String, PromoItems> createNewPromo(Menu menuObj) {
        Scanner sc = new Scanner(System.in);
        HashMap<String, MenuItem> menu = menuObj.getMenu();
        HashMap<String, PromoItems> promo_menu = menuObj.getPromo();
        String name = "";

        System.out.println("---------------Adding a new Promotion set---------------");
        while (true){
            System.out.println("Enter the new set name:");
            name = sc.nextLine();

            if (promo_menu.containsKey(name.toLowerCase())){
                System.out.println("Promo Item already exists");
            }
            else{
                break;
            }
        }
        
        System.out.println("Enter the new set description:");
        String desc = sc.nextLine();
        System.out.println("Enter the price of new set:");
        double price = sc.nextDouble();

        HashMap<String, MenuItem> newPromo_map = new HashMap<String, MenuItem>();
        
        System.out.println("Enter the MenuItems to include in the set: (N to terminate)");
        System.out.println("-------------------------------------------");

        menuObj.displayMenu();
        
        while(true) {
            String temp_menuItemKey = sc.nextLine();
            if (temp_menuItemKey.equals("N")){
                break;
            }

            try{
                if (menu.containsKey(temp_menuItemKey.toLowerCase())){
                    newPromo_map.put(temp_menuItemKey.toLowerCase(), menu.get(temp_menuItemKey));
    
                    System.out.println(temp_menuItemKey + " added to " + name + " promo.");
                }
                // Check if such a index key exist
                else if (menu.containsKey(menuObj.getMenu_key(Integer.parseInt(temp_menuItemKey) - 1))){
                    temp_menuItemKey = menuObj.getMenu_key(Integer.parseInt(temp_menuItemKey) - 1);
                    newPromo_map.put(temp_menuItemKey.toLowerCase(), menu.get(temp_menuItemKey));
    
                    System.out.println(temp_menuItemKey + " added to " + name + " promo.");
                }
                else{
                    System.out.println("No such menu item.");
                }
            }

            catch (Exception e){
                System.out.println("No such menu item.");
            }
        }

        if (newPromo_map.size() != 0){
            PromoItems newPromo_item = new PromoItems(newPromo_map, name, desc, price);
            promo_menu.put(name.toLowerCase(), newPromo_item);
            System.out.println("Promo item " + name + " created.");
        }
        else{
            System.out.println("Cannot create an empty promo set!");
        }
    
        return promo_menu;

    }

    @SuppressWarnings("resource")
    public static HashMap<String, PromoItems> removePromo(Menu menuObj) {
        //HashMap<String, MenuItem> menu = menuObj.getMenu();
        HashMap<String, PromoItems> promo_menu = menuObj.getPromo();
        Scanner sc = new Scanner(System.in);
        String promoToRemove = "";
        System.out.println("---------------Removing a Promotion---------------");
        menuObj.displayPromos();

        System.out.println("Enter the promotion name to remove:");
        promoToRemove = sc.next();
        if (promo_menu.containsKey(promoToRemove.toLowerCase())){
            promo_menu.remove(promoToRemove.toLowerCase());
            System.out.println(promoToRemove + " removed.");
        }
        else if (promo_menu.containsKey(menuObj.getPromo_key(Integer.parseInt(promoToRemove) - 1))){
            promoToRemove = menuObj.getPromo_key(Integer.parseInt(promoToRemove) - 1);
            promo_menu.remove(promoToRemove.toLowerCase());
            System.out.println(promoToRemove + " removed.");
        }
        else{
            System.out.println("No such promo item.");
        }
        
        return promo_menu;
    }

}