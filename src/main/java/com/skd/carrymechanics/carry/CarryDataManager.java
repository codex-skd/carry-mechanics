package com.skd.carrymechanics.carry;

import com.skd.carrymechanics.networking.ClientboundSyncCarryDataPacket;
import com.skd.carrymechanics.networking.NetworkHelper;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public class CarryDataManager {

    public static CarryData getCarryData(Player player) {
        return player.getCapability(CarryMechanicsAccess.getCapability())
                .map(CarryDataHolder::get)
                .orElseGet(CarryData::new);
    }

    public static void setCarryData(Player player, CarryData data) {
        data.setSelected(player.getInventory().selected);
        data.setTick(player.tickCount);
        player.getCapability(CarryMechanicsAccess.getCapability()).ifPresent(holder -> holder.set(data));
        if (player instanceof ServerPlayer serverPlayer) {
            sync(serverPlayer);
        }
    }

    /** Sends the player's CarryData to itself and to every client tracking it. */
    public static void sync(ServerPlayer player) {
        NetworkHelper.sendToTrackingAndSelf(player, createSyncPacket(player));
    }

    public static ClientboundSyncCarryDataPacket createSyncPacket(Player player) {
        return new ClientboundSyncCarryDataPacket(player.getId(), getCarryData(player).getFullNbt().copy());
    }
}
