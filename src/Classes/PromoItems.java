package Classes;

import java.util.*;
import java.io.Serializable;

/**
 * A promotional item in the Menu containing 1 or more menuitems
 */
public class PromoItems implements Serializable{
    
    private HashMap<String, MenuItem> promo_map;
    private String name;
    private double price;
    private String desc;
    /**
     * A constructor for the promotional item
     * @param promo_map store all promo items
     * @param name name of the promo item
     * @param desc description of the promo item
     * @param price price of the promo item
     */
    public PromoItems(HashMap<String, MenuItem> promo_map, String name, String desc, double price){
        this.promo_map = promo_map;
        this.desc = desc;
        this.price = price;
        this.name = name;
    }


    
    /** 
     * get promoitems
     * @return HashMap<String, MenuItem>
     */
    public HashMap<String, MenuItem> getPromoItems(){
        return promo_map;
    }

    
    /** 
     * get description of promoitem
     * @return String
     */
    public String getDesc(){
        return this.desc;
    }

    
    /** 
     * get Name of promoitem
     * @return String
     */
    public String getName(){
        return this.name;
    }

    
    /** 
     * gets the price of the promoitem
     * @return Double
     */
    public Double getPrice(){
        return this.price;
    }

}
