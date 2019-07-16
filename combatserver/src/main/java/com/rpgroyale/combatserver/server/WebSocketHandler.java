package com.rpgroyale.combatserver.server;

import com.google.gson.Gson;
import com.rpgroyale.combatserver.messagedata.CombatData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@ServerEndpoint(value="/combatsocket")
public class WebSocketHandler extends AbstractWebSocketHandler implements HandshakeInterceptor {

    Map<String, WebSocketSession> clients = new ConcurrentHashMap<>();

    private static WebSocketHandler instance;

    public static WebSocketHandler Instance() {
        if (instance == null) {
            instance = new WebSocketHandler();
        }
        return instance;
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        log.info("New text message received from " + session.getId());
        Gson gson = new Gson();
        CombatData combatData = gson.fromJson(message.getPayload(),CombatData.class);
        log.info("checking for correct parse: " + combatData.getPlayerId());
        if (!clients.containsValue(session)) {
            clients.put(combatData.getPlayerId(), session);
            log.info("added client " + combatData.getPlayerId() + " to the session list.");
        }
        broadcastCombatData(combatData);
    }

    @Override
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws IOException {
        log.info("New binary message received");
        session.sendMessage(message);
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

    public void broadcastCombatData(CombatData combatData) {
        try {
            log.info("Client size: " + clients.size());
            for (WebSocketSession session : clients.values()) {
                log.info("sending message to client through broadcast");
                Gson gson = new Gson();
                WebSocketMessage<TextMessage> message = new WebSocketMessage<TextMessage>() {
                    @Override
                    public TextMessage getPayload() {
                        TextMessage textMessage = new TextMessage(gson.toJson(combatData), true);
                        return textMessage;
                    }

                    @Override
                    public int getPayloadLength() {
                        return 0;
                    }

                    @Override
                    public boolean isLast() {
                        return false;
                    }
                };
                session.sendMessage(message.getPayload());

            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
