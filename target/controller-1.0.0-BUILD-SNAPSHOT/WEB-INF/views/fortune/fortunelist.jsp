<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="../includes/header.jsp" %>
<link rel="stylesheet" href="<c:url value='/resources/dist/css/fortune.css' />" />

<div class="fortune-container">
    <div class="fortune-card">
        <h2>운세 카테고리</h2>
        <ul class="fortune-list">
            <li><a href="/fortune/year">🐭 띠별 운세</a></li>
            <li><a href="/fortune/zodiac">🌟 별자리 운세</a></li>
            <li><a href="/fortune/saju">📜 사주 운세</a></li>
        </ul>
    </div>
</div>



<%@ include file="../includes/footer.jsp" %>
