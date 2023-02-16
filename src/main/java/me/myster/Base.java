package me.myster;

import org.bukkit.Server;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class Base {
    public Base() {
    }

    public static final JavaPlugin plugin = JavaPlugin.getPlugin(Lobby.class);
    public static final Server server = plugin.getServer();

    public static void registerEvents(Listener listener) {
        plugin.getServer().getPluginManager().registerEvents(listener, plugin);
    }
    
}
