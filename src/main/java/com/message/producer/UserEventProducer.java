/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.message.producer;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.jms.JMSContext;
import jakarta.jms.Queue;
import jakarta.annotation.Resource;

@Stateless
public class UserEventProducer {

    @Inject
    private JMSContext jmsContext;

    @Resource(lookup = "jms/UserEventsQueue")
    private Queue userEventsQueue;

    public void sendMessage(String message) {

        System.out.println("Producer: Sending message...");

        jmsContext.createProducer()
                  .send(userEventsQueue, message);

    }
}