# Java Messaging Service (JMS) — Producer & Consumer Demo

A lightweight Jakarta EE application demonstrating asynchronous, decoupled communication using **Java Message Service (JMS 2.0+)** on **GlassFish / Payara**.

This project implements a classic **Point-to-Point (Queue)** messaging pattern where a Stateless EJB sends text messages to a JMS Queue, and a Message-Driven Bean (MDB) automatically listens for and processes those messages in the background.

---

## Architecture & Message Flow

1. **Producer (`UserEventProducer`)** injects a `JMSContext` and a `Queue` resource (`jms/UserEventsQueue`).
2. When `sendMessage(String message)` is called, the producer publishes the payload to the queue and returns immediately without waiting for processing.
3. **Message Broker (GlassFish JMS)** holds the message in `jms/UserEventsQueue` until a consumer is ready.
4. **Consumer (`UserEventConsumer`)**, annotated with `@MessageDriven`, is automatically invoked by the Jakarta EE container via `onMessage(Message message)` to read and log the incoming `TextMessage`.

---

## Key Components

### 1. `com.message.producer.UserEventProducer`
* **Type:** `@Stateless` Session Bean
* **Role:** Message Producer
* **Highlights:**
  * Uses `@Inject private JMSContext jmsContext;` to eliminate legacy JMS 1.1 boilerplate (`Connection`, `Session`, `MessageProducer` management).
  * Uses `@Resource(lookup = "jms/UserEventsQueue")` to look up the target destination from the server's JNDI registry.
  * Publishes messages using the fluent `jmsContext.createProducer().send(userEventsQueue, message)` API.

### 2. `com.message.consumer.UserEventConsumer`
* **Type:** `@MessageDriven` Bean (MDB) implementing `MessageListener`
* **Role:** Asynchronous Message Consumer
* **Highlights:**
  * Configured via `@ActivationConfigProperty` to listen specifically to `jms/UserEventsQueue` (`jakarta.jms.Queue`).
  * Uses Java pattern matching (`if (message instanceof TextMessage textMessage)`) to safely extract and process the text payload.

---

## Tech Stack

* **Java** (Jakarta EE / EJB)
* **JMS 2.0+** (`jakarta.jms.*`)
* **Application Server:** GlassFish (or Payara)
* **IDE:** NetBeans

---

## Prerequisites & Setup

1. **Application Server:** Ensure GlassFish (or Payara) is installed and running.
2. **JMS Resource Configuration:** Create a JMS Destination Resource in your server's Admin Console:
   * **JNDI Name:** `jms/UserEventsQueue`
   * **Resource Type:** `jakarta.jms.Queue`
3. **Build & Deploy:** Build the project in NetBeans (or via Maven/Gradle) and deploy the `.war` / `.ear` archive to your GlassFish server.
4. **Verify:** Trigger `UserEventProducer.sendMessage("Your test message")` (e.g., from a REST endpoint or Servlet) and check the GlassFish server logs for the producer and consumer output.
