package com.simplane.domain;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResultVO {

    private Long resultid;
    private Long testid;
    private String result;
    private int totalScore;
    private String resultType;
}
