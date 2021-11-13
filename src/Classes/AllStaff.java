package Classes;

import java.io.*;
import java.util.*;

import Classes.Staff.Gender;
import Classes.Staff.JobTitle;

/**
 * AllStaff 
 * this class is a container class containing all staff info
 */
public class AllStaff implements DatabaseHandler {
    private List<Staff> staffList;

    public AllStaff(){}

    
	/** 
	 * @return List<Staff> retreives the list of staffs
	 */
	public List<Staff> getstaffList(){
        return this.staffList;
    }

    
	/** 
	 * @param lst sets the list of staffs
	 */
	public void setStaff(List<Staff> lst){
		this.staffList = lst;
	}

	/**
	 * Method to serialize staff list
	 */
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

	/**
	 * Method to deserialize staff list
	 */
	@SuppressWarnings("unchecked")
    public void deserializeFromFile(){
        List<Staff> staffDetails = null;
		FileInputStream fis = null;
		ObjectInputStream in = null;
		try {
			fis = new FileInputStream("stafflist.dat");
			in = new ObjectInputStream(fis);
			staffDetails = (List<Staff>) in.readObject();
			in.close();
		} catch (Exception e){
            e.printStackTrace();
        }
		this.staffList = staffDetails;
        //	System.out.println(staffList.size());
    }


	/**
	 * Main to pre-create 2 staff into the system
	 */
	public static void main(String[] args) {
		Staff s1 = new Staff(1001, "Mr Meerkat",Gender.M , JobTitle.WAITER);
		Staff s2 = new Staff(1002, "Ms Meerkat",Gender.F , JobTitle.MANAGER);

		Staff arr[] = {s1,s2};
		List<Staff> ll = Arrays.asList(arr);
		DatabaseHandler db = new AllStaff();
		((AllStaff)db).setStaff(ll);
		db.serializeToFile();
		System.out.println(ll.size() + " Added");
	}



}
