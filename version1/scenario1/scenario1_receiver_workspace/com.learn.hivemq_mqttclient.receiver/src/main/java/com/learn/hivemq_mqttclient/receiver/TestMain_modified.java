package com.learn.hivemq_mqttclient.receiver;

import java.net.InetSocketAddress;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import com.hivemq.client.mqtt.datatypes.MqttQos;
import com.hivemq.client.mqtt.mqtt5.Mqtt5AsyncClient;
import com.hivemq.client.mqtt.mqtt5.Mqtt5Client;
import com.hivemq.client.mqtt.mqtt5.Mqtt5AsyncClient.Mqtt5SubscribeAndCallbackBuilder;
import com.hivemq.client.mqtt.mqtt5.message.auth.Mqtt5SimpleAuth;
import com.hivemq.client.mqtt.mqtt5.message.connect.Mqtt5Connect;
import com.hivemq.client.mqtt.mqtt5.message.connect.connack.Mqtt5ConnAck;

public class TestMain_modified {
    
	private static int expectedNumberOfMessages = 30;
	private static int numberOfMessages = 0;
	
	public static void main(String[] args) {
		
        String topic        	= "Resource1";	// topic
        MqttQos qos             = MqttQos.AT_MOST_ONCE;		// equals qos 0
        String brokerAddress  	= "127.0.0.1";				// broker address
        int brokerPort			= 1883;						// broker port
        String clientId     	= "JavaSample_revcevier";	// client Id
        

        
        String myuserName	= "IamPublisherOne";
        String mypwd		= "123456";
        
        
        //------------------------------- create client --------------------------------------
        final InetSocketAddress LOCALHOST_EPHEMERAL1 = new InetSocketAddress(brokerAddress,brokerPort);;
        // 所以初步认为 MqttAsyncClient 是包含了 MqttRxClient 
        Mqtt5SimpleAuth simpleAuth = Mqtt5SimpleAuth.builder().username(myuserName).password(mypwd.getBytes()).build();
        Mqtt5Connect connectMessage = Mqtt5Connect.builder().cleanStart(false).sessionExpiryInterval(500).simpleAuth(simpleAuth).build();
        Mqtt5AsyncClient client1 = Mqtt5Client.builder().serverAddress(LOCALHOST_EPHEMERAL1).identifier(clientId).buildAsync();
        //------------------------------- client connect --------------------------------------
        CompletableFuture<Mqtt5ConnAck> cplfu_connect_rslt = client1.connect(connectMessage);
        //CompletableFuture<Mqtt5ConnAck> cplfu_connect_rslt = client1.connect();
        //-------------------------------  to subscribe  --------------------------------------
        Mqtt5AsyncClient.Mqtt5SubscribeAndCallbackBuilder.Start subscribeBuilder1 = client1.subscribeWith();
        Mqtt5SubscribeAndCallbackBuilder.Start.Complete c1 = subscribeBuilder1.topicFilter(topic);
        c1.qos(qos);
        c1.callback(publish -> {
        			numberOfMessages = numberOfMessages +1;
        			System.out.println(new String(publish.getPayloadAsBytes())); 
        		}); 	// set callback
        c1.send();		//subscribe callback and something 
        
        
        while(numberOfMessages < expectedNumberOfMessages) {
        	try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        //client1.topicFilter(topic).send();
        client1.disconnect();
        //System.exit(0);				//if using clean start false, disconnect couldn't finished the program
        //client1.disconnectWith().sessionExpiryInterval(0).send();
	}
}
