/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.message.consumer;

import jakarta.ejb.MessageDriven;
import jakarta.ejb.ActivationConfigProperty;
import jakarta.jms.Message;
import jakarta.jms.MessageListener;
import jakarta.jms.TextMessage;
import jakarta.jms.JMSException;

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

    @Override
    public void onMessage(Message message) {

        try {

            if (message instanceof TextMessage textMessage) {

                String receivedMessage = textMessage.getText();

                System.out.println(
                    "GlassFish JMS: Message received from producer."
                );

                System.out.println(
                    "Consumer: " + receivedMessage
                );

                System.out.println(
                    "Consumer: Message processed successfully."
                );

            }

        } catch (JMSException e) {

            e.printStackTrace();

        }

    }
}