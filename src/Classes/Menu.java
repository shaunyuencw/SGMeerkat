package Classes;

import java.io.*;
import java.util.*;

public class Menu implements DatabaseHandler{
    
    private HashMap<String, MenuItem> menu_map;
    private HashMap<String, PromoItems> promo_map;
    private List<String> menu_keys;
    private List<String> promo_keys;

    public Menu(){
        menu_map = new HashMap<String, MenuItem>();
        promo_map = new HashMap<String, PromoItems>();
        menu_keys = new ArrayList<String>();
        promo_keys = new ArrayList<String>();
    };

    public HashMap<String, MenuItem> getMenu(){
        return menu_map;
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
