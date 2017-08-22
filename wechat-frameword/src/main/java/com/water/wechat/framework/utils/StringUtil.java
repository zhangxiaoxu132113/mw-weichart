package com.water.wechat.framework.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.UUID;

/**
 * Created by zhangmiaojie on 2017/7/4.
 */
public class StringUtil {

    private StringUtil(){
    }

    public static String getSha1(String str) {
        if (null == str || 0 == str.length()){
            return null;
        }
        char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
            mdTemp.update(str.getBytes("UTF-8"));

            int k = 0;
            byte[] md = mdTemp.digest();
            int j = md.length;
            char[] buf = new char[j * 2];

            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
                buf[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(buf);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * URL编码（utf-8）
     *
     * @param source
     * @return
     */
    public static String urlEncodeUTF8(String source) {
        String result = source;
        try {
            result = java.net.URLEncoder.encode(source, "utf-8");
            System.out.println();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String getUUID() {
        return UUID.randomUUID().toString();
    }
    //字符串合并方法，返回一个合并后的字符串
    public static String format(String str,Object ... args)
    {

        //这里用于验证数据有效性
        if(str==null||"".equals(str))
            return "";
        if(args.length==0)
        {
            return str;
        }


        String result=str;

        //这里的作用是只匹配{}里面是数字的子字符串
        java.util.regex.Pattern p = java.util.regex.Pattern.compile("\\{(\\d+)\\}");
        java.util.regex.Matcher m = p.matcher(str);

        while(m.find())
        {
            //获取{}里面的数字作为匹配组的下标取值
            int index=Integer.parseInt(m.group(1));

            //这里得考虑数组越界问题，{1000}也能取到值么？？
            if(index<args.length)
            {

                //替换，以{}数字为下标，在参数数组中取值
                result=result.replace(m.group(),args[index].toString());
            }
        }
        return result;
    }
}
