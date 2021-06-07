package user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class UserDAO {
    private Connection conn;
    private Statement stmt;
    private ResultSet rs;
    private PreparedStatement pstmt;

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
        String sql = "select userID from user_info where userID='"+userID+"' and userPassword='"+userPassword+"'";
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            if(rs.next()) {
                if(rs.getString("userID").equals(userID)) {
                    return 1;
                }
                else {
                    return 0;
                }
                
            }return -1;
        } catch(Exception e) {
            e.printStackTrace();
        } return -2;
    }

    public int join(User user) {
        String sql = "insert into user_info(userID, userPassword, userName, userGender, userEmail, user) values(?,?,?,?,?)";

        try {
            String usrAddr = user.getUserPostcode() + ' ' + user.getUserAddress() + ' ' + user.getUserDetailAddress() + ' ' + user.getUserExtraAddress();

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, user.getUserID());
            pstmt.setString(2, user.getUserPassword());
            pstmt.setString(3, user.getUserName());
            pstmt.setString(4, user.getUserGender());
            pstmt.setString(5, user.getUserEmail());
            pstmt.setString(6, usrAddr);

            return pstmt.executeUpdate();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
}