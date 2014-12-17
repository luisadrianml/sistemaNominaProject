/*
 * Sistema de nomina - Analisis y diseño de sistemas
 * Universidad Iberoamericana
 */
package mysql;


import java.sql.*;
import javafx.application.Platform;
import sistemanomina.Dialogs;

/**
 * Clase que realiza de intermediario la conexion con la base de datos
 * @author LuisAdrián
 */
public class MySqlDataBase {
    
    private String url="";
    private Connection conn=null;

    /**
     * Constructor de la clase que hace la conexion
     */
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
    
    /**
     * Getter de la conexion
     * @return Retorna la conexion 
     */
    public Connection getConn(){
        return conn;
    }
    
    /**
     * Metodo para cerrar la conexion con la base de datos
     * @return Retorna la conexion en la que fue cerrada la base de datos (osea NULL)
     */
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
    
    /**
     * Metodo para insertar en la base de datos
     * @param table Parametro del nombre de la tabla
     * @param values Parametros de los valores a insertar separados por coma y con respectivos ''
     */
    public void Insert(String table, String values){
        try{
            PreparedStatement ps = conn.prepareStatement("INSERT INTO "+table+" VALUES ("+values+")");
            ps.execute();
            
        }catch(SQLException ex){
            System.out.println(ex);
            System.out.println("INSERT INTO "+table+" VALUES ("+values+")");
        }
    
    }
    
    /**
     * Metodo para insertar especificamente a unas columnas de una tabla
     * @param table Parametro del nombre de la tabla
     * @param columns Parametro del nombre de las columnas separadas por coma
     * @param values Parametro de los valores separados por coma y ''
     */
    public void Insert(String table, String columns, String values){
        try{
            PreparedStatement ps = conn.prepareStatement("INSERT INTO "+table+" ("+columns+") VALUES ("+values+")");
            ps.execute();
            
        }catch(SQLException ex){
            System.out.println(ex);
            System.out.println("INSERT INTO "+table+" ("+columns+") VALUES ("+values+")");
        }
    
    }
    
    /**
     * Metodo para llenar combobox
     * @deprecated
     * @param query Parametro de lo que se busca ejecutar en la base de datos
     * @return Retorna un resultSet de la base de datos con los datos obtenidos
     */
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
    
        /**
         * Metodo para borrar de una base de datos
         * @param table Parametro del nombre de la tabla
         * @param where_nombre Parametro del nombre del where
         * @param where_value Parametro del valor del where a borrar
         * @return Retorna el resultset con los datos
         * 
         * 
         * where_nombre = "id";
         * where_value = "8";
         * 
         * detele from table example where id = '8';
         * 
         */
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
        
        /**
         * Metodo para actualizar en la base de datos
         * @param table Nombre de la tabla
         * @param set_values Valores a actualizar
         * @param where_type Especificador de columna
         * @param where_value Valor de columna especifica que eligira la dupla correcta
         * @return 
         */
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
        
        /**
         * Metodo para actualizar base de dato
         * @param table Nombre de la tabla
         * @param set_values Valores a actualizar
         * @param where_type Especificacion de columna de dupla 
         * @param where_value Valor especifico
         * @return Retorna los datos en ResultSet
         */
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
        
        /**
         * Metodo para realizar un Select desde la base de datos
         * @param column_from_tables Parametro con el nombre de la columnas
         * @param table Nombre de la tabla
         * @return Retorna un resultset con los datos
         */
        
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
    
        /**
         * Metodo para hacer un Select desde la base de datos
         * @param column_from_tables Nombre de columnas
         * @param table Nombre de tabla
         * @param where_type Nombre del columna especifica
         * @param where_value Valor especifico
         * @return Retorna los datos en un resultset
         */
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
        
        /**
         * Metodo para hacer select desde base de datos
         * @param column_from_tables Nombre columnas de la tabla
         * @param table Nombre de la tabla
         * @param where Conjunto de where con sus respectivos valores cuando se necesita usar logica
         * @return Retorna los datos en un resultset
         */
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
        
        /**
         * Metodo para llamar un storeprocedure
         * @param procedure Nombre del storeprocedure
         */
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
