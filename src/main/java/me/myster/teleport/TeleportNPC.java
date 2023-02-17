package me.myster.teleport;

import net.minecraft.server.level.EntityPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import static me.myster.base.Func.newNPC;
import static me.myster.base.Func.registerEvents;

public class TeleportNPC implements Listener {
    public TeleportNPC() {
        npc = newNPC("world", "傳送使者", new double[]{0.5, - 45, 7.5}, new float[0]);
        registerEvents(this);
    }
    EntityPlayer npc;

    @EventHandler
    public void onNPCbeClicked(PlayerInteractEntityEvent e) {
        e.getRightClicked();
    }
}
