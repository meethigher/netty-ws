package top.meethigher.nettywsserver.listener;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import top.meethigher.nettywsserver.config.WsEvent;

/**
 * SocketIO事件监听
 * 三个常用注解@OnConnect、@OnDisconnect、@OnEvent
 *
 * @author chenchuancheng
 * @since 2023/1/13 10:14
 */
@Component
public class SocketIOEventListener {


    private final static Logger log = LoggerFactory.getLogger(SocketIOEventListener.class);


    /**
     * 客户端连接成功
     *
     * @param client 客户端
     */
    @OnConnect
    public void onConnect(SocketIOClient client) {
        String sessionId = client.getSessionId().toString();
//        String userId = client.getHandshakeData().getUrlParams().get("userId").get(0);
        log.info("sessionId={}连接ws", sessionId);
    }

    /**
     * 客户端断开连接
     *
     * @param client 客户端
     */
    @OnDisconnect
    public void onDisconnect(SocketIOClient client) {
        String sessionId = client.getSessionId().toString();
        log.info("sessionId={}断开ws", sessionId);
    }

    /**
     * 监听事件WsEvent.MORNING_MEETING
     *
     * @param client 客户端
     */
    @OnEvent(WsEvent.MORNING_MEETING)
    public void morningMeeting(SocketIOClient client, AckRequest request, Object o) {
        String str = "会议结束";
        log.info("获取消息:{},回复消息:{}", o, str);
        client.sendEvent(WsEvent.MORNING_MEETING, str);
    }

    /**
     * 监听事件WsEvent.MORNING_MEETING
     *
     * @param client 客户端
     */
    @OnEvent(WsEvent.EVENING_MEETING)
    public void eveningMeeting(SocketIOClient client, AckRequest request, Object o) {
        log.info("获取信息：" + o);
        client.sendEvent(WsEvent.EVENING_MEETING, "会议结束");
    }


}
