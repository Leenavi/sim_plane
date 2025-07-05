package com.simplane.service;

import com.simplane.domain.AuthVO;
import com.simplane.domain.MemberVO;

import java.util.List;

public interface UserService {

    /**
     * 회원가입 처리
     * @param member 회원 정보가 담긴 MemberVO 객체
     * @return 가입 성공 여부
     */
    boolean registerUser(MemberVO member);

    /**
     * userid로 회원 조회
     * @param userid 로그인 아이디
     * @return 회원 정보
     */
    MemberVO getUserById(String userid);

    boolean isAdmin(String name);

    public void updateAuth(AuthVO auth);

    public List<MemberVO> getAllMembers();
}