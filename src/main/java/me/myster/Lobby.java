package me.myster;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import static org.bukkit.Bukkit.broadcastMessage;

public final class Lobby extends JavaPlugin implements Listener {

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Bukkit.getLogger().info(ChatColor.RED + "Disabled" + this.getName());
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getLogger().info(ChatColor.GREEN + "Enabled" + this.getName());
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        System.out.println("test");
        System.out.println(broadcastMessage(ChatColor.YELLOW + "Welcome to Myster's Server, " + ChatColor.BLUE + event.getPlayer().getDisplayName() + "."));
    }
}
