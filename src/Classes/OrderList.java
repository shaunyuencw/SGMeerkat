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
        } else if (newOrder.countSubTotal() >= 100) {
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

    public void printInvoice(boolean byDay){
        int i;
        deserializeFromFile();
        ArrayList<String> invoiceList_keys = new ArrayList<>(invoiceList.keySet());
        ArrayList<Order> allOrders = new ArrayList<>();
        String choice;

        if(byDay){
            System.out.println("Available dates");
            System.out.println("---------------------------------");
            for(i = 1; i <= invoiceList_keys.size(); i++){
                System.out.printf("%-15s", invoiceList_keys.get(i-1));
                if(i%3==0) System.out.print("\n");
            }
            System.out.println("\nType the date to print report: (N to terminate)");
            choice = sc.nextLine();
            if(choice.equals("N")) return;
            allOrders = invoiceList.get(choice);
        } else{
            ArrayList<String> months = new ArrayList<>();
            for(i = 0; i < invoiceList_keys.size(); i++){
                months.add(invoiceList_keys.get(i).split("-")[1]);
            }
            LinkedHashSet<String> hashSet = new LinkedHashSet<>(months);
            ArrayList<String> noDupMonths = new ArrayList<>(hashSet);
            System.out.println("Available months");
            System.out.println("---------------------------------");
            for(i = 1; i <= noDupMonths.size(); i++){
                System.out.printf("%-8s", noDupMonths.get(i-1));
                if(i%3==0) System.out.print("\n");
            }
            System.out.println("\nType the month to print report: (N to terminate)");
            choice = sc.nextLine();
            if(choice.equals("N")) return;
            for(i = 0; i < invoiceList_keys.size(); i++){
                if(invoiceList_keys.get(i).split("-")[1].equals(choice)){
                    allOrders.addAll(invoiceList.get(invoiceList_keys.get(i)));
                }
            }
        }

        double totalRevenue = 0;
        int totalMemberGiven = 0;
        Order thisOrder;
        ArrayList<MenuItem> mItems;
        ArrayList<PromoItems> pItems;
        MenuItem mItem;
        PromoItems pItem;
        HashMap<String, Integer> alaCartesCount = new HashMap<>();
        HashMap<String, Integer> setPackagesCount = new HashMap<>();
        HashMap<String, MenuItem> alaCartesItem = new HashMap<>();
        HashMap<String, PromoItems> setPackagesItem = new HashMap<>();
        for(i = 0; i < allOrders.size(); i++){
            thisOrder = allOrders.get(i);
            if(thisOrder.getMembership()) totalMemberGiven++;
            totalRevenue += thisOrder.getTotal();
            mItems = thisOrder.getMenuItems();
            pItems = thisOrder.getPromoItems();
            for(int j = 0; j < mItems.size(); j++){
                mItem = mItems.get(j);
                if(alaCartesCount.containsKey(mItem.getName())){
                    alaCartesCount.put(mItem.getName(), alaCartesCount.get(mItem.getName()) + 1);
                } else {
                    alaCartesCount.put(mItem.getName(), 1);
                    alaCartesItem.put(mItem.getName(), mItem);
                }
            }
            for(int k = 0; k < pItems.size(); k++){
                pItem = pItems.get(k);
                if(setPackagesCount.containsKey(pItem.getName())){
                    setPackagesCount.put(pItem.getName(), setPackagesCount.get(pItem.getName()) + 1);
                } else {
                    setPackagesCount.put(pItem.getName(), 1);
                    setPackagesItem.put(pItem.getName(), pItem);
                }
            }
        }
        ArrayList<String> alaCartes_keys = new ArrayList<>(alaCartesItem.keySet());
        ArrayList<String> setPackages_keys = new ArrayList<>(setPackagesItem.keySet());

        System.out.println("-----------------------------------------------------");
        System.out.print("Report Period: ");
        switch(choice){
            case "01":
                System.out.print("January");
                break;
            case "02":
                System.out.print("February");
                break;
            case "03":
                System.out.print("March");
                break;
            case "04":
                System.out.print("April");
                break;
            case "05":
                System.out.print("May");
                break;
            case "06":
                System.out.print("June");
                break;
            case "07":
                System.out.print("July");
                break;
            case "08":
                System.out.print("August");
                break;
            case "09":
                System.out.print("September");
                break;
            case "10":
                System.out.print("October");
                break;
            case "11":
                System.out.print("November");
                break;
            case "12":
                System.out.print("December");
                break;
            default:
                System.out.print(choice);
        }
        System.out.println("\n-----------------------------------------------------");
        System.out.printf("%-5s %-25s %-10s %-10s\n", "Qty.", "Description", "Unit P.", "Total P.");
        System.out.printf("%-5s %-25s %-10s %-10s\n", "", "MAIN", "", "");
        for(i = 0; i < alaCartes_keys.size(); i++){
            if(alaCartesItem.get(alaCartes_keys.get(i)).getType() == MenuItem.Type.MAIN){
                mItem = alaCartesItem.get(alaCartes_keys.get(i));
                System.out.printf("%-5d %-25s %-10.2f %-10.2f\n", alaCartesCount.get(mItem.getName()), mItem.getName(), mItem.getPrice(), alaCartesCount.get(mItem.getName())*mItem.getPrice());
            }
        }
        System.out.printf("%-5s %-25s %-10s %-10s\n", "", "DRINK", "", "");
        for(i = 0; i < alaCartes_keys.size(); i++){
            if(alaCartesItem.get(alaCartes_keys.get(i)).getType() == MenuItem.Type.DRINKS){
                mItem = alaCartesItem.get(alaCartes_keys.get(i));
                System.out.printf("%-5d %-25s %-10.2f %-10.2f\n", alaCartesCount.get(mItem.getName()), mItem.getName(), mItem.getPrice(), alaCartesCount.get(mItem.getName())*mItem.getPrice());
            }
        }
        System.out.printf("%-5s %-25s %-10s %-10s\n", "", "DESSERT", "", "");
        for(i = 0; i < alaCartes_keys.size(); i++){
            if(alaCartesItem.get(alaCartes_keys.get(i)).getType() == MenuItem.Type.DESSERT){
                mItem = alaCartesItem.get(alaCartes_keys.get(i));
                System.out.printf("%-5d %-25s %-10.2f %-10.2f\n", alaCartesCount.get(mItem.getName()), mItem.getName(), mItem.getPrice(), alaCartesCount.get(mItem.getName())*mItem.getPrice());
            }
        }
        System.out.printf("%-5s %-25s %-10s %-10s\n", "", "SET PACKAGES", "", "");
        for(i = 0; i < setPackages_keys.size(); i++){
            pItem = setPackagesItem.get(setPackages_keys.get(i));
            System.out.printf("%-5d %-25s %-10.2f %-10.2f\n", setPackagesCount.get(pItem.getName()), pItem.getName(), pItem.getPrice(), setPackagesCount.get(pItem.getName())*pItem.getPrice());
        }
        System.out.println("-----------------------------------------------------");
        System.out.printf("%42s %-8d\n", "Total Membership Given: ", totalMemberGiven);
        System.out.printf("%42s %-8.2f\n", "(Include GST & Service Charge) TOTAL: ", totalRevenue);
        System.out.println("-----------------------------------------------------");
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
