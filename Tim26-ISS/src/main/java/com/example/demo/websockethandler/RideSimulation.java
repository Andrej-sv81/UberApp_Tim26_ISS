package com.example.demo.websockethandler;

import com.example.demo.dto.ride.RideDTO;
import com.example.demo.model.Location;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import org.springframework.http.HttpHeaders;
import org.springframework.web.socket.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RideSimulation implements WebSocketHandler {
    public static final Map<String, WebSocketSession> driverSessions = new HashMap<>();
    public static final Map<String, WebSocketSession> passengerSessions = new HashMap<>();

    public static final Map<String, WebSocketSession> adminSessions = new HashMap<>();

    public static void addSession(WebSocketSession session) {
        HttpHeaders headers = session.getHandshakeHeaders();
        String id = headers.get("id").get(0);
        String role = headers.get("role").get(0);
        if (role.equals("DRIVER")) {
            driverSessions.put(id, session);
        } else if (role.equals("PASSENGER")) {
            passengerSessions.put(id, session);
        }else{
            adminSessions.put(id, session);
        }
    }
    public static void removeSession(WebSocketSession session) {
        HttpHeaders headers = session.getHandshakeHeaders();
        String id = headers.get("id").get(0);
        String role = headers.get("role").get(0);

        if (role.equals("DRIVER")) {
            driverSessions.remove(id);
        } else if (role.equals("PASSENGER")) {
            passengerSessions.remove(id);
        }else{
            adminSessions.remove(id);
        }
    }
    public static void notifyUsersAboutVehicleLocations(List<WebSocketSession> sessions, Location location){
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JSR310Module());
        try {
            TextMessage textMessage = new TextMessage(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(location));
            for(WebSocketSession webSocketSession:sessions){
                webSocketSession.sendMessage(textMessage);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {

    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {

    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
