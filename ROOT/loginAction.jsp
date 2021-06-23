<%@page import="java.io.PrintWriter" %>
<%@page import="user.UserDAO" %>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<% request.setCharacterEncoding("utf-8"); %>
<jsp:useBean id="user" class="user.User" scope="page" />
<jsp:setProperty name="user" property="userID" />
<jsp:setProperty name="user" property="userPassword" />

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>JSP 게시판 웹 사이트</title>
    </head>
    <body>
        <%
            PrintWriter script = response.getWriter();
            String userID = null;
            if(session.getAttribute("id") != null) {
                userID = (String)session.getAttribute("id");
            }

            if(userID != null) {
                script.println("<script>");
                script.println("alert('이미 로그인되어 있습니다.')");
                script.println("location.href='main.jsp'");
                script.println("</script>");            
            }

            UserDAO userDAO = new UserDAO();
            
            userID = user.getUserID();
            String userPW = user.getUserPassword();
            int result = userDAO.login(userID, userPW);
            
            if(result == 1) {
                session.setAttribute("id", user.getUserID());
                script.println("<script>");
                script.println("alert('로그인 성공')");
                session.setAttribute("id", userID);
                script.println("location.href='main.jsp'");
                script.println("</script>");
            } else if(result == 0){
			    script.println("<script>");
			    script.println("alert('비밀번호가 틀립니다')");
			    script.println("history.back()");
			    script.println("</script>");
		    }else if(result == -1){
			    script.println("<script>");
			    script.println("alert('존재하지 않는 아이디입니다')");
			    script.println("history.back()");
			    script.println("</script>");
		    }else if(result == -2){
		    	script.println("<script>");
		    	script.println("alert('데이터베이스 오류입니다')");
		    	script.println("history.back()");
		    	script.println("</script>");
		    }

        %>
    </body>
</html>
