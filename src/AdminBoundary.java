
import java.util.*;

import Classes.*;

/**
 * AdminBoundary
 */
public class AdminBoundary {

    private Scanner sc;
    private ArrayList<MenuItem> menu;
    private ArrayList<PromoItems> promomenu;

    public AdminBoundary(ArrayList<MenuItem> menu, ArrayList<PromoItems> promomenu) {
        this.sc = new Scanner(System.in);
        this.menu = menu;
        this.promomenu = promomenu;
    }

    public void printOptions() {
        
        // KIV
        // System.out.println("6. Add Staff");
        // System.out.println("7. Remove Staff");
        do {
            System.out.println(promomenu.size());
            System.out.println("-------------------\n    Admin Menu     \n-------------------");
            System.out.println("1. Add Item to Menu");
            System.out.println("2. Remove Item from Menu");
            System.out.println("3. Create new Promotion Set");
            System.out.println("4. Remove Promotion Set");
            System.out.println("5. Edit Menu");
            System.out.println("6. View Menu");
            System.out.println("----------------------------------------");
            System.out.println("Enter choice (Admin): (Enter 0 to stop)");
            int ch = sc.nextInt();
            sc.nextLine();
            if (ch == 0) {
                break;
            }
            switch (ch) {
            case 1:
                this.addMenuItem();
                break;
            case 2:
                this.removeMenuItem();
                break;
            case 3:
                this.createNewPromo();
                break;
            case 4:
                this.removePromo();
                break;
            // case 5: this.editMenu();
            // break;
            case 6:
                this.viewMenu();
                break;
            default:
                System.out.println("Invalid choice");
            }

        } while (true);
    }

    public void addMenuItem() {

        System.out.println("---------------Adding a new MenuItem---------------");
        System.out.println("Enter the new item name:");
        String name = sc.nextLine();
        System.out.println("Enter the new item description:");
        String desc = sc.nextLine();
        System.out.println("Enter the price of new item:");
        double price = sc.nextDouble();

        boolean exist = false;
        for (Object pass : menu) {
            MenuItem m = (MenuItem) pass;
            if (m.getname() == name) {
                System.out.println("Another item with the same name already exists");
                exist = true;
            }

        }

        if (exist == false) {
            MenuItem newitem = new MenuItem(desc, name, price);
            menu.add(newitem);
            System.out.println("Menu item added");
        }

    }

    public void viewMenu() {
        System.out.println("-------------\n         Menu:\n-------------");
        for (int i = 0; i < menu.size(); i++) {
            System.out.println((i + 1) + ": " + menu.get(i).getname() + "------ $" + menu.get(i).getprice());
            System.out.println(menu.get(i).getdesc());
        }
        System.out.println(
                "-----------------------------------------\n    Promotion Sets:\n------------------------------------------");
        for (int i = 0; i < promomenu.size(); i++) {
            System.out.println((i + 1) + ": " + promomenu.get(i).getname() + "------ $" + promomenu.get(i).getprice());
            System.out.println(promomenu.get(i).getdesc());
            for (int y = 0; y < promomenu.get(i).getpromoitems().size(); y++) {
                System.out.println("-" + promomenu.get(i).getpromoitems().get(y).getname());
            }
            System.out.println("--------------------------------------------");
        }

    }

    public void removeMenuItem() {

        System.out.println("---------------Removing a new MenuItem---------------");
        System.out.println("Enter the item name to remove:");

        boolean removed = false;
        MenuItem m1 = null;

        while (true) {
            String name = sc.nextLine();
            if (name.equalsIgnoreCase("N"))
                break;

            for (Object pass : menu) {
                MenuItem m = (MenuItem) pass;
                if (m.getname().equalsIgnoreCase(name)) {
                    m1 = m;
                    removed = true;
                }
            }

            if (removed == false) {
                System.out.println("Item does not exist try again (N to quit)");
            } else {
                menu.remove(m1);
                System.out.println(m1.getname() + " removed Successfully");
                break;
            }
        }
    }

    public void createNewPromo() {

        System.out.println("---------------Adding a new Promotion set---------------");
        System.out.println("Enter the new set name:");
        String name = sc.nextLine();
        System.out.println("Enter the new set description:");
        String desc = sc.nextLine();
        System.out.println("Enter the price of new set:");
        double price = sc.nextDouble();
        ArrayList<MenuItem> newpromo = new ArrayList<MenuItem>();
        boolean added = false; // check if anything was successfully added

        boolean exist = false; // check if the name of the promo already exist
        for (Object pass : promomenu) {
            PromoItems p = (PromoItems) pass;
            if (p.getname() == name) {
                System.out.println("Another item with the same name already exists");
                exist = true;
            }
        }

        if (exist == false) {

            System.out.println("Enter the MenuItems to include in the set: (N to terminate)");
            System.out.println("-------------------------------------------");

            int i = 1;
            for (Object pass2 : menu) {
                MenuItem m = (MenuItem) pass2;
                System.out.println(i + ": " + m.getname());
                i++;
            }

            while (true) {
                String gg = sc.nextLine();

                if (gg.equalsIgnoreCase("N"))
                    break;
                else {
                    MenuItem newpromoitem = null;

                    boolean valid = false; // check if the new item to be added to the promo exist
                    for (Object pass2 : menu) {
                        MenuItem m = (MenuItem) pass2;
                        if (gg.equalsIgnoreCase(m.getname())) {
                            newpromoitem = m;
                            valid = true;
                        }
                    }

                    if (valid) {
                        newpromo.add(newpromoitem);
                        System.out.println("Item added to promo");
                        System.out.println("Add another Item (N to quit)");
                        added = true;
                    } else
                        System.out.println("item does not exist, try again (N to quit)");
                }
            }

            if (added) {
                System.out.println("Promo Set " + name + " Created");
                promomenu.add(new PromoItems(newpromo, desc, price, name));
            }

        }
    }

    public void removePromo() {

        System.out.println("---------------Removing a Promotion---------------");
        System.out.println("Enter the promotion name to remove:");

        boolean removed = false;
        PromoItems m1 = null;

        while (true) {
            String name = sc.nextLine();

            if (name.equalsIgnoreCase("N"))
                break;

            for (Object pass : promomenu) {
                PromoItems m = (PromoItems) pass;
                if (m.getname().equalsIgnoreCase(name)) {
                    m1 = m;
                    removed = true;
                }
            }

            if (removed == false) {
                System.out.println("Item does not exist try again (N to quit)");
            } else {
                promomenu.remove(m1);
                System.out.println(m1.getname() + " removed Successfully");
                break;
            }
        }
    }

}