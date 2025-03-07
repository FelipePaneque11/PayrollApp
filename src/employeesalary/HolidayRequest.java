/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package employeesalary;

/**
 *
 * @author paneq
 */

public class HolidayRequest {
    private int holidayId;
    private int employeeId;
    private String startWeek;
    private String endWeek;
    private String employeeReason; // New field

    public HolidayRequest() {
    }
    
    
    
    // Constructor including the employee reason
    public HolidayRequest(int holidayId, int employeeId, String startWeek, String endWeek, String employeeReason) {
        this.holidayId = holidayId;
        this.employeeId = employeeId;
        this.startWeek = startWeek;
        this.endWeek = endWeek;
        this.employeeReason = employeeReason;
    }

    public int getHolidayId() {
        return holidayId;
    }

    public void setHolidayId(int holidayId) {
        this.holidayId = holidayId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getStartWeek() {
        return startWeek;
    }

    public void setStartWeek(String startWeek) {
        this.startWeek = startWeek;
    }

    public String getEndWeek() {
        return endWeek;
    }

    public void setEndWeek(String endWeek) {
        this.endWeek = endWeek;
    }

    public String getEmployeeReason() {
        return employeeReason;
    }

    public void setEmployeeReason(String employeeReason) {
        this.employeeReason = employeeReason;
    }
    
    
    
}
