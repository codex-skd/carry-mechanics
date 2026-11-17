package com.skd.carrymechanics.networking;

import com.skd.carrymechanics.PacketIds;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.PacketDistributor;

public class NetworkHelper {

    public static void sendToAllPlayers(ServerLevel level, CustomPacketPayload packet) {
        for (ServerPlayer player : level.players()) {
            PacketDistributor.sendToPlayer(player, packet);
        }
    }
}