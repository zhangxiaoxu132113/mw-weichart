package com.water.wechat.reception.web.controller;

import com.water.wechat.framework.utils.CheckSignatureUtil;
import com.water.wechat.framework.utils.MessageUtil;
import com.water.wechat.reception.core.WechatDispatcher;
import com.water.wechat.reception.core.redis.CacheManager;
import com.water.wechat.reception.utils.TimeHandler;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * 微信交互
 * Created by zhangmiaojie on 2017/8/22.
 */
public class WechatController {
    private Log logger = LogFactory.getLog(WechatController.class);
    public static final String NONCE = "nonce";
    public static final String ECHOSTR = "echostr";
    public static final String SIGNATURE = "signature";
    public static final String TIMESTAMP = "timestamp";

    @Resource
    private WechatDispatcher dispatcher;

    @Resource
    private CacheManager cacheManager;

    @RequestMapping(value = "/wechat", method = RequestMethod.GET)
    public void checkSignature(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = null;
        String nonce = request.getParameter(NONCE);
        String echostr = request.getParameter(ECHOSTR);
        String signature = request.getParameter(SIGNATURE);
        String timestamp = request.getParameter(TIMESTAMP);

        boolean isSuccess = CheckSignatureUtil.check(timestamp, nonce, signature);
        if (isSuccess) {
            logger.info("成功接入微信公众号！");
            out = response.getWriter();
            out.print(echostr);
        } else {
            logger.info("接入微信公众号失败！");
        }
    }

    @RequestMapping(value = "/wechat", method = RequestMethod.POST)
    public void handleMsg(HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("utf-8");
        try {
            Map<String, String> map = MessageUtil.parseXml(request);
            if (!checkIsFirstTimeSendMsg(map)) {//防止重复提交
                return;
            }
            String msgtype = map.get("MsgType");
            String eventType = map.get("EventType");
            String key = msgtype + "_" + eventType;
            String msgrsp = dispatcher.process(key, map);
            PrintWriter out = response.getWriter();
            out.print(msgrsp);
            out.close();
        } catch (Exception e) {
            logger.error(e, e);
        }
    }


    /**
     * 方法执行前，判断微信端是否第一次发送消息
     */
    private boolean checkIsFirstTimeSendMsg(Map<String, String> map) {
        String openId = map.get(MessageUtil.FROM_USER_NAME);
        String createTime = map.get(MessageUtil.CREATE_TIME);
        boolean isFirst = false;
        if (StringUtils.isNotBlank(openId) && StringUtils.isNotBlank(createTime)) {
            String flag = openId + createTime;
            if (StringUtils.isBlank(cacheManager.get(flag))) { //微信端第一次发送的消息
                cacheManager.set(flag, "1", TimeHandler.getSecondsByMinute(3));
                isFirst = true;
            }
        }

        return isFirst;
    }
}
