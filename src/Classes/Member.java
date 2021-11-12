package Classes;

import java.io.Serializable;

public class Member implements Serializable {
    private static final long serialVersionUID = 1L;
    private String memberName;
    private long memberContact;

    public Member(String memberName, long memberContact){
        this.memberName = memberName;
        this.memberContact = memberContact;
    }

    public String getMemberName(){ return this.memberName; }
    public long getMemberContact(){ return this.memberContact; }
}
