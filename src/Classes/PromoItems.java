package Classes;

import java.util.*;
import java.io.Serializable;

public class PromoItems implements Serializable{
    
    private ArrayList<MenuItem> promoitems;
    private String name;
    private double price;
    private String desc;

    PromoItems(ArrayList<MenuItem> promoitems,String desc, double price, String name){
        this.promoitems = promoitems;
        this.desc = desc;
        this.price = price;
        this.name = name;
    }

    public ArrayList<MenuItem> getpromoitems(){
        return promoitems;
    }

    public String getdesc(){
        return this.desc;
    }

    public String getname(){
        return this.name;
    }

    public Double getprice(){
        return this.price;
    }

}
