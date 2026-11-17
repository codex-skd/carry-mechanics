package com.skd.carrymechanics.networking;

import com.skd.carrymechanics.PacketIds;
import com.skd.carrymechanics.carry.CarryData;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

import java.util.UUID;

public record ClientboundSyncCarryDataPacket(UUID playerUuid, CarryData data) implements CustomPacketPayload {

    public static final StreamCodec<RegistryFriendlyByteBuf, ClientboundSyncCarryDataPacket> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.STRING_UTF8.map(UUID::fromString, UUID::toString), ClientboundSyncCarryDataPacket::playerUuid,
                    CarryData.STREAM_CODEC, ClientboundSyncCarryDataPacket::data,
                    ClientboundSyncCarryDataPacket::new
            );

    public static final CustomPacketPayload.Type<ClientboundSyncCarryDataPacket> TYPE = PacketIds.SYNC_CARRY_DATA;

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() { return TYPE; }
}
