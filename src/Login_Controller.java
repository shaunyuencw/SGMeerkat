import java.util.*;

import Classes.*;

public class Login_Controller {
   
    private List loginList;

    public Login_Controller(List loginData){
        this.loginList = loginData;
    }

    public boolean checkPassword(String userName1 , String password,int num){
        Account tobeChecked = new Account(userName1, password, num);
        for (Object pass: loginList){
           
            Account p = (Account) pass;
            System.out.println("Username in database"+p.getUsername());
            System.out.println("Entered Username" +tobeChecked.getUsername());

            if (p.getUsername() == tobeChecked.getUsername())
                    return true;
            
        }
        return true;
    }
}
