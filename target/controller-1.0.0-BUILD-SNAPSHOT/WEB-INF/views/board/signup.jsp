<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%
    request.setAttribute("tab", "performance");
%>
<link rel="stylesheet" href="<c:url value='/resources/dist/css/main.css' />" />

<%@ include file="../includes/header.jsp" %>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

<!-- 회원가입 폼 -->
<div class="login-form-area" style="max-width: 500px; margin: 100px auto;">
    <div class="login-wrap">
        <h1 class="h-tit" style="text-align: center;">회원가입</h1>
        <form method="post" action="${pageContext.request.contextPath}/signup" onsubmit="return validateForm()">
            <div class="fieldset">

                <!-- 아이디 -->
                <div class="form-group">
                    <label for="userid">아이디</label>
                    <div style="display: flex; gap: 10px;">
                        <input type="text" id="userid" name="userid"
                               class="krds-input" placeholder="아이디를 입력하세요"
                               required style="flex: 1;" oninput="resetIdChecked()" />

                        <button type="button" class="krds-btn secondary"
                                onclick="checkUserid()">
                            중복확인
                        </button>
                    </div>
                </div>

                <!-- 비밀번호 -->
                <div class="form-group">
                    <label for="password">비밀번호</label>
                    <input type="password" id="password" name="password" class="krds-input"
                           placeholder="비밀번호를 입력하세요" required minlength="8">
                </div>

                <!-- 이름 -->
                <div class="form-group">
                    <label for="name">이름</label>
                    <input type="text" id="name" name="name" class="krds-input"
                           placeholder="이름을 입력하세요" required>
                </div>

                <!-- 생년월일 -->
                <div class="form-group">
                    <label for="birthdate">생년월일</label>
                    <input type="date" id="birthdate" name="birthdate" class="krds-input"
                           placeholder="YYYY-MM-DD" required>
                </div>

                <!-- 성별 -->
                <div class="form-group">
                    <label>성별</label>
                    <div>
                        <label><input type="radio" name="sex" value="0" checked> 남성</label>
                        <label><input type="radio" name="sex" value="1"> 여성</label>
                    </div>
                </div>

                <!-- hidden 필드 -->
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                <input type="hidden" id="idChecked" value="false">

                <!-- 가입 버튼 -->
                <div class="form-group" style="margin-top: 20px;">
                    <button type="submit" class="krds-btn large primary" style="width: 100%;">가입하기</button>
                </div>
            </div>
        </form>
    </div>
</div>

<!-- 아이디 중복 체크 & 폼 검증 -->
<script>
    function checkUserid() {
        const userid = document.getElementById("userid").value;
        if (!userid) {
            alert("아이디를 입력하세요.");
            return;
        }

        fetch("${pageContext.request.contextPath}/checkUsername?userid=" + encodeURIComponent(userid))
            .then(response => response.text())
            .then(result => {
                if (result === "available") {
                    alert("사용 가능한 아이디입니다.");
                    document.getElementById("idChecked").value = "true";
                } else {
                    alert("이미 사용 중인 아이디입니다.");
                    document.getElementById("idChecked").value = "false";
                }
            });
    }

    function validateForm() {
        const idChecked = document.getElementById("idChecked").value;
        if (idChecked !== "true") {
            alert("아이디 중복 확인을 해주세요.");
            return false;
        }
        return true;
    }

    function resetIdChecked() {
        document.getElementById("idChecked").value = "false";
    }

    // 오늘 날짜를 yyyy-mm-dd 형식으로 구함
    const today = new Date().toISOString().split('T')[0];
    document.getElementById("birthdate").setAttribute("max", today);
</script>

<%@ include file="../includes/footer.jsp" %>