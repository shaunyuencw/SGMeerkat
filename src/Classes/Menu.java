package Classes;

import java.io.*;
import java.util.*;
/**
 * The main Menu of the restaurant containing all the menuitems and promotional items
 */
public class Menu implements DatabaseHandler{
    private HashMap<String, MenuItem> menu_map;
    private HashMap<String, PromoItems> promo_map;
    private List<String> menu_keys;
    private List<String> promo_keys;

    private static Scanner sc = new Scanner(System.in);
    /**
     * Default constructor for the menu
     */
    public Menu(){
        menu_map = new HashMap<>();
        promo_map = new HashMap<>();
        menu_keys = new ArrayList<>();
        promo_keys = new ArrayList<>();
    }

    /**
     * Method to add a new menuitem to the menu
     */
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
            System.out.println("2. Drinks");
            System.out.println("3. Dessert");
            int typein;
            while(true){
                try{
                    typein = sc.nextInt();
                    sc.nextLine();
                    break;
                } catch(Exception e){
                    System.out.println("Invalid input.");
                }
                sc.nextLine();
            }
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
        System.out.println("Enter the price of new item:");
        double price;
        while(true){
            try{
                price = sc.nextDouble();
                sc.nextLine();
                break;
            } catch(Exception e){
                System.out.println("Invalid input.");
            }
            sc.nextLine();
        }
        menu.put(name.toLowerCase(), new MenuItem(name, desc, price, type));
        updateMenu(menu);
    }

    /**
     * Method to remove an existing menuitem to the menu
     */
    public void removeMenuItem() {
        this.displayMenu();
        HashMap<String, MenuItem> menu = this.getMenu();
        // HashMap<String, PromoItems> promo_menu = menuObj.getPromo();
        System.out.println("---------------Removing a MenuItem---------------");
        System.out.println("Enter the item number to remove:");

        try{
            String nameToRemove = sc.nextLine();
            if (menu.containsKey(this.getMenu_key(Integer.parseInt(nameToRemove) - 1))) {
                nameToRemove = this.getMenu_key(Integer.parseInt(nameToRemove) - 1);
                menu.remove(nameToRemove);
                System.out.println(nameToRemove + " removed.");
            } else {
                System.out.println("No such items exist.");
            }
        } catch (Exception e){
            System.out.println("Invalid input.");
        }

        updateMenu(menu);
    }
    
    /**
     * Method to edit an existing menuitem from the menu
     */
    public void editMenuItem(){
        System.out.println("Menu Items");
        System.out.println("---------------------------------");
        displayMenu();
        System.out.println("Select menu item to edit: (N to terminate)");
        String selection;
        String name;
        try{
            selection = sc.nextLine();
            if(selection.equals("N")) return;
            name = menu_keys.get(Integer.parseInt(selection)-1);
        } catch (Exception e){
            System.out.println("Invalid input.");
            return;
        }
        menu_map.remove(name);
        addMenuItem();
    }

     /**
     * Method to add a new promotional item to the menu
     */
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
        double price;
        while(true){
            try{
                price = sc.nextDouble();
                sc.nextLine();
                break;
            } catch(Exception e){
                System.out.println("Invalid input.");
            }
            sc.nextLine();
        }
        HashMap<String, MenuItem> newPromo_map = new HashMap<String, MenuItem>();

        System.out.println("Enter the MenuItems number to include in the set: (N to terminate)");
        System.out.println("-------------------------------------------");

        this.displayMenu();
        while (true) {
            String temp_menuItemKey = sc.nextLine();
            if (temp_menuItemKey.equals("N")) {
                break;
            }

            try {
                if (menu.containsKey(this.getMenu_key(Integer.parseInt(temp_menuItemKey) - 1))) {
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
    
     /**
     * Method to remove an existing promotional item from the menu
     */
    public void removePromo() {
        //HashMap<String, MenuItem> menu = menuObj.getMenu();
        HashMap<String, PromoItems> promo_menu = this.getPromo();
        System.out.println("---------------Removing a Promotion---------------");
        this.displayPromos();

        System.out.println("Enter the promotion name to remove:");

        try{
            String promoToRemove = sc.nextLine();
            if (promo_menu.containsKey(this.getPromo_key(Integer.parseInt(promoToRemove) - 1))) {
                promoToRemove = this.getPromo_key(Integer.parseInt(promoToRemove) - 1);
                promo_menu.remove(promoToRemove.toLowerCase());
                System.out.println(promoToRemove + " removed.");
            } else {
                System.out.println("No such items exist.");
            }
        } catch (Exception e){
            System.out.println("Invalid input.");
        }

        updatePromoMenu(promo_menu);
    }

    /**
     * Method to edit an existing promotional item from the menu
     */
    public void editPromo(){
        System.out.println("Promo Items");
        System.out.println("---------------------------------");
        displayPromos();
        System.out.println("Select promo item to edit: (N to terminate)");
        String selection;
        String name;
        try{
            selection = sc.nextLine();
            if(selection.equals("N")) return;
            name = promo_keys.get(Integer.parseInt(selection)-1);
        } catch (Exception e){
            System.out.println("Invalid input.");
            return;
        }
        menu_map.remove(name);
        addMenuItem();
    }

    
    /** 
     * fetch and update the menuitems in the menu
     * @param newMenu hashmap of menuitems
     */
    public void updateMenu(HashMap<String, MenuItem> newMenu){
        this.menu_map = newMenu;
        menu_keys = new ArrayList<String>(menu_map.keySet());
        serializeToFile();
    }

    
    /** 
     * fetch and update the promotional items in the menu
     * @param newPromoMenu hashmap of promotional items
     */
    public void updatePromoMenu(HashMap<String, PromoItems> newPromoMenu){
        this.promo_map = newPromoMenu;
        promo_keys = new ArrayList<String>(promo_map.keySet());
        serializeToFile();
    }

    
    /** 
     * Method to obtain hashmapkey
     * @param index index of an item
     * @return String, Returns the key of the hashmap for menuitems
     */
    public String getMenu_key(int index){
        return menu_keys.get(index);
    }

    
    /** 
     * Method to obtain hashmapkey
     * @param index index of an item
     * @return String, returns the key of the hashmap for promotional items
     */
    public String getPromo_key(int index){
        return promo_keys.get(index);
    }

    
    /** 
     * Method to fetch all menuitems in the menu
     * @return HashMap<String, MenuItem>
     */
    public HashMap<String, MenuItem> getMenu(){
        return menu_map;
    }

    
    /** 
     * Method to fetch all promotional items in the menu
     * @return HashMap<String, PromoItems>
     */
    public HashMap<String, PromoItems> getPromo(){
        return promo_map;
    }

    
    /** 
     * Sets the menu
     * @param menu all the ala carte in a hashmap
     */
    public void setMenu(HashMap<String, MenuItem> menu){
		this.menu_map = menu;
	}

    
    /** 
     * sets the promoitems in the menu
     * @param promo all the promo set in a hashmap
     */
    public void setPromo(HashMap<String, PromoItems> promo){
		this.promo_map = promo;
	}

    /** 
     * displays the menu
     */
    public void displayMenu(){
        for (int i = 0; i < menu_keys.size(); i++){
            System.out.println("(" + (i+1) + ") " + menu_map.get(menu_keys.get(i)).getName() + " - $" + menu_map.get(menu_keys.get(i)).getPrice());
        }
    }

    /** 
     * displays the promotional items
     */
    public void displayPromos(){
        for (int i = 0; i < promo_keys.size(); i++){
            System.out.println("(" + (i+1) + ") " + promo_map.get(promo_keys.get(i)).getName() + " - $" + promo_map.get(promo_keys.get(i)).getPrice());
        }
    }

    /** 
     * displays the menu
     */
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

    /**
     * Method to reset menu list
     */
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

    /**
     * Method to serialize menu list
     */
    public void serializeToFile() {
         
        // ? serialize menu to menu.dat
        try {      
            File menu_file = new File("menu.dat");
            ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(menu_file));

            output.writeObject(menu_map);
            output.flush();
            output.close();

            //System.out.println("Menu updated.");

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

            //System.out.println("Promo menu updated.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to deserialize menu list
     */
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
