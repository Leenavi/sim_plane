<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!--프레임워크 태그라이브러리 선언 "sec"-->
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<link rel="stylesheet" href="<c:url value='/resources/dist/css/header.css' />" />
<!DOCTYPE html>
<html lang="ko">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">



    <title>simplane</title>

</head>
<body>
<div class="wrapper">
    <!-- HEADER -->
    <header>
        <div class="header-flex">
            <!-- 왼쪽: 로고 -->
            <div id="logo">
                <a href="/"><img src="/resources/images/logo.png" alt="로고 이미지"></a>
            </div>

            <!-- 오른쪽: 로그인 + 메뉴 묶음 -->
            <div class="right-block">
                <div id="login">
                    <ul>
                        <!--로그인 하면 로그인, 회원가입 버튼 사라지고 로그아웃 버튼이 나오게-->
                        <sec:authorize access="!isAuthenticated()">
                            <li><a href="<c:url value='/login' />">로그인</a></li>
                            <li>|</li>
                            <li><a href="<c:url value='/signup' />">회원가입</a></li>
                        </sec:authorize>

                        <sec:authorize access="isAuthenticated()">
                            <li>
                                <a href="#" onclick="document.getElementById('logoutForm').submit(); return false;">로그아웃</a>

                                <form id="logoutForm" action="<c:url value='/logout' />" method="post" style="display:none;">
                                </form>
                            </li>
                        </sec:authorize>
                    </ul>
                </div>
                <nav>
                    <ul>
                        <li><a href="/test/list">심리테스트</a></li>
                        <li><a href="/fortune/list">운세</a></li>
                        <li><a href="#">궁합</a></li>
                        <li><a href="#">포춘쿠키</a></li>
                        <li><a href="/board/list">문의게시판</a></li>
                    </ul>
                </nav>
            </div>
        </div>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

</header>