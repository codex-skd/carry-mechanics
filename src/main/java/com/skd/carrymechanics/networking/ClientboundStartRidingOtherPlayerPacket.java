package com.skd.carrymechanics.networking;

import com.skd.carrymechanics.PacketIds;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

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
}
