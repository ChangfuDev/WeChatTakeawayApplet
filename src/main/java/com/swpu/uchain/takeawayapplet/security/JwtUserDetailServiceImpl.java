package com.swpu.uchain.takeawayapplet.security;

import com.swpu.uchain.takeawayapplet.service.UserService;
import com.swpu.uchain.takeawayapplet.entity.User;
import com.swpu.uchain.takeawayapplet.enums.RoleEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @ClassName UserDetailServiceImpl
 * @Description
 * @Author hobo
 * @Date 18-12-9 下午2:32
 **/

@Service
@Slf4j
public class JwtUserDetailServiceImpl implements UserDetailsService {


    @Autowired
    UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userService.findUserByUserName(username);
        if (user == null) {
            log.info("此用户不存在");
            throw new UsernameNotFoundException(String.format("用户名为 %s 的用户不存在", username));
        } else {
            String role = RoleEnum.getRole(user.getRole());
            return new JwtUser(username, user.getPassword(), role);
        }
    }
}
