package com.simplane.domain;

import lombok.Data;

import java.sql.Date;
import java.util.List;


@Data
public class MemberVO {

    private Integer memberid;
    private String userid;
    private String password;
    private Date birthdate;
    private int sex;
    private String name;
    private List<AuthVO> authList;


}