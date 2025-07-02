<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../includes/header.jsp" %>
<link rel="stylesheet" href="<c:url value='/resources/dist/css/main.css' />" />

<div class="container">
  <h2>게시글 수정</h2>

  <form role="form" action="/board/modify" method="post">

    <!-- 검색조건 유지용 히든 -->
    <input type="hidden" name="pageNum" value= '<c:out value="${cri.pageNum}"/>'>
    <input type="hidden" name="amount" value='<c:out value="${cri.amount}"/>'>
    <input type="hidden" name="keyword" value= <c:out value="${cri.keyword}" />>
    <input type="hidden" name="type" value="${cri.type}">

    <table class="table table-bordered">
      <tr>
        <th>번호</th>
        <td>
          <input type="text" name="boardid" class="form-control" value="${board.boardid}" readonly>
        </td>
      </tr>
      <tr>
        <th>제목</th>
        <td colspan="3">
          <input type="text" name="title" class="form-control" value="${board.title}">
        </td>
      </tr>
      <tr>
        <th>작성자</th>
        <td>
          <input type="text" name="writer" class="form-control" value="${board.writer}" readonly>
        </td>
        <th>작성일</th>
        <td>
          <fmt:formatDate value="${board.regDate}" pattern="yyyy-MM-dd HH:mm:ss" />
        </td>
      </tr>
      <tr>
        <th>내용</th>
        <td colspan="3">
          <textarea name="content" class="form-control" rows="10" style="white-space: pre-wrap; background-color: white;">${board.content}</textarea>
        </td>
      </tr>
    </table>



    <button type="submit" data-oper='modify' class="btn btn-default">수정 완료</button>
    <button type="submit" data-oper='remove' class="btn btn-danger">삭제</button>
    <button type="submit" data-oper='list' class="btn btn-info">목록</button>

  </form>
</div>

<script>
  $(document).ready(function(){
    let formObj = $("form");

    $("button").on("click", function(e){
      e.preventDefault(); //  이벤트의 기본 동작을 막는 역할

      let operation = $(this).data("oper");

      if(operation === 'remove'){
        formObj.attr("action", "/board/remove")
      }else if(operation === 'list'){
        formObj.attr("action", "/board/list").attr("method","get");

        let pageNumTag = $("input[name='pageNum']").clone();
        let amountTag = $("input[name='amount']").clone();
        let typeTag = $("input[name='type']").clone();
        let keywordTag = $("input[name='keyword']").clone();

        formObj.empty();  // input 태크 name속성값을 클리어.
        formObj.append(pageNumTag);
        formObj.append(amountTag);

        formObj.append(typeTag);
        formObj.append(keywordTag);
      }
      formObj.submit();
    });
  });
</script>


<%@ include file="../includes/footer.jsp" %>