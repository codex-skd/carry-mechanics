package com.skd.carrymechanics.networking;

import com.skd.carrymechanics.PacketIds;
import com.skd.carrymechanics.carry.CarryDataManager;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record ServerboundCarryKeyPressedPacket(boolean pressed) implements CustomPacketPayload {

    public static final StreamCodec<RegistryFriendlyByteBuf, ServerboundCarryKeyPressedPacket> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.BOOL, ServerboundCarryKeyPressedPacket::pressed,
                    ServerboundCarryKeyPressedPacket::new
            );

    public static final CustomPacketPayload.Type<ServerboundCarryKeyPressedPacket> TYPE = PacketIds.KEY_PRESSED;

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() { return TYPE; }

    public static void handle(ServerboundCarryKeyPressedPacket packet, IPayloadContext context) {
        context.enqueueWork(() -> {
            if (context.player() instanceof ServerPlayer player) {
                var data = CarryDataManager.getCarryData(player);
                data.setKeyPressed(packet.pressed());
                CarryDataManager.setCarryData(player, data);
            }
        });
    }
}