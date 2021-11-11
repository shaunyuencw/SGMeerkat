import java.util.*;

import Classes.*;
import Classes.MenuItem.Type;

/**
 * RRPSS_Login_Boundary
 */
public class RRPSS {

    public static void printOptions() {
        System.out.println("-----------------------------------");
        System.out.println("1. Menu Management");
        System.out.println("2. Order Management");
        System.out.println("3. Reservation Management");
        System.out.println("4. Reports");
        System.out.println("0. Exit RRPSS");
        System.out.println("-----------------------------------");
    }

    public static void printReservationOptions(){
        System.out.println("-----------Reservation------------");
        System.out.println("1. Create Reservation");
        System.out.println("2. Remove Reservation");
        System.out.println("3. View Reservation");
        System.out.println("-----------------------------------");


    }
    public static void printMenuOptions() {
        System.out.println("-------Select Menu Options--------");
        System.out.println("1. Create Menu Item");
        System.out.println("2. Delete Menu Item");
        System.out.println("3. Update Menu Item");
        System.out.println("4. Create Promo Item");
        System.out.println("5. Delete Promo Item");
        System.out.println("6. Update Promo Item");
        System.out.println("7. ViewMenu");
        System.out.println("8. Exit Menu Options");
        System.out.println("-----------------------------------");
    }

    public static void printOrderOptions(){
        System.out.println("-------Select Order Options--------");
        System.out.println("1. Order");
        System.out.println("2. Remove Item From Order");
        System.out.println("3. ViewOrder");
        System.out.println("4. Print Order Invoice (this will close the order)");
        System.out.println("-----------------------------------");
    }
    
}