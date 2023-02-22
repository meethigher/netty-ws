package top.meethigher.nettywsserver.config;

import com.corundumstudio.socketio.Configuration;
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
        config.setMaxFramePayloadLength(1024 * 1024);
        config.setMaxHttpContentLength(1024 * 1024);
        //默认是/socket.io
        config.setContext("/ws");
        return new SocketIOServer(config);
    }

    @Bean
    public SpringAnnotationScanner springAnnotationScanner(SocketIOServer server) {
        return new SpringAnnotationScanner(server);
    }
}
