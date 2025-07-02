// 콘솔 확인용 로그
console.log("Test Module for Psychotest............");

// 즉시 실행 함수로 testService 객체 생성
let testService = (function () {

    // ✅ [1] 질문 가져오기 함수
    function getQuestion(step, callback, error) {
        let testid = $("#testid").val();  // 숨겨진 input에서 testid 가져옴

        $.ajax({
            type: 'GET',
            url: '/test/question?testid=' + testid + '&step=' + step,
            success: function (data) {
                if (callback) callback(data);
            },
            error: function (xhr, status, er) {
                if (error) error(er);
            }
        });
    }

    // ✅ [2] 답변 전송 함수
    function submitAnswer(answerid, callback, error) {
        $.ajax({
            type: 'POST',
            url: '/test/answer',
            data: JSON.stringify({ answerid }),
            contentType: 'application/json; charset=utf-8',
            success: function (result) {
                if (callback) callback(result);
            },
            error: function (xhr, status, er) {
                if (error) error(er);
            }
        });
    }

    // ✅ [3] 결과 요청 함수
    function getResult(callback, error) {
        let testid = $("#testid").val();

        $.ajax({
            type: 'GET',
            url: '/test/result?testid=' + testid,
            success: function (data) {
                if (callback) callback(data);
            },
            error: function (xhr, status, er) {
                if (error) error(er);
            }
        });
    }

    return {
        getQuestion,
        submitAnswer,
        getResult
    };
})();

// ✅ [4] 질문 출력 및 선택지 처리 함수
function renderQuestion(data) {

    // 질문 출력
    $('#question-box').html('<p>' + data.question + '</p>');

    // 선택지 출력
    let html = '';
    data.answers.forEach(function (answer) {
        html += '<button class="answer-btn" data-id="' + answer.answerid + '">' + answer.answer + '</button><br>';
    });
    $('#choices-box').html(html);

    // 클릭 이벤트 등록
    // renderQuestion 안에서
    $('.answer-btn').click(function () {
        const answerid = $(this).data('id');

        console.log('step:', data.step);
        console.log('질문 데이터:', data);

        testService.submitAnswer(answerid, function () {
            if (data.hasNext) {
                // 확실하게 step을 확인하고 증가시켜야 함
                let currentStep = data.step || 1; // step 없으면 1로
                let nextStep = currentStep + 1;

                testService.getQuestion(nextStep, renderQuestion);
            } else {
                testService.getResult(renderResult);
            }
        });
    });


    console.log('질문 데이터:', data);
    console.log('선택지:', data.answers);

}

// ✅ [5] 결과 출력 함수
function renderResult(result) {
    $('#test-section').hide();  // 질문 영역 숨기기
    $('#result-box').html('<p>' + result.result + '</p>');
    $('#result-section').show();  // 결과 영역 보이기
}

// ✅ [6] 다시 시작 버튼 처리
function restartTest() {
    $('#result-section').hide();
    $('#test-section').show();
    testService.getQuestion(1, renderQuestion);
}

// ✅ [7] 페이지 로드 시 첫 질문 가져오기
$(function () {
    testService.getQuestion(1, renderQuestion);
});
