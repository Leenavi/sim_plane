<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<link href="https://fonts.googleapis.com/css2?family=Gowun+Dodum&display=swap" rel="stylesheet">

<style>
    /* 팝업창의 흰색 박스 (실제 내용물이 들어가는 부분) 스타일이에요. */
    .popup-content {
        display: flex;
        flex-direction: column; /* 아이템들을 세로로 정렬 */
        align-items: center; /* 가로 방향으로 가운데 정렬 */
        background-color: white; /* 배경은 하얀색 */
        padding: 20px; /* 안쪽 여백 */
        border-radius: 10px; /* 모서리를 둥글게 만들어요. */
        text-align: center; /* 글자나 그림을 가운데 정렬 */
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2); /* 그림자 효과를 줘서 입체감을 높여요. */
        max-width: 650px;
        width: 80%;
        box-sizing: border-box;
    }

    /* 포춘쿠키 그림의 스타일이에요. */
    .fortune-cookie-image {
        width: 500px;
        height: auto;
        cursor: pointer;
        margin-bottom: 20px;
        user-select: none;
        -webkit-user-drag: none;
    }

    /* 명언이 표시될 부분의 스타일이에요. */
    .fortune-message {
        font-size: 1.4em;
        color: #333;
        min-height: 50px;
        display: flex;
        align-items: center;
        font-family: 'Gowun Dodum', sans-serif;
        justify-content: center;
        line-height: 1.4;
    }

    /* 닫기 버튼 스타일 */
    .close-button {
        background-color: #f44336;
        color: white;
        border: none;
        padding: 8px 15px;
        border-radius: 5px;
        cursor: pointer;
        font-size: 1em;
        margin-top: 20px;
        transition: background-color 0.3s ease;
    }

    .close-button:hover {
        background-color: #d32f2f;
    }
</style>

<div class="popup-content" id="fortuneCookieContent">
    <img src="/resources/images/fortune_cookie1.png" alt="포춘쿠키" class="fortune-cookie-image" id="cookieImageInModal">

    <p id="fortuneMessageInModal" class="fortune-message"></p>

    <button class="close-button" id="closeButtonInModal">닫기</button>
</div>