package user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class UserDAO {
    private Connection conn;
    private PreparedStatement pstmt;
    private ResultSet rs;

    public UserDAO() {
        try {
            String oradriver = "oracle.jdbc.driver.OracleDriver";
            String dbURL = "jdbc:oracle:thin:@localhost:1521:xe";
            Class.forName(oradriver);

            String dbID = "hhhoh";
            String dbPassword = "oracle";

            conn = DriverManager.getConnection(dbURL, dbID, dbPassword);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public int login(String userID, String userPassword) {
        String sql = "select * from user_info " + "where userID='"+userID+"' and userPassword='"+userPassword+"'";
        try {
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            if(rs.next()) {
                return 1;
            } else {
                return 0;
            }
        } catch(Exception e) {
            e.printStackTrace();
        } return -2;
    }
}