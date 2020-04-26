package com.uni.gateway.security.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * 用户登录处理
 *
 * @author gehoubao
 * @create 2020-01-21 10:16
 **/
@Service
public class UserDetailHandler implements UserDetailsService {

    @Autowired
    SecurityUserRepository securityUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SoUserEntity entity = securityUserRepository.findByName(username);
        if (entity == null) {
            throw new UsernameNotFoundException(username + "用户不存在!");
        }

        String[] roles = entity.getRoles().split(",");
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (int i = 0; i < roles.length; i++) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + roles[i]));
        }
        return new User(username, entity.getPassword(), authorities);
    }
}
