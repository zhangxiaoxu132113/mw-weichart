package com.water.weichart.utils;

import java.util.Arrays;
import org.apache.commons.lang3.StringUtils;
/**
 * Created by zhangmiaojie on 2017/7/4.
 */
public class WechatHelper {

    private WechatHelper() {
    }

    public static boolean checkSignature(String timestamp, String nonce, String signature) {
        if (StringUtils.isAnyBlank(timestamp, nonce, signature)) {
            return false;
        }
        String[] arr = new String[]{Constants.TOKEN, timestamp, nonce};
        Arrays.sort(arr);
        StringBuffer content = new StringBuffer();
        for (int i = 0; i < arr.length; i++) {
            content.append(arr[i]);
        }
        String temp = StringUtil.getSha1(content.toString());

        return temp.equals(signature);
    }
}
