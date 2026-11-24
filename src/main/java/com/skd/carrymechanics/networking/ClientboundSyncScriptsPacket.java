package com.skd.carrymechanics.networking;

import com.skd.carrymechanics.PacketIds;
import com.skd.carrymechanics.scripting.CarryScript;
import com.skd.carrymechanics.scripting.ScriptManager;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record ClientboundSyncScriptsPacket(Tag serialized) implements CustomPacketPayload {

    public static final StreamCodec<RegistryFriendlyByteBuf, ClientboundSyncScriptsPacket> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.TAG, ClientboundSyncScriptsPacket::serialized,
                    ClientboundSyncScriptsPacket::new
            );

    public static final CustomPacketPayload.Type<ClientboundSyncScriptsPacket> TYPE = PacketIds.SYNC_SCRIPTS;

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() { return TYPE; }

    public static void apply(ClientboundSyncScriptsPacket packet) {
        var scripts = CarryScript.CODEC.listOf()
                .parse(NbtOps.INSTANCE, packet.serialized())
                .getOrThrow(msg -> new RuntimeException("Script sync: " + msg));
        ScriptManager.setScripts(scripts);
    }
}
