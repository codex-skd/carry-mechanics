package com.skd.carrymechanics.networking;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record ClientboundStartRidingPacket(int entityId, boolean ride) {

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(entityId);
        buf.writeBoolean(ride);
    }

    public static ClientboundStartRidingPacket decode(FriendlyByteBuf buf) {
        return new ClientboundStartRidingPacket(buf.readInt(), buf.readBoolean());
    }

    public static void handle(ClientboundStartRidingPacket packet, Supplier<NetworkEvent.Context> context) {
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientPacketHandlers.handleStartRiding(packet));
        context.get().setPacketHandled(true);
    }
}
