package com.swpu.uchain.takeawayapplet.VO;

import lombok.Data;

/**
 * @ClassName AccessTokenVO
 * @Author hobo
 * @Date 19-3-28 下午2:20
 * @Description 用来换去accessToken
 **/
@Data
public class AccessTokenVO {

    private String accessToken;

    private String expiresIn;

}
