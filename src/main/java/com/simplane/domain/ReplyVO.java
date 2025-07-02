package com.simplane.domain;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReplyVO {

    private Long replyid;
    private Long testid;
    private String replyer;
    private String reply;
    private Date replyDate;
}
