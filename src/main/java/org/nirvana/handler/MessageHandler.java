package org.nirvana.handler;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import lombok.extern.slf4j.Slf4j;
import org.nirvana.entity.ChatObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Slf4j
@Component
public class MessageHandler {

    @Autowired
    private SocketIOServer socketIOServer;

    private final ConcurrentMap<String, SocketIOClient> clientMaps = new ConcurrentHashMap<>();

    @OnConnect
    public void onConnect(SocketIOClient client) {
        log.info("new-client-connect, client: {}", client);
        Map<String, List<String>> urlParams = client.getHandshakeData().getUrlParams();

        log.info("urlParams: {}", urlParams);

//        clientMaps.put(mac, client);
//        client.sendEvent("message", "connect-back");
    }

    @OnDisconnect
    public void onDisConnect(SocketIOClient client) {
        log.info("client-dis-connect, 客户端断开链接");
    }

    @OnEvent(value = "chatevent")
    public void onEvent(SocketIOClient client, AckRequest request, ChatObject data) {
        log.info("message: {}, request: {}", data, request);
        client.sendEvent("message", "send-message");
    }

}
