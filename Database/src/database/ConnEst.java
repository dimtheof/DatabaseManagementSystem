/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.DriverManager;
import java.sql.*;
import com.microsoft.sqlserver.jdbc.SQLServerDriver;
/**
 *
 * @author dinos
 */
public class ConnEst {
    
    public  Connection estcon(){
    try{
    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
    Connection conn=DriverManager.getConnection("jdbc:sqlserver://localhost:53206;databaseName=Microloans","kanell","deminio1967");
    return conn;
    }
    catch(Exception sqle)
    {
        System.out.println(""+sqle);
        return null;
    }
    
 }
}
