package com.simplane.domain;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardVO {

    private Long boardid;
    private String title;
    private String content;
    private Date regDate;
    private String imagePath;
    private String writer;
}
