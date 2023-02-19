package me.myster;

import me.myster.teleport.TeleportMenu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import static me.myster.base.Func.*;
import static me.myster.base.Var.npcList;

public class Events implements Listener {
    public Events() {
        registerEvents(this);
    }

    @EventHandler
    public void playerInteractEvent(PlayerInteractEvent e) {
        if (e.getAction() != Action.PHYSICAL && e.getItem() != null) {
            ItemStack item = e.getItem();
            Player p = e.getPlayer();
            if (item.getType().equals(Material.COMPASS)) {
                TeleportMenu tpMenu = new TeleportMenu();
                tpMenu.openInventory(p);
            } else if (item.getType().equals(Material.RECOVERY_COMPASS)) {
                npcList.add(newNPC("world", "testNPC", new double[]{p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ()}));
                sendNPCToAllPlayer(npcList, "world");
            } else if (item.getType().equals(Material.BLAZE_ROD)) {
                removeNPCToAllPlayer(npcList, "world");
                npcList.clear();
                sendNPCToAllPlayer(npcList, "world");
            }
        }
    }
}
