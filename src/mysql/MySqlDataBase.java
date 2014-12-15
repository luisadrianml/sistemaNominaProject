/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mysql;


import java.sql.*;
import javafx.application.Platform;
import sistemanomina.Dialogs;


public class MySqlDataBase {
    
    private String url="";
    private Connection conn=null;

    public MySqlDataBase(){
    
        try {
            Class.forName("com.mysql.jdbc.Driver");
            url="jdbc:mysql://localhost:3306/nomina1";
            conn = DriverManager.getConnection(url,"root","");
            System.out.println("Se ha conectado la Base de Datos "+url+".........Ok");
 
        
        }catch(SQLException ex){

            if (ex.toString().startsWith("com.mysql.jdbc.exceptions.jdbc4.CommunicationsException")) {
                Dialogs dg = new Dialogs();  
                dg.errorDialog("Error con base de datos", "Problemas con la base de datos", "Se ha encontrado un problema con la base de datos, donde no se ha podido conectar. Reinicie.");
                Platform.exit();
            }
                
         }catch(ClassNotFoundException ex){

            System.out.println(ex);
         }
    
        

    }
    
    public Connection getConn(){
        return conn;
    }
    
    public Connection closeConn(){
        
        try{
            conn.close();
            System.out.println("La conexion con la Base de Datos: "+url+"se esta cerrando......");

        }catch(SQLException ex){
            System.out.println(ex);
        }
        
        conn=null;
        return conn;
    }
    
    public void Insert(String table, String values){
        try{
            PreparedStatement ps = conn.prepareStatement("INSERT INTO "+table+" VALUES ("+values+")");
            ps.execute();
            
        }catch(SQLException ex){
            System.out.println(ex);
            System.out.println("INSERT INTO "+table+" VALUES ("+values+")");
        }
    
    }
    
    public void Insert(String table, String columns, String values){
        try{
            PreparedStatement ps = conn.prepareStatement("INSERT INTO "+table+" ("+columns+") VALUES ("+values+")");
            ps.execute();
            
        }catch(SQLException ex){
            System.out.println(ex);
            System.out.println("INSERT INTO "+table+" ("+columns+") VALUES ("+values+")");
        }
    
    }
    
    public ResultSet fillComboBox(String query){
        ResultSet rs=null;
        
        try{
            Statement s = conn.createStatement();
            
            
                rs = s.executeQuery(query);
          

        }catch(SQLException ex){
            System.out.println(ex);            
//            System.out.println("query "+select);

        }
        System.out.println(rs);
        
        return rs;
    }
    
        
        public ResultSet Delete(String table, String where_nombre, String where_value){
            
            ResultSet rs=null;
            
            try{
                Statement s = conn.createStatement();
                
                s.executeUpdate("DELETE FROM "+table+" WHERE "+where_nombre+"='"+where_value+"'");
                
            }catch(SQLException ex){
                System.out.println(ex);
                System.out.println("DELETE FROM "+table+" WHERE "+where_nombre+"='"+where_value+"'");
            }
            
            return rs;
        }
        
        public ResultSet Update(String table, String set_values, String where_type, String where_value) {
            
            ResultSet rs=null;
            
            try{
                Statement s = conn.createStatement();
       
                s.executeUpdate("UPDATE "+table+" SET "+set_values+" WHERE "+where_type+" = '"+where_value+"'");
                
            } catch(SQLException ex){
                System.out.println(ex);
                System.out.println("UPDATE "+table+" SET "+set_values+" WHERE "+where_type+" = '"+where_value+"'");
            }
            
            
            
            return rs;
        }
        
        public ResultSet Update(String table, String set_values, String where_type, int where_value){
            
            ResultSet rs=null;
            
            try{
                Statement s = conn.createStatement();
       
                s.executeUpdate("UPDATE "+table+" SET "+set_values+" WHERE "+where_type+" = "+where_value);
                
            }catch(SQLException ex){
                System.out.println(ex);
                System.out.println("UPDATE "+table+" SET "+set_values+" WHERE "+where_type+" = "+where_value);
            }
            
            
            
            return rs;
        }
        
        
        
        public ResultSet Select(String column_from_tables, String table) {
        
                ResultSet rs=null;
            
            try{
                Statement s = conn.createStatement();
                
                rs = s.executeQuery("select "+column_from_tables+" from "+table);
                
            }catch(SQLException ex){
                System.out.println(ex);
                System.out.println("select "+column_from_tables+" from "+table);
            }
            
            return rs;
        }
    
        public ResultSet Select(String column_from_tables, String table, String where_type,
                String where_value) {
        
                ResultSet rs=null;
            
            try{
                Statement s = conn.createStatement();
                
                rs = s.executeQuery("select "+column_from_tables+" from "+table+" where "
                        +where_type+"='"+where_value+"'");
                
            }catch(SQLException ex){
                System.out.println(ex);
                System.out.println("select "+column_from_tables+" from "+table+" where "
                        +where_type+"='"+where_value+"'");
            }
            
            return rs;
        }
        
        
                public ResultSet Select(String column_from_tables, String table, String where) {
        
                ResultSet rs=null;
            
            try{
                Statement s = conn.createStatement();
                
                rs = s.executeQuery("select "+column_from_tables+" from "+table+" where "
                        +where);
                
            }catch(SQLException ex){
                System.out.println(ex);
                System.out.println("select "+column_from_tables+" from "+table+" where "
                        +where);
            }
            
            return rs;
        }
        
        public void Call(String procedure){
            try{
                CallableStatement callSt = conn.prepareCall(procedure);
                callSt.execute();
                System.out.println(procedure);
                
            }catch(SQLException ex){
                
                System.out.println(ex);
     //           duplicatedIdLabel.setText("Clave ya existe");
                System.out.println(procedure);
            }

        }
  
}
