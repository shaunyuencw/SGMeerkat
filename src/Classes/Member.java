package Classes;

import java.io.Serializable;
/**
 * A member of this restaurant
 */
public class Member implements Serializable {
    private static final long serialVersionUID = 1L;
    private String memberName;
    private long memberContact;

    /**
     * default constructor for the menu
     * @param memberName
     * @param memberContact
     */
    public Member(String memberName, long memberContact){
        this.memberName = memberName;
        this.memberContact = memberContact;
    }
    /**
     * Get name of member
     * @return memberName
     */
    public String getMemberName(){ return this.memberName; }

    /**
     * Get contact of member
     * @return contact number of member
     */
    public long getMemberContact(){ return this.memberContact; }
}
