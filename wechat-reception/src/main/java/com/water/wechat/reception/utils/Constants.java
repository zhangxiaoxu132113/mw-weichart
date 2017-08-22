package com.water.wechat.reception.utils;

/**
 * Created by zhangmiaojie on 2017/8/22.
 */
public final class Constants {

    /**
     * 微信消息类型
     */
    public static enum WechatMsType {
        EVENT("event", "事件"),
        text("text", "文本消息"),
        image("image", "图片消息"),
        voice("voice", "语音消息"),
        video("video", "视频消息"),
        shortvideo("shortvideo", "小视频消息"),
        location("location", "地理位置消息");

        public String key;
        private String value;

        private WechatMsType(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public static String getValue(String key) {
            for (WechatMsType enumType : WechatMsType.values()) {
                if (enumType.key.equals(key)) {
                    return enumType.getValue();
                }
            }
            return null;
        }

    }

    /**
     * 微信日志类型
     */
    public static enum WechatEventKey {
        subscribe("subscribe", "关注"),
        unsubscribe("unsubscribe", "取消关注"),
        scan("scan", "扫码带参二维码(已关注)"),
        sub_qr("sub_qr", "扫码带参二维码(未关注)"),
        location("location", "上报地理位置"),
        click("click", "点击菜单拉取消息"),
        view("view", "点击菜单跳转url");

        public String type;
        private String desc;

        private WechatEventKey(String value, String desc) {
            this.type = value;
            this.desc = desc;
        }

        public String getDesc() {
            return desc;
        }

        public static String getDesc(String type) {
            for (WechatEventKey enumType : WechatEventKey.values()) {
                if (enumType.type.equals(type)) {
                    return enumType.getDesc();
                }
            }
            return null;
        }

    }
}
