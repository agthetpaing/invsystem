package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class dbConnection {

    private static final String USERNAME = "dbuser";
    private static final String PASSWORD = "dbpassword";
    //private static final String CONN  = "jdbc:mysql://localhost/login";
    private static final String CONN = "jdbc:sqlite:inventory.sqlite";

    public static Connection getConnection() throws SQLException
    {

        try{
            Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection(CONN);
        }
        catch (ClassNotFoundException e){
            e.printStackTrace();

        }
        return null;
    }

}
