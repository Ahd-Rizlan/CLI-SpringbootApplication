package com.ticketingSystem.TicketingSimulation.webSocketConfig;

import com.ticketingSystem.TicketingSimulation.entity.Vendor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;

import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WebSocketHandler extends TextWebSocketHandler {
    //Socket Connection Configuration Class
    private static final Logger logger = LoggerFactory.getLogger(WebSocketHandler.class);


    //in this list all the connections will be Stored and used to BroadCast the messages
    private static List<WebSocketSession> webSocketSessions = Collections.synchronizedList(new ArrayList<>());

    //the method will be called when a new connection is established
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        // Logging the connection ID with Connected Message
        System.out.println("Connection ID : " + session.getId() + " Connected");
        logger.info("Connection ID : {} Connected", session.getId());
        webSocketSessions.add(session);
    }

    //when the client is Disconnected the method will be called
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        System.out.println("Connection ID : " + session.getId() + " Disconnected");
        // Logging the connection ID with Disconnected Message
        logger.info("Connection ID : {} Disconnected", session.getId());
        webSocketSessions.remove(session);
    }


    public static void broadcastMessage(String message) {
        for (WebSocketSession webSocketSession : webSocketSessions) {
            try {
                webSocketSession.sendMessage(new TextMessage(message));
                //System.out.println("Message Sent to Connection ID : " + webSocketSession.getId());
                logger.info("Message Sent to Connection ID : {}", webSocketSession.getId());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public static void logAndBrodcastMessage(String message){
        logger.info(message);
        broadcastMessage(message);

    }

}