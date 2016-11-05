/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conf;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author dana
 */
public class Conexion {
    public Connection getConexion(){
        Connection conexion = null;
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://johnfedb.cetojpwybnz3.us-west-2.rds.amazonaws.com:3306/crud";
        String user = "johnfeuser";
        String password = "j0hnf3db";
        
        try {
            Class.forName(driver);
            conexion = DriverManager.getConnection(url, user, password);
            System.out.println("Conexion correcta");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error: " + e.toString());
        }
        
        return conexion;
    }
}
