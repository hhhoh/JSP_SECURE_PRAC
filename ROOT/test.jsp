<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Test Connection</title>
    </head>
    <body>
        <%@ page import="java.sql.*" %>
        <h3> tomcat + oracle connect</h3>
        <%
        try {
            String oradriver = "oracle.jdbc.driver.OracleDriver";
            String dbURL = "jdbc:oracle:thin:@localhost:1521:XE";
            Class.forName(oradriver);
            Connection conn = DriverManager.getConnection(dbURL, "hhhoh", "oracle");
            out.println("OracleDB Connect Success");
            out.println("성공!");
            conn.close();
        } catch(Exception e) {
            out.println("Oracle DB 연동 실패");
            out.println(e.getMessage());
            e.printStackTrace();
        }
        %>
    </body>
</html>