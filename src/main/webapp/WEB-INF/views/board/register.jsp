<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%@ include file="../includes/header.jsp" %>
<link rel="stylesheet" href="<c:url value='/resources/dist/css/main.css' />" />

<div class="container">
    <h2>문의하기</h2>
    <form role="form" action="/board/register" method="post">

        <table class="table table-bordered">


            <tr>
                <th>제목</th>
                <td><input type="text" name="title" style="width: 100%;"></td>
            </tr>
            <tr>
                <th>작성자</th>
                <td>
                    <input type="text" name="writer" style="width: 100%;"
                           value="<sec:authentication property='principal.username' />" readonly>
                </td>
            </tr>
            <tr>
                <th>내용</th>
                <td colspan="5">
                    <textarea name="content" class="form-control" rows="10"></textarea>
                </td>
            </tr>
            <!-- 이미지 업로드 영역 추가 -->
            <tr>
                <th>이미지</th>
                <td colspan="5">
                    <input type="file" name="uploadFile" multiple>
                    <div class="uploadResult">
                        <ul></ul>
                    </div>
                </td>
            </tr>

        </table>

        <button data-oper='register' class="btn btn-info">등록</button>
        <button data-oper='list' class="btn btn-default">목록</button>

    </form>
</div>


<script>
    $(function () {
        const uploadUL = $(".uploadResult ul");

        $("input[name='uploadFile']").on("change", function () {
            const formData = new FormData();
            const inputFile = $("input[name='uploadFile']");
            const files = inputFile[0].files;

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
                }
            });
        });

        function showUploadedImages(uploadResultArr) {

            if (!uploadResultArr || uploadResultArr.length === 0) return;

            // 기존 목록 비우기 (추가된 부분)
            $(".uploadResult ul").empty();

            uploadResultArr.forEach(function (obj) {
                const fileCallPath = encodeURIComponent(obj.uploadPath + "/s_" + obj.uuid + "_" + obj.fileName);
                const originPath = obj.uploadPath + "/" + obj.uuid + "_" + obj.fileName;

                // 새로운 li 요소 생성
                const newLi = $('<li>').attr({
                    'data-path': obj.uploadPath,
                    'data-uuid': obj.uuid,
                    'data-filename': obj.fileName,
                    'data-type': obj.image
                });

                // 이미지 요소 추가
                newLi.append($('<img>').attr('src', "/display?fileName=" + fileCallPath).width(100));
                newLi.append($('<span>').text(obj.fileName));

                // 삭제 버튼 추가
                newLi.append($('<button>').attr({
                    'type': 'button',
                    'class': 'btn btn-sm btn-danger btn-remove',
                    'data-file': originPath, // 삭제 시 사용할 전체 경로
                    'data-type': obj.image ? 'image' : 'file'
                }).text('삭제'));

                // 핵심: hidden input을 생성하고 value를 명시적으로 설정
                const hiddenInput = $('<input>').attr({
                    'type': 'hidden',
                    'name': 'imagePaths'
                }).val(originPath); // 여기서 originPath 값을 확실히 할당

                newLi.append(hiddenInput);

                // 생성된 li를 ul에 추가
                $(".uploadResult ul").append(newLi);

                // 디버깅을 위한 로그 (꼭 확인해보세요)
                console.log("생성된 hidden input의 value:", hiddenInput.val());
            });
        }


        // 목록 버튼 이벤트
        $("button[data-oper='list']").on("click", function(e){
            e.preventDefault();
            location.href = "/board/list";
        });


        // 이미지 삭제 버튼 처리
        $(".uploadResult").on("click", ".btn-remove", function () {
            const targetLi = $(this).closest("li");
            const fileName = $(this).data("file");
            const type = $(this).data("type");

            $.ajax({
                url: "/deleteFile",
                type: "POST",
                data: { fileName: fileName, type: type },
                success: function (result) {
                    console.log("삭제 완료: " + result);
                    targetLi.remove();
                }
            });
        });

        // --- 여기에 추가하시면 됩니다. ---
        $("button[data-oper='register']").on("click", function(e){
            e.preventDefault(); // 기본 폼 제출 방지

            const form = $("form[role='form']");

            // serialize()를 사용하면 별도의 처리 없이 모든 폼 데이터를 문자열로 만들어줍니다.
            const formData = form.serialize();

            // AJAX를 사용하여 폼 데이터 제출
            $.ajax({
                url: form.attr('action'),
                type: form.attr('method'),
                data: formData, // 직렬화된 데이터를 바로 사용합니다.
                success: function(response) {
                    console.log("폼 제출 성공:", response);
                    alert("게시물이 성공적으로 등록되었습니다!");
                    location.href = "/board/list";
                },
                error: function(xhr, status, error) {
                    console.error("폼 제출 오류:", error);
                    alert("게시물 등록에 실패했습니다.");
                }
            });
        });
// ------------------------------------
    });

</script>

<%@ include file="../includes/footer.jsp" %>
