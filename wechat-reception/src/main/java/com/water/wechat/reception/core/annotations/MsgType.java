package com.water.wechat.reception.core.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by zhangmiaojie on 2017/8/22.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface MsgType {
    String msgType() default "";
    String eventType() default "";
}
