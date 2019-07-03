package com.rpgroyale.combatserver.server;

import com.google.gson.Gson;
import com.rpgroyale.combatserver.messagedata.CombatData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@ServerEndpoint(value="/combatsocket")
public class WebSocketHandler extends AbstractWebSocketHandler implements HandshakeInterceptor {

    Map<String, Session> clients = new ConcurrentHashMap<>();

    private static WebSocketHandler instance;

    public static WebSocketHandler Instance() {
        if (instance == null) {
            return new WebSocketHandler();
        }
        return instance;
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        log.info("New text message received from " + session.getId());
        Gson gson = new Gson();
        CombatData combatData = gson.fromJson(message.getPayload(),CombatData.class);
        log.info("checking for correct parse: " + combatData.getPlayerData().getPlayerId());

    }

    @Override
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws IOException {
        log.info("New binary message received");
        session.sendMessage(message);
    }

    @OnOpen
    public void onOpen(Session session) {
        clients.put("1", session);
        log.info("added " + "1" + " to client list.");
    }

    @Override
    public boolean beforeHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, org.springframework.web.socket.WebSocketHandler webSocketHandler, Map<String, Object> map) throws Exception {
        log.info("before handshake");

        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, org.springframework.web.socket.WebSocketHandler webSocketHandler, Exception e) {

        log.info("after handshake");
    }
}
