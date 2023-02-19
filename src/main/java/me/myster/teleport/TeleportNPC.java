package me.myster.teleport;

import net.minecraft.server.level.ServerPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import static me.myster.base.Func.newNPC;
import static me.myster.base.Func.registerEvents;

public class TeleportNPC implements Listener {
    public TeleportNPC() {
        registerEvents(this);
    }

    ServerPlayer npc;

    public ServerPlayer newTeleportNPC() {
        npc = newNPC("world", "teleportNPC", new double[]{0.5, - 45.5, 7.5});
        return npc;
    }

    @EventHandler
    public void onNPCbeClicked(PlayerInteractEntityEvent e) {
        if (! e.getRightClicked().getName().equals(npc.displayName)) return;
        new TeleportMenu().openInventory(e.getPlayer());
    }
}
