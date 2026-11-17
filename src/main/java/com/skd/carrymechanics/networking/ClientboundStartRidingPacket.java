package com.skd.carrymechanics.networking;

import com.skd.carrymechanics.PacketIds;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record ClientboundStartRidingPacket(int entityId, boolean ride) implements CustomPacketPayload {

    public static final StreamCodec<RegistryFriendlyByteBuf, ClientboundStartRidingPacket> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.INT, ClientboundStartRidingPacket::entityId,
                    ByteBufCodecs.BOOL, ClientboundStartRidingPacket::ride,
                    ClientboundStartRidingPacket::new
            );

    public static final CustomPacketPayload.Type<ClientboundStartRidingPacket> TYPE = PacketIds.START_RIDING;

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() { return TYPE; }
}
