package main;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

/**

 * Project: Lab 4
 * Purpose Details: Pizza Shop
 * Course: IST 242
 * Author: Matthew Sulpizio
 * Date Developed: 3/20/2026
 * Last Date Changed: 3/31/2026
 * Rev:

 */

public class RabbitMQReceiver {

    /**
     * Default constructor for RabbitMQReceiver.
     */
    public RabbitMQReceiver() {
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
     * Main method that receives both flat-file and JSON Pizza messages.
     *
     * @param args Command-line arguments.
     * @throws Exception If a RabbitMQ or connection error occurs.
     */
    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(FLAT_QUEUE, false, false, false, null);
        channel.queueDeclare(JSON_QUEUE, false, false, false, null);

        System.out.println("Waiting for flat-file and JSON pizza messages...");
        System.out.println();

        receiveFlatFilePizza(channel);
        receiveJsonPizza(channel);
    }

    /**
     * Receives flat-file Pizza messages and converts them back to Pizza objects.
     *
     * @param channel The RabbitMQ channel.
     * @throws Exception If a RabbitMQ error occurs.
     */
    public static void receiveFlatFilePizza(Channel channel) throws Exception {
        DeliverCallback flatCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            Pizza pizza = fromFlatFileString(message);

            System.out.println("Received flat-file pizza message:");
            System.out.println(message);
            System.out.println("Converted flat-file back to Pizza object:");
            System.out.println(pizza);
            System.out.println();
        };

        channel.basicConsume(FLAT_QUEUE, true, flatCallback, consumerTag -> { });
    }

    /**
     * Receives JSON Pizza messages and converts them back to Pizza objects.
     *
     * @param channel The RabbitMQ channel.
     * @throws Exception If a RabbitMQ error occurs.
     */
    public static void receiveJsonPizza(Channel channel) throws Exception {
        DeliverCallback jsonCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");

            Gson gson = new Gson();
            Pizza pizza = gson.fromJson(message, Pizza.class);

            System.out.println("Received JSON pizza message:");
            System.out.println(message);
            System.out.println("Converted JSON back to Pizza object:");
            System.out.println(pizza);
            System.out.println();
        };

        channel.basicConsume(JSON_QUEUE, true, jsonCallback, consumerTag -> { });
    }

    /**
     * Converts a fixed-width flat-file string back into a Pizza object.
     *
     * @param line The flat-file string to convert.
     * @return A Pizza object.
     */
    public static Pizza fromFlatFileString(String line) {
        String crust = line.substring(0, 15).trim();
        String size = line.substring(15, 25).trim();
        String topping = line.substring(25, 40).trim();
        double price = Double.parseDouble(line.substring(40, 48).trim());

        return new Pizza(crust, size, topping, price);
    }
}