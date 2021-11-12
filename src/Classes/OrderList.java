package Classes;

import java.util.*;
// import java.io.*; // Not used

public class OrderList {
    private HashMap<Integer, Order> orderList;
    private AllMember memberList;
    private static Scanner sc = new Scanner(System.in);

    public OrderList(){
        orderList = new HashMap<>();
        memberList = new AllMember();
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

    public void generateInvoice(int tableNo){
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
        newOrder.printOrderInvoice();
        // TODO change table no not occupied
        // TODO create invoice
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
}
