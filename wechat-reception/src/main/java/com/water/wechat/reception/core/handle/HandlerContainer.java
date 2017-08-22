package com.water.wechat.reception.core.handle;

import com.water.wechat.reception.core.annotations.MsgType;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Handler;

/**
 * Created by zhangmiaojie on 2017/8/22.
 */
public class HandlerContainer {

    public static Map<String, BaseHandler> handlerContainer = new HashMap<>();
    private static Log logger = LogFactory.getLog(HandlerContainer.class);

    static {
        initLoad(); //初始化容器
    }

    public static void initLoad() {
        logger.info("开始初始化Handler容器...");
        String path = "/com/water/wechat/reception/core/handle/";
        String rootPath = HandlerContainer.class.getResource("/").getPath();
        File file = new File(HandlerContainer.class.getResource(path).getPath());
        File rootPathFile = new File(rootPath);
        HandlerContainer.loopLoadHandle(file, rootPathFile.getAbsolutePath());
    }

    public static void loopLoadHandle(File file, String rootPath) {
        if (file.isDirectory()) {
            File[] fileList = file.listFiles();
            Arrays.asList(fileList).forEach(p -> loopLoadHandle(p, rootPath));
            return;
        }
        String fileName = file.getAbsoluteFile().getPath();
        if (fileName.lastIndexOf(".class") != -1) {
            fileName = fileName.substring(rootPath.length() + 1, fileName.length());
            fileName = fileName.replace("\\", ".");
            fileName = fileName.replace(".class", "");
            try {
                Class handleClass = Class.forName(fileName);
                Annotation[] annotations = handleClass.getAnnotations();
                for (Annotation annotation : annotations) {
                    if (annotation instanceof MsgType) {
                        logger.info(fileName);
                        String msgType = ((MsgType) annotation).msgType().toLowerCase();
                        String eventType = ((MsgType) annotation).eventType();
                        String key = msgType + "_" + eventType;
                        BaseHandler baseHandler = (BaseHandler) handleClass.newInstance();
                        handlerContainer.put(key, baseHandler);
                        return;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public static BaseHandler getHandler(String key) {
        if (StringUtils.isBlank(key)) {
            throw new RuntimeException("key不合法！");
        }

        return handlerContainer.get(key);
    }

    public static void main(String[] args) {
        HandlerContainer container = new HandlerContainer();
        container.initLoad();
        String key = "event1_scan";
        BaseHandler baseHandler = handlerContainer.get(key);
        if (baseHandler == null) {
            System.out.println("消息类型异常!");
        }
        baseHandler.handle(new HashMap<>());
    }
}
