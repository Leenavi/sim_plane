package com.simplane.service;

import com.simplane.domain.AnswerVO;
import com.simplane.domain.QuestionVO;
import com.simplane.domain.ResultVO;
import com.simplane.domain.TestVO;
import com.simplane.mapper.AnswerMapper;
import com.simplane.mapper.QuestionMapper;
import com.simplane.mapper.ResultMapper;
import com.simplane.mapper.TestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final TestMapper testMapper;
    private final QuestionMapper questionMapper;
    private final AnswerMapper answerMapper;
    private final ResultMapper resultMapper;

    @Override
    public List<TestVO> getAllTests() {
        return testMapper.readAllTests();
    }

    @Override
    public void saveTestFromRequest(HttpServletRequest request) {
        String testName = request.getParameter("testname");

        // 1. 테스트 등록
        TestVO test = TestVO.builder()
                .testName(testName)
                .build();
        testMapper.insertTest(test);
        Long testid = test.getTestid();

        // 2. 질문 + 답변 저장
        for (int q = 1; ; q++) {
            String questionText = request.getParameter("questions[" + q + "].text");
            if (questionText == null) break;

            QuestionVO question = QuestionVO.builder()
                    .testid(testid)
                    .question(questionText)
                    .q_index(q)
                    .build();
            questionMapper.insertQuestion(question);
            Long questionid = question.getQuestionid();

            for (int a = 0; ; a++) {
                String answerText = request.getParameter("questions[" + q + "].answers[" + a + "].text");
                String scoreStr = request.getParameter("questions[" + q + "].answers[" + a + "].score");
                String type = request.getParameter("questions[" + q + "].answers[" + a + "].type");

                if (answerText == null) break;

                AnswerVO answer = AnswerVO.builder()
                        .questionid(questionid)
                        .answer(answerText)
                        .score(scoreStr != null ? Integer.parseInt(scoreStr) : 0)
                        .type(type)
                        .build();
                answerMapper.insertAnswer(answer);
            }
        }

        // 3. 결과 저장
        String[] scores = request.getParameterValues("resultScore");
        String[] types = request.getParameterValues("resultType");
        String[] texts = request.getParameterValues("resultText");

        if (scores != null) {
            for (int i = 0; i < scores.length; i++) {
                ResultVO result = ResultVO.builder()
                        .testid(testid)
                        .totalScore(Integer.parseInt(scores[i]))
                        .resultType(types[i])
                        .result(texts[i])
                        .build();
                resultMapper.insertResult(result);
            }
        }
    }

    @Transactional
    @Override
    public void deleteTestById(int testid) {
        // 1. 해당 테스트의 질문 목록 조회
        List<QuestionVO> questions = questionMapper.getQuestionsByTestId(testid);

        // 2. 각 질문에 대한 답변 삭제
        for (QuestionVO question : questions) {
            answerMapper.deleteAnswersByQuestionId(question.getQuestionid());
        }

        // 3. 질문 삭제
        questionMapper.deleteQuestionsByTestId(testid);

        // 4. 결과 삭제
        resultMapper.deleteResultsByTestId(testid);

        // 5. 테스트 삭제
        testMapper.deleteTest(testid);
    }
}