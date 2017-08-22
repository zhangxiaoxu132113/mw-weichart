package com.water.wechat.reception.core;

import com.water.wechat.reception.core.handle.BaseHandler;
import com.water.wechat.reception.core.handle.HandlerContainer;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by zhangmiaojie on 2017/8/22.
 */
@Service
public class WechatDispatcher {

    public String process(String key, Map<String, String> data) {
        BaseHandler baseHandler = HandlerContainer.getHandler(key);
        return baseHandler.handle(data);
    }
}
