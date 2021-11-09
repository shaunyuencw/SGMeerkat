package Classes;
import java.io.Serializable;

public class MenuItem implements Serializable{
    public enum Type{MAIN,DRINKS,DESSERT}

    private String desc;
    private String name;
    private double price;
    private Type type;

    public MenuItem(String name, String desc, double price, Type type) {
        this.name = name;
        this.desc = desc;
        this.price = price;
        this.type = type;
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

    public Type getType(){
        return this.type;
    }

    public void setType(Type type){
        this.type = type;
    }
}
