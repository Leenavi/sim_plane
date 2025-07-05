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
			<h3>나는 어떤 영화 캐릭터?</h3>
			<a href="http://localhost:8080/test/start?testid=3"><img src="/resources/images/animals.png"></a>
		</article>
		<article>
			<h3>당신의 이상적인 휴가 스타일은?</h3>
			<a href="http://localhost:8080/test/start?testid=4"><img src="/resources/images/animals.png"></a>
		</article>
		<article>
			<h3>당신의 감정 표현 스타일은?</h3>
			<a href="http://localhost:8080/test/start?testid=6"><img src="/resources/images/animals.png"></a>
		</article>
		<article>
			<h3>나는 전생에 어떤 동물이였을까?</h3>
			<a href="http://localhost:8080/test/start?testid=1"><img src="/resources/images/animals.png"></a>
		</article>
	</section>
</main>


<%@ include file="./includes/footer.jsp" %>