<%@page import="java.io.PrintWriter"%>
<%@page import="user.UserDAO"%>
<%@page import="bbs.BbsDAO"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<% request.setCharacterEncoding("utf-8"); %>
<jsp:useBean id="bbs" class="bbs.Bbs" scope="page"/>
<jsp:setProperty name="bbs" property="bbsTitle"/>
<jsp:setProperty name="bbs" property="bbsContent"/>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>JSP 웹 사이트</title>
</head>
<body>

    <% 
        PrintWriter script = response.getWriter();
        String userID = null;
        if(session.getAttribute("id") != null) {
            userID = (String)session.getAttribute("id");
        }

        if(userID == null) {
            script.println("<script>");
            script.println("alert('로그인을 하세요.')");
            script.println("location.href='login.jsp'");
            script.println("</script>");
        } 
        else{

            if(bbs.getBbsTitle() == null || bbs.getBbsContent() == null) {
                script.println("<script>");
                script.println("alert('임력이 안 된 사항이 있습니다.')");
                script.println("history.back()");
                script.println("</script>");
            } else {

                BbsDAO bbsDAO = new BbsDAO();
                int result = bbsDAO.write(bbs.getBbsTitle(), userID, bbs.getBbsContent());

                if(result == -1) {
                    script.println("<script>");
                    script.println("alert('글쓰기에 실패했습니다.')");
                    script.println("history.back()");
                    script.println("</script>");
                } else {
                    script.println("<script>");
                    script.println("alert('글쓰기 성공')");
                    script.println("location.href = 'bbs.jsp'");
                    script.println("</script>");
                }
            }
        }
        
        %>
</body>
</html>
