package Classes;

import java.io.Serializable;

// ! NOT CONFIRMED

public class Order implements Serializable, DatabaseHandler {
    private MenuItem menuItem;
    private PromoItems promoItems;
    private boolean is_promo;

    // Ale-carte order
    public Order(MenuItem menuItem, boolean is_promo){
        if (!is_promo){
            this.menuItem = menuItem;
        }
        else{
            System.out.println("Mismatch");
        }
    }

    // Promo-set order
    public Order(PromoItems promoItem, boolean is_promo){
        if (is_promo){
            this.promoItems = promoItem;
        }
        else{
            System.out.println("Mismatch");
        }
    }

    public boolean get_isPromo(){
        return this.get_isPromo();
    }

    public MenuItem getMenuItem(){
        if (!is_promo){
            return this.menuItem;
        }
        else{
            System.out.println("Mismatch");
            return null;
        }
        
    }

    public PromoItems getPromoItems(){
        if (!is_promo){
            return this.promoItems;
        }
        else{
            System.out.println("Mismatch");
            return null;
        }
    }

    
    public void serializeToFile() {
        // TODO Auto-generated method stub
        
    }

    
    public void deserializeFromFile() {
        // TODO Auto-generated method stub
        
    }
}
