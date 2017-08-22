package com.water.wechat.reception.core.handle.event;

import com.water.wechat.reception.core.annotations.MsgType;
import com.water.wechat.reception.core.handle.BaseHandler;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 扫描关注事件
 * Created by zhangmiaojie on 2017/7/4.
 */
@Service
@MsgType(msgType = "event", eventType = "scan")
public class ScanHandler extends BaseHandler {

    @Override
    public String handle(Map<String, String> map) {
        System.out.println("lalalalala");
        return null;
    }
}
