package com.learn.hivemq_mqttclient.sender.scenario1.totesttp;

import java.net.InetSocketAddress;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

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
public class TestMain_modified_toTestTopicAliasInPub {


	public static void main(String[] args) {
		long startTime			=System.nanoTime();   		//nanoTime 会比 currentTimeMillis更加精确  
		System.out.println(new Date(System.currentTimeMillis())); 
		
        String topic        	= "Resource1";	// topic
        //MqttQos qos             = MqttQos.AT_MOST_ONCE;		// equals qos 0
        MqttQos qos             = MqttQos.AT_LEAST_ONCE;		// equals qos 0
        String brokerAddress  	= "192.168.239.137";				// broker address
        int brokerPort			= 1883;						// broker port
        String clientId     	= "JavaSample_sender";		// client Id
        String content     	 	= "Hello_World!";
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
        Mqtt5Connect connectMessage = Mqtt5Connect.builder().cleanStart(false).simpleAuth(simpleAuth).build();
        //第二种 auth 方式 2.3
        CompletableFuture<Mqtt5ConnAck> cplfu_connect_rslt = client1.connect(connectMessage);		// publisher connect
        while(cplfu_connect_rslt.isDone()==false) {
        	
        }
		//------------------------------- client publish --------------------------------------
    	com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilder.Send<CompletableFuture<Mqtt5PublishResult>>  publishBuilder1 = client1.publishWith();
    	com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilder.Send.Complete<CompletableFuture<Mqtt5PublishResult>> c1 = publishBuilder1.topic(topic);
    	/*
    	com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilder.Send.Complete<CompletableFuture<Mqtt5PublishResult>> c2 = publishBuilder1.topic(topic+"2");
    	com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilder.Send.Complete<CompletableFuture<Mqtt5PublishResult>> c3 = publishBuilder1.topic(topic+"3");
    	com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilder.Send.Complete<CompletableFuture<Mqtt5PublishResult>> c4 = publishBuilder1.topic(topic+"4");
    	com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilder.Send.Complete<CompletableFuture<Mqtt5PublishResult>> c5 = publishBuilder1.topic(topic+"5");
    	com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilder.Send.Complete<CompletableFuture<Mqtt5PublishResult>> c6 = publishBuilder1.topic(topic+"6");
    	com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilder.Send.Complete<CompletableFuture<Mqtt5PublishResult>> c7 = publishBuilder1.topic(topic+"7");
    	com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilder.Send.Complete<CompletableFuture<Mqtt5PublishResult>> c8 = publishBuilder1.topic(topic+"8");
    	com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilder.Send.Complete<CompletableFuture<Mqtt5PublishResult>> c9 = publishBuilder1.topic(topic+"9");
    	com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilder.Send.Complete<CompletableFuture<Mqtt5PublishResult>> c10 = publishBuilder1.topic(topic+"10");
    	com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilder.Send.Complete<CompletableFuture<Mqtt5PublishResult>> c11 = publishBuilder1.topic(topic+"11");
    	com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilder.Send.Complete<CompletableFuture<Mqtt5PublishResult>> c12 = publishBuilder1.topic(topic+"12");
    	*/
    	c1.qos(qos);
    	//
        while(statusUpdate<=statusUpdateMaxTimes-1) {
        	statusUpdate = statusUpdate+1;
        	String str_content_tmp = content + statusUpdate;
        	//
        	
        	
        	/*
        	c1.payload(str_content_tmp.getBytes());
        	c1.send();
        	*/
        	
        	
        	
        	//
        	//
        	//
        	//
        	/*
        	c2.payload(str_content_tmp.getBytes());
        	c3.payload(str_content_tmp.getBytes());
        	c4.payload(str_content_tmp.getBytes());
        	c5.payload(str_content_tmp.getBytes());
        	c6.payload(str_content_tmp.getBytes());
        	c7.payload(str_content_tmp.getBytes());
        	c8.payload(str_content_tmp.getBytes());
        	c9.payload(str_content_tmp.getBytes());
        	c10.payload(str_content_tmp.getBytes());
        	c11.payload(str_content_tmp.getBytes());
        	c12.payload(str_content_tmp.getBytes());
        	//
        	c2.send();
        	c3.send();
        	c4.send();
        	c5.send();
        	c6.send();
        	c7.send();
        	c8.send();
        	c9.send();
        	c10.send();
        	c11.send();
        	c12.send();
        	*/
        	
			Mqtt5Publish publishMessage1 = Mqtt5Publish.builder().topic(topic).qos(MqttQos.AT_LEAST_ONCE).payload(str_content_tmp.getBytes()).build();
			client1.publish(publishMessage1);
			Mqtt5Publish publishMessage2 = Mqtt5Publish.builder().topic(topic+"2").qos(MqttQos.AT_LEAST_ONCE).payload(str_content_tmp.getBytes()).build();
			client1.publish(publishMessage2);
			Mqtt5Publish publishMessage3 = Mqtt5Publish.builder().topic(topic+"3").qos(MqttQos.AT_LEAST_ONCE).payload(str_content_tmp.getBytes()).build();
			client1.publish(publishMessage3);
			Mqtt5Publish publishMessage4 = Mqtt5Publish.builder().topic(topic+"4").qos(MqttQos.AT_LEAST_ONCE).payload(str_content_tmp.getBytes()).build();
			client1.publish(publishMessage4);
			Mqtt5Publish publishMessage5 = Mqtt5Publish.builder().topic(topic+"5").qos(MqttQos.AT_LEAST_ONCE).payload(str_content_tmp.getBytes()).build();
			client1.publish(publishMessage5);
			Mqtt5Publish publishMessage6 = Mqtt5Publish.builder().topic(topic+"6").qos(MqttQos.AT_LEAST_ONCE).payload(str_content_tmp.getBytes()).build();
			client1.publish(publishMessage6);
			Mqtt5Publish publishMessage7 = Mqtt5Publish.builder().topic(topic+"7").qos(MqttQos.AT_LEAST_ONCE).payload(str_content_tmp.getBytes()).build();
			client1.publish(publishMessage7);
			Mqtt5Publish publishMessage8 = Mqtt5Publish.builder().topic(topic+"8").qos(MqttQos.AT_LEAST_ONCE).payload(str_content_tmp.getBytes()).build();
			client1.publish(publishMessage8);
			Mqtt5Publish publishMessage9 = Mqtt5Publish.builder().topic(topic+"9").qos(MqttQos.AT_LEAST_ONCE).payload(str_content_tmp.getBytes()).build();
			client1.publish(publishMessage9);
			Mqtt5Publish publishMessage10 = Mqtt5Publish.builder().topic(topic+"10").qos(MqttQos.AT_LEAST_ONCE).payload(str_content_tmp.getBytes()).build();
			client1.publish(publishMessage10);
			Mqtt5Publish publishMessage11 = Mqtt5Publish.builder().topic(topic+"11").qos(MqttQos.AT_LEAST_ONCE).payload(str_content_tmp.getBytes()).build();
			client1.publish(publishMessage11);
			Mqtt5Publish publishMessage12 = Mqtt5Publish.builder().topic(topic+"12").qos(MqttQos.AT_LEAST_ONCE).payload(str_content_tmp.getBytes()).build();
			client1.publish(publishMessage12);
        	//
        	//
        	//
        	//
        	//
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
