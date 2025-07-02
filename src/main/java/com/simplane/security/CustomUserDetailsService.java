package com.simplane.security;

import com.simplane.domain.MemberVO;
import com.simplane.mapper.MemberMapper;
import com.simplane.security.domain.CustomUser;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Spring Security에서 사용자 인증 정보를 DB에서 가져오기 위한 구현 클래스
 * UserDetailsService를 구현하고, CustomUser로 사용자 정보를 감싸서 리턴함
 */
@Log4j
@Service
public class CustomUserDetailsService implements UserDetailsService {

    // MyBatis 매퍼를 주입받아 DB에서 사용자 정보를 조회
    @Autowired
    private MemberMapper memberMapper;

    /**
     * AuthenticationManager가 사용자 인증을 시도할 때 호출됨
     * @param username 로그인 폼에서 입력된 사용자 ID (보통 userid)
     * @return UserDetails 객체 (CustomUser로 래핑된 MemberVO)
     * @throws UsernameNotFoundException 사용자를 찾지 못했을 때 예외 발생
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.warn("Load User By UserName: " + username); // 로그인 시도 로그

        // DB에서 사용자 조회
        MemberVO vo = memberMapper.readByUserid(username); // 일반적으로 username은 userid

        // 사용자가 존재하지 않으면 예외 발생 (null 반환은 지양)
        if (vo == null) {
            log.warn("User not found: " + username);
            throw new UsernameNotFoundException("User not found");
        }

        // DB에서 가져온 사용자 정보를 CustomUser로 감싸서 반환
        // CustomUser는 Spring Security에서 사용 가능한 UserDetails 구현체
        return new CustomUser(vo);
    }
}