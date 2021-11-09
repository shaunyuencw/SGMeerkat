package Classes;

import java.io.*;
import java.util.*;

import Classes.Staff.Gender;
import Classes.Staff.JobTitle;

/**
 * AllStaff 
 * this class is a container class containing all staff info
 */
public class AllStaff implements DataBaseHandler {
    private List<Staff> staffList;

    public AllStaff(){}

    public List<Staff> getstaffList(){
        return this.staffList;
    }

    public void setStaff(List<Staff> lst){
		this.staffList = lst;
	}

    public void serializeToFile() {
        FileOutputStream fos = null;
		ObjectOutputStream out = null;
		try {
			fos = new FileOutputStream("stafflist.dat");
			out = new ObjectOutputStream(fos);
			out.writeObject(this.staffList);
			out.close();
			//	System.out.println("Object Persisted");
		} catch (IOException ex) {
			ex.printStackTrace();
		}

    }

    public void deserializeFromFile(){
        List<Staff> staffDetails = null;
		FileInputStream fis = null;
		ObjectInputStream in = null;
		try {
			fis = new FileInputStream("stafflist.dat");
			in = new ObjectInputStream(fis);
			staffDetails = (List<Staff>) in.readObject();
			in.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}
		this.staffList = staffDetails;

    }


    public static void main(String[] args) {
		Staff s1 = new Staff(1001, "Mr Meerkat",Gender.M , JobTitle.WAITER);
		Staff s2 = new Staff(1002, "Ms Meerkat",Gender.F , JobTitle.MANAGER);

		Staff arr[] = {s1,s2};
		List<Staff> ll = Arrays.asList(arr);
		DataBaseHandler db = new AllStaff();
		((AllStaff)db).setStaff(ll);
		db.serializeToFile();
		System.out.println(ll.size());
	}



}
