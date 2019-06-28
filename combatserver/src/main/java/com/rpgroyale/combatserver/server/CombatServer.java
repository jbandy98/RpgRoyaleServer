package com.rpgroyale.combatserver.server;

import com.rpgroyale.combatserver.components.Message;
import com.rpgroyale.combatserver.components.MessageDecoder;
import com.rpgroyale.combatserver.components.MessageEncoder;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint(value="/combat/{playerId}",
    decoders = MessageDecoder.class,
    encoders = MessageEncoder.class)
public class CombatServer {

    private Session session;
    private static Set<CombatServer> clients = new CopyOnWriteArraySet<>();
    private static HashMap<String, String> users = new HashMap<>();

    @OnOpen
    public void OnOpen(Session session, @PathParam("playerId") String playerId) throws IOException, EncodeException {
        // Get session and websocket connection
        this.session = session;
        clients.add(this);
        users.put(session.getId(), playerId);

        Message message = new Message();
        message.setFrom(playerId);
        message.setContent("Connected!");
        broadcast(message);
    }

    @OnMessage
    public void onMessage(Session session, Message message) throws IOException, EncodeException {
        // Handle new messages
        message.setFrom(users.get(session.getId()));
        broadcast(message);
    }

    @OnClose
    public void onClose(Session session) throws IOException, EncodeException {
        // websocket connection closes
        clients.remove(this);
        Message message = new Message();
        message.setFrom(users.get(session.getId()));
        message.setContent("Disconnected!");
        broadcast(message);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        // Do error handling here
    }

    private static void broadcast(Message message) throws IOException, EncodeException {
        clients.forEach(client -> {
            synchronized (client) {
                try {
                    client.session.getBasicRemote().sendObject(message);

                } catch (IOException | EncodeException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
