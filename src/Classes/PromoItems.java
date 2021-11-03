package Classes;

import java.util.*;
import java.io.Serializable;

public class PromoItems implements Serializable{
    
    private HashMap<String, MenuItem> promo_map;
    private String name;
    private double price;
    private String desc;

    public PromoItems(HashMap<String, MenuItem> promo_map, String name, String desc, double price){
        this.promo_map = promo_map;
        this.desc = desc;
        this.price = price;
        this.name = name;
    }

    public HashMap<String, MenuItem> getPromoItems(){
        return promo_map;
    }

    public String getDesc(){
        return this.desc;
    }

    public String getName(){
        return this.name;
    }

    public Double getPrice(){
        return this.price;
    }

}
