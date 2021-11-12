package Classes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

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
