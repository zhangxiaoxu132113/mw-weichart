package com.water.wechat.framework.utils;

import org.apache.log4j.Logger;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.util.Properties;

/**
 * Created by zhangmiaojie on 2017/8/22.
 */
public class Constants {
    private static Logger logger = Logger.getLogger(Constants.class.getName());
    public static Properties props;

    public static String TOKEN;
    public static Integer WEB_PORT;
    public static String WX_APP_ID;
    public static String WX_APP_SECRET;
    public static String FILE_OUT_PATH; //文件
    public static  String WECHAT_CONTEXT;
    static {
        Resource resource = new ClassPathResource("/wf.config.properties");
        try {
            props = PropertiesLoaderUtils.loadProperties(resource);
            TOKEN = props.getProperty("token");
            WX_APP_ID = props.getProperty("wx.appid");
            WX_APP_SECRET = props.getProperty("wx.appsecret");
            FILE_OUT_PATH = props.getProperty("out.path");

            WEB_PORT = Integer.parseInt(props.getProperty("web.port"));

            WECHAT_CONTEXT=props.getProperty("wx.context");
        } catch (Exception e) {
            logger.error("load config.properties failure.", e);
        }
    }
}
