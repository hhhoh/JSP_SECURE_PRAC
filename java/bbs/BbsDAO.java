package bbs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class BbsDAO {
    private Connection conn;
    private ResultSet rs;

    public BbsDAO() {
        try {
            String oradriver = "oracle.jdbc.driver.OracleDriver";
            String dbURL = "jdbc:oracle:thin:@localhost:1521:xe";
            String dbID = "hhhoh";
            String dbPassword = "oracle";

            Class.forName(oradriver);

            conn = DriverManager.getConnection(dbURL, dbID, dbPassword);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public Date getDate() {
        String sql = "select sysdate from dual";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            if(rs.next()) {
                return rs.getDate("sysdate");
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        long millis=System.currentTimeMillis();  
        java.sql.Date date=new java.sql.Date(millis);  
        return date;
    }

    public int getNext() {
        String sql = "select bbsID from bbs order by bbsID desc";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            if(rs.next()) {
                return rs.getInt(1) + 1;
            }
            return 1;
        } catch(Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int write(String title, String userID, String bbsContent) {
        String sql = "insert into bbs(bbsID, bbsTitle, userID, bbsDate, BbsContent, bbsAvailable) values(?,?,?,?,?,?)";

        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, getNext());
            pstmt.setString(2, title);
            pstmt.setString(3, userID);
            pstmt.setDate(4, getDate());
            pstmt.setString(5, bbsContent);
            pstmt.setInt(6, 1);

            return pstmt.executeUpdate();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public ArrayList<Bbs> getList(int pageNumber) {
        String sql = "select * from bbs where bbsID < ? and bbsAvailable=1 and rownum <= 10 order by bbsID desc";
        ArrayList<Bbs> list = new ArrayList<Bbs>();
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, getNext() - (pageNumber - 1) * 10);
            rs = pstmt.executeQuery();
            while(rs.next()) {
                Bbs bbs = new Bbs();
                bbs.setBbsID(rs.getInt("bbsID"));
                bbs.setBbsTitle(rs.getString("bbsTitle"));
                bbs.setUserID(rs.getString("userID"));
                bbs.setBbsDate(rs.getDate("bbsDate"));
                bbs.setBbsContent(rs.getString("bbsContent"));
                bbs.setBbsAvailable(rs.getInt("bbsAvailable"));
                list.add(bbs);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean nextPage(int pageNumber) {
        String sql = "select * from bbs where bbsID < ? and bbsAvailable = 1";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, getNext() - (pageNumber - 1) * 10);
            rs = pstmt.executeQuery();
            if(rs.next()) {
                return true;
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public Bbs getBbs(int bbsID) {
        String sql = "select * from bbs where bbsID = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, bbsID);
            rs = pstmt.executeQuery();
            if(rs.next()) {
                Bbs bbs = new Bbs();
                bbs.setBbsID(rs.getInt("bbsID"));
                bbs.setBbsTitle(rs.getString("bbsTitle"));
                bbs.setUserID(rs.getString("userID"));
                bbs.setBbsDate(rs.getDate("bbsDate"));
                bbs.setBbsContent(rs.getString("bbsContent"));
                bbs.setBbsAvailable(rs.getInt("bbsAvailable"));
                return bbs;
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}