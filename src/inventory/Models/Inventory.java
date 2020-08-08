package inventory.Models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.dbConnection;

public class Inventory {

    private Connection connection;

    public Inventory() {
        try {
            this.connection = dbConnection.getConnection();
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        if (!isDBConnected()){
            System.exit(1);
        }

    }

    public boolean isDBConnected(){
        System.out.println("DB Connected");
        return this.connection != null;
    }

    public boolean isAuthenticated(String uname, String pwd, String mode) throws Exception{
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM user WHERE username = ? and userpass = ? and mode = ?";

        try {
            ps = this.connection.prepareStatement(query);
            ps.setString(1,uname);
            ps.setString(2,pwd);
            ps.setString(3, mode);

            rs = ps.executeQuery();

            if (rs.next()){
                return true;
            }

            return false;

        }
        catch (SQLException e){
            return false;
        }

        finally {
            ps.close();
            rs.close();
        }

    }
}

