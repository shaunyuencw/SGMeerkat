package Classes;

import java.io.Serializable;

public class Account implements Serializable{
    private String password;
    private String username;
    private int domain;

    public Account(String username, String password, int domain){
        this.username = username;
        this.password = password;
        this.domain = domain;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public void setDomain(int domain){
        this.domain = domain;
    }

    public int getDomain(){
        return this.domain;
    }

    public String getPassword(){
        return this.password;
    }

    public String getUsername(){
        return this.username;
    }

    @Override
    public boolean equals(Object p1){
        Account p2 = (Account) p1;
        if (this.password == p2.getPassword() && this.username == p2.getUsername() && this.domain == p2.getDomain())
            return true;
        else
            return false;
    }

    
	}



