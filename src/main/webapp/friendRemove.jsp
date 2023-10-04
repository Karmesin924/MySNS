<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="dao.*" %>
<% 
	String uid = request.getParameter("id");
	String frid = request.getParameter("frid");
	out.print((new FriendDAO()).remove(uid, frid));
%>