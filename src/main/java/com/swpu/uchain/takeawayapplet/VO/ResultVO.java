package com.swpu.uchain.takeawayapplet.VO;

import lombok.Data;

/**
 * @ClassName ResultVO
 * @Description TODO
 * @Author hobo
 * @Date 19-1-5 下午1:37
 **/
@Data
public class ResultVO<T> {

    private Integer code;

    private String msg;

    private T data;
}
