package me.myster;

import me.myster.teleport.TeleportMenu;
import me.myster.teleport.TeleportNPC;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import static me.myster.base.Func.*;
import static me.myster.base.Var.npcList;
import static me.myster.base.Var.plugin;
import static org.bukkit.Bukkit.broadcastMessage;

public final class Lobby extends JavaPlugin implements Listener {


    @Override
    public void onDisable() {
        // Plugin shutdown logic
        System.out.println("[Lobby Plugin]Disabled \"" + this.getName() + "\"");
        removeNPCToAllPlayer(npcList, "world");
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        System.out.println("[Lobby Plugin]Enabled \"" + this.getName() + "\"");
        registerEvents(this);
        plugin.getServer().getMessenger().registerOutgoingPluginChannel(plugin, "BungeeCord");
        TeleportNPC tpNPC = new TeleportNPC();
        npcList.add(tpNPC.newTeleportNPC());
        sendNPCToAllPlayer(npcList, "world");
        new TeleportMenu();
        new Events();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        e.setJoinMessage(null);
        broadcastMessage(ChatColor.YELLOW + "Welcome to Myster's Server, " + ChatColor.AQUA + p.getDisplayName());
        new BukkitRunnable() {
            @Override
            public void run() {
                p.getWorld().playSound(p, Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f);
            }
        }.runTaskLater(this, 20L);
        sendNPCPacket(npcList, p);
    }


}
