package me.myster.teleport;

import net.minecraft.server.level.EntityPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import static me.myster.base.Func.newNPC;
import static me.myster.base.Func.registerEvents;

public class TeleportNPC implements Listener {
    public TeleportNPC() {
        registerEvents(this);
    }

    EntityPlayer npc;

    public EntityPlayer newTeleportNPC() {
        npc = newNPC("world", "", new double[]{0.5, - 45, 7.5});
        return npc;
    }

    @EventHandler
    public void onNPCbeClicked(PlayerInteractEntityEvent e) {
        if (! e.getRightClicked().equals((Entity) npc)) return;
        new TeleportMenu().openInventory(e.getPlayer());
    }
}
