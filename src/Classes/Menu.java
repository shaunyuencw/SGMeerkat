package Classes;

import java.io.*;
import java.util.*;
import java.text.ParseException;

public class Menu extends DataBaseHandler{
    
    private ArrayList menuList;
    private ArrayList promotionSets;
    public Menu(){};

    public ArrayList getmenu(){
        return menuList;
    }

    public ArrayList getpromo(){
        return promotionSets;
    }

    public void setMenu(ArrayList<MenuItem> lst){
		this.menuList = lst;
	}

    public void setPromo(ArrayList<PromoItems> promotionsets){
		this.promotionSets = promotionsets;
	}

    public void serializeToFile() {
        try {
            if (menuList.size()!=0) {
                FileOutputStream fileOut = new FileOutputStream("menu.dat");
                ObjectOutputStream out = new ObjectOutputStream(fileOut);
                out.writeObject(menuList);
                out.close();
                fileOut.close();
                System.out.printf("Serialized data is saved in menu.dat\n");
            }
        } catch (IOException i) {
            i.printStackTrace();
        }

        try {
            if (promotionSets.size()!=0) {
                FileOutputStream fileOut = new FileOutputStream("promo.dat");
                ObjectOutputStream out = new ObjectOutputStream(fileOut);
                out.writeObject(promotionSets);
                out.close();
                fileOut.close();
                System.out.printf("Serialized data is saved in promo.dat\n");
            }
        } catch (IOException i) {
            i.printStackTrace();
        }
    }


    public void deserializeFromFile() {
        ArrayList<MenuItem> Details = null;
        try {
            FileInputStream fileIn = new FileInputStream("Menu.dat");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            Details =  (ArrayList<MenuItem>)in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException e) {
           
        } catch (ClassNotFoundException i) {
            i.printStackTrace();
        }
        this.menuList = Details;

        ArrayList<PromoItems> promodetails = null;
        try {
            FileInputStream fileIn2 = new FileInputStream("promo.dat");
            ObjectInputStream in2 = new ObjectInputStream(fileIn2);
            promodetails =  (ArrayList<PromoItems>)in2.readObject();
            in2.close();
            fileIn2.close();
        } catch (IOException e) {
           
        } catch (ClassNotFoundException i) {
            i.printStackTrace();
        }
        this.promotionSets = promodetails;
        System.out.println(promodetails.size());
    }


    
    public static void main(String[] args) {
		MenuItem m1 = new MenuItem("rice", "fried rice", 4);
		MenuItem arr[] = {m1};
        MenuItem d1 = new MenuItem("less sugar","Coffee",3);

        ArrayList<MenuItem>  promo1 = new ArrayList<MenuItem>();
        promo1.add(m1);
        promo1.add(d1);
        PromoItems p1 = new PromoItems(promo1, "special lunch set", 6, "Fried Rice Set");
        ArrayList<PromoItems> ll2 = new ArrayList<PromoItems>();
        ll2.add(p1);

		ArrayList<MenuItem> ll = new ArrayList<MenuItem>(Arrays.asList(arr));
		DataBaseHandler db2 = new Menu();
		((Menu)db2).setMenu(ll);
        ((Menu)db2).setPromo(ll2);

		db2.serializeToFile();
        System.out.println(((Menu)db2).getpromo().size());

	}







    
    
	


}
