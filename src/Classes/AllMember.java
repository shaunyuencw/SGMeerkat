package Classes;

import java.io.*;
import java.util.*;

/**
 * The control class to handle Member entity class
 */
public class AllMember {
    private ArrayList<Member> memberList;
    private static Scanner sc = new Scanner(System.in);

    /**
     * Default constructor for the menu
     */
    public AllMember(){
        memberList = new ArrayList<>();
        this.deserializeFromFile();
    }

    /**
     * Method to verify if a customer is a member
     */
    public boolean checkIfMember(){
        System.out.println("May i have your name: ");
        String memName = sc.nextLine();
        long memContact;
        while(true){
            System.out.println("May i have your contact number: ");
            try{
                memContact = sc.nextLong();
                sc.nextLine();
                break;
            } catch (Exception e){
                System.out.println("Invalid input.");
            }
            sc.nextLine();
        }
        for(int i = 0; i < memberList.size(); i++){
            Member checkMember = memberList.get(i);
            if(checkMember.getMemberName().equals(memName) && checkMember.getMemberContact() == memContact){
                return true;
            }
        }
        return false;
    }

    /**
     * Method to add a new Member (aka sign up for membership)
     */
    public void addNewMember(){
        System.out.println("May i have your name: ");
        String memName = sc.nextLine();
        long memContact;
        while(true){
            System.out.println("May i have your contact number: ");
            try{
                memContact = sc.nextLong();
                sc.nextLine();
                break;
            } catch (Exception e){
                System.out.println("Invalid input.");
            }
            sc.nextLine();
        }
        Member newMember = new Member(memName, memContact);
        memberList.add(newMember);
        this.serializeToFile();
    }

    /**
     * Method to serialize member list
     */
    public void serializeToFile() {
        try {
            File member_file = new File("member.dat");
            ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(member_file));

            output.writeObject(memberList);
            output.flush();
            output.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to deserialize member list
     */
    @SuppressWarnings("unchecked")
    public void deserializeFromFile() {
        try {
            File member_file = new File("member.dat");
            ObjectInputStream input = new ObjectInputStream(new FileInputStream(member_file));

            //Reads the first object in
            Object readObject = input.readObject();
            input.close();

            this.memberList = (ArrayList<Member>) readObject;
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
