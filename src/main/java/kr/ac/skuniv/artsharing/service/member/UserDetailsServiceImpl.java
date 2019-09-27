package kr.ac.skuniv.artsharing.service.member;

import kr.ac.skuniv.artsharing.domain.entity.Member;
import kr.ac.skuniv.artsharing.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class  UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findById(username);

        if(member == null) {
            throw new UsernameNotFoundException(username);
        }

//        return User.builder()
//                .username(member.getId())
//                .password(member.getPassword())
//                .roles(member.getRole())
//                .build();
        return new User(member.getId(),member.getPassword(),makeGrantedAuthority(member.getRole()));
    }

    private List<? extends GrantedAuthority> makeGrantedAuthority(String role) {
        List<GrantedAuthority> list = new ArrayList<>();
        list.add(new SimpleGrantedAuthority("ROLE_" + role));
        return list;
    }


}
