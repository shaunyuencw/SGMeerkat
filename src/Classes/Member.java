package Classes;

import java.io.*;
// import java.util.*; // Not used

public class Member implements Serializable {
    private static final long serialVersionUID = 1L;
    private String memberName;
    private int memberContact;

    public Member(String memberName, int memberContact){
        this.memberName = memberName;
        this.memberContact = memberContact;
    }

    public String getMemberName(){ return this.memberName; }
    public int getMemberContact(){ return this.memberContact; }
}
