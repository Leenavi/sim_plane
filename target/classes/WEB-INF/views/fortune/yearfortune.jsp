<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../includes/header.jsp" %>
<link rel="stylesheet" href="<c:url value='/resources/dist/css/main.css' />" />


<h2>띠별 운세 보기 (2025)</h2>

<form id="fortuneForm">
    출생 연도 입력:
    <input type="number" id="birthYear" name="birthYear"/>
    <button type="submit">운세 확인</button>
</form>

<hr>

<div id="result" style="display:none;">
    <h3>운세 결과</h3>
    <p><strong>요약:</strong><span id="summary"></span></p>
    <ul>
        <li>❤️ 사랑운: <span id="love"></span></li>
        <li>💰 금전운: <span id="money"></span></li>
        <li>💼 커리어: <span id="career"></span></li>
        <li>💪 건강: <span id="health"></span></li>
    </ul>
</div>



<script>
    document.addEventListener("DOMContentLoaded", function () {
        document.getElementById("fortuneForm").addEventListener("submit", function (e) {
            e.preventDefault();
            let year = document.getElementById("birthYear").value.trim();

            console.log(`/fortune/api/year?birthYear=${year}`);


            console.log("입력값:", year);  // ?는 null-safe
            console.log(typeof year)

            if (!year || isNaN(year)) {
                alert("출생 연도를 입력해주세요!");
                return;
            }
            // URL을 명시적으로 문자열 연결로 구성
            const apiUrl = "/fortune/api/year?birthYear=" + year;
            console.log("생성된 API URL:", apiUrl); // 이 로그를 통해 URL이 제대로 만들어졌는지 확인

            fetch(apiUrl)
                .then(response => {
                    if (!response.ok) throw new Error("API 호출 실패");
                    return response.json();
                })
                .then(data => {
                    document.getElementById("summary").innerText = data.summary;
                    document.getElementById("love").innerText = data.love;
                    document.getElementById("money").innerText = data.money;
                    document.getElementById("career").innerText = data.career;
                    document.getElementById("health").innerText = data.health;
                    document.getElementById("result").style.display = "block";

                    console.log(data); // 응답 데이터 구조 확인
                })
                .catch(error => {
                    console.error("운세 불러오기 실패:", error);
                    alert("운세를 불러오는 중 오류가 발생했습니다.");
                });
        });
    });

</script>



<%@ include file="../includes/footer.jsp" %>

