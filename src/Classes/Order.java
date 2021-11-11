package Classes;
import java.util.*;
import java.io.*;
import java.io.Serializable;

// ! NOT CONFIRMED

public class Order implements Serializable, DatabaseHandler {
    private static final long serialVersionUID = 1L;
    private ArrayList<MenuItem> menuItems; 
    private ArrayList<PromoItems> promoItems ;
    private Staff staff;
    private int tableno;
    private double total;
    private double gst = 0.07*this.total;
    private double serviceCharge = 0.1*this.total;

    // Ale-carte order
    public Order(Staff staff, int tableno){
        this.staff = staff;
        this.tableno = tableno;
    }
    
    public void addMenuItems(MenuItem menuItems){
        this.menuItems.add(menuItems);
        this.total += menuItems.getPrice();
    }
    //Method Overloading
    public void addMenuItems(PromoItems promoItem){
        this.promoItems.add(promoItem);
        this.total += promoItem.getPrice();
    }

    public void printInvoice(){
        System.out.println("----------------Receipt-------------------");
        if(menuItems!=null)
        {
        for (int i = 0; i < menuItems.size(); i++){
            System.out.println(menuItems.get(i).getName() + "------------" +menuItems.get(i).getPrice());
        }
        }


        if(promoItems!=null)
        {
        for (int i = 0; i < promoItems.size(); i++){
            System.out.println(promoItems.get(i).getName() + "------------" +promoItems.get(i).getPrice());
        }
        }
        System.out.println("-----------------------------------------");
        System.out.println("total:     " + this.total);
        System.out.println("tax(gst/service) " + "("+ (this.gst + this.serviceCharge)+")");
        System.out.println("                 "+ (this.total + this.gst +this.serviceCharge));
        System.out.println("--------------------------------------------------------------");
    }
    
    public void serializeToFile() {
        // TODO Auto-generated method stub

        
    }

    
    public void deserializeFromFile() {
        // TODO Auto-generated method stub
        
    }
}
