package ca.vigilem.minecraft.MinecraftSentry;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

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
    	
    	//Publish that we are live
    	pubevent.publishEvent("The server " + config.getString("mqtt-serverid") + " has started.");
   
    }
   
	@Override
    public void onDisable() 
    {
		pubevent.publishEvent("The server " + config.getString("mqtt-serverid") + " has stopped.");
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
