/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package payrollsystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class SubmissionManager {
    
    public HoursSubmissionData loadStatus() {
        
        String query = "SELECT wf.employee_id, CONCAT(e.fname, ' ', e.lname) AS fullName, " +
               "wf.weekNumber, wf.total_hours, wf.salary, " +
               "wf.monday, wf.tuesday, wf.wednesday, wf.thursday, wf.friday, wf.saturday, wf.sunday " +
               "FROM weekly_finance wf " +
               "INNER JOIN employees e ON wf.employee_id = e.employee_id " +
               "WHERE wf.status = 'pending' " +
               "LIMIT 1";


        try (Connection con = DatabaseManager.getConnection();
             PreparedStatement psTotal = con.prepareStatement(query);
             ResultSet rsTotal = psTotal.executeQuery()) {

            if (rsTotal.next()) { 
                int employeeId = rsTotal.getInt("employee_id");
                String employeeName = rsTotal.getString("fullName"); 
                int weekNumber = rsTotal.getInt("weekNumber");
                double totalHours = rsTotal.getDouble("total_hours");
                double salary = rsTotal.getDouble("salary");

                StringBuilder workedDays = new StringBuilder();

                if (rsTotal.getDouble("monday") > 0) workedDays.append("Monday ");
                if (rsTotal.getDouble("tuesday") > 0) workedDays.append("Tuesday ");
                if (rsTotal.getDouble("wednesday") > 0) workedDays.append("Wednesday ");
                if (rsTotal.getDouble("thursday") > 0) workedDays.append("Thursday ");
                if (rsTotal.getDouble("friday") > 0) workedDays.append("Friday ");
                if (rsTotal.getDouble("saturday") > 0) workedDays.append("Saturday ");
                if (rsTotal.getDouble("sunday") > 0) workedDays.append("Sunday ");
                
                String workedDaysString = workedDays.toString();
                
                return new HoursSubmissionData(employeeId, employeeName, weekNumber, totalHours, salary, workedDaysString, true);

            }

        } catch (SQLException e) {
            System.out.println("Error fetching pending employee: " + e);
        }
        
        return new HoursSubmissionData(0, " ", 0, 0, 0, " ", false);
}
    
    public void approveHours(int employeeId){
    
        String query = "UPDATE weekly_finance SET status = 'approved' WHERE employee_id = ?";

            try (Connection con = DatabaseManager.getConnection();
                 PreparedStatement ps = con.prepareStatement(query)) {

                ps.setInt(1, employeeId); 
                ps.executeUpdate();

                JOptionPane.showMessageDialog(null, "Hours approved for Employee ID n." + employeeId);

                loadStatus(); 

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error approving employee hours: " + e);
            }

    }
    
     public void rejectHours(int employeeId){
   
        String query = "UPDATE weekly_finance SET status = 'rejected' WHERE employee_id = ?";

        try (Connection con = DatabaseManager.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, employeeId); 
            ps.executeUpdate();

            JOptionPane.showMessageDialog(null, "Hours rejected for employee ID n." + employeeId);

            loadStatus(); 

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error rejecting employee hours: " + e);
        }
    }    
    
}
