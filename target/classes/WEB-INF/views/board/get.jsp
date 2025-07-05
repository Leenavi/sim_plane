<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ include file="../includes/header.jsp" %>
<link rel="stylesheet" href="<c:url value='/resources/dist/css/register.css' />" />

<div class="container">
  <h2>게시글 상세보기</h2>

  <table class="table table-bordered">
    <tr>
      <th>번호</th>
      <td><c:out value="${board.boardid}" /></td>
    </tr>
    <tr>
      <th>제목</th>
      <td colspan="3"><c:out value="${board.title}" /></td>
    </tr>
    <tr>
      <th>작성자</th>
      <td><c:out value="${board.writer}" /></td>
      <th>작성일</th>
      <td><fmt:formatDate value="${board.regDate}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
    </tr>
    <tr>
      <th>내용</th>
      <td colspan="3">
        <pre style="white-space: pre-wrap; margin: 0; border: none;"><c:out value="${board.content}" /></pre>
      </td>
    </tr>

  </table>

  <c:if test="${not empty images}">
    <h4>첨부 이미지</h4>
    <div class="row mb-3">
      <c:forEach var="img" items="${images}">
        <div class="col-md-3 mb-3">
          <img src="<c:url value='/display?fileName=${img.imagePath}' />"
               alt="첨부 이미지" class="img-fluid img-thumbnail" />
        </div>
      </c:forEach>
    </div>
  </c:if>


  <sec:authentication property="principal" var="pinfo" />

  <sec:authorize access="isAuthenticated()">
    <c:if test="${pinfo.username eq board.writer}">
      <button data-oper='modify' class="btn btn-info">수정</button>
    </c:if>
  </sec:authorize>

  <button data-oper='list' class="btn btn-default">목록</button>


  <form id="operForm" action="/board/modify" method="get">
    <input type="hidden" id="boardid" name="boardid" value='<c:out value="${board.boardid}" />'>
    <input type="hidden" name="pageNum" value= '<c:out value="${cri.pageNum}"/>'>
    <input type="hidden" name="amount" value='<c:out value="${cri.amount}"/>'>
    <input type="hidden" name="keyword" value= <c:out value="${cri.keyword}" />>
    <input type="hidden" name="type" value="${cri.type}">
  </form>
</div>
</div>

</script>

<script type="text/javascript">
  $(document).ready(function(){

    let operForm = $("#operForm");

    $("button[data-oper='modify']").on("click", function(e){
      operForm.attr("action", "/board/modify").submit();
    });

    $("button[data-oper='list']").on("click", function(e){
      operForm.find("#boardid").remove();
      operForm.attr("action", "/board/list").submit();
    });
  });
</script>

<%@ include file="../includes/footer.jsp" %>