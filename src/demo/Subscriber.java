package demo;

import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.text.*;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

public class Subscriber
{

    private static final String TEST_TOPIC = "demo.EXCHANGE3";

    public static void main(String[] args)
            throws java.io.IOException,
            java.lang.InterruptedException, KeyManagementException, NoSuchAlgorithmException, URISyntaxException
    {

    	
    	System.out.print("hello");
    		
			//setting dateFormat
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		
			
		
    	//setting RMQ connection
        ConnectionFactory factory = new ConnectionFactory();
        //factory.setUri("amqp://admin:admin@140.119.163.199");
        //factory.setUri("amqp://admin:admin@192.168.4.100");
        factory.setUri("amqp://admin:admin@wearable.nccu.edu.tw");
        //factory.setUri("amqp://admin:admin@127.0.0.1");
        
        
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(TEST_TOPIC, "fanout");
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, TEST_TOPIC, "");

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
      
        QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume(queueName, true, consumer);

        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());
           //System.out.print(" ["+dateFormat.format(new Date())+"]\r\n");
           System.out.print(" ["+dateFormat.format(new Date())+"] Received '" + message+"'\r\n");
           
        }
        
		

        
		

		
    }
}
