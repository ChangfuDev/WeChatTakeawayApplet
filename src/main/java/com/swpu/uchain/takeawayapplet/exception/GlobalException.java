package com.swpu.uchain.takeawayapplet.exception;

import com.swpu.uchain.takeawayapplet.enums.ResultEnum;
import lombok.Data;

/**
 * @ClassName GlobalException
 * @Author hobo
 * @Date 19-3-4 上午10:49
 * @Description 全局异常类
 **/
@Data
public class GlobalException extends RuntimeException {
    private ResultEnum resultEnum;

    public GlobalException(ResultEnum resultEnum) {
        super();
        this.resultEnum = resultEnum;
    }

}
