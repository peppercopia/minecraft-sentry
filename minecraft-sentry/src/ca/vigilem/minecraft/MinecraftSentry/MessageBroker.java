package ca.vigilem.minecraft.MinecraftSentry;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MessageBroker {

	//Local objects to get the plugin configuration file
	private JavaPlugin plugin;
	private FileConfiguration config;

	//This method will connect to the MQTT server specified in the configuration
	//file, publish the EventMessage and disconnect.
	
	public void publishEvent(String EventMessage)
	{
		MqttClient client;
	    MqttConnectOptions options;
	    plugin = Main.getInstance();
	    config = plugin.getConfig();
	    		
	    //Set the MQTT connection options
	    String BROKER_URL = config.getString("broker-url");
		String MQTT_USERNAME = config.getString("mqtt-username");
		String MQTT_PASSWORD = config.getString("mqtt-password");
		String MQTT_TOPIC = config.getString("mqtt-topic-prefix");
		String MQTT_SERVERID = config.getString("mqtt-serverid");

	    options = new MqttConnectOptions();
	    options.setCleanSession(true);
	    options.setKeepAliveInterval(30);
	    options.setUserName(MQTT_USERNAME);
	    options.setPassword(MQTT_PASSWORD.toCharArray());
	
	    try {
	      client = new MqttClient(BROKER_URL, MQTT_SERVERID);
	      client.connect(options);
	      MqttMessage message = new MqttMessage();
	      message.setPayload(EventMessage.getBytes());
	      client.publish(MQTT_TOPIC + MQTT_SERVERID, message);
	      client.disconnect();
	    
	    } catch (MqttException e) {
	    	//TODO Might want it a little more robust than this
	    	e.printStackTrace();
	    }
	}
}
