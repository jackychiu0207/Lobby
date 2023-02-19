package me.myster;

import me.myster.teleport.TeleportMenu;
import me.myster.teleport.TeleportNPC;
import net.minecraft.server.level.EntityPlayer;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static me.myster.base.Func.*;
import static me.myster.base.Var.plugin;
import static me.myster.base.Var.server;
import static org.bukkit.Bukkit.broadcastMessage;

public final class Lobby extends JavaPlugin implements Listener {
    ArrayList<EntityPlayer> npcList = new ArrayList<EntityPlayer>();

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        System.out.println("[Lobby Plugin]Disabled \"" + this.getName() + "\"");
        List<Player> playerList = Objects.requireNonNull(server.getWorld("world")).getPlayers();
        for (int i = 0; i < npcList.size(); i++) {
            EntityPlayer npc = npcList.get(i);
            for (int ii = 0; ii < playerList.size(); ii++) {
                Player p = playerList.get(ii);
                System.out.println(p.getDisplayName());
                removeNPCPacket(npc, p);
            }
        }
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        System.out.println("[Lobby Plugin]Enabled \"" + this.getName() + "\"");
        registerEvents(this);
        plugin.getServer().getMessenger().registerOutgoingPluginChannel(plugin, "BungeeCord");
        npcList.add(new TeleportNPC().newTeleportNPC());
        List<Player> playerList = Objects.requireNonNull(server.getWorld("world")).getPlayers();
        for (int i = 0; i < npcList.size(); i++) {
            EntityPlayer npc = npcList.get(i);
            for (int ii = 0; ii < playerList.size(); ii++) {
                Player p = playerList.get(ii);
                System.out.println(p.getDisplayName());
                addNPCPacket(npc, p);
            }
        }
        new TeleportMenu();
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

    }

    @EventHandler
    public void onPlayerUseItem(PlayerInteractEvent e) {
        Player pl = e.getPlayer();
        npcList.add(newNPC("world", "testname", new double[]{pl.getLocation().getX(), pl.getLocation().getY(), pl.getLocation().getZ()}));
        List<Player> playerList = Objects.requireNonNull(server.getWorld("world")).getPlayers();
        for (
                int i = 0; i < npcList.size(); i++) {
            EntityPlayer npc = npcList.get(i);
            for (int ii = 0; ii < playerList.size(); ii++) {
                Player p = playerList.get(ii);
                System.out.println(p.getDisplayName());
                addNPCPacket(npc, p);
            }
        }
    }


}
