package main;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**

 * Project: Lab 4
 * Purpose Details: Pizza Shop
 * Course: IST 242
 * Author: Matthew Sulpizio
 * Date Developed: 3/20/2026
 * Last Date Changed: 3/31/2026
 * Rev:

 */

public class RabbitMQSender {

    /**
     * Default constructor for RabbitMQSender.
     */
    public RabbitMQSender() {
    }

    /**
     * The RabbitMQ queue name for flat-file messages.
     */
    private static final String FLAT_QUEUE = "pizza_flat_queue";

    /**
     * The RabbitMQ queue name for JSON messages.
     */
    private static final String JSON_QUEUE = "pizza_json_queue";

    /**
     * Main method that sends both flat-file and JSON Pizza messages.
     *
     * @param args Command-line arguments.
     * @throws Exception If a RabbitMQ or connection error occurs.
     */
    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        Pizza flatPizza = new Pizza("Garlic", "Large", "Pepperoni", 21.99);
        Pizza jsonPizza = new Pizza("Garlic", "Large", "Pepperoni", 21.99);

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            channel.queueDeclare(FLAT_QUEUE, false, false, false, null);
            channel.queueDeclare(JSON_QUEUE, false, false, false, null);

            sendFlatFilePizza(channel, flatPizza);
            sendJsonPizza(channel, jsonPizza);
        }
    }

    /**
     * Sends a Pizza object as a flat-file message.
     *
     * @param channel The RabbitMQ channel.
     * @param pizza The Pizza object to send.
     * @throws Exception If a RabbitMQ error occurs.
     */
    public static void sendFlatFilePizza(Channel channel, Pizza pizza) throws Exception {
        String flatMessage = toFlatFileString(pizza);

        channel.basicPublish("", FLAT_QUEUE, null, flatMessage.getBytes());

        System.out.println("Sent flat-file pizza message:");
        System.out.println(flatMessage);
        System.out.println();
    }

    /**
     * Sends a Pizza object as a JSON message.
     *
     * @param channel The RabbitMQ channel.
     * @param pizza The Pizza object to send.
     * @throws Exception If a RabbitMQ error occurs.
     */
    public static void sendJsonPizza(Channel channel, Pizza pizza) throws Exception {
        Gson gson = new Gson();
        String jsonMessage = gson.toJson(pizza);

        channel.basicPublish("", JSON_QUEUE, null, jsonMessage.getBytes());

        System.out.println("Sent JSON pizza message:");
        System.out.println(jsonMessage);
        System.out.println();
    }

    /**
     * Converts a Pizza object to a fixed-width flat-file string.
     *
     * @param pizza The Pizza object to convert.
     * @return A fixed-width flat-file string.
     */
    public static String toFlatFileString(Pizza pizza) {
        return String.format("%-15s%-10s%-15s%-8.2f",
                pizza.getCrust(),
                pizza.getSize(),
                pizza.getTopping(),
                pizza.getPrice());
    }
}