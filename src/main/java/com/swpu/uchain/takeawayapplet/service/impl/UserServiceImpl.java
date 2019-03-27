package com.swpu.uchain.takeawayapplet.service.impl;

import com.swpu.uchain.takeawayapplet.VO.ResultVO;
import com.swpu.uchain.takeawayapplet.dao.UserMapper;
import com.swpu.uchain.takeawayapplet.entity.User;
import com.swpu.uchain.takeawayapplet.enums.ResultEnum;
import com.swpu.uchain.takeawayapplet.form.LoginForm;
import com.swpu.uchain.takeawayapplet.form.UserForm;
import com.swpu.uchain.takeawayapplet.redis.RedisService;
import com.swpu.uchain.takeawayapplet.redis.key.UserKey;
import com.swpu.uchain.takeawayapplet.security.JwtProperties;
import com.swpu.uchain.takeawayapplet.security.JwtUserDetailServiceImpl;
import com.swpu.uchain.takeawayapplet.service.UserService;
import com.swpu.uchain.takeawayapplet.util.JwtTokenUtil;
import com.swpu.uchain.takeawayapplet.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName UserServiceImpl
 * @Author hobo
 * @Date 19-3-25 下午7:33
 * @Description
 **/
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private RedisService redisService;

    @Autowired
    private JwtUserDetailServiceImpl jwtUserDetailService;

    @Override
    public boolean insert(User user) {
        if (userMapper.insert(user) == 1) {
            redisService.set(UserKey.userKey, user.getUsername(), user);
            return true;
        }
        return false;
    }

    @Override
    public boolean update(User user) {
        if (userMapper.updateByPrimaryKey(user) == 1) {
            redisService.set(UserKey.userKey, user.getUsername(), user);
            return true;
        }
        return false;
    }


    @Override
    public boolean delete(Long id) {
        redisService.delete(UserKey.userKey, id + "");
        return (userMapper.deleteByPrimaryKey(id) == 1);
    }

    @Override
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        if (authentication != null && !username.equals("anonymousUser")) {
            return findUserByUserName(username);
        }
        return null;
    }

    @Override
    public User findUserByUserName(String username) {
        return userMapper.findUserByUserName(username);
    }

    @Override
    public ResultVO insertUser(UserForm userForm) {
        if (findUserByUserName(userForm.getUsername()) != null) {
            return ResultUtil.error(ResultEnum.USER_EXIST);
        }
        String password = userForm.getPassword();
        password = new BCryptPasswordEncoder().encode(password);
        userForm.setPassword(password);
        User user = new User();
        BeanUtils.copyProperties(userForm, user);
        user.setRole(0);
        if (insert(user)) {
            return ResultUtil.success();
        }
        return ResultUtil.error(ResultEnum.SERVER_ERROR);
    }

    @Override
    public ResultVO deleteUser(Long id) {
        if (userMapper.selectByPrimaryKey(id) == null) {
            return ResultUtil.error(ResultEnum.USER_NOT_EXIST);
        }
        if (delete(id)) {
            return ResultUtil.success();
        }
        return ResultUtil.error(ResultEnum.SERVER_ERROR);
    }

    @Override
    public ResultVO login(LoginForm loginForm, HttpServletResponse response) {
        User user = userMapper.findUserByUserName(loginForm.getUsername());
        if (user == null) {
            return ResultUtil.error(ResultEnum.USER_NOT_EXIST);
        }
        UserDetails userDetails = jwtUserDetailService.loadUserByUsername(loginForm.getUsername());

        if (!new BCryptPasswordEncoder().matches(loginForm.getPassword(), userDetails.getPassword())) {
            return ResultUtil.error(ResultEnum.PASSWORD_ERROR);
        }
        Authentication token = new UsernamePasswordAuthenticationToken(loginForm.getUsername(), loginForm.getPassword(), userDetails.getAuthorities());

        Authentication authentication = authenticationManager.authenticate(token);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        final String realToken = jwtTokenUtil.generateToken(userDetails);
        response.addHeader(jwtProperties.getTokenName(), realToken);
        Map map = new HashMap();
        map.put("role", user.getRole());
        map.put("token", realToken);
        return ResultUtil.success(map);
    }

    @Override
    public ResultVO addRole(Long id) {
        User user = userMapper.selectByPrimaryKey(id);
        if (user == null) {
            return ResultUtil.error(ResultEnum.USER_NOT_EXIST);
        }
        user.setRole(2);
        if (update(user)) {
            return ResultUtil.success();
        }
        return ResultUtil.error(ResultEnum.SERVER_ERROR);
    }

    @Override
    public ResultVO selectAll() {
        return ResultUtil.success(userMapper.selectAll());
    }


}
