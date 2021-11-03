package Classes;

import java.io.Serializable;

public class MenuItem implements Serializable{
    private String desc;
    private String name;
    private double price;

    public MenuItem(String name, String desc, double price) {
        this.name = name;
        this.desc = desc;
        this.price = price;
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
