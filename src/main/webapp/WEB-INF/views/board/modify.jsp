<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="../includes/header.jsp" %>
<link rel="stylesheet" href="<c:url value='/resources/dist/css/main.css' />" />
<link rel="stylesheet" href="<c:url value='/resources/dist/css/register.css' />" />


<div class="container">
  <h2>게시글 수정</h2>

  <form role="form" action="/board/modify" method="post" id="modifyForm">

    <input type="hidden" name="pageNum" value='<c:out value="${cri.pageNum}"/>'>
    <input type="hidden" name="amount" value='<c:out value="${cri.amount}"/>'>
    <input type="hidden" name="keyword" value='<c:out value="${cri.keyword}" />'>
    <input type="hidden" name="type" value="${cri.type}">
    <input type="hidden" name="boardid" value="${board.boardid}"> <%-- 이 boardid는 hidden으로 유지 --%>

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
      <tr>
        <th>기존 이미지</th>
        <td colspan="3">
          <div class="uploadResult existing-images">
            <ul>
              <c:forEach var="img" items="${images}">
                <li data-file="${img.imagePath}" data-type="image">
                  <img src="/display?fileName=${fn:replace(img.imagePath, '/s_', '/')}" width="100" />
                  <span>${fn:substringAfter(img.imagePath, '_')}</span>
                  <button type="button" class="btn btn-sm btn-danger btn-remove-existing-image">삭제</button>
                    <%-- !!! 중요: name을 'existingImagePaths'에서 'imagePaths'로 변경 !!! --%>
                  <input type="hidden" name="imagePaths" value="${img.imagePath}" class="existing-image-path-input" />
                </li>
              </c:forEach>
            </ul>
          </div>
        </td>
      </tr>
      <tr>
        <th>새로운 이미지 추가</th>
        <td colspan="3">
          <div class="uploadDiv">
            <input type="file" name="uploadFile" multiple>
          </div>
          <div class="uploadResult new-images">
            <ul>
              <%-- 새로 업로드된 이미지가 여기에 추가됩니다. (hidden input의 name도 'imagePaths'로 추가될 것임) --%>
            </ul>
          </div>
        </td>
      </tr>
    </table>

    <button type="button" data-oper='modify' class="btn btn-default">수정 완료</button>
    <button type="button" data-oper='remove' class="btn btn-danger">삭제</button>
    <button type="button" data-oper='list' class="btn btn-info">목록</button>

  </form>
</div>

<script>
  $(document).ready(function() {
    const formObj = $("#modifyForm"); // 폼의 ID를 사용합니다.

    // 1. 새로운 이미지 업로드 (AJAX)
    $("input[name='uploadFile']").on("change", function () {
      const formData = new FormData();
      const files = this.files;
      if (files.length === 0) return;

      for (let i = 0; i < files.length; i++) {
        formData.append("uploadFile", files[i]);
      }

      $.ajax({
        url: "/uploadAjaxAction",
        processData: false,
        contentType: false,
        data: formData,
        type: "POST",
        dataType: "json",
        success: function (result) {
          showUploadedImages(result);
          $("input[name='uploadFile']").val(""); // 파일 선택 초기화
        },
        error: function(xhr, status, error) {
          console.error("파일 업로드 실패:", status, error);
          alert("파일 업로드에 실패했습니다.");
        }
      });
    });

    // 2. 새로 업로드된 이미지 미리보기와 hidden input 생성
    function showUploadedImages(uploadResultArr) {
      if (!uploadResultArr || uploadResultArr.length === 0) return;
      let str = "";
      uploadResultArr.forEach(function (obj) {
        const thumbnailFileCallPath = encodeURIComponent(obj.uploadPath + "/s_" + obj.uuid + "_" + obj.fileName);
        const originalFilePath = obj.uploadPath + "/" + obj.uuid + "_" + obj.fileName;

        str +=
          '<li data-path="' + obj.uploadPath + '" data-uuid="' + obj.uuid + '" data-filename="' + obj.fileName + '" data-type="' + (obj.image ? 'image' : 'file') + '">' +
          '<img src="/display?fileName=' + thumbnailFileCallPath + '" width="100"><br>' +
          '<span>' + obj.fileName + '</span>' +
          '<button type="button" class="btn btn-sm btn-danger btn-remove-new-image">삭제</button>' +
          '<input type="hidden" name="imagePaths" value="' + originalFilePath + '">' +
          '</li>';
      });
      $(".uploadResult.new-images ul").append(str); // 새로운 이미지 섹션에 추가
    }

    // 3. 새로 업로드된 이미지 삭제 (미리보기에서만 삭제)
    $(".uploadResult.new-images").on("click", ".btn-remove-new-image", function () {
      $(this).closest("li").remove();
      // 서버에서 파일 삭제가 필요하다면 여기에 AJAX 요청 추가
    });

    // 4. 기존 이미지 삭제 (미리보기에서만 삭제)
    $(".uploadResult.existing-images").on("click", ".btn-remove-existing-image", function () {
      // 기존 이미지를 제거할 때 해당 hidden input도 함께 제거하여 서버로 전송되지 않게 합니다.
      $(this).closest("li").remove();
    });

    // 5. 수정 버튼 클릭 처리
    $("button[data-oper='modify']").on("click", function(e) {
      e.preventDefault();

      // 폼 데이터를 수동으로 FormData 객체에 추가
      const dataToSend = new FormData();

      // 일반 폼 필드 추가
      dataToSend.append("pageNum", formObj.find("input[name='pageNum']").val());
      dataToSend.append("amount", formObj.find("input[name='amount']").val());
      dataToSend.append("keyword", formObj.find("input[name='keyword']").val() || "");
      dataToSend.append("type", formObj.find("input[name='type']").val());
      dataToSend.append("boardid", formObj.find("input[name='boardid']").first().val()); // hidden boardid 사용
      dataToSend.append("title", formObj.find("input[name='title']").val());
      dataToSend.append("writer", formObj.find("input[name='writer']").val());
      dataToSend.append("content", formObj.find("textarea[name='content']").val());

      // 모든 이미지 경로를 'imagePaths' 이름으로 추가
      // 기존 이미지 (남아있는 것들)
      $(".uploadResult.existing-images ul li input[name='imagePaths']").each(function () {
        dataToSend.append("imagePaths", $(this).val());
      });

      // 새로 업로드된 이미지
      $(".uploadResult.new-images ul li input[name='imagePaths']").each(function () {
        dataToSend.append("imagePaths", $(this).val());
      });

      // 디버깅을 위해 FormData 내용 확인
      for (let pair of dataToSend.entries()) {
        console.log(pair[0] + ': ' + pair[1]);
      }

      $.ajax({
        url: "/board/modify",
        type: "POST",
        data: dataToSend,
        processData: false, // FormData 사용 시 필수
        contentType: false, // FormData 사용 시 필수
        success: function () {
          alert("수정 완료");
          formObj.attr("action", "/board/list").attr("method", "get").submit();
        },
        error: function (xhr, status, error) {
          console.error("수정 실패:", status, error, xhr.responseText);
          alert("수정 실패: " + xhr.responseText);
        }
      });
    });


    // 6. 삭제 버튼 (form submit)
    $("button[data-oper='remove']").on("click", function(e){
      e.preventDefault();
      if(confirm("정말로 게시글을 삭제하시겠습니까?")) {
        formObj.attr("action", "/board/remove").attr("method", "post").submit();
      }
    });

    // 7. 목록 버튼 (form submit, GET)
    $("button[data-oper='list']").on("click", function(e){
      e.preventDefault();
      formObj.attr("action", "/board/list").attr("method","get").submit();
    });
  });
</script>

<%@ include file="../includes/footer.jsp" %>