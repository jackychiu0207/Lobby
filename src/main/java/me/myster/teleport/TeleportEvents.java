package me.myster.teleport;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Objects;

public class TeleportEvents implements Listener {
    @EventHandler
    public void teleportMenu(PlayerInteractEvent e) {
        if (e.getAction() != Action.PHYSICAL)
            if (Objects.requireNonNull(e.getItem()).getType().equals(Material.COMPASS)) {
                TeleportMenu tpMenu = new TeleportMenu();
                tpMenu.openInventory(e.getPlayer());
            }
    }

    @EventHandler
    public void teleportNPC(PlayerInteractAtEntityEvent e) {

    }
}
