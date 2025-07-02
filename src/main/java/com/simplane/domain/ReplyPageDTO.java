package com.simplane.domain;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter @Setter
public class ReplyPageDTO {

    private int replyCnt;

    private List<ReplyVO> list;
}
