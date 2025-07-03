package com.simplane.domain;

import lombok.Getter;
import lombok.ToString;
import lombok.extern.log4j.Log4j;

@Getter
@ToString
@Log4j
public class PageDTO {

    private int startPage;
    private int endPage;
    private boolean prev, next;
    //전체 레코드 개수
    private int total;
    //페이지당 레코드 개수
    private Criteria cri;

    public PageDTO(Criteria cri, int total) {
        this.cri = cri;
        this.total = total;

        // 한 페이지당 10개 기준으로 endPage 계산
        this.endPage = (int)(Math.ceil(cri.getPageNum() / 10.0)) * 10;
        this.startPage = this.endPage - 9;

        // 진짜 마지막 페이지 (총 게시물 수에 따라 결정)
        int realEnd = (int)(Math.ceil((total * 1.0) / cri.getAmount()));

        if (realEnd < this.endPage) {
            this.endPage = realEnd;
        }

        this.prev = this.startPage > 1;
        this.next = this.endPage < realEnd;

        // 디버깅용 출력
        System.out.println("========== PageDTO 디버그 ==========");
        System.out.println("총 게시글 수: " + total);
        System.out.println("startPage: " + startPage);
        System.out.println("endPage: " + endPage);
        System.out.println("realEnd: " + realEnd);
        System.out.println("next: " + next);
        System.out.println("prev: " + prev);
        System.out.println("===================================");
    }


}
