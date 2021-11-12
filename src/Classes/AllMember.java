package Classes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class AllMember {
    private List<Member> memberList;
    private static Scanner sc = new Scanner(System.in);

    public AllMember(){
        memberList = new ArrayList<>();
        this.deserializeFromFile();
    }

    public boolean checkIfMember(){
        System.out.println("May i have your name: ");
        String memName = sc.nextLine();
        System.out.println("May i have your contact number: ");
        int memContact = sc.nextInt();
        for(int i = 0; i < memberList.size(); i++){
            Member checkMember = memberList.get(i);
            if(checkMember.getMemberName().equals(memName) && checkMember.getMemberContact() == memContact){
                return true;
            }
        }
        return false;
    }

    public void addNewMember(){
        System.out.println("May i have your name: ");
        String memName = sc.nextLine();
        System.out.println("May i have your contact number: ");
        int memContact = sc.nextInt();
        Member newMember = new Member(memName, memContact);
        memberList.add(newMember);
        this.serializeToFile();
    }

    public void serializeToFile() {
        try {
            File menu_file = new File("member.dat");
            ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(menu_file));

            output.writeObject(memberList);
            output.flush();
            output.close();

            System.out.println("Menu updated.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public void deserializeFromFile() {
        try {
            File menu_file = new File("member.dat");
            ObjectInputStream input = new ObjectInputStream(new FileInputStream(menu_file));

            //Reads the first object in
            Object readObject = input.readObject();
            input.close();

            if(!(readObject instanceof HashMap)) throw new IOException("Data is not a hashmap");
            this.memberList = (ArrayList<Member>) readObject;
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
