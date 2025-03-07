package payrollsystem;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JList;

public class StatementGenerator {
    
    private final EmployeeInfo employeeInfo = EmployeeInfo.getInstance();
    private final FinanceDataFetcher fm = new FinanceDataFetcher();
    DefaultListModel<String> listModel;
    
    public void generateEmployeeStatement() throws IOException {
        
       fm.loadEmployeeFinance(employeeInfo.getId());
       
       File directory = new File("statements"); // Relative path
       
       if (!directory.exists()) {
           directory.mkdir(); // Create directory if it doesn't exist
       }
       
       String dest = "statements/Week" + employeeInfo.getWeekNumber() + "_statement.pdf";
       
       try{
       PdfWriter pw = new PdfWriter(dest);
       PdfDocument pdfDoc = new PdfDocument(pw);
           try (Document document = new Document(pdfDoc)) {
               PdfFont fontBold = PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);
               PdfFont fontRegular = PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);
               document.setMargins(100, 100, 100, 100);
               
               // Add a page
                PdfPage page = pdfDoc.addNewPage();

                // Create a PdfCanvas for drawing on the page
                PdfCanvas canvas = new PdfCanvas(page);
               
                canvas.setStrokeColor(ColorConstants.ORANGE)
                      .moveTo(100, 710) // Start point
                      .lineTo(500, 710) // End point
                      .stroke();
                
               document.add(new Paragraph("EMPLOYEE STATEMENT REPORT").setFont(fontBold).setFontSize(20).setMarginBottom(30));
               document.add(new Paragraph("FROM PayrollPRO TO ").setFont(fontBold).setFontSize(15));
               document.add(new Paragraph("EMPLOYEE: " + employeeInfo.getName()).setFont(fontRegular).setFontSize(12));
               document.add(new Paragraph("ID: " + employeeInfo.getId()).setFont(fontRegular).setFontSize(12));
               document.add(new Paragraph("ROLE: " + employeeInfo.getRole()).setFont(fontRegular).setFontSize(12).setMarginBottom(30));
               document.add(new Paragraph("WEEK " + employeeInfo.getWeekNumber() +" SUMMARY").setFont(fontBold).setFontSize(15));
               document.add(new Paragraph("TOTAL HOURS WORKED: " + employeeInfo.getTotalHours()).setFont(fontRegular).setFontSize(12));
               document.add(new Paragraph("ESTIMATED SALARY: " + employeeInfo.getSalary()).setFont(fontRegular).setFontSize(12));
           }
       
       System.out.println("Created");
           
       } catch (IOException e){
           System.out.println("Error:" + e);
       }
       
       
       saveStatement(employeeInfo.getId(), dest);
       
    }
    
    public void generateFinancialReport(){
    
        List<EmployeeInfo> empFinancialInfo = fm.loadAllEmployeesFinance();
        File directory = new File("statements"); // Relative path
       
       if (!directory.exists()) {
           directory.mkdir(); // Create directory if it doesn't exist
       }
       
       String dest = "statements/Week" + employeeInfo.getWeekNumber() + "FinancialReport.pdf";
       
      try (PdfWriter pw = new PdfWriter(dest);
             PdfDocument pdfDoc = new PdfDocument(pw);
             Document document = new Document(pdfDoc)) {

            PdfFont fontBold = PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);
            PdfFont fontRegular = PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);

            document.setMargins(100, 100, 100, 100);
            
            // Add a page
                PdfPage page = pdfDoc.addNewPage();

                // Create a PdfCanvas for drawing on the page
                PdfCanvas canvas = new PdfCanvas(page);
               
                canvas.setStrokeColor(ColorConstants.BLUE)
                      .moveTo(100, 710) // Start point
                      .lineTo(500, 710) // End point
                      .stroke();
            
            document.add(new Paragraph("FINANCIAL REPORT").setFont(fontBold).setFontSize(20).setMarginBottom(30));
                
            for (EmployeeInfo ei : empFinancialInfo) {  // Loop through employees

                document.add(new Paragraph("EMPLOYEE ID: " + ei.getId()).setFont(fontBold).setFontSize(18));
                document.add(new Paragraph("TOTAL HOURS WORKED: " + ei.getTotalHours()).setFont(fontRegular).setFontSize(12));
                document.add(new Paragraph("ESTIMATED SALARY: €" + ei.getSalary()).setFont(fontRegular).setFontSize(12).setMarginBottom(30));

            }
            
            document.add(new Paragraph("TOTAL LABOUR COST: €" + fm.loadTotalLabourCost()).setFont(fontRegular).setFontSize(12).setMarginBottom(30));

        } catch (Exception e) {
            System.out.println("Error creating PDF: " + e);
        }
       
       saveStatement(employeeInfo.getId(), dest);
       
    }
    
    
    public void saveStatement(int employeeID, String statementPath){
    
        String query = "INSERT INTO statements (employee_id, statement_path) VALUES (?, ?)";
        
        try (Connection con = DatabaseManager.getConnection();
             PreparedStatement ps = con.prepareStatement(query);){
        
            ps.setInt(1, employeeID);
            ps.setString(2, statementPath);
            ps.executeUpdate();
            System.out.println("Statement saved");
            
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }
        
        }
    
    public void loadPastPDFs(JList<String> pdfList){
    
        listModel = new DefaultListModel<>();
        File directory = new File("statements");
        if (directory.exists() && directory.isDirectory()){
            File[] files = directory.listFiles((dir, name) -> name.toLowerCase().endsWith(".pdf"));
        
            if (files != null & files.length > 0){
                for (File file : files){
                    listModel.addElement(file.getName());
                }
            }
        
        }//first if
    
        pdfList.setModel(listModel);
        
    }
    
    public void openPastPDFs(JList<String> pdfList){
        String selectedFile = pdfList.getSelectedValue();
        if (selectedFile != null){
            File file = new File("statements/" + selectedFile);
            if (file.exists()){
                try {
                    Desktop.getDesktop().open(file);
                } catch (IOException e){
                    System.out.println(e);
                }
            }
        }
    
    }
}
