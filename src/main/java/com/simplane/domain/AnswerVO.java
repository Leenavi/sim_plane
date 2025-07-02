package com.simplane.domain;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnswerVO {

    private Long answerid;
    private Long anserid;
    private String answer;
    private int score;
    private String type;
}
