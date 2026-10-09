package com.nexa.business.config;

import com.nexa.business.domain.AppUser;
import com.nexa.business.repository.AppUserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collections;

@Service
public class DatabaseUserDetailsService implements UserDetailsService {
    private final AppUserRepository users;
    public DatabaseUserDetailsService(AppUserRepository users){this.users=users;}
    @Override @Transactional(readOnly=true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser u=users.findByUsernameIgnoreCase(username).orElseThrow(()->new UsernameNotFoundException("User tidak ditemukan"));
        return new User(u.getUsername(),u.getPasswordHash(),u.isEnabled(),true,true,true,Collections.singletonList(new SimpleGrantedAuthority(u.getRole())));
    }
}
