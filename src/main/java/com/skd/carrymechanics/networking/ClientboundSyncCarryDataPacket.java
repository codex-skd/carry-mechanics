package com.skd.carrymechanics.networking;

import com.skd.carrymechanics.carry.CarryDataSyncHandler;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * Replaces NeoForge's automatic attachment sync: sends a player's CarryData to itself
 * and to every client tracking it, so carried objects render in third person too.
 */
public record ClientboundSyncCarryDataPacket(int entityId, CompoundTag data) {

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(entityId);
        CarryDataSyncHandler.write(buf, data);
    }

    public static ClientboundSyncCarryDataPacket decode(FriendlyByteBuf buf) {
        return new ClientboundSyncCarryDataPacket(buf.readInt(), CarryDataSyncHandler.read(buf));
    }

    public static void handle(ClientboundSyncCarryDataPacket packet, Supplier<NetworkEvent.Context> context) {
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientPacketHandlers.handleSyncCarryData(packet));
        context.get().setPacketHandled(true);
    }
}
