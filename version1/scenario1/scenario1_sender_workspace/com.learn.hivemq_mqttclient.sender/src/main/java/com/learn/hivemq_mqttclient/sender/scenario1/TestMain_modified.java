package com.learn.hivemq_mqttclient.sender.scenario1;

import java.net.InetSocketAddress;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;



import com.hivemq.client.internal.mqtt.MqttRxClient;
import com.hivemq.client.internal.mqtt.message.publish.MqttPublishBuilder;

import com.hivemq.client.mqtt.datatypes.MqttQos;
import com.hivemq.client.mqtt.mqtt5.Mqtt5AsyncClient;
import com.hivemq.client.mqtt.mqtt5.Mqtt5BlockingClient;
import com.hivemq.client.mqtt.mqtt5.Mqtt5Client;
import com.hivemq.client.mqtt.mqtt5.message.auth.Mqtt5SimpleAuth;
import com.hivemq.client.mqtt.mqtt5.message.connect.Mqtt5Connect;
import com.hivemq.client.mqtt.mqtt5.message.connect.connack.Mqtt5ConnAck;
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5Publish;
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilder;
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilderBase;
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishResult;
/**
 * 
 * 
 * <p>
 * 							description:																				</br>	
 * &emsp;						qos 0																					</br>	
 * &emsp;						if it couldn't connect, still wait though there are something wrong during connection	</br>																							</br>
 *
 *
 * @author laipl
 *
 * 这里有 connectMessage
 *
 */
public class TestMain_modified {


	public static void main(String[] args) {
		long startTime			=System.nanoTime();   		//nanoTime 会比 currentTimeMillis更加精确  
		System.out.println(new Date(System.currentTimeMillis())); 
		
        String topic        	= "Resource1";	// topic
        MqttQos qos             = MqttQos.AT_MOST_ONCE;		// equals qos 0
        String brokerAddress  	= "127.0.0.1";				// broker address
        int brokerPort			= 1883;						// broker port
        String clientId     	= "JavaSample_sender";		// client Id
        String content     	 	= "Hello World!";
        //
        int statusUpdate		=0;
        int statusUpdateMaxTimes=100;
        //
        

        
        String myuserName	= "IamPublisherOne";
        String mypwd		= "123456";
        
        //------------------------------- create client --------------------------------------
        final InetSocketAddress LOCALHOST_EPHEMERAL1 = new InetSocketAddress(brokerAddress,brokerPort);
        // 所以初步认为 MqttAsyncClient 是包含了 MqttRxClient 
        Mqtt5SimpleAuth simpleAuth = Mqtt5SimpleAuth.builder().username(myuserName).password(mypwd.getBytes()).build();
        //第一种 auth 方式 1.1
        //Mqtt5AsyncClient client1 = Mqtt5Client.builder().serverAddress(LOCALHOST_EPHEMERAL1).identifier(clientId).simpleAuth(simpleAuth).buildAsync();
        //第二种 auth 方式 2.1
        Mqtt5AsyncClient client1 = Mqtt5Client.builder().serverAddress(LOCALHOST_EPHEMERAL1).identifier(clientId).buildAsync();
        //------------------------------- client connect --------------------------------------
        //第一种 auth 方式 1.2
        //CompletableFuture<Mqtt5ConnAck> cplfu_connect_rslt = client1.connect();	
        //第二种 auth 方式 2.2
        Mqtt5Connect connectMessage = Mqtt5Connect.builder().cleanStart(true).simpleAuth(simpleAuth).build();
        //第二种 auth 方式 2.3
        CompletableFuture<Mqtt5ConnAck> cplfu_connect_rslt = client1.connect(connectMessage);	
		//------------------------------- client publish --------------------------------------
    	com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilder.Send<CompletableFuture<Mqtt5PublishResult>>  publishBuilder1 = client1.publishWith();
    	com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilder.Send.Complete<CompletableFuture<Mqtt5PublishResult>> c1 = publishBuilder1.topic(topic);
    	c1.qos(qos);
    	//
        while(statusUpdate<=statusUpdateMaxTimes-1) {
        	statusUpdate = statusUpdate+1;
        	String str_content_tmp = content + statusUpdate;
        	//
        	c1.payload(str_content_tmp.getBytes());
        	c1.send();
        	//
        	System.out.println(str_content_tmp);
        	try {
        		Thread.sleep(1000);
    		} catch (InterruptedException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
        }
        

        client1.disconnect();
        long endTime			=System.nanoTime();   		//nanoTime 会比 currentTimeMillis更加精确
        long usedTime			= endTime - startTime;
        System.out.println("usedTime:"+usedTime);

    }

}
