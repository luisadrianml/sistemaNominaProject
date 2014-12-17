/*
 * Sistema de nomina - Analisis y diseño de sistemas
 * Universidad Iberoamericana
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
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sistemanomina.Dialogs;

/**
 * Clase que plantea los lineamientos de impresion de PDF del comprobante
 * @author SistemaNomina LJ
 */
public class Comprobante {
    Dialogs dg;
    private  int id_employee = 130980;
    private  String name_employee = "Richard";
    private  String ced_employee = "001-0000000-1";
    private String lastname_employee = "Parker";
    private  double salary_employee = 15000.00;
     private  String month = "DICIEMBRE";
   // private  String FILE = "C:\\Comprobante_Nomina_"+id_employee+".pdf";
     private  String FILE = "C:\\Nomina";
    private  int columns = 5;
    private int columnsTodos = 6;
    private  Font columnTitle = new Font(Font.FontFamily.HELVETICA,10,Font.BOLD);
    private  PdfPTable pdfTable = new PdfPTable(columns);
    private PdfPTable pdfTableTodos = new PdfPTable(columnsTodos);
    private int cantidadPagos = 0;
    private File filePDF;
    Document document;

    public int getCantidadPagos() {
        return cantidadPagos;
    }

    public void setCantidadPagos(int cantidadPagos) {
        this.cantidadPagos = cantidadPagos;
    }

    public File getFilePDF() {
        return filePDF;
    }

    public void setFilePDF(File filePDF) {
        this.filePDF = filePDF;
    }

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

    /**
     * Metodo que crea el archivo PDF y el header de este con los atributos asignados en ese momento al objeto
     * @param event Evento de las vistas
     */
    public void createandheaderPDF(ActionEvent event) {
        
        try{
                    filePDF = new File(FILE);
                    filePDF.setWritable(true);
                    filePDF.mkdirs();
                    filePDF = new File(FILE+"\\comprobante_"+id_employee+"_periodo_"+month+"_tipo_"+cantidadPagos+"_#"+Math.floor(Math.random()*(10000-1000)+1000)+".pdf");
                    if (!filePDF.exists()) {
                        filePDF.createNewFile();                    
                } else if (filePDF.exists()) {
                    filePDF.getAbsoluteFile().renameTo(new File(FILE+"\\comprobante_"+id_employee+"_periodo_"+month+"_tipo_"+cantidadPagos+"_#"+Math.floor(Math.random()*(10000-1000)+1000)+"_2.pdf"));
                    filePDF.createNewFile();
                }

                OutputStream file = new FileOutputStream(filePDF);
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
                paragraph.add(new Paragraph("Pago numero: "+cantidadPagos));
                paragraph.add(new Paragraph(" "));
                paragraph.add(new Paragraph(" "));
                document.add(paragraph);
            
        } catch(Exception e){
            e.printStackTrace();
            if(e.toString().startsWith("java.io.FileNotFoundException:")) {
                dg = new Dialogs( (Stage) ((Node) event.getSource()).getScene().getWindow());
                dg.exceptionDialog("Error - archivo pdf", "Errores encontrados", "El archivo no se ha podido crear por problemas de permisos, o esta en uso.", e);
            }
        }
    
        
    }
    
    /**
     * Metodo que sirve solo para crear la instancia del PDF, es decir crear su ubicacion y empezar proceso de escritura
     * @param event Evento de la vista
     */
    public void createHeader(ActionEvent event) {
        try{
                    filePDF = new File(FILE);
                    filePDF.setWritable(true);
                    filePDF.mkdirs();
                    filePDF = new File(FILE+"\\comprobante_todos_periodo_"+month+"_tipo_"+cantidadPagos+"_#"+Math.floor(Math.random()*(10000-1000)+1000)+".pdf");
                    if (!filePDF.exists()) {
                        filePDF.createNewFile();                    
                } else if (filePDF.exists()) {
                    filePDF.getAbsoluteFile().renameTo(new File(FILE+"\\comprobante_todos_periodo_"+month+"_tipo_"+cantidadPagos+"_#"+Math.floor(Math.random()*(10000-1000)+1000)+"_2.pdf"));
                    filePDF.createNewFile();
                }

                OutputStream file = new FileOutputStream(filePDF);
                document = new Document();
                PdfWriter.getInstance(document, file);
                
                document.open();
                document.add(new Paragraph(new Date().toString()));
                document.add(new Paragraph(""));
                Paragraph p = new Paragraph("COMPROBANTE DE NOMINA", new Font(Font.FontFamily.HELVETICA,20,Font.BOLD));
                p.setAlignment(ALIGN_CENTER);
                document.add(p);
                document.add(new Paragraph(" "));
                document.add(new Paragraph(" "));
                document.add(new Paragraph("MES: " +month.toUpperCase()));
                document.add(new Paragraph("PERIODO: "+cantidadPagos + ""));
                document.add(new Paragraph(" "));
                document.add(new Paragraph(" "));
        } catch(Exception e){
            e.printStackTrace();
            if(e.toString().startsWith("java.io.FileNotFoundException:")) {
                dg = new Dialogs( (Stage) ((Node) event.getSource()).getScene().getWindow());
                dg.exceptionDialog("Error - archivo pdf", "Errores encontrados", "El archivo no se ha podido crear por problemas de permisos, o esta en uso.", e);
            }
        }
    }
    
    /**
     * Metodo que setea los datos el empleado en el PDF
     * @param event Evento de la vista
     */
    public void creatingDataEmployee(ActionEvent event) {
        
        try {
                Paragraph paragraph = new Paragraph("COMPROBANTE DE NOMINA", new Font(Font.FontFamily.HELVETICA,12,Font.BOLD));
                paragraph.setAlignment(ALIGN_CENTER);
                
                paragraph.add(new Paragraph(" "));
                paragraph.add(new Paragraph("Empleado: "+name_employee+" "+ lastname_employee));
                paragraph.add(new Paragraph("ID: "+id_employee));
                paragraph.add(new Paragraph("Salario: "+salary_employee));
                paragraph.add(new Paragraph("Periodo: "+month));
                paragraph.add(new Paragraph("Pago numero: "+cantidadPagos));
                paragraph.add(new Paragraph(" "));
                paragraph.add(new Paragraph(" "));
                document.add(paragraph);
                document.add(new Paragraph(" "));
        } catch(Exception e){
            e.printStackTrace();
            if(e.toString().startsWith("java.io.FileNotFoundException:")) {
                dg = new Dialogs( (Stage) ((Node) event.getSource()).getScene().getWindow());
                dg.exceptionDialog("Error - archivo pdf", "Errores encontrados", "El archivo no se ha podido crear por problemas de permisos, o esta en uso.", e);
            }
        }
    }
    
    /**
     * Metodo que crea la tabla general de los comprobantes
     */
    public void createTable() {
                PdfPCell cell1 = new PdfPCell(new Phrase("DESCRIPCION",columnTitle));
                cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
                pdfTable.addCell(cell1);
                
                cell1 = new PdfPCell(new Phrase("SALARIO BRUTO",columnTitle));
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
    
    /**
     * Metodo que crea la tabla para multitud de empleados
     */
        public void createTableEmpleado() {
                PdfPCell cell1 = new PdfPCell(new Phrase("EMPLEADO",columnTitle));
                cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
                pdfTableTodos.addCell(cell1);
                
                cell1 = new PdfPCell(new Phrase("SALARIO BRUTO",columnTitle));
                cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
                pdfTableTodos.addCell(cell1);
                
                cell1 = new PdfPCell(new Phrase("INGRESOS",columnTitle));
                cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
                pdfTableTodos.addCell(cell1);
                
                cell1 = new PdfPCell(new Phrase("IMPUESTOS",columnTitle));
                cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
                pdfTableTodos.addCell(cell1);
                
                cell1 = new PdfPCell(new Phrase("DEDUCCIONES",columnTitle));
                cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
                pdfTableTodos.addCell(cell1);
                
                cell1 = new PdfPCell(new Phrase("SALARIO NETO",columnTitle));
                cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
                pdfTableTodos.addCell(cell1);
                pdfTableTodos.setHeaderRows(1);
    }
    
        /**
         * Metodo que adjunta la tabla al documento, y finaliza la escritura en el documento
         */
    public void cerrarDoc() {
        try {
            document.add(pdfTable);
            document.addAuthor("Sistema de Nomina - LJ");
            document.addTitle("Analisis y diseño de sistemas");
            document.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
                
    }
    
    /**
     * Metodo que cierra la tabla del documento
     */
    public void cerrarTabla() {
        try {
            document.add(pdfTableTodos);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Metodo que unicamente cierra el documento (la escritura en el documento)
     */
    public void soloCerrarDocFinal() {
                try {
            document.addAuthor("Sistema de Nomina - LJ");
            document.addTitle("Analisis y diseño de sistemas");
            document.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Metodo que llena el salario en la tabla
     * @param salario Salario a poner en respectiva celda
     */
    public void llenarSalario(Double salario) {
        pdfTable.addCell("Salario bruto");
        pdfTable.addCell(salario.toString());
        pdfTable.addCell("");
        pdfTable.addCell("");
        pdfTable.addCell("");
    }
    
    /**
     * Metodo que configura los ingresos de un empleado
     * @param ingreso Monto del ingreso
     * @param nombre  Nombre del ingreso
     */
    public void llenarIngresos(Double ingreso, String nombre) {
        pdfTable.addCell(nombre);
        pdfTable.addCell("");
        pdfTable.addCell(ingreso.toString());
        pdfTable.addCell("");
        pdfTable.addCell("");
    }
    
    /**
     * Metodo que configura las deducciones de un empleado
     * @param deducc Monto de la deduccion
     * @param nombre Nombre de la deduccion
     */
    public void llenarDeduccion(Double deducc, String nombre) {
        pdfTable.addCell(nombre);
        pdfTable.addCell("");
        pdfTable.addCell("");
        pdfTable.addCell(deducc.toString());
        pdfTable.addCell("");
    }
    
    /**
     * Metodo que inserta el total de ingresos
     * @param tingreso Monto del total de ingresos
     */
    public void totalIngresos(Double tingreso) {
        pdfTable.getDefaultCell().setBackgroundColor(BaseColor.YELLOW);
        pdfTable.addCell("Total de ingresos:");
        pdfTable.addCell("");
        pdfTable.addCell(tingreso.toString());
        pdfTable.addCell("");
        pdfTable.addCell("");
        pdfTable.getDefaultCell().setBackgroundColor(null);
        
    }
    
    /**
     * Metodo que inserta el total de ducciones
     * @param tdeduc Monto total de deducciones
     */
    public void totalDeducciones(Double tdeduc) {
        pdfTable.getDefaultCell().setBackgroundColor(BaseColor.YELLOW);
        pdfTable.addCell("Total de deducciones:");
        pdfTable.addCell("");
        pdfTable.addCell("");
        pdfTable.addCell(tdeduc.toString());
        pdfTable.addCell("");
        pdfTable.getDefaultCell().setBackgroundColor(null);
    }
    
    /**
     * Metodo que inserta el valor neto de un empleado
     * @param neto Monto del valor neto
     */
    
    public void neto(Double neto) {
        pdfTable.getDefaultCell().setBackgroundColor(BaseColor.RED);
        pdfTable.addCell("Total a pagar");
        pdfTable.addCell("");
        pdfTable.addCell("");
        pdfTable.addCell("");
        pdfTable.addCell(neto.toString());
        pdfTable.getDefaultCell().setBackgroundColor(null);
    }
    
    /**
     * Metodo que permite llenado para comprobante de muchos empleados
     * @param empleado Nombre empleado
     * @param sueldo Monto de su sueldo
     * @param ingresos Monto de sus ingresos
     * @param impuestos Monto de los impuestos
     * @param deducciones Monto de las deducciones
     * @param neto Monto del neto a pagar
     */
    public void fillNecesario(String empleado, String sueldo, String ingresos, String impuestos, String deducciones, String neto) {
        pdfTableTodos.addCell(empleado);
        pdfTableTodos.addCell(sueldo);
        pdfTableTodos.addCell(ingresos);
        pdfTableTodos.addCell(impuestos);
        pdfTableTodos.addCell(deducciones);    
        pdfTableTodos.addCell(neto);
    }
    
    /**
     * Metodo que permite abrir el PDF
     */
    public void abrirPDF() {
        try {
            File path = filePDF;
            Desktop.getDesktop().open(path);
        } catch(IOException ex) {
            ex.printStackTrace();
        }
    }
    

    
}
