package com.water.weichart.web.dto;

import java.io.Serializable;

/**
 * Created by zhangmiaojie on 2017/7/4.
 */
public class BaseMessage implements Serializable {
    private long CreateTime;
    private String MsgType;
    private String ToUserName;
    private String FromUserName;

    public long getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(long createTime) {
        CreateTime = createTime;
    }

    public String getMsgType() {
        return MsgType;
    }

    public void setMsgType(String msgType) {
        MsgType = msgType;
    }

    public String getToUserName() {
        return ToUserName;
    }

    public void setToUserName(String toUserName) {
        ToUserName = toUserName;
    }

    public String getFromUserName() {
        return FromUserName;
    }

    public void setFromUserName(String fromUserName) {
        FromUserName = fromUserName;
    }
}
