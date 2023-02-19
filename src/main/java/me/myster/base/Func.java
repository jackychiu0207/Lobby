package me.myster.base;

import com.mojang.authlib.GameProfile;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.level.WorldServer;
import net.minecraft.server.network.PlayerConnection;
import org.bukkit.craftbukkit.v1_19_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_19_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.Objects;
import java.util.UUID;

import static me.myster.base.Var.*;

public abstract class Func {
    public Func() {
    }

    public static void registerEvents(Listener listener) {
        plugin.getServer().getPluginManager().registerEvents(listener, plugin);
    }

    public static WorldServer getNMSWorld(String name) {
        return ((CraftWorld) Objects.requireNonNull(server.getWorld(name))).getHandle();
    }

    public static EntityPlayer newNPC(String worldName, String npcName, double[] location, float[] facing) {
        WorldServer nmsWorld = getNMSWorld(worldName);
        EntityPlayer npc = new EntityPlayer(nmsServer, nmsWorld, new GameProfile(UUID.randomUUID(), npcName));
        npc.teleportTo(nmsWorld, location[0], location[1], location[2], facing[0], facing[1], null);
        return npc;
    }

    public static EntityPlayer newNPC(String worldName, String npcName, double[] location) {
        WorldServer nmsWorld = getNMSWorld(worldName);
        EntityPlayer npc = new EntityPlayer(nmsServer, nmsWorld, new GameProfile(UUID.randomUUID(), npcName));
        npc.teleportTo(nmsWorld, location[0], location[1], location[2], 0f, 0f, null);
        return npc;
    }

    public static void addNPCPacket(EntityPlayer npc, Player player) {
        PlayerConnection connection = ((CraftPlayer) player).getHandle().b;
        connection.a(new ClientboundPlayerInfoUpdatePacket(ClientboundPlayerInfoUpdatePacket.a.a, npc));
    }
}
