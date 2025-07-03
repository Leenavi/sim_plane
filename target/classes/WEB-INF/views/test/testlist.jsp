<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="../includes/header.jsp" %>

<style>
    .test-grid {
        display: flex;
        grid-template-columns: repeat(auto-fit, minmax(500px, 1fr));
        flex-wrap: wrap;
        justify-content: center;  /* 가운데 정렬 */
        gap: 50px;
    }

    .test-card {
        width: 350px;
        height: 300px;
        border: 1px solid #ddd;
        background-color: white;  /* 카드 배경 흰색 */
        border-radius: 10px;
        overflow: hidden;
        text-align: center;
        box-shadow: 1px 1px 10px rgba(0,0,0,0.05);
        transition: transform 0.2s ease;
        background-image: url("/resources/images/testcard.png");
        background-size: contain;
    }

    .test-card:hover {
        transform: scale(1.03);
    }

    .test-card img {
        width: 100%;
        height: 160px;
        object-fit: contain;
    }

    .test-card h4 {
        padding: 10px;
        margin: 0;
    }

    .test-card-link {
        text-decoration: none;
        color: inherit;
    }

    .container {
        background-repeat: no-repeat;
        background-size: 110% 700px;
        background-position: center center;
        padding: 40px;
    }

    .blank {
        flex-grow: 1; /* 남은 공간 차지해서 아래 요소를 밀어줌 */
        height: 200px;
    }
</style>

<div class="container">
    <h2 style="text-align:center;">심리테스트 목록</h2>
    <div class="test-grid">
        <c:forEach var="test" items="${testList}">
            <a href="/test/start?testid=${test.testid}" class="test-card-link">
            <div class="test-card">
                <div class="blank"></div> <!-- 상단 빈 공간 -->
                <h3>${test.testName}</h3>
                <button>테스트 시작</button>
            </div>
            </a>
        </c:forEach>
    </div>
</div>

<%@ include file="../includes/footer.jsp" %>
