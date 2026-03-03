package com.dispatchsim.dispatchsim.websocket;


import com.dispatchsim.dispatchsim.websocket.dto.SimulationEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventPublisher {

    private final SimpMessagingTemplate messagingTemplate;

    public void publish(SimulationEvent event) {
        messagingTemplate.convertAndSend("/topic/simulation", event);
    }
}