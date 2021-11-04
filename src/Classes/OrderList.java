package Classes;

import java.util.*;
import java.time.LocalDateTime;
import java.io.*;

// ! NOT CONFIRMED

public class OrderList implements DataBaseHandler {
    private ArrayList<Order> order_list;
    private String staff_name;
    private String dineTime;
    private double totalPrice;

    public OrderList(String staff_name){
        LocalDateTime now = LocalDateTime.now();

        this.staff_name = staff_name;
        order_list = new ArrayList<Order>();
        this.dineTime = now.getYear() + "-" + now.getMonthValue() + "-" + now.getDayOfMonth() + " " + now.getHour() + ":" + now.getMinute();
        System.out.println(dineTime);
        this.totalPrice = 0;
    }

    public double getTotalPrice(){
        return this.totalPrice;
    }

    public String getStaffName(){
        return this.staff_name;
    }

    public ArrayList<Order> getOrderList(){
        return this.order_list;
    }

    public void serializeToFile() {
        // ? serialize menu to menu.dat
        try {      
            File order_file = new File("orders.dat");
            ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(order_file));

            output.writeObject(order_file);
            output.flush();
            output.close();

            System.out.println("orders updated.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public void deserializeFromFile() {
        // ? get menu from menu.dat
        try {
            File order_file = new File("orders.dat");
            ObjectInputStream input = new ObjectInputStream(new FileInputStream(order_file));

            //Reads the first object in
            Object readObject = input.readObject();
            input.close();
        
            if(!(readObject instanceof ArrayList)) throw new IOException("Data is not a Arraylist");
            this.order_list = (ArrayList<Order>) readObject;
            
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public static void main(String[] args) {

        OrderList order_list = new OrderList("zw");
        ArrayList<Order> order_items = order_list.getOrderList();
        
        for (int i = 0; i < order_items.size(); i++){
            if (!order_items.get(i).get_isPromo()){
                System.out.println(order_items.get(i).getMenuItem().getName());
            }
            else{
                System.out.println(order_items.get(i).getPromoItems().getName());
            }
        }

    }
}
