package demo;

//模擬傳14個節點訊息，節點角度持續從0到360變化，設定qfps可設定每秒傳送訊息量
//p.s.fps的模擬方式是使用thread sleep 最小單位是1ms，因此fps不可大於1000


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Publisher extends Thread {
    private static final String TEST_TOPIC = "demo.EXCHANGE3";
    
	public void run(){
		try {
	        ConnectionFactory factory = new ConnectionFactory();
	        //factory.setUri("amqp://admin:admin@192.168.4.3");
	        //factory.setUri("amqp://admin:admin@192.168.4.100");
	        factory.setUri("amqp://admin:admin@wearable.nccu.edu.tw");
	        //factory.setUri("amqp://admin:admin@127.0.0.1");
	        Connection connection = factory.newConnection();
	        Channel channel = connection.createChannel();
	        
	        channel.exchangeDeclare(TEST_TOPIC, "fanout");
	        int qFPS=5;          //frame per second建議1000除的盡的 50 100 250 500 1000
	        int duration=1200;       //持續時間 s
	        int q,angle;             //計數用變數,換算角度
	        int QuantityOfMsg=(int)qFPS*duration;  //訊息總數量
	        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
	        
	        for(q=0;q<QuantityOfMsg;q++){
	        	angle=q%360;
	        	//  14個節點
            	String cmd = " { \"RightHand\": { x: " + angle + ", y: " + angle + ", z:" + angle + " },\n"
            			+ "\"RightForeArm\": { x: " + angle + ", y: " + angle + ", z:" + angle + " },\n"
            			+ "\"RightArm\": { x: " + angle + ", y: " + angle + ", z:" + angle + " },\n"
            			+ "\"LeftHand\": { x: " + angle + ", y: " + angle + ", z:" + angle + " },\n"
            			+ "\"LeftForeArm\": { x: " + angle + ", y: " + angle + ", z:" + angle + " },\n"
            			+ "\"LeftArm\": { x: " + angle + ", y: " + angle + ", z:" + angle + " },\n"
            			+ "\"RightUpLeg\": { x: " + angle + ", y: " + angle + ", z:" + angle + " },\n"
            			+ "\"RightLeg\": { x: " + angle + ", y: " + angle + ", z:" + angle + " },\n"
            			+ "\"RightFoot\": { x: " + angle + ", y: " + angle + ", z:" + angle + " },\n"
            			+ "\"LeftUpLeg\": { x: " + angle + ", y: " + angle + ", z:" + angle + " },\n"
            			+ "\"LeftLeg\": { x: " + angle + ", y: " + angle + ", z:" + angle + " },\n"
            			+ "\"LeftFoot\": { x: " + angle + ", y: " + angle + ", z:" + angle + " },\n"
            			+ "\"Head\": { x: " + angle + ", y: " + angle + ", z:" + angle + " },\n"
            			+ "\"Hips\": { x: " + angle + ", y: " + angle + ", z:" + angle + " } }";
            	
            	System.out.print(" ["+dateFormat.format(new Date())+"]\r\n");
            	//System.out.print(" ["+dateFormat.format(new Date())+"] Sent '" +cmd+"'\r\n");
        		channel.basicPublish(TEST_TOPIC, "", null, cmd.getBytes());

				Thread.sleep((1000/qFPS));
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Publisher mqTester = new Publisher();
		mqTester.start();
	}
}
