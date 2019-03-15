package com.swpu.uchain.takeawayapplet.util;

import com.swpu.uchain.takeawayapplet.VO.ResultVO;
import com.swpu.uchain.takeawayapplet.enums.ResultEnum;

/**
 * @ClassName ResultUtil
 * @Author hobo
 * @Date 19-3-2 下午7:01
 * @Description 输出结果格式化工具
 **/
public class ResultUtil {



    /**
     * @Author hobo
     * @Description :
     * @Param [object]
     * @return com.swpu.uchain.takeawayapplet.VO.ResultVO
     **/
    public static ResultVO success(Object object) {
        ResultVO resultVO = new ResultVO();
        resultVO.setData(object);
        resultVO.setCode(0);
        resultVO.setMsg("成功");
        return resultVO;
    }

    /**
     * @Author hobo
     * @Description :
     * @Param []
     * @return com.swpu.uchain.takeawayapplet.VO.ResultVO
     **/
    public static ResultVO success() {
        return success(null);
    }

    /**
     * @Author hobo
     * @Description :
     * @Param [resultEnmus]
     * @return com.swpu.uchain.takeawayapplet.VO.ResultVO
     **/
    public static ResultVO error(ResultEnum resultEnmus) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(resultEnmus.getCode());
        resultVO.setMsg(resultEnmus.getMsg());
        return resultVO;
    }
}
