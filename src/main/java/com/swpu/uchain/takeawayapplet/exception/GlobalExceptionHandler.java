package com.swpu.uchain.takeawayapplet.exception;

import com.swpu.uchain.takeawayapplet.VO.ResultVO;
import com.swpu.uchain.takeawayapplet.enums.ResultEnum;
import com.swpu.uchain.takeawayapplet.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.BindException;
import java.util.List;

/**
 * @ClassName GlobalExceptionHandler
 * @Author hobo
 * @Date 19-3-4 上午10:49
 * @Description 异常拦截器
 **/
@ControllerAdvice
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {
    public ResultVO exceptionHandler(HttpServletRequest request,
                                     HttpServletResponse response,
                                     Exception e) {
        if (e instanceof GlobalException) {
            GlobalException globalException = (GlobalException) e;
            return ResultUtil.error(globalException.getResultEnum());
        }
        if (e instanceof BindException) {
            org.springframework.validation.BindException bindException = (org.springframework.validation.BindException) e;
            List<ObjectError> errors = bindException.getAllErrors();
            String msg = null;
            for (ObjectError error : errors) {
                if (msg == null) {
                    msg = error.getDefaultMessage();
                } else {
                    msg += "," + error.getDefaultMessage();
                }
            }
            log.error(msg);
            ResultVO<String> resultVO = ResultUtil.error(ResultEnum.BIND_ERROR);
            resultVO.setMsg(String.format(resultVO.getMsg(), msg));
            return resultVO;
        }
        if (e instanceof HttpRequestMethodNotSupportedException) {
            HttpRequestMethodNotSupportedException exception = (HttpRequestMethodNotSupportedException) e;
            log.error("请求方式错误,不支持:{}", exception.getMethod());
            ResultVO<String> resultVO = ResultUtil.error(ResultEnum.REQUEST_METHOD_ERROR);
            resultVO.setMsg(String.format(resultVO.getMsg(), e.getMessage()));
            return resultVO;
        } else {
            log.error(e.getMessage());
            e.printStackTrace();
            String requestURI = request.getRequestURI();
            log.info("请求异常的接口:{}", requestURI);
            ResultVO<String> error = ResultUtil.error(ResultEnum.SERVER_ERROR);
            error.setMsg(String.format(error.getMsg(), e.getMessage()));
            return error;
        }
    }

}
