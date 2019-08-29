package kr.ac.skuniv.service.member;

import kr.ac.skuniv.domain.entity.Member;
import kr.ac.skuniv.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;

public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByIdentity(username);

        if(member == null) {
            throw new UsernameNotFoundException(username);
        }

        return new User(member.getIdentity(), member.getPassword(), makeGrantedAuthority(member.getType()));
    }

    private Collection<? extends GrantedAuthority> makeGrantedAuthority(String type) {
        return null;
    }
}
