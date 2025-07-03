package com.simplane.service;

import com.simplane.domain.AuthVO;
import com.simplane.domain.MemberVO;
import com.simplane.mapper.MemberMapper;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Log4j
public class UserServiceImpl implements UserService {

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public boolean registerUser(MemberVO member) {
        // 1. 중복 아이디 체크 (userid 기준)
        if (memberMapper.readByUserid(member.getUserid()) != null) {
            log.info("회원가입 실패 - 중복 아이디: " + member.getUserid());
            return false;
        }

        // 2. 비밀번호 암호화 후 세팅
        member.setPassword(passwordEncoder.encode(member.getPassword()));

        // 3. 회원정보 저장 (memberid는 DB에서 자동 생성)
        int insertCount = memberMapper.insert(member);

        // 4. 권한 저장 (기본 ROLE_USER)
        AuthVO auth = new AuthVO();
        auth.setMemberid(member.getMemberid());  // insert 후 자동 생성된 PK 가져옴
        auth.setAuth("ROLE_ADMIN");
        memberMapper.insertAuth(auth);

        return insertCount == 1;
    }

    @Override
    public MemberVO getUserById(String userid) {
        return memberMapper.readByUserid(userid);
    }

    @Override
    public boolean isAdmin(String userid) {
        MemberVO member = memberMapper.readByUserid(userid);
        if (member == null) return false;

        // memberid 기준으로 권한 목록 조회
        int memberid = member.getMemberid();
        List<AuthVO> authList = memberMapper.readAuthByMemberid(memberid);

        return authList.stream()
                .anyMatch(auth -> "ROLE_ADMIN".equals(auth.getAuth()));
    }
}