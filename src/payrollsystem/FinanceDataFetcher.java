package payrollsystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FinanceDataFetcher {
    
    String financeQuery, totalSalary;
    private final EmployeeInfo employeeInfo = EmployeeInfo.getInstance();
    
    public void loadEmployeeFinance(int id) {
        financeQuery = "SELECT * FROM weekly_finance WHERE employee_id = ? AND weekNumber = WEEK(CURDATE(), 1)";

        try (Connection con = DatabaseManager.getConnection();
             PreparedStatement ps = con.prepareStatement(financeQuery)) {

            // Pass the correct ID
            ps.setInt(1, id);

            // Execute query
            ResultSet rs = ps.executeQuery();

            // Process results
            if (rs.next()) {
                int weekNumber = rs.getInt("weekNumber");
                int totalHours = rs.getInt("total_hours");
                double salary = rs.getDouble("salary");

                System.out.println("Total Hours Worked: " + totalHours);
                System.out.println("Salary: €" + salary);

                employeeInfo.setWeekNumber(weekNumber);
                employeeInfo.setTotalHours(totalHours);
                employeeInfo.setSalary(salary);

            } else {
                System.out.println("No financial record found for user ID: " + id + " for the current week.");
            }

        } catch (SQLException e) {
            System.out.println("Error accessing database: " + e);
        }
    }
    
    public List<EmployeeInfo> loadAllEmployeesFinance() {
        financeQuery = "SELECT employee_id, weekNumber, total_hours, salary FROM weekly_finance WHERE weekNumber = WEEK(CURDATE(), 1)";
        List<EmployeeInfo> employeeList = new ArrayList<>();

        try (Connection con = DatabaseManager.getConnection();
             PreparedStatement ps = con.prepareStatement(financeQuery);
             ResultSet rs = ps.executeQuery()) {

            // Loop through the rows for employee data
            while (rs.next()) {
                int id = rs.getInt("employee_id");
                int weekNumber = rs.getInt("weekNumber");
                int totalHours = rs.getInt("total_hours");
                double salary = rs.getDouble("salary");

                employeeList.add(new EmployeeInfo(id, weekNumber, totalHours, salary));
            }

        } catch (SQLException e) {
            System.out.println("Error fetching employee finance data: " + e);
        }

        return employeeList;
    }
    
    public double loadTotalLabourCost(){
        double totalLabourCost = 0;
        totalSalary = "SELECT SUM(salary) AS total_labour_cost FROM weekly_finance WHERE weekNumber = WEEK(CURDATE(), 1)";
        
        try (Connection con = DatabaseManager.getConnection();
             PreparedStatement psTotal = con.prepareStatement(totalSalary);
             ResultSet rsTotal = psTotal.executeQuery()) {

            if (rsTotal.next()) {
                totalLabourCost = rsTotal.getDouble("total_labour_cost");
                System.out.println("Total Labor Cost for Current Week: €" + totalLabourCost);
            }

        } catch (SQLException e) {
            System.out.println("Error fetching total labor cost: " + e);
        }
    
        return totalLabourCost;
    }
}
