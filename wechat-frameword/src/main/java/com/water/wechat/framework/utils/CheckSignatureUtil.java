package com.water.wechat.framework.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Date;

/**
 * Created by zhangmiaojie on 2017/7/4.
 */
public final class CheckSignatureUtil {
    public final static String MsgTextXml = "<xml> <ToUserName><![CDATA[{0}]]></ToUserName>" +
            " <FromUserName><![CDATA[{1}]]></FromUserName> " +
            "  <CreateTime>{2}</CreateTime>" +
            " <MsgType><![CDATA[text]]></MsgType>" +
            "<Content><![CDATA[{3}]]></Content> " +
            " </xml>";

    private CheckSignatureUtil() {
    }

    public static boolean check(String timestamp, String nonce, String signature) {
        if (StringUtils.isAnyBlank(timestamp, nonce, signature)) {
            return false;
        }
        String[] arr = new String[]{Constants.TOKEN, timestamp, nonce};
        Arrays.sort(arr);
        StringBuilder content = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            content.append(arr[i]);
        }
        String temp = StringUtil.getSha1(content.toString());

        return temp.equals(signature);
    }
}
