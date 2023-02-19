package me.myster.teleport;

import me.myster.Lobby;
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
import org.bukkit.plugin.java.JavaPlugin;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

import static me.myster.base.Func.registerEvents;
import static org.bukkit.Bukkit.createInventory;

public class TeleportMenu implements Listener {

    public TeleportMenu() {

        registerEvents(this);
        // Create a new inventory, with no owner (as this isn't a real inventory), a size of nine, called example
        inv = createInventory(null, 9, "傳送選單");
        initializeItems();

    }

    private final Inventory inv;

    @EventHandler
    public void teleportMenu(PlayerInteractEvent e) {
        if (e.getAction() != Action.PHYSICAL)
            if (e.getItem() != null && e.getItem().getType().equals(Material.COMPASS)) {
                TeleportMenu tpMenu = new TeleportMenu();
                tpMenu.openInventory(e.getPlayer());
            }
    }

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
        Objects.requireNonNull(meta).setDisplayName(name);
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
        // verify current item is not null
        if (clickedItem == null || clickedItem.getType().isAir()) return;
        final Material itemType = clickedItem.getType();
        String server;
        final Player p = (Player) e.getWhoClicked();
        if (itemType.equals(Material.BEACON)) {
            server = "lobby";
        } else if (itemType.equals(Material.NETHERITE_SWORD)) {
            server = "survival";
        } else if (itemType.equals(Material.ENDER_EYE)) {
            server = "hide_and_seek";
        } else {
            server = "none";
        }
        if (! server.equals("none")) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
            try {
                dataOutputStream.writeUTF("Connect");
                dataOutputStream.writeUTF(server);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            p.sendPluginMessage(JavaPlugin.getPlugin(Lobby.class), "BungeeCord", byteArrayOutputStream.toByteArray());
        }
    }
}
