/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nomina;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import static com.itextpdf.text.Element.ALIGN_CENTER;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

/**
 *
 * @author pc167
 */
public class Comprobante {
    private  int id_employee = 130980;
    private  String name_employee = "Richard";
    private  String ced_employee = "001-0000000-1";
    private String lastname_employee = "Parker";
    private  double salary_employee = 15000.00;
     private  String month = "DICIEMBRE";
    private  String FILE;
    private  int columns = 4;
    private  Font columnTitle = new Font(Font.FontFamily.HELVETICA,13,Font.BOLD);
    private  PdfPTable pdfTable = new PdfPTable(columns);
    Document document;

    public  int getId_employee() {
        return id_employee;
    }

    public String getLastname_employee() {
        return lastname_employee;
    }

    public void setLastname_employee(String lastname_employee) {
        this.lastname_employee = lastname_employee;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    
    public  void setId_employee(int id_employee) {
        this.id_employee = id_employee;
    }

    public  String getName_employee() {
        return name_employee;
    }

    public  void setName_employee(String name_employee) {
        this.name_employee = name_employee;
    }

    public  String getCed_employee() {
        return ced_employee;
    }

    public  void setCed_employee(String ced_employee) {
        this.ced_employee = ced_employee;
    }

    public  double getSalary_employee() {
        return salary_employee;
    }

    public  void setSalary_employee(double salary_employee) {
        this.salary_employee = salary_employee;
    }

    public  String getMonth() {
        return month;
    }

    public  void setMonth(String month) {
        this.month = month;
    }

    public  String getFILE() {
        return FILE;
    }

    public  void setFILE(String FILE) {
        this.FILE = FILE;
    }

    public  int getColumns() {
        return columns;
    }

    public  void setColumns(int columns) {
        this.columns = columns;
    }

    public  Font getColumnTitle() {
        return columnTitle;
    }

    public  void setColumnTitle(Font columnTitle) {
        this.columnTitle = columnTitle;
    }

    public  PdfPTable getPdfTable() {
        return pdfTable;
    }

    public  void setPdfTable(PdfPTable pdfTable) {
        this.pdfTable = pdfTable;
    }

    public void createandheaderPDF() {
        FILE = "C:\\Nomina\\Comprobante_Nomina_"+id_employee+".pdf";
        try{
                File filePDF = new File(FILE);
                if (!filePDF.exists()) {
                    filePDF.createNewFile();
                }
                OutputStream file = new FileOutputStream(new File (FILE));
                document = new Document();
                PdfWriter.getInstance(document, file);
                
                document.open();
                document.add(new Paragraph(new Date().toString()));
                
                          
                Paragraph paragraph = new Paragraph("COMPROBANTE DE NOMINA", new Font(Font.FontFamily.HELVETICA,20,Font.BOLD));
                paragraph.setAlignment(ALIGN_CENTER);
                
                paragraph.add(new Paragraph(" "));
                paragraph.add(new Paragraph("Empleado: "+name_employee+" "+ lastname_employee));
                paragraph.add(new Paragraph("ID: "+id_employee));
                paragraph.add(new Paragraph("Salario: "+salary_employee));
                paragraph.add(new Paragraph("Periodo: "+month));
                paragraph.add(new Paragraph(" "));
                paragraph.add(new Paragraph(" "));
                document.add(paragraph);
            
        } catch(Exception e){
            e.printStackTrace();
        }
    
        
    }
    
    public void createTable() {
                PdfPCell cell1 = new PdfPCell(new Phrase("DESCRIPCION",columnTitle));
                cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
                pdfTable.addCell(cell1);
                
                cell1 = new PdfPCell(new Phrase("INGRESOS",columnTitle));
                cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
                pdfTable.addCell(cell1);
                
                cell1 = new PdfPCell(new Phrase("DEDUCCIONES",columnTitle));
                cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
                pdfTable.addCell(cell1);
                
                cell1 = new PdfPCell(new Phrase("SALARIO A PAGAR",columnTitle));
                cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
                pdfTable.addCell(cell1);
                pdfTable.setHeaderRows(1);
    }
    
    public void cerrarDoc() {
        try {
            document.add(pdfTable);
                
            document.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
                
    }
    
    public void llenarIngresos(Double ingreso, String nombre) {
        pdfTable.addCell(nombre);
        pdfTable.addCell(ingreso.toString());
        pdfTable.addCell("");
        pdfTable.addCell("");
    }
    
    public void llenarDeduccion(Double deducc, String nombre) {
        pdfTable.addCell(nombre);
        pdfTable.addCell("");
        pdfTable.addCell(deducc.toString());
        pdfTable.addCell("");
    }
    
    public void totalIngresos(Double tingreso) {
        pdfTable.addCell("Total de ingresos:");
        pdfTable.addCell(tingreso.toString());
        pdfTable.addCell("");
        pdfTable.addCell("");
    }
    
    public void totalDeducciones(Double tdeduc) {
        pdfTable.addCell("Total de deducciones:");
        pdfTable.addCell("");
        pdfTable.addCell(tdeduc.toString());
        pdfTable.addCell("");
    }
    
    public void neto(Double neto) {
        pdfTable.addCell("Total a pagar");
        pdfTable.addCell("");
        pdfTable.addCell("");
        pdfTable.addCell(neto.toString());
    }
    
    public void fillNecesario(String first, String second, String third, String four) {
        
    }
    
    public void abrirPDF() {
        try {
            File path = new File(FILE);
            Desktop.getDesktop().open(path);
        } catch(IOException ex) {
            ex.printStackTrace();
        }
    }
    

    
}
