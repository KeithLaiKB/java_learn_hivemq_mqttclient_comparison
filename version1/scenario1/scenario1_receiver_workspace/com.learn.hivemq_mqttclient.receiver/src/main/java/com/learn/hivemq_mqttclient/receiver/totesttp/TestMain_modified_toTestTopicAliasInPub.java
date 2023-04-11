package com.learn.hivemq_mqttclient.receiver.totesttp;

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

public class TestMain_modified_toTestTopicAliasInPub {
    
	private static int expectedNumberOfMessages = 30;
	private static int numberOfMessages = 0;
	
	public static void main(String[] args) {
		
        String topic        	= "Resource1";	// topic
        //MqttQos qos             = MqttQos.AT_MOST_ONCE;		// equals qos 0
        MqttQos qos             = MqttQos.AT_LEAST_ONCE;
        String brokerAddress  	= "192.168.239.137";				// broker address
        int brokerPort			= 1883;						// broker port
        String clientId     	= "JavaSample_recver";	// client Id
        

        
        String myuserName	= "IamPublisherOne";
        String mypwd		= "123456";
        
        
        //------------------------------- create client --------------------------------------
        final InetSocketAddress LOCALHOST_EPHEMERAL1 = new InetSocketAddress(brokerAddress,brokerPort);;
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
        CompletableFuture<Mqtt5ConnAck> cplfu_connect_rslt = client1.connect(connectMessage);												// subscriber connect
        while(cplfu_connect_rslt.isDone()==false) {
        	
        }
        //-------------------------------  to subscribe  --------------------------------------
        Mqtt5AsyncClient.Mqtt5SubscribeAndCallbackBuilder.Start subscribeBuilder1 = client1.subscribeWith();
        Mqtt5SubscribeAndCallbackBuilder.Start.Complete c1 = subscribeBuilder1.topicFilter(topic);
        c1.qos(qos);
        c1.callback(publish -> {
        			numberOfMessages = numberOfMessages +1;
        			System.out.println(new String(publish.getPayloadAsBytes())); 
        		}); 	// set callback
        c1.send();		//subscribe callback and something 
        
        //
        // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        // test connect ack中描述  和 publish的 topic alias
        //

        
        Mqtt5SubscribeAndCallbackBuilder.Start.Complete c2 = client1.subscribeWith().topicFilter(topic+"2");		//如果要订阅两个主题, 就需要一口气  client1.subscribeWith().topicFilter不能拆开, 不然订阅上会有问题 比如  会重复订阅第一个
        Mqtt5SubscribeAndCallbackBuilder.Start.Complete c3 = client1.subscribeWith().topicFilter(topic+"3");		//如果要订阅两个主题, 就需要一口气  client1.subscribeWith().topicFilter不能拆开, 不然订阅上会有问题 比如  会重复订阅第一个
        Mqtt5SubscribeAndCallbackBuilder.Start.Complete c4 = client1.subscribeWith().topicFilter(topic+"4");		//如果要订阅两个主题, 就需要一口气  client1.subscribeWith().topicFilter不能拆开, 不然订阅上会有问题 比如  会重复订阅第一个
        Mqtt5SubscribeAndCallbackBuilder.Start.Complete c5 = client1.subscribeWith().topicFilter(topic+"5");		//如果要订阅两个主题, 就需要一口气  client1.subscribeWith().topicFilter不能拆开, 不然订阅上会有问题 比如  会重复订阅第一个
        Mqtt5SubscribeAndCallbackBuilder.Start.Complete c6 = client1.subscribeWith().topicFilter(topic+"6");		//如果要订阅两个主题, 就需要一口气  client1.subscribeWith().topicFilter不能拆开, 不然订阅上会有问题 比如  会重复订阅第一个
        Mqtt5SubscribeAndCallbackBuilder.Start.Complete c7 = client1.subscribeWith().topicFilter(topic+"7");		//如果要订阅两个主题, 就需要一口气  client1.subscribeWith().topicFilter不能拆开, 不然订阅上会有问题 比如  会重复订阅第一个
        Mqtt5SubscribeAndCallbackBuilder.Start.Complete c8 = client1.subscribeWith().topicFilter(topic+"8");		//如果要订阅两个主题, 就需要一口气  client1.subscribeWith().topicFilter不能拆开, 不然订阅上会有问题 比如  会重复订阅第一个
        Mqtt5SubscribeAndCallbackBuilder.Start.Complete c9 = client1.subscribeWith().topicFilter(topic+"9");		//如果要订阅两个主题, 就需要一口气  client1.subscribeWith().topicFilter不能拆开, 不然订阅上会有问题 比如  会重复订阅第一个
        Mqtt5SubscribeAndCallbackBuilder.Start.Complete c10 = client1.subscribeWith().topicFilter(topic+"10");		//如果要订阅两个主题, 就需要一口气  client1.subscribeWith().topicFilter不能拆开, 不然订阅上会有问题 比如  会重复订阅第一个
        Mqtt5SubscribeAndCallbackBuilder.Start.Complete c11 = client1.subscribeWith().topicFilter(topic+"11");		//如果要订阅两个主题, 就需要一口气  client1.subscribeWith().topicFilter不能拆开, 不然订阅上会有问题 比如  会重复订阅第一个

        c2.qos(qos);
        c3.qos(qos);
        c4.qos(qos);
        c5.qos(qos);
        c6.qos(qos);
        c7.qos(qos);
        c8.qos(qos);
        c9.qos(qos);
        c10.qos(qos);
        c11.qos(qos);

        c2.callback(publish -> {
			numberOfMessages = numberOfMessages +1;
			System.out.println(new String(publish.getPayloadAsBytes())); 
		}); 	// set callback
        c3.callback(publish -> {
			numberOfMessages = numberOfMessages +1;
			System.out.println(new String(publish.getPayloadAsBytes())); 
		}); 	// set callback
        c4.callback(publish -> {
			numberOfMessages = numberOfMessages +1;
			System.out.println(new String(publish.getPayloadAsBytes())); 
		}); 	// set callback
        c5.callback(publish -> {
			numberOfMessages = numberOfMessages +1;
			System.out.println(new String(publish.getPayloadAsBytes())); 
		}); 	// set callback
        c6.callback(publish -> {
			numberOfMessages = numberOfMessages +1;
			System.out.println(new String(publish.getPayloadAsBytes())); 
		}); 	// set callback
        c7.callback(publish -> {
			numberOfMessages = numberOfMessages +1;
			System.out.println(new String(publish.getPayloadAsBytes())); 
		}); 	// set callback
        c8.callback(publish -> {
			numberOfMessages = numberOfMessages +1;
			System.out.println(new String(publish.getPayloadAsBytes())); 
		}); 	// set callback
        c9.callback(publish -> {
			numberOfMessages = numberOfMessages +1;
			System.out.println(new String(publish.getPayloadAsBytes())); 
		}); 	// set callback
        c10.callback(publish -> {
			numberOfMessages = numberOfMessages +1;
			System.out.println(new String(publish.getPayloadAsBytes())); 
		}); 	// set callback
        c11.callback(publish -> {
			numberOfMessages = numberOfMessages +1;
			System.out.println(new String(publish.getPayloadAsBytes())); 
		}); 	// set callback
        
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
        
        // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        
        
        
        
        
        
        Mqtt5SubscribeAndCallbackBuilder.Start.Complete c12 = client1.subscribeWith().topicFilter(topic+"12");		//如果要订阅两个主题, 就需要一口气  client1.subscribeWith().topicFilter不能拆开, 不然订阅上会有问题 比如  会重复订阅第一个
        
        c12.qos(qos);
        c12.callback(publish2 -> {
        			numberOfMessages = numberOfMessages +1;
        			System.out.println(new String(publish2.getPayloadAsBytes())); 
        		}); 	// set callback
        c12.send();		//subscribe callback and something 				如果订阅两个只需要最后那个send就可以了
        //	
        //
        /*
        Mqtt5SubscribeAndCallbackBuilder.Start.Complete c13_14 = client1.subscribeWith().topicFilter(topic+"13").topicFilter("14");		//如果要订阅两个主题, 就需要一口气  client1.subscribeWith().topicFilter不能拆开, 不然订阅上会有问题 比如  会重复订阅第一个
        
        c13_14.qos(qos);
        c13_14.callback(publish2 -> {
        			numberOfMessages = numberOfMessages +1;
        			System.out.println(new String(publish2.getPayloadAsBytes())); 
        		}); 	// set callback
        c13_14.send();		//subscribe callback and something 				如果订阅两个只需要最后那个send就可以了
        */
        
        
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
