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
> 通过这个容器，我就可以找到每条消息对应到的处理的handler类

* ScanHandler 处理扫码事件的句柄

```java
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
```

> 通过注解@MsgType，定义该handler是处理哪种消息的处理类
> 项目在启动的时候，就是去扫描所有的handler，加载到HandlerContainer容器中

* WechatDispatcher 最后由WechatDispatcher分发任务

```java
    String msgtype = map.get("MsgType");
    String eventType = map.get("EventType");
    String key = msgtype + "_" + eventType;
    String msgrsp = dispatcher.process(key, map);
```

```java
@Service
    public class WechatDispatcher {
     public String process(String key, Map<String, String> data) {
         BaseHandler baseHandler = HandlerContainer.getHandler(key);
         return baseHandler.handle(data);
     }
    }
```

#### 总结

> 这样写法最明显的好处就是代码解耦，当微信消息发送服务端，在controller的处理上，根本就不需要知道具体是
> 哪一个handler处理微信的消息，而且在WechatDispatcher这个分发任务的类根据就看不到实现类，他只面向了一个抽象类
> 具体处理的类只有容器知道。
> 如果不这么写的话，就很有可能是下面的代码那种写法了,每一个handler都暴露出去了

```java
    public class EventDispatcher {
        private static Logger logger = Logger.getLogger(EventDispatcher.class);
        @Resource
        private SubscribeHandler subscribeHandler;
        @Resource
        private UnsubscribeHandler unsubscribeHandler;
        @Resource
        private MenuClickHandler menuClickHandler;
        @Resource
        private MenuViewHandler menuViewHandler;
        @Autowired
        private ScanHandler scanHandler;
        public String processEvent(Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {
            String msg = null;
            if (map.get("Event").equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) { // 关注事件
                msg = subscribeHandler.handle(map);
            } else if (map.get("Event").equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) { // 取消关注事件
                msg = unsubscribeHandler.handle(map);
            } else if (map.get("Event").equals(MessageUtil.EVENT_TYPE_SCAN)) { // 扫描二维码事件
                msg = scanHandler.handle(map);
            } else if (map.get("Event").equals(MessageUtil.EVENT_TYPE_LOCATION)) { // 位置上报事件
            } else if (map.get("Event").equals(MessageUtil.EVENT_TYPE_CLICK)) { // 自定义菜单点击事件
                return menuClickHandler.handle(map);
            } else if (map.get("Event").equals(MessageUtil.EVENT_TYPE_VIEW)) { // 自定义菜单View事件
                menuViewHandler.handle(map);
            }
            return msg;
        }
    }
```

> 设计思想由来，这种做法跟spring ioc容器的思想很像，spring mvc 说白了，其最基本的思路就是将url和处理的类，方法作为一个key-value
> 保存到一个容器中，然后每一次请求都是从url找到对应处理的类。