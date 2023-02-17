package me.myster.base;

import me.myster.Lobby;
import net.minecraft.server.MinecraftServer;
import org.bukkit.Server;
import org.bukkit.craftbukkit.v1_19_R2.CraftServer;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class Var {
    public Var() {
    }

    public static final JavaPlugin plugin = JavaPlugin.getPlugin(Lobby.class);
    public static final Server server = plugin.getServer();
    public static final MinecraftServer nmsServer = ((CraftServer) server).getServer();


}
