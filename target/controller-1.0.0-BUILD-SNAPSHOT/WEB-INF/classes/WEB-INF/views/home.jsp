<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ include file="./includes/header.jsp" %>
<link rel="stylesheet" href="<c:url value='/resources/dist/css/main.css' />" />

<!-- MAIN -->
<main>
	<div id="slider">
		<h1>금주의 추천 테스트</h1>
		<img src="/resources/images/animals.png" alt="슬라이드 이미지">
	</div>

	<h3>~BEST 굿즈를 한 번에 만나보세요~</h3>

	<section>
		<article>
			<h3>포춘쿠키</h3>
			<a href="#"><img src="/resources/images/animals.png"></a>
		</article>
		<article>
			<h3>당신의 이상적인 휴가 스타일은?</h3>
			<a href="#"><img src="/resources/images/animals.png"></a>
		</article>
		<article>
			<h3>올해의 운세</h3>
			<a href="#"><img src="/resources/images/animals.png"></a>
		</article>
		<article>
			<h3>당신은 어떤 동물 유형일까? 2탄</h3>
			<a href="#"><img src="/resources/images/animals.png"></a>
		</article>
	</section>
</main>


<%@ include file="./includes/footer.jsp" %>
