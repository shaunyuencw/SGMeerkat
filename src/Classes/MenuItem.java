package Classes;

import java.io.Serializable;

public class MenuItem implements Serializable{
    private String desc;
    private String name;
    private double price;

    public MenuItem(String desc, String name, double price) {
        this.name = name;
        this.desc = desc;
        this.price = price;
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
