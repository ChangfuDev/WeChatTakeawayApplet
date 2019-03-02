package com.swpu.uchain.takeawayapplet.enums;

import lombok.Getter;

@Getter
public enum ResultEnum {

    ;

    private Integer code;

    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
