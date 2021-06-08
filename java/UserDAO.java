package user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
                if(rs.getObject("userID") != null ? true : false) {
                    return 1;
                } else return 0;
            }
            return -1;
        } catch(Exception e) {
            e.printStackTrace();
        } return -2;
    }

    public int login_h(String userID, String userPassword) {
        String sql = "select userID, userPassword from user_info where userID=?";

        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userID);

            String hasedPW = getHashString(userPassword);
            rs = pstmt.executeQuery();
            if(rs.next()) {
                if(rs.getString("userPassword").equals(hasedPW)) return 1;
                else{

                }
                
            } 
            return -1;
        }
            catch(Exception e) {
                e.printStackTrace();
            }
            return -2;
        }

    public int join(User user) {
        String sql = "insert into user_info(userID, userPassword, userName, userGender, userEmail, userAddress) values(?,?,?,?,?,?)";

        try {
            String usrAddr = user.getUserPostcode() + ' ' + user.getUserAddress() + ' ' + user.getUserDetailAddress() + ' ' + user.getUserExtraAddress();
            String userPW = getHashString(user.getUserPassword());
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, user.getUserID());
            pstmt.setString(2, userPW);
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

    public String getHashString(String str) throws NoSuchAlgorithmException{
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(str.getBytes());
        StringBuilder builder = new StringBuilder();
        for(byte b:md.digest()) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }
}
