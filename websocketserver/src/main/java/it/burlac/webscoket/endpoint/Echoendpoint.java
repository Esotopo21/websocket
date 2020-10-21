package it.burlac.webscoket.endpoint;


import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

/**
 * Class that send message back to ws client
 */

@ServerEndpoint("/wsecho")
public class Echoendpoint {

    @OnOpen
    public void onOpen(Session session){
        System.out.println("Initializing ws session ... ");
        try{
            session.getBasicRemote().sendText("Connection started with remote host id " + session.getId());
        }catch (IOException e){
            System.out.println("Error starting connection");
            e.printStackTrace();
        }
    }

    @OnMessage
    public void onMessage(String message, Session session){
        System.out.println("Message receveid from " + session.getId() + ":");
        System.out.println(message);
        StringBuilder sb = new StringBuilder();
        sb.append("Message length: ").append(message.length()).append("<br>");
        sb.append("Sender: ").append(session.getId()).append("<br>");
        sb.append("Content: ").append(message);
        try{
            session.getBasicRemote().sendText(sb.toString());
        }catch (IOException e){
            System.out.println("Error while echoing message from remote host with id " + session.getId());
            e.printStackTrace();
        }
    }

    @OnClose
    public void onClose(Session session){
        System.out.println("Closing connection with id " + session.getId());
    }

}
