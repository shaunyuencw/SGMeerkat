
import java.util.*;

import Classes.*;

/**
 * AdminBoundary
 */
public class AdminBoundary {

    private Scanner sc;
    private HashMap<String, MenuItem> menu;
    private HashMap<String, PromoItems> promo_menu;

    public AdminBoundary(HashMap<String, MenuItem> menu, HashMap<String, PromoItems> promo_menu) {
        this.sc = new Scanner(System.in);
        this.menu = menu;
        this.promo_menu = promo_menu;
    }

    public void displayMenu(){
        for (String menu_itemKey : menu.keySet()){
            MenuItem temp = menu.get(menu_itemKey);
            System.out.println(temp.getName());
        }
    }

    public void printOptions() {
        
        // KIV
        // TODO Add Account
        // System.out.println("7. Add Account");
        // TODO Remove Account
        // System.out.println("8. Remove Account");
        do {
            System.out.println(promo_menu.size());
            System.out.println("-------------------\n    Admin Menu     \n-------------------");
            System.out.println("1. Add Item to Menu");
            System.out.println("2. Remove Item from Menu");
            System.out.println("3. Create new Promotion Set");
            System.out.println("4. Remove Promotion Set");
            System.out.println("5. Edit Menu");
            System.out.println("6. View Menu");
            System.out.println("----------------------------------------");
            System.out.println("Enter choice (Admin): (Enter 0 to stop)");
            int ch = sc.nextInt();
            sc.nextLine();
            if (ch == 0) {
                break;
            }
            switch (ch) {
            case 1:
                this.addMenuItem();
                break;
            case 2:
                this.removeMenuItem();
                break;
            case 3:
                this.createNewPromo();
                break;
            case 4:
                this.removePromo();
                break;
            // case 5: this.editMenu();
            // break;
            case 6:
                this.viewMenu();
                break;
            default:
                System.out.println("Invalid choice");
            }

        } while (true);
    }

    public void viewMenu() {

        if (menu.size() == 0){
            System.out.println("Menu is empty :(");
        }

        else {
            System.out.println("---------- Menu ----------");
            for (String menuItemKey : menu.keySet()){
                MenuItem menuItem = menu.get(menuItemKey);
                System.out.println(menuItem.getName() + ": $" + menuItem.getPrice());
                System.out.println(menuItem.getDesc());
            }
            System.out.println("------------------------------");
        }

        if (promo_menu.size() == 0){
            System.out.println("Promo menu is empty :(");
        }
        else {
            System.out.println("---------- Promo Sets ----------");
            for (String promoItemKey : promo_menu.keySet()){
                PromoItems promoItem = promo_menu.get(promoItemKey);
                System.out.println(promoItem.getName() + ": $" + promoItem.getPrice());
                HashMap<String, MenuItem> promoItems = promo_menu.get(promoItem.getName()).getPromoItems();
                for (String menuItem : promoItems.keySet()){
                    System.out.println("- " + menuItem);
                }
            }
        }
    }

    public void addMenuItem() {

        System.out.println("---------------Adding a new MenuItem---------------");
        System.out.println("Enter the new item name:");
        String name = sc.nextLine();
        System.out.println("Enter the new item description:");
        String desc = sc.nextLine();
        System.out.println("Enter the price of new item:");
        double price = sc.nextDouble();

        if (!menu.containsKey(name.toLowerCase())){
            menu.put(name, new MenuItem(desc, name, price));
        }
        else{
            System.out.println("Another item with the same name already exists");
        }
    }

    public void removeMenuItem() {

        System.out.println("---------------Removing a new MenuItem---------------");
        System.out.println("Enter the item name to remove:");

        while (true) {
            String name = sc.nextLine();
            if (name.equalsIgnoreCase("N"))
                break;

            if (!menu.containsKey(name.toLowerCase())){
                menu.remove(name);
            }
            else{
                System.out.println("No such items exist");
            }
        }
    }

    public void createNewPromo() {

        System.out.println("---------------Adding a new Promotion set---------------");
        System.out.println("Enter the new set name:");
        String name = sc.nextLine();
        System.out.println("Enter the new set description:");
        String desc = sc.nextLine();
        System.out.println("Enter the price of new set:");
        double price = sc.nextDouble();


        if (!promo_menu.containsKey(name)){
            HashMap<String, MenuItem> newPromo_map = new HashMap<String, MenuItem>();
            
            System.out.println("Enter the MenuItems to include in the set: (N to terminate)");
            System.out.println("-------------------------------------------");

            displayMenu();
            
            do {
                String temp_menuItemKey = sc.next();
                if (temp_menuItemKey.equals("N")){
                    break;
                }

                if (menu.containsKey(temp_menuItemKey.toLowerCase())){
                    newPromo_map.put(temp_menuItemKey, menu.get(temp_menuItemKey));

                    System.out.println(temp_menuItemKey + " added to " + name + " promo.");
                }
                else{
                    System.out.println("No such menu item.");
                }
            } while(true);

            promo_menu.put(name,  new PromoItems(newPromo_map, name, desc, price));
            System.out.println("Promo item " + name + " created.");
        }
        else{
            System.out.println("Promo Item already exists");
        }
    }

    public void removePromo() {

        System.out.println("---------------Removing a Promotion---------------");
        System.out.println("Enter the promotion name to remove:");

        String promoToRemove = sc.next();
        if (promo_menu.containsKey(promoToRemove)){
            promo_menu.remove(promoToRemove);
        }
        else{
            System.out.println("No such promo item.");
        }
    }

}