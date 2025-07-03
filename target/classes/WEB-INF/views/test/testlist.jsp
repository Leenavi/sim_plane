<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="../includes/header.jsp" %>

<style>
    .test-grid {
        display: flex;
        flex-wrap: wrap;
        justify-content: center;  /* 가운데 정렬 */
        gap: 20px;
    }

    .test-card {
        width: 250px;
        border: 1px solid #ddd;
        background-color: white;  /* 카드 배경 흰색 */
        border-radius: 10px;
        overflow: hidden;
        text-align: center;
        box-shadow: 1px 1px 10px rgba(0,0,0,0.05);
        transition: transform 0.2s ease;
    }

    .test-card:hover {
        transform: scale(1.03);
    }

    .test-card img {
        width: 100%;
        height: 160px;
        object-fit: cover;
    }

    .test-card h4 {
        padding: 10px;
        margin: 0;
    }

    .test-card a {
        text-decoration: none;
        color: inherit;
    }

    .container {
        background-image: url("/resources/images/plane.png");
        background-repeat: no-repeat;
        background-size: 110% 700px;
        background-position: center center;
        padding: 40px;
        height: 700px;
    }
</style>

<div class="container">
    <h2 style="text-align:center;">심리테스트 목록</h2>
    <div class="test-grid">
        <c:forEach var="test" items="${testList}">
            <div class="test-card">
                <h3>${test.testName}</h3>
                <a href="/test/start?testid=${test.testid}">
                    <button>테스트 시작</button>
                </a>
            </div>
        </c:forEach>
    </div>
</div>

<%@ include file="../includes/footer.jsp" %>
