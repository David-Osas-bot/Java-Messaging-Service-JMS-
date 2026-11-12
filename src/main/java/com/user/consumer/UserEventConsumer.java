package com.user.consumer;

import com.user.RequestDTO.NotificationRequestDTO;
import com.user.Service.NotificationService;

import jakarta.ejb.MessageDriven;
import jakarta.ejb.ActivationConfigProperty;
import jakarta.inject.Inject;
import jakarta.jms.Message;
import jakarta.jms.MessageListener;
import jakarta.jms.TextMessage;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbException;

@MessageDriven(
        activationConfig = {
            @ActivationConfigProperty(
                    propertyName = "destinationLookup",
                    propertyValue = "jms/UserEventsQueue"
            ),
            @ActivationConfigProperty(
                    propertyName = "destinationType",
                    propertyValue = "jakarta.jms.Queue"
            )
        }
)
public class UserEventConsumer implements MessageListener {

    @Inject
    private NotificationService notificationService;

    @Override
    public void onMessage(Message message) {

        try {

            if (message instanceof TextMessage textMessage) {

                String receivedMessage = textMessage.getText();

                System.out.println(
                        "GlassFish JMS: Message received from producer."
                );

                try (Jsonb jsonb = JsonbBuilder.create()) {

                    NotificationRequestDTO event
                            = jsonb.fromJson(
                                    receivedMessage,
                                    NotificationRequestDTO.class
                            );

                    notificationService.saveNotification(event);

                    System.out.println(
                            "Consumer: Notification saved successfully."
                    );
                }

            } else {
                throw new IllegalArgumentException(
                        "Unsupported JMS message type"
                );
            }

        } catch (Exception e) {

            e.printStackTrace();

            throw new IllegalStateException(
                    "Failed to process notification message", e
            );
        }
    }
}
