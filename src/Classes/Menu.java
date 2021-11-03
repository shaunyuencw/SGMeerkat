package Classes;

import java.io.*;
import java.util.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class Menu extends DataBaseHandler{
    
    private HashMap<String, MenuItem> menu_map;
    private HashMap<String, PromoItems> promo_map;
    public Menu(){
        menu_map = new HashMap<String, MenuItem>();
        promo_map = new HashMap<String, PromoItems>();
    };

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
            ObjectInputStream input = new ObjectInputStream(new GZIPInputStream(new FileInputStream(promo_file)));

            //Reads the first object in
            Object readObject = input.readObject();
            input.close();
        
            if(!(readObject instanceof HashMap)) throw new IOException("Data is not a hashmap");
            this.promo_map = (HashMap<String, PromoItems>) readObject;

            // Prints out everything in the map.
            // for(String key : promo_map.keySet()) {
            //     System.out.println(key + ": " + promo_map.get(key));
            // }
            
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        HashMap<String, MenuItem> menuMap = new HashMap<String, MenuItem>();
        
        MenuItem m1 = new MenuItem("rice that is fragantly fried with shrimps", "fried rice", 4);
        MenuItem m2 = new MenuItem("less sugar","Coffee",3);

        menuMap.put(m1.getName().toLowerCase(), m1);
        menuMap.put(m2.getName().toLowerCase(), m2);

        HashMap<String, PromoItems> promo_map = new HashMap<String, PromoItems>();



        PromoItems p1 = new PromoItems(menuMap, "Fried Rice Set", "special lunch set", 6);
        promo_map.put(p1.getName().toLowerCase(), p1);


		DataBaseHandler db2 = new Menu();       
		db2.serializeToFile();
        System.out.println(((Menu)db2).getPromo().size());

	}







    
    
	


}
