# 基于SpringBoot+Netty实现的服务端与客户端demo

先启动netty-ws-server，再启动netty-ws-client进行连接、断开

记录一套nginx转发socket配置，参考[nginx基础](https://meethigher.top/blog/2021/nginx/)

```conf
upstream server {
		server 127.0.0.1:8080;
		server 127.0.0.1:8081;
		server 127.0.0.1:8082;  
}
server {
        listen 13000;
        server_name localhost;
        location / {
             proxy_pass http://server;
        }
}
server {
        listen 13001;
        server_name localhost;
        location /ws/ {
             proxy_pass http://server;
        }
}
```

