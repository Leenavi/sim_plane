  <%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
  <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
  <%@ include file="../includes/header.jsp" %>

  <h2>🧠 심리테스트 생성</h2>

  <form method="post" action="/test/createTest" id="testForm">
    <label>테스트 이름:</label>
    <input type="text" name="testname" required><br><br>

    <div id="question-section">
      <!-- 질문이 동적으로 추가될 영역 -->
    </div>
    <button type="button" id="add-question-btn">질문 추가</button>

    <h3>🔎 결과 입력</h3>
    <div id="result-section">
      <!-- 결과가 추가될 영역 -->
    </div>
    <button type="button" onclick="addResult()">결과 추가</button>

    <br><br>
    <button type="submit">✅ 테스트 저장</button>
  </form>

  <script src="${pageContext.request.contextPath}/resources/js/createTest.js"></script>


  <%@ include file="../includes/footer.jsp" %>
