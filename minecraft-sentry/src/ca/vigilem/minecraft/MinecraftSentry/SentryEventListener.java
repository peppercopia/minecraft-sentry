package ca.vigilem.minecraft.MinecraftSentry;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class SentryEventListener implements Listener 
{
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event)
    {
    	Bukkit.broadcastMessage("This is where I am going to try and publish an event to MQTT.");
    }
}
