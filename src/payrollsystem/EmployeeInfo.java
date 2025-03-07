/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package payrollsystem;

public class EmployeeInfo {
    
    private static EmployeeInfo instance = null;
    private String fName, lName, role, email, address, phoneNo, hireDate;
    private int id, weekNumber, totalHours;
    private double salary, rate;
    
    private EmployeeInfo() {}

    public static synchronized EmployeeInfo getInstance() {
        if (instance == null) instance = new EmployeeInfo();
        return instance;
    }

    public EmployeeInfo(int id, int weekNumber, int totalHours, double salary) {
        this.id = id;
        this.weekNumber = weekNumber;
        this.totalHours = totalHours;
        this.salary = salary;
    }
    
    public void setName(String fName, String lName) {
        this.fName = fName;
        this.lName = lName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public void setRole(String role) {
        this.role = role;
    }

    public void setWeekNumber(int weekNumber) {
        this.weekNumber = weekNumber;
    }

    public void setTotalHours(int totalHours) {
        this.totalHours = totalHours;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public void setHireDate(String hireDate) {
        this.hireDate = hireDate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }
    
    public String getRole() {
        return role;
    }

    public int getWeekNumber() {
        return weekNumber;
    }

    public int getTotalHours() {
        return totalHours;
    }

    public double getSalary() {
        return salary;
    }

    public String getName() {
        return fName + " " + lName;
    }

    public String getfName() {
        return fName;
    }

    public String getlName() {
        return lName;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public String getHireDate() {
        return hireDate;
    }

    public double getRate() {
        return rate;
    }
       
}
