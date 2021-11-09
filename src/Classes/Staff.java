package Classes;

public class Staff {

    public enum Gender{M,F};
    public enum JobTitle{CASHIER,WAITER,MANAGER};

    private int employeeID;
    private String staffName;
    Gender gender;
    JobTitle job;

    Staff(int employeeID, String staffName, Gender gender, JobTitle job)
    {
        this.employeeID = employeeID;
        this.staffName = staffName;
        this.gender = gender;
        this.job = job;
    }

    public String getStaffName() {
        return staffName;
    }



    
}