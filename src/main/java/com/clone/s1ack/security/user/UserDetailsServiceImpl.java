package com.clone.s1ack.security.user;

import com.clone.s1ack.domain.Member;
import com.clone.s1ack.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email){

        Member member = memberRepository.findByEmail(email).orElseThrow(
                ()-> new UsernameNotFoundException("Account 가 존재하지 않습니다.")
        );

        return new UserDetailsImpl(member);
    }
}
