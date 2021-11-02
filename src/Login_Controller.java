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

            if (p.getUsername().equals(tobeChecked.getUsername()) && p.getPassword().equals(tobeChecked.getPassword()) && p.getDomain()==tobeChecked.getDomain())
                    return true;
            
        }
        return false;
    }
}
