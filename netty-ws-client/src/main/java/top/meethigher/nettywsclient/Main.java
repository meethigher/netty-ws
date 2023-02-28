package top.meethigher.nettywsclient;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

import java.util.Date;
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
        options.path = "/ws";
        //websocket协议
        options.transports = new String[]{"websocket"};
        //失败重试次数，默认是int最大值
        //options.reconnectionAttempts = 3;
        //失败重连的时间间隔(ms)，默认是1秒
        options.reconnectionDelay = 5000;
        //连接超时时间(ms)，默认是20秒
        options.timeout = 5000;
        //是否开启新连接(与同ip、同端口的服务建立链接时，是否复用已有链接)，默认是否。改为true可以用于压测
        options.forceNew = true;

        Socket socket = null;
        try {
            socket = IO.socket(url, options);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (socket != null) {
            socket.on(Socket.EVENT_CONNECT, objects -> {
                System.out.println("连接成功" + new Date());
            });
            socket.on(Socket.EVENT_ERROR, objects -> {
                System.out.println("连接失败" + new Date());
            });
            socket.on(Socket.EVENT_RECONNECT_ATTEMPT, objects -> {
                System.out.println("尝试重连" + new Date());
            });
            socket.on(Socket.EVENT_CONNECT_TIMEOUT, objects -> {
                System.out.println("连接超时" + new Date());
            });
            socket.on(Socket.EVENT_MESSAGE, o -> {
                System.out.println("收到消息：" + o[0]);
            });
        }
        return socket;
    }

    public static void main(String[] args) throws Exception {
        Socket socket = connect(template);
        socket.connect();

        while (true) {
            if (socket.connected()) {
                String str = "我来开会了";
                System.out.println("发送消息：" + str);
                //socket.emit(Socket.EVENT_MESSAGE, str);
                socket.send(str);
            }
            Thread.sleep(2000L);
        }
    }
}
