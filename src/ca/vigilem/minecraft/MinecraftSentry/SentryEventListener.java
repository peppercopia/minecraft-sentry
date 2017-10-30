package ca.vigilem.minecraft.MinecraftSentry;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class SentryEventListener implements Listener 
{
	//Local objects to get the plugin configuration file
	private JavaPlugin plugin;
	private FileConfiguration config;
	MessageBroker pubevent = new MessageBroker();
	
	@EventHandler
    public void onPlayerJoin(PlayerJoinEvent event)
    {
	    plugin = Main.getInstance();
	    config = plugin.getConfig();
    	String PlayerName = event.getPlayer().getName();
    	
    	//Create the MQTT Event Payload and send it.
    	String EventMessage = PlayerName + " has joined the game on server " + config.getString("mqtt-serverid") + "." ;
    	pubevent.publishEvent(EventMessage);
       }
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event)
	{
	    plugin = Main.getInstance();
	    config = plugin.getConfig();
    	String PlayerName = event.getPlayer().getName();
    	
    	//Create the MQTT Event Payload and send it.
    	String EventMessage = PlayerName + " has logged off server " + config.getString("mqtt-serverid") + "." ;
    	pubevent.publishEvent(EventMessage);

	}
}
