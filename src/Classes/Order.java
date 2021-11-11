package Classes;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.io.Serializable;

public class Order implements Serializable {
    private ArrayList<MenuItem> menuItems;
    private ArrayList<PromoItems> promoItems ;
    private Staff staff;
    private int customerId;
    private LocalDate dineDate;
    private LocalTime dineTime;
    private int tableNo;
    private double total;
    private double gst = 0.07;
    private double serviceCharge = 0.1;

    public Order(Staff staff, int tableNo, int customerId){
        this.staff = staff;
        this.tableNo = tableNo;
        this.customerId = customerId;
        menuItems = new ArrayList<>();
        promoItems = new ArrayList<>();
        this.dineDate = LocalDate.now();
        this.dineTime = LocalTime.now();
        this.total = 0;
    }
    
    public void addMenuItems(MenuItem menuItems){
        this.menuItems.add(menuItems);
        this.total += menuItems.getPrice();
    }

    public void addMenuItems(PromoItems promoItem){
        this.promoItems.add(promoItem);
        this.total += promoItem.getPrice();
    }

    public void removeMenuItems(int index){
        MenuItem deleted = this.menuItems.remove(index-1);
        this.total -= deleted.getPrice();
    }

    public void removePromoItems(int index){
        PromoItems deleted = this.promoItems.remove(index-1);
        this.total -= deleted.getPrice();
    }

    public void viewOrder() {
        if (menuItems.size() == 0){
            System.out.println("Ala carte is empty :(");
        }

        else {
            System.out.println("---------- Ala carte ----------");
            for (int i = 0; i < menuItems.size(); i++){
                MenuItem menuItem = menuItems.get(i);
                System.out.println("(" + (i+1) + ") " + menuItem.getName() + " - $" + menuItem.getPrice());
                System.out.println("- " + menuItem.getDesc());
            }
            System.out.println("------------------------------");
        }

        if (promoItems.size() == 0){
            System.out.println("Set package is empty :(");
        }
        else {
            System.out.println("---------- Set Package ----------");
            for (int j = 0; j < promoItems.size(); j++){
                PromoItems promoItem = promoItems.get(j);
                System.out.println("(" + (j+1) + ") " + promoItem.getName() + " - $" + promoItem.getPrice());
                HashMap<String, MenuItem> promoItems = promoItem.getPromoItems();
                for (String menuItem : promoItems.keySet()){
                    System.out.println("- " + menuItem);
                }
            }
        }
    }

    public void printOrderInvoice(){
        System.out.println("----------------SGMeerkat Receipt-------------------");
        System.out.printf("Server: %-15s Date: %-15s \n", staff.getStaffName(), dineDate);
        System.out.printf("Table: %-16s Time: %-15s \n", tableNo, dineTime);
        System.out.println("----------------------------------------------------");
        if(menuItems!=null)
        {
            for (int i = 0; i < menuItems.size(); i++){
                System.out.printf("1 %-15s %20.2f \n", menuItems.get(i).getName(), menuItems.get(i).getPrice());
            }
        }
        if(promoItems!=null)
        {
            for (int i = 0; i < promoItems.size(); i++){
                System.out.printf("1 %-15s %20.2f \n", promoItems.get(i).getName(), promoItems.get(i).getPrice());
            }
        }
        System.out.println("----------------------------------------------------");
        System.out.printf("Subtotal: %28.2f \n", this.total);
        System.out.printf("10%% Service charge: %18.2f \n", this.serviceCharge * this.total);
        System.out.printf("7%% GST: %30.2f \n", this.gst * this.total);
        System.out.printf("TOTAL: %31.2f \n", this.total * (1 + this.serviceCharge + this.gst));
        System.out.println("----------------------------------------------------");
    }

    public ArrayList<MenuItem> getMenu(){
        return menuItems;
    }
    public ArrayList<PromoItems> getPromo(){
        return promoItems;
    }
    public int getMenuSize() { return menuItems.size(); }
    public int getPromoSize() { return promoItems.size(); }

    public void displayMenu(){
        for (int i = 0; i < menuItems.size(); i++){
            System.out.println("(" + (i+1) + ") " + menuItems.get(i).getName() + " - $" + menuItems.get(i).getPrice());
        }
    }

    public void displayPromos(){
        for (int i = 0; i < promoItems.size(); i++){
            System.out.println("(" + (i+1) + ") " + promoItems.get(i).getName() + " - $" + promoItems.get(i).getPrice());
        }
    }
}
