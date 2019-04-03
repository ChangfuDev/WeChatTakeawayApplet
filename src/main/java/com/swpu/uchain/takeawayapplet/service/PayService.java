package com.swpu.uchain.takeawayapplet.service;

import com.swpu.uchain.takeawayapplet.VO.ResultVO;
import com.swpu.uchain.takeawayapplet.form.PayForm;
import com.swpu.uchain.takeawayapplet.form.RefundForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface PayService {

    ResultVO creat(PayForm payForm, String code,HttpServletRequest request);

    ResultVO notify(HttpServletRequest request, HttpServletResponse response) throws IOException, Exception;

    ResultVO refund(RefundForm refundForm);

}
