package top.meethigher.nettywsclient;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

import java.util.concurrent.atomic.AtomicReference;


/**
 * 模拟websocket收发
 *
 * @author chenchuancheng
 * @since 2023/1/13 11:28
 */
public class Main {

    private static final String host = "127.0.0.1";

    private static final Integer port = 5555;

    /**
     * userId是参数，根据服务端配置
     */
    private static final String template = String.format("ws://%s:%s?auth=NDk5YjQ1ZTItYmY0Yy00MzdiLWI2ODktYjAxMmNjZTMxMWY0", host, port);


    public static Socket connect(String url) {
        IO.Options options = new IO.Options();
        //配置路径，该路径与服务端的context一致
        options.path="/ws";
        //websocket协议
        options.transports = new String[]{"websocket"};
        //失败重试次数
        options.reconnectionAttempts = 3;
        //失败重连的时间间隔
        options.reconnectionDelay = 1000;
        //连接超时时间(ms)
        options.timeout = 500;
        Socket socket = null;
        try {
            socket = IO.socket(url, options);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return socket;
    }

    public static void main(String[] args) {
        Socket socket = connect(template);
        AtomicReference<String> message = new AtomicReference<>("");
        socket.on(Socket.EVENT_CONNECT, objects -> {
            System.out.println("连接成功");
            System.out.println(socket.connected());
        });
        socket.on(Socket.EVENT_ERROR, objects -> {
            System.out.println("连接失败");
            System.exit(0);
        });
        //监听消息
        socket.on("morning-meeting", args1 -> message.set((String) args1[0]));
        socket.connect();
        //发送消息
        socket.emit("morning-meeting", "我来开会了");
        while (true) {
            if (message.get() != null && message.get().equals("会议结束")) {
                System.out.println("结束");
                break;
            }
        }
        socket.disconnect();
        socket.close();
        System.exit(0);
    }
}
