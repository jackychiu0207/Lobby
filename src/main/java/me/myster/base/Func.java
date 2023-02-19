package me.myster.base;

import com.mojang.authlib.GameProfile;
import net.minecraft.network.protocol.game.ClientboundAddPlayerPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket;
import net.minecraft.network.protocol.game.ClientboundRemoveEntitiesPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.bukkit.craftbukkit.v1_19_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_19_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static me.myster.base.Var.*;

public abstract class Func {
    public Func() {
    }

    public static void registerEvents(Listener listener) {
        plugin.getServer().getPluginManager().registerEvents(listener, plugin);
    }

    public static ServerLevel getNMSWorld(String name) {
        return ((CraftWorld) Objects.requireNonNull(server.getWorld(name))).getHandle();
    }

    public static ServerPlayer newNPC(String worldName, String npcName, double[] location) {
        ServerLevel nmsWorld = getNMSWorld(worldName);
        ServerPlayer npc = new ServerPlayer(nmsServer, nmsWorld, new GameProfile(UUID.randomUUID(), npcName));
        npc.absMoveTo(location[0], location[1], location[2]);
        return npc;
    }

    public static void sendNPCPacket(ServerPlayer npc, Player player) {
        ServerGamePacketListenerImpl connection = ((CraftPlayer) player).getHandle().connection;
        connection.send(new ClientboundPlayerInfoUpdatePacket(ClientboundPlayerInfoUpdatePacket.Action.ADD_PLAYER, npc));
        connection.send(new ClientboundAddPlayerPacket(npc));
    }

    public static void sendNPCPacket(ArrayList<ServerPlayer> npcList, Player player) {
        ServerGamePacketListenerImpl connection = ((CraftPlayer) player).getHandle().connection;
        for (int i = 0; i < npcList.size(); i++) {
            connection.send(new ClientboundPlayerInfoUpdatePacket(ClientboundPlayerInfoUpdatePacket.Action.ADD_PLAYER, npcList.get(i)));
            connection.send(new ClientboundAddPlayerPacket(npcList.get(i)));
        }
    }

    public static void removeNPCPacket(ServerPlayer npc, Player player) {
        ServerGamePacketListenerImpl connection = ((CraftPlayer) player).getHandle().connection;
        connection.send(new ClientboundRemoveEntitiesPacket(npc.getId()));
    }

    public static void removeNPCPacket(ArrayList<ServerPlayer> npcList, Player player) {
        ServerGamePacketListenerImpl connection = ((CraftPlayer) player).getHandle().connection;
        for (int i = 0; i < npcList.size(); i++) {
            connection.send(new ClientboundRemoveEntitiesPacket(npcList.get(i).getId()));
        }
    }

    public static void sendNPCToAllPlayer(ArrayList<ServerPlayer> npcList, String worldName) {
        List<Player> playerList = Objects.requireNonNull(server.getWorld(worldName)).getPlayers();
        for (int i = 0; i < npcList.size(); i++) {
            ServerPlayer npc = npcList.get(i);
            for (int ii = 0; ii < playerList.size(); ii++) {
                Player p = playerList.get(ii);
                sendNPCPacket(npc, p);
            }
        }
    }

    public static void sendNPCToAllPlayer(ServerPlayer npc, String worldName) {
        List<Player> playerList = Objects.requireNonNull(server.getWorld(worldName)).getPlayers();
        for (int ii = 0; ii < playerList.size(); ii++) {
            Player p = playerList.get(ii);
            System.out.println(p.getDisplayName());
            sendNPCPacket(npc, p);
        }
    }

    public static void removeNPCToAllPlayer(ServerPlayer npc, String worldName) {
        List<Player> playerList = Objects.requireNonNull(server.getWorld(worldName)).getPlayers();
        for (int ii = 0; ii < playerList.size(); ii++) {
            Player p = playerList.get(ii);
            System.out.println(p.getDisplayName());
            removeNPCPacket(npc, p);
        }
    }

    public static void removeNPCToAllPlayer(ArrayList<ServerPlayer> npcList, String worldName) {
        List<Player> playerList = Objects.requireNonNull(server.getWorld(worldName)).getPlayers();
        for (int i = 0; i < npcList.size(); i++) {
            ServerPlayer npc = npcList.get(i);
            for (int ii = 0; ii < playerList.size(); ii++) {
                Player p = playerList.get(ii);
                System.out.println(p.getDisplayName());
                removeNPCPacket(npc, p);
            }
        }
    }
}
