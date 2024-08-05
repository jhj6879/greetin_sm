package com.example.greeting.service;

import com.example.greeting.dao.EmployeeDao;
import com.example.greeting.dto.EmployeeDto;
import com.example.greeting.role.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserSecurityService implements UserDetailsService {

    private final EmployeeDao dao;
    private final PasswordEncoder passwordEncoder;

    // pwd 암호화 작업
    public EmployeeDto create(EmployeeDto dto) {
        dto.setUser_pw(passwordEncoder.encode(dto.getUser_pw()));
        this.dao.insertEmployee(dto);
        return dto;
    }

    // 지정 아이디에 권한 부여하기
    // security에서 불러오는 이름이 Username이 userid를 뜻하는것 주의해서 보기
    @Override
    public UserDetails loadUserByUsername(String user_id) throws UsernameNotFoundException {
//		System.out.println(userid);
        EmployeeDto member = this.dao.getByUserId(user_id);
//		System.out.println(member.toString());
        if(member == null) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }
        List<GrantedAuthority> authorities = new ArrayList<>();

        // 권한은 0이 제일 낮고 9가 제일 높음 (예 : 카페 권한)
        if(member.getPermit() == 9) {
            authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));
        }
        else if(member.getPermit() == 8) {
            authorities.add(new SimpleGrantedAuthority(UserRole.MMANGER.getValue()));
        }
        else {
            authorities.add(new SimpleGrantedAuthority(UserRole.USER.getValue()));
        }

        return new User(member.getUser_id(), member.getUser_pw(), authorities);
    }

}
