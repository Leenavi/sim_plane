package com.simplane.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class Criteria {
    
    private int pageNum; //현재 페이지
    private int amount; //총 페이지 개수
    
    private String type; //검색 조건(제목, 내용, 작성자)
    private String keyword; //검색 값

    public Criteria(int pageNum, int amount) {
        this.pageNum = pageNum;
        this.amount =amount;
    }

    public Criteria() {
        this(1, 10);
    }

    public String[] getTypeArr() {
        return type==null ? new String[] {} : type.split("");
    }

    // MySQL OFFSET 계산용 메서드
    public int getOffset() {
        return (pageNum - 1) * amount;
    }
}
