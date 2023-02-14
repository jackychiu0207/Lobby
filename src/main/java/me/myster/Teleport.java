package me.myster;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;

import static org.bukkit.Bukkit.createInventory;

public class Teleport implements Listener {
    @EventHandler
    public void teleportMenu(PlayerInteractEvent e) {
        Action a = e.getAction();
        if (Arrays.asList(Action.values()).contains(a) && a != Action.PHYSICAL) {
            TeleportMenu tpMenu = new TeleportMenu();
            tpMenu.openInventory(e.getPlayer());
        }
    }

    static class TeleportMenu {
        public TeleportMenu() {
            // Create a new inventory, with no owner (as this isn't a real inventory), a size of nine, called example
            inv = createInventory(null, 5, "傳送選單");
            initializeItems();
        }

        private final Inventory inv;

        // You can call this whenever you want to put the items in
        public void initializeItems() {
            inv.addItem(createGuiItem(Material.BEACON, "§2大廳", "§a去大廳"));
            inv.addItem(createGuiItem(Material.NETHERITE_SWORD, "§2生存", "§a去生存"));
            inv.addItem(createGuiItem(Material.ENDER_EYE, "§2躲貓貓", "§a去躲貓貓"));
        }

        // Nice little method to create a gui item with a custom name, and description
        protected ItemStack createGuiItem(final Material material, final String name, final String... lore) {
            final ItemStack item = new ItemStack(material, 1);
            final ItemMeta meta = item.getItemMeta();
            // Set the name of the item
            meta.setDisplayName(name);
            // Set the lore of the item
            meta.setLore(Arrays.asList(lore));
            item.setItemMeta(meta);
            return item;
        }

        public void openInventory(final HumanEntity ent) {
            ent.openInventory(inv);
        }

        // Check for clicks on items
        @EventHandler
        public void onInventoryClick(final InventoryClickEvent e) {
            if (! e.getInventory().equals(inv)) return;
            e.setCancelled(true);
            final ItemStack clickedItem = e.getCurrentItem();
            final Material itemType = clickedItem.getType();
            // verify current item is not null
            if (clickedItem == null || itemType.isAir()) return;
            String server;
            final Player p = (Player) e.getWhoClicked();
            if (itemType.equals(Material.BEACON)) {
                server = "lobby";
            } else if (itemType.equals(Material.NETHERITE_SWORD)) {
                server = "survival";
            } else if (itemType.equals(Material.ENDER_EYE)) {
                server = "hide_and_seek";
            } else {
                server = null;
            }
            if (! server.equals(null)) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
                try {
                    dataOutputStream.writeUTF("Connect");
                    dataOutputStream.writeUTF(server);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                p.sendPluginMessage(Lobby.getPlugin(), "Bungeecord", byteArrayOutputStream.toByteArray());
                p.sendMessage(ChatColor.GREEN + "Connecting to server....");
            }
        }
    }
}
