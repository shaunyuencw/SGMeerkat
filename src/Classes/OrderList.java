package Classes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.util.*;

public class OrderList {
    private HashMap<Integer, Order> orderList;
    private HashMap<String, ArrayList<Order>> invoiceList;
    private AllMember memberList;
    private static Scanner sc = new Scanner(System.in);

    public OrderList(){
        orderList = new HashMap<>();
        memberList = new AllMember();
        invoiceList = new HashMap<>();
    }

    public void createNewOrder(Staff curStaff, int tableNo, int noOfCust){
        Order newOrder = new Order(curStaff, tableNo, noOfCust);
        orderList.put(tableNo, newOrder);
    }

    public void orderAlaCarte(Menu menu, int tableNo){
        Order newOrder = orderList.get(tableNo);
        System.out.println("Enter the Ala Carte to include in the order: (N to terminate)");
        System.out.println("-------------------------------------------");
        menu.displayMenu();
        while (true) {
            String temp_menuItemKey = sc.nextLine();
            if (temp_menuItemKey.equals("N")) {
                break;
            }
            try {
                if (menu.getMenu().containsKey(menu.getMenu_key(Integer.parseInt(temp_menuItemKey) - 1))) {
                    temp_menuItemKey = menu.getMenu_key(Integer.parseInt(temp_menuItemKey) - 1);
                    newOrder.addMenuItems(menu.getMenu().get(temp_menuItemKey));
                    System.out.println(temp_menuItemKey + " added to order.");
                } else {
                    System.out.println("No such ala carte item.");
                }
            }
            catch (Exception e) {
                System.out.println("No such ala carte item.");
            }
        }
    }

    public void removeAlaCarte(int tableNo){
        Order newOrder = orderList.get(tableNo);
        if(newOrder.getMenuSize() == 0){
            System.out.println("No ala carte selected yet");
        } else {
            System.out.println("Enter the Ala Carte to be removed from the order: (N to terminate)");
            System.out.println("-------------------------------------------");
            newOrder.displayMenu();
            while (true) {
                String temp_menuItemKey = sc.nextLine();
                if (temp_menuItemKey.equals("N")) {
                    break;
                }
                try {
                    newOrder.removeMenuItems(Integer.parseInt(temp_menuItemKey));
                    System.out.println(temp_menuItemKey + " removed.");
                    break;
                }
                catch (Exception e) {
                    System.out.println("No such ala carte item.");
                }
            }
        }
    }

    public void orderSetPackage(Menu menu, int tableNo){
        Order newOrder = orderList.get(tableNo);
        System.out.println("Enter the Set Package to include in the order: (N to terminate)");
        System.out.println("-------------------------------------------");
        menu.displayPromos();
        while (true) {
            String temp_menuItemKey = sc.nextLine();
            if (temp_menuItemKey.equals("N")) {
                break;
            }
            try {
                if (menu.getPromo().containsKey(menu.getPromo_key(Integer.parseInt(temp_menuItemKey) - 1))) {
                    temp_menuItemKey = menu.getPromo_key(Integer.parseInt(temp_menuItemKey) - 1);
                    newOrder.addMenuItems(menu.getPromo().get(temp_menuItemKey));
                    System.out.println(temp_menuItemKey + " added to order.");
                } else {
                    System.out.println("No such set package.");
                }
            }
            catch (Exception e) {
                System.out.println("No such set package.");
            }
        }
    }

    public void removeSetPackage(int tableNo){
        Order newOrder = orderList.get(tableNo);
        if(newOrder.getPromoSize() == 0){
            System.out.println("No set package selected yet");
        } else {
            System.out.println("Enter the Set Package to be removed from the order: (N to terminate)");
            System.out.println("-------------------------------------------");
            newOrder.displayPromos();
            while (true) {
                String temp_menuItemKey = sc.nextLine();
                if (temp_menuItemKey.equals("N")) {
                    break;
                }
                try {
                    newOrder.removePromoItems(Integer.parseInt(temp_menuItemKey));
                    System.out.println(temp_menuItemKey + " removed.");
                    break;
                }
                catch (Exception e) {
                    System.out.println("No such set package.");
                }
            }
        }
    }

    public void viewCurrentOrder(int tableNo){
        Order newOrder = orderList.get(tableNo);
        newOrder.viewOrder();
    }

    public void generateInvoice(int tableNo, String date, String time){
        Order newOrder = orderList.get(tableNo);
        System.out.println("Do you have membership? (Y/N): ");
        String isMembership = sc.nextLine();
        if (isMembership.equals("Y")){
            if(memberList.checkIfMember()){
                System.out.println("Membership confirmed, 10% discount will be given.");
                newOrder.setMembership(true);
            } else {
                System.out.println("Membership not valid.");
            }
        } else if (newOrder.getTotal() >= 100) {
            System.out.println("You've spend more than $100 and is eligible to be a member.");
            System.out.println("Do you want to create a membership? (Y/N): ");
            String wantMembership = sc.nextLine();
            if(wantMembership.equals("Y")){
                memberList.addNewMember();
                System.out.println("Membership created, 10% discount will be given.");
                newOrder.setMembership(true);
            }
        }
        newOrder.printOrderInvoice(date, time);
        storeInvoice(orderList.get(tableNo));
        orderList.remove(tableNo);
    }

    public void viewAllOrder() {
        System.out.println("Select a table number: ");
        System.out.println("------------------------");
        List<Integer> orderList_keys = new ArrayList<>(orderList.keySet());
        System.out.println("(0) Exit");
        for(int i = 0; i < orderList_keys.size(); i++){
            System.out.println("(" + (orderList_keys.get(i)) + ") Table " + orderList_keys.get(i));
        }
    }

    public void storeInvoice(Order deletedOrder){
        deserializeFromFile();
        if(invoiceList.get(deletedOrder.getDate()) == null){
            ArrayList<Order> orders = new ArrayList<>();
            orders.add(deletedOrder);
            invoiceList.put(deletedOrder.getDate(), orders);
        } else {
            invoiceList.get(deletedOrder.getDate()).add(deletedOrder);
        }
        serializeToFile();
    }

    public void serializeToFile() {
        try {
            File menu_file = new File("invoice.dat");
            ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(menu_file));

            output.writeObject(invoiceList);
            output.flush();
            output.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deserializeFromFile() {
        try {
            File menu_file = new File("invoice.dat");
            ObjectInputStream input = new ObjectInputStream(new FileInputStream(menu_file));

            //Reads the first object in
            Object readObject = input.readObject();
            input.close();

            this.invoiceList = (HashMap<String, ArrayList<Order>>) readObject;
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
