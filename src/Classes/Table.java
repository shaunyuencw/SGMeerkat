package Classes;

//import java.util.*;

// ! NOT CONFIRMED
public class Table {
    private static final double GST_CHARGE = 0.07; // in percentage
    private static final double SERVICE_CHARGE = 0.10; // in percentage

    private int table_id;
    private int num_seats;
    private boolean occupied;
    private boolean reserved;
    private OrderList order_list;

    public Table(int table_id, int num_seats, String staff_name){
        this.table_id = table_id;
        this.num_seats = num_seats;

        // Table is not occupied nor reserved on init
        this.occupied = false;
        this.reserved = false;
        this.order_list = new OrderList(staff_name);
    }

    public int getTable_id() {
        return this.table_id;
    }

    public void setTable_id(int table_id) {
        this.table_id = table_id;
    }

    public int getNum_seats() {
        return this.num_seats;
    }

    public void setNum_seats(int num_seats) {
        this.num_seats = num_seats;
    }

    public void reserve(){
        if (this.reserved == false){
            this.reserved = true;
        }
        else{
            System.out.println("Table " + this.table_id + " is not reserved.");
        }
    }

    public boolean isReserved() {
        return this.reserved;
    }

    public void seatGuest(String staff_name){
        if (!this.occupied){
            this.occupied = true;
            this.order_list = new OrderList(staff_name);
        }
        else{
            System.out.println("Table " + this.table_id + " is already occupied.");
        }
    }

    public boolean isOccupied(){
        return this.occupied;
    }

    public void printInvoice(){
        // TODO Some calculation and output
        System.out.println(order_list.getTotalPrice() * (1 + GST_CHARGE + SERVICE_CHARGE));
        this.occupied = false;
    }
}