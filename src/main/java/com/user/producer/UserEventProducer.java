package com.user.producer;

import com.user.RequestDTO.NotificationRequestDTO;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.jms.JMSContext;
import jakarta.jms.Queue;
import jakarta.annotation.Resource;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;

@Stateless
public class UserEventProducer {

    @Inject
    private JMSContext jmsContext;

    @Resource(lookup = "jms/UserEventsQueue")
    private Queue userEventsQueue;

    public void sendEvent(NotificationRequestDTO event) {

        try (Jsonb jsonb = JsonbBuilder.create()) {

            String message = jsonb.toJson(event);

            System.out.println("Producer: Sending event...");

            jmsContext.createProducer()
                    .send(userEventsQueue, message);

            System.out.println("Producer: Event sent successfully.");

        } catch (Exception e) {

            throw new IllegalStateException(
                    "Unable to publish user event", e
            );
        }
    }
}
