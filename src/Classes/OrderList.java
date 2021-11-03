package Classes;

import java.util.*;
import java.time.LocalDateTime;

// ! NOT CONFIRMED

public class OrderList {
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
