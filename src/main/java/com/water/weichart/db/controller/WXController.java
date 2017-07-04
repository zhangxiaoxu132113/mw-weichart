package com.water.weichart.db.controller;

import com.water.weichart.utils.Constants;
import com.water.weichart.utils.WechatHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by zhangmiaojie on 2017/7/4.
 */
@Controller
public class WXController {
    private Log log = LogFactory.getLog(WXController.class);

    @RequestMapping(value = "/wechat", method = RequestMethod.GET)
    public void checkSignature(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = null;
        String nonce = request.getParameter(Constants.NONCE);
        String echostr = request.getParameter(Constants.ECHOSTR);
        String signature = request.getParameter(Constants.SIGNATURE);
        String timestamp = request.getParameter(Constants.TIMESTAMP);

        boolean isSuccess = WechatHelper.checkSignature(timestamp, nonce, signature);
        if (isSuccess) {
            log.info("成功接入微信公众号！");
            out = response.getWriter();
            out.print(echostr);
        } else {
            log.info("接入微信公众号失败！");
        }


    }

    @RequestMapping(value = "/wechat", method = RequestMethod.POST)
    public void handleMsg(HttpServletRequest request, HttpServletResponse response) {

    }



}
