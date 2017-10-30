/*
 * MinecraftSentry
 * Author: peppercopia@gmail.com
 * Publishes on MQTT a selection of Spigot events for the purpose of monitoring the server.
 */


package ca.vigilem.minecraft.MinecraftSentry;

import java.text.SimpleDateFormat;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.simple.JSONObject;
import java.util.Date;

public class Main extends JavaPlugin {

	//Declare objects needed
	FileConfiguration config = getConfig();
    MessageBroker pubevent = new MessageBroker();
    private static Main instance;

    @Override
    public void onEnable() 
    {

    	//To make the instance available for calling
    	instance = this;
    	
    	//Create a default config file
    	//TODO Need to make this a little more robust to check first if
    	//the configuration file exists and then create it.
    	//https://www.spigotmc.org/wiki/config-files/
    	
    	this.saveDefaultConfig();
        config.options().copyDefaults(true);
        saveConfig();  
        
    	//Register our events on the server
    	getServer().getPluginManager().registerEvents(new SentryEventListener(), this);
    	
    	//Tell the outside world that the server has started.
        JSONObject EventObj = new JSONObject();
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss").format(new Date());
        EventObj.put("event-type", "serverStart");
        EventObj.put("server", config.getString("mqtt-serverid"));
        EventObj.put("start-time", timeStamp);
        EventObj.put("declaration", "The minecraft server " + config.getString("mqtt-serverid") + " has started.");
    	
    	//Publish that we are live
    	pubevent.publishEvent(EventObj.toJSONString());
    	
    	//Clean up
    	EventObj = null;
    	
    }
   
	@Override
    public void onDisable() 
    {
    	//Tell the outside world that the server has started.
        JSONObject EventObj = new JSONObject();
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss").format(new Date());
        EventObj.put("event-type", "serverStop");
        EventObj.put("server", config.getString("mqtt-serverid"));
        EventObj.put("start-time", timeStamp);
        EventObj.put("declaration", "The server " + config.getString("mqtt-serverid") + " has stopped.");
    	
    	//Publish that we are live
    	pubevent.publishEvent(EventObj.toJSONString());

    	//Clean up
    	EventObj = null;
		timeStamp = null;
		instance = null;
    }
    
    @Override
    public boolean onCommand(CommandSender sender,
            Command command,
            String label,
            String[] args) 
    {
        if (command.getName().equalsIgnoreCase("sentry")) {
            sender.sendMessage("Minecraft Sentry is running.");
            return true;
        }
        return false;
    }

    public static Main getInstance() 
    {
    	return instance;
    }
}
