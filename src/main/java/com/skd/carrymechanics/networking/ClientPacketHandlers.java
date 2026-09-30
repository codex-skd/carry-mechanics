package com.skd.carrymechanics.networking;

import com.skd.carrymechanics.carry.CarryData;
import com.skd.carrymechanics.carry.CarryDataCapability;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

/**
 * Client-only packet logic. Kept apart from the packet classes so a dedicated
 * server never loads net.minecraft.client classes.
 */
public class ClientPacketHandlers {

    public static void handleStartRiding(ClientboundStartRidingPacket packet) {
        Player player = Minecraft.getInstance().player;
        if (player == null) return;
        Entity entity = player.level().getEntity(packet.entityId());
        if (entity != null) {
            if (packet.ride()) {
                entity.startRiding(player, true);
            } else {
                entity.stopRiding();
            }
        }
    }

    public static void handleStartRidingOther(ClientboundStartRidingOtherPlayerPacket packet) {
        var level = Minecraft.getInstance().level;
        if (level == null) return;
        Entity carrier = level.getEntity(packet.carrierId());
        Entity passenger = level.getEntity(packet.passengerId());
        if (carrier != null && passenger != null) {
            if (packet.startRiding()) {
                passenger.startRiding(carrier, true);
            } else {
                passenger.stopRiding();
            }
        }
    }

    public static void handleSyncCarryData(ClientboundSyncCarryDataPacket packet) {
        var level = Minecraft.getInstance().level;
        if (level == null) return;
        if (level.getEntity(packet.entityId()) instanceof Player player) {
            player.getCapability(CarryDataCapability.CARRY_DATA)
                    .ifPresent(holder -> holder.set(new CarryData(packet.data())));
        }
    }
}
