<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%
  request.setAttribute("tab", "performance");
%>
<%@ include file="../includes/header.jsp" %>

<link rel="stylesheet" href="<c:url value='/resources/dist/css/customlogin.css' />" />

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<title>로그인</title>


<div class="content-wrapper">
  <div class="login-form-container">
    <h2>로그인</h2>
    <form action="<c:url value='/login'/>" method="post">
      <label for="username">아이디</label>
      <input type="text" name="username" id="username" required>

      <label for="password">비밀번호</label>
      <input type="password" name="password" id="password" required>

      <button type="submit">로그인</button>
    </form>
  </div>
</div>

<%@ include file="../includes/footer.jsp" %>
