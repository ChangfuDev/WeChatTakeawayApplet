package com.swpu.uchain.takeawayapplet.enums;

import lombok.Getter;

import java.util.HashMap;

@Getter
public enum RoleEnum {

    ADMIN(1, "普通管理员"),
    SUPER_ADMIN(2, "超级管理员"),
    ;


    private Integer value;
    private String role;

    RoleEnum(Integer value, String role) {
        this.value = value;
        this.role = role;
    }

    public static String getRole(Integer integer) {
        HashMap<Integer, String> hashMap = new HashMap<>();
        hashMap.put(SUPER_ADMIN.getValue(), SUPER_ADMIN.getRole());
        hashMap.put(ADMIN.getValue(), ADMIN.getRole());
        return hashMap.get(integer);
    }

    public static Integer getValue(String role) {
        HashMap<String, Integer> hashMap = new HashMap<>();
        hashMap.put(SUPER_ADMIN.getRole(), SUPER_ADMIN.getValue());
        hashMap.put(ADMIN.getRole(), ADMIN.getValue());
        return hashMap.get(role);
    }

}
