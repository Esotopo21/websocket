package it.burlac.webscoket.endpoint;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.burlac.webscoket.model.DelivererMessage;
import jdk.nashorn.internal.parser.JSONParser;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@ServerEndpoint("/wsdeliverer")
public class DeliverEndpoint {

    /**
     * Class that send messages from a ws client to another
     */

    private final static Map<String, RemoteEndpoint.Basic> clientsMap = new HashMap<>();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @OnOpen
    public void onOpen(Session session){
        System.out.println("Starting connection with id" + session.getId());
        sendMessage(session.getBasicRemote(), "Server: Connection started, your id is " + session.getId());
        clientsMap.put(session.getId(),session.getBasicRemote());
    }

    @OnMessage
    public void onMessage(String textMessage, Session session){
        DelivererMessage message = null;
        try {
            message = objectMapper.readValue(textMessage, DelivererMessage.class);
        } catch (JsonProcessingException e) {
            System.out.println("Error parsing message");
            e.printStackTrace();
        }
        String senderId = session.getId();
        String receveirId = message.getReceiverId();

        if(receveirId == null || receveirId.trim().length() == 0){
            sendMessage(session.getBasicRemote(), "Server: Impossibile inviare messaggio senza destinatario");
            return;
        }

        if(message.getMessage() == null || message.getMessage().trim().length() == 0 ){
           return;
        }

        System.out.println("Receveid a message from " + senderId + " for " + receveirId );

        RemoteEndpoint.Basic receiver = clientsMap.get(receveirId);

        if (receiver != null){
            sendMessage(receiver, senderId + ":" + message.getMessage());
        } else {
            System.out.println("Connection with id " + receveirId + "does not exist");
            sendMessage(session.getBasicRemote(), "No user with such id was found: " + receveirId);
        }
    }

    @OnClose
    public void onClose(Session session){
        sendMessage(session.getBasicRemote(), "Server: Connection closed");
        System.out.println("Closing connection with id " + session.getId());
        clientsMap.remove(session.getId());
    }

    private void sendMessage(RemoteEndpoint.Basic receiver, String message){
        try {
            receiver.sendText(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
