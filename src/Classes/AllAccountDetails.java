package Classes;

import java.io.*;
import java.util.*;


public class AllAccountDetails extends DataBaseHandler{

    private List<Account> AccountList;

    public AllAccountDetails(){
	}
    
    public List<Account> getAccounts(){
		return AccountList;
	}

    public void setAccounts(List<Account> lst){
		this.AccountList = lst;
	}



    public void serializeToFile(){
		FileOutputStream fos = null;
		ObjectOutputStream out = null;
		try {
			fos = new FileOutputStream("password.dat");
			out = new ObjectOutputStream(fos);
			out.writeObject(this.AccountList);
			out.close();
			//	System.out.println("Object Persisted");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public void deserializeFromFile(){
		List<Account> oDetails = null;
		FileInputStream fis = null;
		ObjectInputStream in = null;
		try {
			fis = new FileInputStream("password.dat");
			in = new ObjectInputStream(fis);
			oDetails = (List<Account>) in.readObject();
			in.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}
		this.AccountList = oDetails;
	}

	public static void main(String[] args) {
		Account l1 = new Account("zongwei2","1234",2);
		Account l2 = new Account("minion1","1234",1);
		Account l3 = new Account("minion2","1234",1);
		Account l4 = new Account("minion3","1234",1);
		Account l5 = new Account("minion4","1234",1);

		Account arr[] = {l1,l2,l3, l4,l5};
		List<Account> ll = Arrays.asList(arr);
		DataBaseHandler db = new AllAccountDetails();
		((AllAccountDetails)db).setAccounts(ll);
		db.serializeToFile();
		System.out.println(ll.size());
	}

}
