package com.simplane.domain;

import lombok.*;

import java.util.Date;
import java.util.List;

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
    private String writer;

    // 이미지 정보 리스트 추가
    private List<ImgPathVO> imageList;
}
