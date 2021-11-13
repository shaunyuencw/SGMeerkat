package Classes;
import java.io.Serializable;
/**
 Represents an item in the menu of this restaurant
*/
public class MenuItem implements Serializable{
    private static final long serialVersionUID = 1L;
    public enum Type{MAIN,DRINKS,DESSERT}

    private String desc;
    private String name;
    private double price;
    private Type type;
    /**
    * Constructor for MenuItem
    *@param name The name of the MenuItem
    *@param desc A short description of the menuItem
    *@param price The price of the MenuItem
    *@param type the category which the MenuItem falls under
    */
    public MenuItem(String name, String desc, double price, Type type) {
        this.name = name;
        this.desc = desc;
        this.price = price;
        this.type = type;
    }

    
    /** 
     * @return String
     * Returns the desciption of the menuitem
     */
    public String getDesc(){
        return this.desc;
    }

    
    /** 
     * @return String 
     * returns the name of the menuitem
     */
    public String getName(){
        return this.name;
    }

    
    /** 
     * @return Double
     * returns the price of the menuitem
     */
    public Double getPrice(){
        return this.price;
    }

    
    /** 
     * @return Type
     * returns the category of the menuitem
     */
    public Type getType(){
        return this.type;
    }

    
    /** 
     * @param type
     * Sets the category of the menuitem
     */
    public void setType(Type type){
        this.type = type;
    }
}
