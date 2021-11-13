package Classes;

import java.io.Serializable;
/**
 * A Staff in the restaurant 
 */
public class Staff implements Serializable {
    private static final long serialVersionUID = 1L;
    public enum Gender{M,F};
    public enum JobTitle{CASHIER,WAITER,MANAGER};

    private int employeeID;
    private String staffName;
    Gender gender;
    JobTitle job;
    /** 
     * Constructor for Staff
    *@param employeeID id of the employee
    *@param staffName name of the staff
    *@param gender gender of the staff
    *@param job job of the staff
     */
    Staff(int employeeID, String staffName, Gender gender, JobTitle job)
    {
        this.employeeID = employeeID;
        this.staffName = staffName;
        this.gender = gender;
        this.job = job;
    }

    
    /** 
     * getstaffname
     * @return String the Name of the Staff
     */
    public String getStaffName() {
        return staffName;
    }

    
    /** 
     * get employee ID
     * @return int The EmployeeID
     */
    public int getEmployeeID(){
        return this.employeeID;
    }



    
}
