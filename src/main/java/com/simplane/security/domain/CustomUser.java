package com.simplane.security.domain;

import com.simplane.domain.MemberVO;
import lombok.Getter;
import lombok.extern.log4j.Log4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Log4j
@Getter
public class CustomUser extends User {

    private static final long serialVersionUID = 1L;

    private MemberVO member;

    // 기본 생성자 (UserDetails 구조 유지)
    public CustomUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    // MemberVO 기반 생성자
    public CustomUser(MemberVO vo) {
        super(
                vo.getUserid(),                                 // username
                vo.getPassword(),                               // password (암호화된)
                mapAuthorities(vo.getAuthList())                // 권한 목록
        );
        this.member = vo;
    }

    // 권한 변환 로직 분리 (가독성 향상)
    private static List<GrantedAuthority> mapAuthorities(List<com.simplane.domain.AuthVO> authList) {
        return authList.stream()
                .map(auth -> new SimpleGrantedAuthority(auth.getAuth()))
                .collect(Collectors.toList());
    }
}