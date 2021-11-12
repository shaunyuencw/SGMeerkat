package Classes;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.io.Serializable;

public class Order implements Serializable {
    private static final long serialVersionUID = 1L;
    private ArrayList<MenuItem> menuItems;
    private ArrayList<PromoItems> promoItems ;
    private Staff staff;
    private int noOfCust;
    private boolean membership;
    private String dineDate;
    private String dineTime;
    private int tableNo;
    private double total;
    private double gst = 0.07;
    private double serviceCharge = 0.1;

    // private static Scanner sc = new Scanner(System.in); // not needed?

    public Order(Staff staff, int tableNo, int noOfCust){
        this.staff = staff;
        this.tableNo = tableNo;
        this.noOfCust = noOfCust;
        this.menuItems = new ArrayList<>();
        this.promoItems = new ArrayList<>();
        this.membership = false;
        this.total = 0;
    }
    
    public void addMenuItems(MenuItem menuItems){
        this.menuItems.add(menuItems);
    }

    public void addMenuItems(PromoItems promoItem){
        this.promoItems.add(promoItem);
    }

    public void removeMenuItems(int index){
        this.menuItems.remove(index-1);
    }

    public void removePromoItems(int index){
        this.promoItems.remove(index-1);
    }

    public void viewOrder() {
        if (menuItems.size() == 0){
            System.out.println("No ala carte selected yet.");
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
            System.out.println("No set package selected yet.");
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

    public void printOrderInvoice(String date, String time){
        this.dineDate = date;
        this.dineTime = time;
        this.total = countSubTotal();
        HashMap<MenuItem, Integer> menuItemCount = new HashMap<>();
        HashMap<PromoItems, Integer> promoItemCount = new HashMap<>();
        for(int i = 0; i < menuItems.size(); i++){
            if(menuItemCount.containsKey(menuItems.get(i))){
                menuItemCount.put(menuItems.get(i), menuItemCount.get(menuItems.get(i)) + 1);
            } else {
                menuItemCount.put(menuItems.get(i), 1);
            }
        }
        for(int i = 0; i < promoItems.size(); i++){
            if(promoItemCount.containsKey(promoItems.get(i))){
                promoItemCount.put(promoItems.get(i), promoItemCount.get(promoItems.get(i)) + 1);
            } else {
                promoItemCount.put(promoItems.get(i), 1);
            }
        }
        ArrayList<MenuItem> menuItemCount_keys = new ArrayList<>(menuItemCount.keySet());
        ArrayList<PromoItems> promoItemCount_keys = new ArrayList<>(promoItemCount.keySet());

        System.out.println("----------------SGMeerkat Receipt-------------------");
        System.out.printf("Server: %-15s Date: %-18s \n", staff.getStaffName(), dineDate);
        System.out.printf("Table: %-16s Time: %-18s \n", tableNo, dineTime);
        System.out.printf("                   Client: %s \n", noOfCust);
        System.out.println("----------------------------------------------------");
        if(menuItemCount!=null)
        {
            for (int i = 0; i < menuItemCount_keys.size(); i++){
                System.out.printf("%-2d x %-30s %7.2f \n", menuItemCount.get(menuItemCount_keys.get(i)), menuItemCount_keys.get(i).getName(), menuItemCount.get(menuItemCount_keys.get(i))*menuItemCount_keys.get(i).getPrice());
            }
        }
        if(promoItemCount!=null)
        {
            for (int i = 0; i < promoItemCount_keys.size(); i++){
                System.out.printf("%-2d x %-30s %7.2f \n", promoItemCount.get(promoItemCount_keys.get(i)), promoItemCount_keys.get(i).getName(), promoItemCount.get(promoItemCount_keys.get(i))*promoItemCount_keys.get(i).getPrice());
            }
        }
        System.out.println("----------------------------------------------------");
        if(membership){
            System.out.printf("10%% Membership discount: %18.2f \n", this.total * 0.1);
            this.total *= 0.9;
        }
        System.out.printf("Subtotal: %33.2f \n", this.total);
        System.out.printf("10%% Service charge: %23.2f \n", this.serviceCharge * this.total);
        System.out.printf("7%% GST: %35.2f \n", this.gst * this.total);
        this.total *= (1 + this.serviceCharge + this.gst);
        System.out.printf("TOTAL: %36.2f \n", this.total);
        System.out.println("----------------------------------------------------");
    }

    public int getMenuSize() { return menuItems.size(); }
    public int getPromoSize() { return promoItems.size(); }
    public boolean getMembership() { return this.membership; }
    public void setMembership(boolean bool) { this.membership = bool; }
    public String getDate() { return this.dineDate; }
    public ArrayList<MenuItem> getMenuItems() { return this.menuItems; }
    public ArrayList<PromoItems> getPromoItems() { return this.promoItems; }
    public double getTotal() { return this.total; }

    public double countSubTotal() {
        this.total = 0;
        if(menuItems!=null)
        {
            for (int i = 0; i < menuItems.size(); i++){
                this.total += menuItems.get(i).getPrice();
            }
        }
        if(promoItems!=null)
        {
            for (int i = 0; i < promoItems.size(); i++){
                this.total += promoItems.get(i).getPrice();
            }
        }
        return this.total;
    }

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
