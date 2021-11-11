package Classes;

import java.io.*;
import java.util.*;

public class Menu implements DatabaseHandler{
    private HashMap<String, MenuItem> menu_map;
    private HashMap<String, PromoItems> promo_map;
    private List<String> menu_keys;
    private List<String> promo_keys;

    private static Scanner sc = new Scanner(System.in);

    public Menu(){
        menu_map = new HashMap<String, MenuItem>();
        promo_map = new HashMap<String, PromoItems>();
        menu_keys = new ArrayList<String>();
        promo_keys = new ArrayList<String>();
    };

    public void addMenuItem() {
        HashMap<String, MenuItem> menu = this.getMenu();
        // HashMap<String, PromoItems> promo_menu = menuObj.getPromo();
        String name = "";
        System.out.println("---------------Adding a new MenuItem---------------");

        while (true) {
            System.out.println("Enter the new item name:");
            name = sc.nextLine();
            if (menu.containsKey(name.toLowerCase())) {
                System.out.println("Another item with the same name already exists");
            } else {
                break;
            }
        }

        MenuItem.Type type;
        do {
            System.out.println("Enter item type: ");
            System.out.println("1. Main");
            System.out.println("2. Dessert");
            System.out.println("3. Drinks");
            int typein = sc.nextInt();
            sc.nextLine();
            if (typein == 1) {
                type = MenuItem.Type.MAIN;
                break;
            } else if (typein == 2) {
                type = MenuItem.Type.DRINKS;
                break;
            } else if (typein == 3) {
                type = MenuItem.Type.DESSERT;
                break;
            } else
                System.out.println("Invalid type...");
        } while (true);

        System.out.println("Enter the new item description:");
        String desc = sc.nextLine();
        System.out.println("desc " + desc);
        System.out.println("Enter the price of new item:");
        double price = sc.nextDouble();

        menu.put(name.toLowerCase(), new MenuItem(name, desc, price, type));
        updateMenu(menu);
    }

    public void removeMenuItem() {
        this.displayMenu();
        HashMap<String, MenuItem> menu = this.getMenu();
        // HashMap<String, PromoItems> promo_menu = menuObj.getPromo();
        System.out.println("---------------Removing a MenuItem---------------");
        System.out.println("Enter the item name to remove:");

        String nameToRemove = sc.nextLine();

        if (menu.containsKey(nameToRemove.toLowerCase())) {
            menu.remove(nameToRemove);
            System.out.println(nameToRemove + " removed.");
        } else if (menu.containsKey(this.getMenu_key(Integer.parseInt(nameToRemove) - 1))) {
            nameToRemove = this.getMenu_key(Integer.parseInt(nameToRemove) - 1);
            menu.remove(nameToRemove);
            System.out.println(nameToRemove + " removed.");
        } else {
            System.out.println("No such items exist");
        }

        updateMenu(menu);
    }

    public void createNewPromo() {
        HashMap<String, MenuItem> menu = this.getMenu();
        HashMap<String, PromoItems> promo_menu = this.getPromo();
        String name = "";

        System.out.println("---------------Adding a new Promotion set---------------");
        while (true) {
            System.out.println("Enter the new set name:");
            name = sc.nextLine();

            if (promo_menu.containsKey(name.toLowerCase())) {
                System.out.println("Promo Item already exists");
            } else {
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

        this.displayMenu();
        sc.nextLine();
        while (true) {
            String temp_menuItemKey = sc.nextLine();
            if (temp_menuItemKey.equals("N")) {
                break;
            }

            try {
                if (menu.containsKey(temp_menuItemKey.toLowerCase())) {
                    newPromo_map.put(temp_menuItemKey.toLowerCase(), menu.get(temp_menuItemKey));

                    System.out.println(temp_menuItemKey + " added to " + name + " promo.");
                }
                // Check if such a index key exist
                else if (menu.containsKey(this.getMenu_key(Integer.parseInt(temp_menuItemKey) - 1))) {
                    temp_menuItemKey = this.getMenu_key(Integer.parseInt(temp_menuItemKey) - 1);
                    newPromo_map.put(temp_menuItemKey.toLowerCase(), menu.get(temp_menuItemKey));

                    System.out.println(temp_menuItemKey + " added to " + name + " promo.");
                } else {
                    System.out.println("No such menu item.");
                }
            }

            catch (Exception e) {
                System.out.println("No such menu item.");
            }
        }

        if (newPromo_map.size() != 0) {
            PromoItems newPromo_item = new PromoItems(newPromo_map, name, desc, price);
            promo_menu.put(name.toLowerCase(), newPromo_item);
            System.out.println("Promo item " + name + " created.");
        } else {
            System.out.println("Cannot create an empty promo set!");
        }

        updatePromoMenu(promo_menu);
    }

    public void removePromo() {
        //HashMap<String, MenuItem> menu = menuObj.getMenu();
        HashMap<String, PromoItems> promo_menu = this.getPromo();
        String promoToRemove = "";
        System.out.println("---------------Removing a Promotion---------------");
        this.displayPromos();

        System.out.println("Enter the promotion name to remove:");
        promoToRemove = sc.next();
        if (promo_menu.containsKey(promoToRemove.toLowerCase())){
            promo_menu.remove(promoToRemove.toLowerCase());
            System.out.println(promoToRemove + " removed.");
        }

        else if (promo_menu.containsKey(this.getPromo_key(Integer.parseInt(promoToRemove) - 1))){
            promoToRemove = this.getPromo_key(Integer.parseInt(promoToRemove) - 1);
            promo_menu.remove(promoToRemove.toLowerCase());

            System.out.println(promoToRemove + " removed.");
        }

        updatePromoMenu(promo_menu);
    }

    public void updateMenu(HashMap<String, MenuItem> newMenu){
        this.menu_map = newMenu;
        menu_keys = new ArrayList<String>(menu_map.keySet());
    }

    public void updatePromoMenu(HashMap<String, PromoItems> newPromoMenu){
        this.promo_map = newPromoMenu;
        promo_keys = new ArrayList<String>(promo_map.keySet());
    }

    public String getMenu_key(int index){
        return menu_keys.get(index);
    }

    public String getPromo_key(int index){
        return promo_keys.get(index);
    }

    public HashMap<String, MenuItem> getMenu(){
        return menu_map;
    }

    public HashMap<String, PromoItems> getPromo(){
        return promo_map;
    }

    public void setMenu(HashMap<String, MenuItem> menu){
		this.menu_map = menu;
	}

    public void setPromo(HashMap<String, PromoItems> promo){
		this.promo_map = promo;
	}

    public void displayMenu(){
        for (int i = 0; i < menu_keys.size(); i++){
            System.out.println("(" + (i+1) + ") " + menu_map.get(menu_keys.get(i)).getName() + " - $" + menu_map.get(menu_keys.get(i)).getPrice());
        }
    }

    public void displayPromos(){
        for (int i = 0; i < promo_keys.size(); i++){
            System.out.println("(" + (i+1) + ") " + promo_map.get(promo_keys.get(i)).getName() + " - $" + promo_map.get(promo_keys.get(i)).getPrice());
        }
    }

    public void viewMenu() {
        if (menu_map.size() == 0){
            System.out.println("Menu is empty :(");
        }

        else {
            System.out.println("---------- Menu ----------");
            for (int i = 0; i < menu_keys.size(); i++){
                MenuItem menuItem = menu_map.get(menu_keys.get(i));
                System.out.println("(" + (i+1) + ") " + menuItem.getName() + " - $" + menuItem.getPrice());
                System.out.println("- " + menuItem.getDesc());
            }
            System.out.println("------------------------------");
        }

        if (promo_map.size() == 0){
            System.out.println("Promo menu is empty :(");
        }
        else {
            System.out.println("---------- Promo Sets ----------");
            for (int j = 0; j < promo_keys.size(); j++){
                PromoItems promoItem = promo_map.get(promo_keys.get(j));
                System.out.println("(" + (j+1) + ") " + promoItem.getName() + " - $" + promoItem.getPrice());
                HashMap<String, MenuItem> promoItems = promo_map.get(promoItem.getName().toLowerCase()).getPromoItems();
                for (String menuItem : promoItems.keySet()){
                    System.out.println("- " + menuItem);
                }
            }
        }
    }

    public void serializeToFile() {
         
        // ? serialize menu to menu.dat
        try {      
            File menu_file = new File("menu.dat");
            ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(menu_file));

            output.writeObject(menu_map);
            output.flush();
            output.close();

            System.out.println("Menu updated.");

        } catch (IOException e) {
            e.printStackTrace();
        }

        // ? serialize menu to menu.dat
        try {      
            File promo_file = new File("promo.dat");
            ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(promo_file));

            output.writeObject(promo_map);
            output.flush();
            output.close();

            System.out.println("Promo menu updated.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void resetDat(){
        // ? serialize menu to menu.dat
        try {      
            File menu_file = new File("menu.dat");
            ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(menu_file));

            output.writeObject("");
            output.flush();
            output.close();

            System.out.println("Menu resetted.");

        } catch (IOException e) {
            e.printStackTrace();
        }

        // ? serialize menu to menu.dat
        try {      
            File promo_file = new File("promo.dat");
            ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(promo_file));

            output.writeObject("");
            output.flush();
            output.close();

            System.out.println("Promo menu resetted.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @SuppressWarnings("unchecked")
    public void deserializeFromFile() {
        // ? get menu from menu.dat
        try {
            File menu_file = new File("menu.dat");
            ObjectInputStream input = new ObjectInputStream(new FileInputStream(menu_file));

            //Reads the first object in
            Object readObject = input.readObject();
            input.close();
        
            if(!(readObject instanceof HashMap)) throw new IOException("Data is not a hashmap");
            this.menu_map = (HashMap<String, MenuItem>) readObject;

            menu_keys = new ArrayList<String>(menu_map.keySet());

            // Prints out everything in the map.
            // for(String key : menu_map.keySet()) {
            //     System.out.println(key + ": " + menu_map.get(key));
            // }
            
        } catch (Exception e){
            System.out.println(e.getMessage());
        }


        // ? get menu from menu.dat
        try {
            File promo_file = new File("promo.dat");
            ObjectInputStream input = new ObjectInputStream(new FileInputStream(promo_file));

            //Reads the first object in
            Object readObject = input.readObject();
            input.close();
        
            if(!(readObject instanceof HashMap)) throw new IOException("Data is not a hashmap");
            this.promo_map = (HashMap<String, PromoItems>) readObject;

            promo_keys = new ArrayList<String>(promo_map.keySet());

            // Prints out everything in the map.
            // for(String key : promo_map.keySet()) {
            //     System.out.println(key + ": " + promo_map.get(key));
            // }
            
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
