package Classes;

import java.util.*;
import java.io.*;

public class OrderList implements DatabaseHandler {
    private ArrayList<Order> orderList;

    private static Scanner sc = new Scanner(System.in);

    public OrderList(){
        orderList = new ArrayList<>();
    }

    public void createNewOrder(Menu menu, Staff curStaff, int tableNo, int noOfCust){
        Order newOrder = new Order(curStaff, tableNo, noOfCust);
        int selection;
        while(true){
            System.out.println("---------------Select Order Options---------------");
            System.out.println("1. Order Ala Carte");
            System.out.println("2. Remove Ala Carte");
            System.out.println("3. Order Set Package");
            System.out.println("4. Remove Set Package");
            System.out.println("5. View Order");
            System.out.println("6. Print Order Invoice (this will close the order)");
            System.out.println("-----------------------------------");
            try{
                selection = sc.nextInt();
                switch (selection){
                    case 1:
                        System.out.println("Enter the Ala Carte to include in the order: (N to terminate)");
                        System.out.println("-------------------------------------------");
                        menu.displayMenu();
                        sc.nextLine();
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
                        break;
                    case 2:
                        if(newOrder.getMenuSize() == 0){
                            System.out.println("No ala carte selected yet");
                        } else {
                            System.out.println("Enter the Ala Carte to be removed from the order: (N to terminate)");
                            System.out.println("-------------------------------------------");
                            newOrder.displayMenu();
                            sc.nextLine();
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
                        break;
                    case 3:
                        System.out.println("Enter the Set Package to include in the order: (N to terminate)");
                        System.out.println("-------------------------------------------");
                        menu.displayPromos();
                        sc.nextLine();
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
                        break;
                    case 4:
                        if(newOrder.getPromoSize() == 0){
                            System.out.println("No set package selected yet");
                        } else {
                            System.out.println("Enter the Set Package to be removed from the order: (N to terminate)");
                            System.out.println("-------------------------------------------");
                            newOrder.displayPromos();
                            sc.nextLine();
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
                        break;
                    case 5:
                        newOrder.viewOrder();
                        break;
                    case 6:
                        newOrder.printOrderInvoice();
                        orderList.add(newOrder);
                        return;
                    default:
                        System.out.println("Invalid Options");
                }
            } catch (Exception e){
                System.out.println("Invalid Options");
            }
        }
    }

    public void serializeToFile() {
        // ? serialize menu to menu.dat
        try {
            File order_file = new File("orders.dat");
            ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(order_file));

            output.writeObject(orderList);
            output.flush();
            output.close();

            System.out.println("Orders updated.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public void deserializeFromFile() {
        try {
            File order_file = new File("orders.dat");
            ObjectInputStream input = new ObjectInputStream(new FileInputStream(order_file));

            //Reads the first object in
            Object readObject = input.readObject();
            input.close();

            if(!(readObject instanceof ArrayList)) throw new IOException("Data is not a Arraylist");
            this.orderList = (ArrayList<Order>) readObject;

        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
