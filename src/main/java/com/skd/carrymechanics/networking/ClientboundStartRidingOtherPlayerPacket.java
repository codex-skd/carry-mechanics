package com.skd.carrymechanics.networking;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record ClientboundStartRidingOtherPlayerPacket(int carrierId, int passengerId, boolean startRiding) {

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(carrierId);
        buf.writeInt(passengerId);
        buf.writeBoolean(startRiding);
    }

    public static ClientboundStartRidingOtherPlayerPacket decode(FriendlyByteBuf buf) {
        return new ClientboundStartRidingOtherPlayerPacket(buf.readInt(), buf.readInt(), buf.readBoolean());
    }

    public static void handle(ClientboundStartRidingOtherPlayerPacket packet, Supplier<NetworkEvent.Context> context) {
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientPacketHandlers.handleStartRidingOther(packet));
        context.get().setPacketHandled(true);
    }
}
