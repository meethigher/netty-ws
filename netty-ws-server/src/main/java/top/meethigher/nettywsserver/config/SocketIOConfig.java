package top.meethigher.nettywsserver.config;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketConfig;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.SpringAnnotationScanner;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import top.meethigher.nettywsserver.listener.AuthListener;

import javax.annotation.Resource;

/**
 * 配置SocketIO
 *
 * @author chenchuancheng
 * @since 2023/1/13 10:04
 */
@org.springframework.context.annotation.Configuration
@EnableConfigurationProperties(WsConfig.class)
public class SocketIOConfig {

    @Resource
    private AuthListener authListener;


    @Bean(initMethod = "start", destroyMethod = "stop")
    public SocketIOServer socketIOServer(WsConfig wsConfig) {
        Configuration config = new Configuration();
        config.setAuthorizationListener(authListener);
        config.setHostname(wsConfig.getHost());
        config.setPort(wsConfig.getPort());
        //配置最大传输大小,10M
        config.setMaxFramePayloadLength(10 * 1024 * 1024);
        config.setMaxHttpContentLength(10 * 1024 * 1024);
        //默认是/socket.io
        config.setContext("/ws");
        //在TCP连接中，socket在重启时断开连接要经过四次握手，这时处于TIME_WAIT状态，所以会占用端口一段时间，大概两到三分钟，所以才会反复重启三四分钟后才会成功
        //将socket配置信息设置SO_REUSEADDR关键字为true。这个套接字选项通知内核，如果端口忙，但TCP状态位于 TIME_WAIT ，可以重用端口。
        SocketConfig socketConfig = new SocketConfig();
        socketConfig.setReuseAddress(true);
        config.setSocketConfig(socketConfig);
        return new SocketIOServer(config);
    }

    @Bean
    public SpringAnnotationScanner springAnnotationScanner(SocketIOServer server) {
        return new SpringAnnotationScanner(server);
    }
}
