package com.rpgroyale.combatserver.server;

import com.rpgroyale.combatserver.components.Message;
import com.rpgroyale.combatserver.components.MessageDecoder;
import com.rpgroyale.combatserver.components.MessageEncoder;
import lombok.extern.slf4j.Slf4j;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@Slf4j
@ServerEndpoint(value="/",
    decoders = MessageDecoder.class,
    encoders = MessageEncoder.class)
public class CombatServer {

    private Session session;
    private static Set<CombatServer> clients = new CopyOnWriteArraySet<>();
//    private static HashMap<String, String> users = new HashMap<>();

    @OnOpen
    public void OnOpen(Session session) throws IOException, EncodeException {
        log.info("running onOpen method for socket server");
        // Get session and websocket connection
        this.session = session;
        clients.add(this);
//        users.put(session.getId());

        Message message = new Message();
        message.setFrom("guest");
        message.setContent("Connected!");
        broadcast(message);
    }

    @OnMessage
    public void onMessage(Session session, Message message) throws IOException, EncodeException {
        log.info("running onMessage method for socket server");
        // Handle new messages
//        message.setFrom(users.get(session.getId()));
        broadcast(message);
    }

    @OnClose
    public void onClose(Session session) throws IOException, EncodeException {
        log.info("running onClose method for socket server");
        // websocket connection closes
        clients.remove(this);
        Message message = new Message();
//        message.setFrom(users.get(session.getId()));
        message.setContent("Disconnected!");
        broadcast(message);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        // Do error handling here
    }

    private static void broadcast(Message message) throws IOException, EncodeException {
        log.info("running broadcast on the socket server");
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
