package top.meethigher.nettywsserver.listener;

import com.corundumstudio.socketio.AuthorizationListener;
import com.corundumstudio.socketio.HandshakeData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class AuthListener implements AuthorizationListener {

    private final static Logger log = LoggerFactory.getLogger(AuthListener.class);

    @Override
    public boolean isAuthorized(HandshakeData data) {
        try {
            String userId = data.getUrlParams().get("auth").get(0);
            if (userId.equals("NDk5YjQ1ZTItYmY0Yy00MzdiLWI2ODktYjAxMmNjZTMxMWY0")) {
                log.info("认证成功");
                return true;
            }
        } catch (Exception ignore) {
        }
        log.error("认证失败");
        return false;
    }
}
