package com.skd.carrymechanics.networking;

import com.skd.carrymechanics.PacketIds;
import net.minecraft.client.Minecraft;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record ClientboundStartRidingOtherPlayerPacket(int carrierId, int passengerId, boolean startRiding) implements CustomPacketPayload {

    public static final StreamCodec<RegistryFriendlyByteBuf, ClientboundStartRidingOtherPlayerPacket> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.INT, ClientboundStartRidingOtherPlayerPacket::carrierId,
                    ByteBufCodecs.INT, ClientboundStartRidingOtherPlayerPacket::passengerId,
                    ByteBufCodecs.BOOL, ClientboundStartRidingOtherPlayerPacket::startRiding,
                    ClientboundStartRidingOtherPlayerPacket::new
            );

    public static final CustomPacketPayload.Type<ClientboundStartRidingOtherPlayerPacket> TYPE = PacketIds.START_RIDING_OTHER;

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() { return TYPE; }

    public static void handle(ClientboundStartRidingOtherPlayerPacket packet, IPayloadContext context) {
        context.enqueueWork(() -> {
            var level = Minecraft.getInstance().level;
            if (level == null) return;
            Entity carrier = level.getEntity(packet.carrierId());
            Entity passenger = level.getEntity(packet.passengerId());
            if (carrier != null && passenger != null) {
                if (packet.startRiding()) {
                    passenger.startRiding(carrier, true, false);
                } else {
                    passenger.stopRiding();
                }
            }
        });
    }
}