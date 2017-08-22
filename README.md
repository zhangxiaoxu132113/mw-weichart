"# mw-weichart"
## 微信项目
该项目分为两个模块：

1.  一个模块(wechat-framework)负责提供微信接口文档的相关操作
2.  另一个模块(wechat-reception)负责具体业务的处理

#### wechat-reception 如何处理微信的消息

* HandlerContainer 是一个存放消息和对应的处理类的容器

```java
    ...
    public static Map<String, BaseHandler> handlerContainer = new HashMap<>();
    public static void initLoad() {
        logger.info("开始初始化Handler容器...");
    }
    ...
```


    > handlerContainer的key是msgType_eventType组成的字符串，而value是一个实际处理微信服务的类，BaseHandler是一个抽象类


