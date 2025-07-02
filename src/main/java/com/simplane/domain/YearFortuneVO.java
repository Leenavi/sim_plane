package com.simplane.domain;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class YearFortuneVO {

    private String summary;
    private String love;
    private String money;
    private String career;
    private String health;
}
