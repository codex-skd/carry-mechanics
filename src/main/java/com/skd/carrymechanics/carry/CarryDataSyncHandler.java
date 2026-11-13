package com.skd.carrymechanics.carry;

import com.skd.carrymechanics.networking.ClientboundSyncCarryDataPacket;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.level.ServerPlayer;

public class CarryDataSyncHandler {

    public static void broadcast(ServerPlayer carrier) {
        ClientboundSyncCarryDataPacket packet =
                new ClientboundSyncCarryDataPacket(carrier.getUUID(), CarryDataManager.get(carrier.getUUID()));
        var server = ((net.minecraft.server.level.ServerLevel) carrier.level()).getServer();
        if (server == null) return;
        for (ServerPlayer p : server.getPlayerList().getPlayers()) {
            ServerPlayNetworking.send(p, packet);
        }
    }

    public static void syncOnJoin(ServerPlayer joined) {
        for (var entry : CarryDataManager.entrySet()) {
            if (entry.getValue().isCarrying()) {
                ServerPlayNetworking.send(joined,
                        new ClientboundSyncCarryDataPacket(entry.getKey(), entry.getValue()));
            }
        }
    }
}
