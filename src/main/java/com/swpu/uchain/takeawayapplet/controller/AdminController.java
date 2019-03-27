package com.swpu.uchain.takeawayapplet.controller;

import com.swpu.uchain.takeawayapplet.accessctro.RoleContro;
import com.swpu.uchain.takeawayapplet.enums.RoleEnum;
import com.swpu.uchain.takeawayapplet.form.UserForm;
import com.swpu.uchain.takeawayapplet.service.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName AdminController
 * @Author hobo
 * @Date 19-3-26 下午4:45
 * @Description
 **/
@RestController
@RequestMapping("/admin")
@Api(tags = "管理员界面")
public class AdminController {

    @Autowired
    private UserService userService;

    @RoleContro(role = RoleEnum.SUPER_ADMIN)
    @PostMapping(value = "/insert", name = "添加用户")
    public Object insertUser(UserForm userForm) {
        return userService.insertUser(userForm);
    }

    @RoleContro(role = RoleEnum.SUPER_ADMIN)
    @GetMapping(value = "/list",name = "查询用户列表")
    public Object selectAll(){
        return userService.selectAll();
    }

    @RoleContro(role = RoleEnum.SUPER_ADMIN)
    @GetMapping(value = "/delete", name = "删除用户")
    public Object deleteUser(Long id) {
        return userService.deleteUser(id);
    }

    @RoleContro(role = RoleEnum.SUPER_ADMIN)
    @PostMapping(value = "/updateRole", name = "将用户提升为管理员")
    public Object addAdminRole(Long id) {
        return userService.addRole(id);
    }

}
