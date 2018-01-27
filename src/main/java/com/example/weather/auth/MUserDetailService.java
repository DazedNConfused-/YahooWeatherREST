package com.example.weather.auth;

import com.example.weather.auth.entity.Account;
import com.example.weather.auth.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("userDetailsService")
@Transactional
public class MUserDetailService implements UserDetailsService {

    @Autowired
    AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(final String appUserName) throws UsernameNotFoundException {

        Account account = accountRepository.findByUsername(appUserName);
        if (account == null) throw new UsernameNotFoundException(appUserName);
        else{
            return new User(account.getUsername(),account.getPassword(),true,true,true,true, getGrantedAuthorities(account));
        }
    }

    private List<GrantedAuthority> getGrantedAuthorities(Account appUser){
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

//        for (Authority authority : appUser.getAuthorities()){
//            authorities.add(new SimpleGrantedAuthority(authority.getAuthorityName()));
//        }
//        return authorities;

        authorities.add(new SimpleGrantedAuthority("USER"));

        return authorities;
    }
}