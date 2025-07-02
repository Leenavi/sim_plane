package com.simplane.dto;

import com.simplane.domain.AnswerVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDTO {

    private String question;
    private List<AnswerVO> answers;
    private boolean hasNext;
    private int step;
}
