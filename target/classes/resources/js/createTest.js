// createTest.js (또는 원하는 이름의 .js 파일)
let questionIndex = 1; // 질문 인덱스를 0으로 초기화
let resultIndex = 1;   // 결과 인덱스를 0으로 초기화
const answerIndices = {}; // 각 질문별 보기 인덱스를 관리하는 객체


// 질문 추가 함수
function addQuestion() {
    // 현재 questionIndex 값을 qIdx에 할당하고, questionIndex는 1 증가시킵니다.
    // 예: 처음 호출 시 qIdx=1, questionIndex=2. 두 번째 호출 시 qIdx=2, questionIndex=3.
    const qIdx = questionIndex++;


    answerIndices[qIdx] = 1;

    const html = `
    <div class="question-box" data-qidx="${qIdx}">
      <h4>질문 ${qIdx}</h4> 
      <input type="text" name="questions[${qIdx}].text" placeholder="질문 내용" required><br>
      <div class="answers" id="answers-${qIdx}">
        <h5>보기</h5>
        <div>
          <input type="text" name="questions[${qIdx}].answers[0].text" placeholder="보기1" required>
          <input type="number" name="questions[${qIdx}].answers[0].score" placeholder="점수" required><br>
        </div>
        <div>
          <input type="text" name="questions[${qIdx}].answers[1].text" placeholder="보기2" required>
          <input type="number" name="questions[${qIdx}].answers[1].score" placeholder="점수" required><br>
        </div>
      </div>
      <button type="button" class="add-answer-btn" data-qidx="${qIdx}">보기 추가</button>
      <button type="button" class="remove-question-btn" data-qidx="${qIdx}">질문 삭제</button>
    </div><br>
  `;
    document.getElementById("question-section").insertAdjacentHTML("beforeend", html);
}

// 보기 추가 함수
function addAnswer(qIdx) {
    const answersDiv = document.getElementById(`answers-${qIdx}`);
    if (!answersDiv) {
        console.error(`answers-${qIdx}를 찾을 수 없습니다.`);
        return;
    }

    // 해당 질문의 다음 보기 인덱스를 가져오고 1 증가시킵니다.
    const aIdx = ++answerIndices[qIdx]; // 보기 인덱스는 여전히 0부터 시작하므로 그대로 유지
    const html = `
    <div>
      <input type="text" name="questions[${qIdx}].answers[${aIdx}].text" placeholder="보기${aIdx + 1}" required>
      <input type="number" name="questions[${qIdx}].answers[${aIdx}].score" placeholder="점수" required>
      <button type="button" class="remove-answer-btn">삭제</button><br>
    </div>
  `;
    answersDiv.insertAdjacentHTML("beforeend", html);
}

// 결과 추가 함수
function addResult() {
    // ✅ 변경: 결과 인덱스도 1부터 시작
    const idx = resultIndex++;
    const html = `
<div class="result-box" data-ridx="${idx}">
  <h4>결과 ${idx}</h4> 
  <input type="number" name="resultScore" placeholder="점수 기준" required>
  <input type="text" name="resultType" placeholder="결과 유형" required><br>
  <textarea name="resultText" placeholder="결과 설명" required></textarea>
  <button type="button" class="remove-result-btn" data-ridx="${idx}">결과 삭제</button>
</div><br>
`;
    document.getElementById("result-section").insertAdjacentHTML("beforeend", html);
}

// 이벤트 위임 (question-section 내부 클릭 이벤트 처리)
// '질문 추가', '보기 추가', '질문 삭제', '보기 삭제' 버튼 클릭을 처리합니다.
document.getElementById("question-section").addEventListener("click", (e) => {
    // '보기 추가' 버튼 클릭 시
    if (e.target.classList.contains("add-answer-btn")) {
        const qIdx = e.target.getAttribute("data-qidx");
        addAnswer(qIdx);
    }
    // '질문 삭제' 버튼 클릭 시
    else if (e.target.classList.contains("remove-question-btn")) {
        const qIdxToRemove = e.target.getAttribute("data-qidx");
        // 클릭된 버튼의 가장 가까운 'question-box' 부모 요소를 찾습니다.
        const questionBox = e.target.closest(`.question-box[data-qidx="${qIdxToRemove}"]`);
        if (questionBox) {
            questionBox.remove(); // 해당 질문 박스를 DOM에서 제거합니다.
            // 삭제된 질문에 대한 보기 인덱스 정보도 정리합니다.
            delete answerIndices[qIdxToRemove];
        }
    }
    // '보기 삭제' 버튼 클릭 시
    else if (e.target.classList.contains("remove-answer-btn")) {
        const answerDiv = e.target.closest('div'); // 삭제할 보기의 div 요소를 찾습니다.
        if (answerDiv) {
            answerDiv.remove(); // 해당 보기 div를 DOM에서 제거합니다.
        }
    }
});

// 이벤트 위임 (result-section 내부 클릭 이벤트 처리)
// '결과 삭제' 버튼 클릭을 처리합니다.
document.getElementById("result-section").addEventListener("click", (e) => {
    // '결과 삭제' 버튼 클릭 시
    if (e.target.classList.contains("remove-result-btn")) {
        const rIdxToRemove = e.target.getAttribute("data-ridx");
        // 클릭된 버튼의 가장 가까운 'result-box' 부모 요소를 찾습니다.
        const resultBox = e.target.closest(`.result-box[data-ridx="${rIdxToRemove}"]`);
        if (resultBox) {
            resultBox.remove(); // 해당 결과 박스를 DOM에서 제거합니다.
        }
    }
});

// '질문 추가' 버튼에 클릭 이벤트 리스너를 연결합니다.
// (DOMContentLoaded 이벤트는 외부 .js 파일 사용 시 자동 처리됩니다.)
document.getElementById("add-question-btn").addEventListener("click", addQuestion);