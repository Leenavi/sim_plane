package com.simplane.domain;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionVO {

    private Long questionid;
    private Long testid;
    private String question;
    private String answer;
    private int q_index;
}
