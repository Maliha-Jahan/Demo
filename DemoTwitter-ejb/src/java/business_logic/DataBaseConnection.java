/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business_logic;
import java.sql.*;
import javax.swing.JOptionPane;
/**
 *
 * @author ASUS
 */
public class DataBaseConnection {

    public Connection con;
    public Statement stmt,stmt2;
    public PreparedStatement pstmt;
    public ResultSet rs,rs2;
    
    public DataBaseConnection() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");

            con = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:orcl", "DemoTwitter", "DemoTwitter"); //change "oracle" to "orcl" depening on sid

            stmt = con.createStatement();
            stmt2 = con.createStatement();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
}
