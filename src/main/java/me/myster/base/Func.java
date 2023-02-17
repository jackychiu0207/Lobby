package me.myster.base;

import com.mojang.authlib.GameProfile;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.level.WorldServer;
import org.bukkit.event.Listener;

import java.util.UUID;

import static me.myster.base.Var.*;

public abstract class Func {
    public Func() {
    }

    public static void registerEvents(Listener listener) {
        plugin.getServer().getPluginManager().registerEvents(listener, plugin);
    }

    public static WorldServer getNMSWorld(String name) {
        return ((WorldServer) server.getWorld(name));
    }

    public static EntityPlayer newNPC(String worldName, String npcName, double[] location, float[] facing) {
        if (facing.length == 0) {
            facing = new float[]{0f, 0f};
        }
        WorldServer nmsWorld = getNMSWorld(worldName);
        EntityPlayer npc = new EntityPlayer(nmsServer, nmsWorld, new GameProfile(UUID.randomUUID(), npcName));
        npc.teleportTo(nmsWorld, location[0], location[1], location[2], facing[0], facing[1], null);
        return npc;
    }
}
