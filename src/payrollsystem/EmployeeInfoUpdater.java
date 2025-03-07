/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package payrollsystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EmployeeInfoUpdater {
    
    public void saveInfo(int id, String firstName, String lastName, String address, String email, String phoneNo){
    
        String updateInfo = "UPDATE employees SET fName=?, lName=?, address=?, email=?, phoneN=? WHERE employee_id=?";
        
        try (Connection con = DatabaseManager.getConnection();
             PreparedStatement ps = con.prepareStatement(updateInfo);){
        
            ps.setString(1, firstName);
            ps.setString(2, lastName);
            ps.setString(3, address);
            ps.setString(4, email);
            ps.setString(5, phoneNo);
            ps.setInt(6, id);
            int rowsUpdated = ps.executeUpdate();
            
            if (rowsUpdated > 0) {
                System.out.println("Success");
            } else {
                System.out.println("Not updated");
            }
            
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }
        
    }
}
