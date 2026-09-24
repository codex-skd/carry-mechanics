package com.skd.carrymechanics.networking;

import com.skd.carrymechanics.PacketIds;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class NetworkHelper {

    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            PacketIds.CHANNEL,
            () -> PacketIds.PROTOCOL_VERSION,
            PacketIds.PROTOCOL_VERSION::equals,
            PacketIds.PROTOCOL_VERSION::equals);

    public static void registerPackets() {
        CHANNEL.messageBuilder(ClientboundStartRidingPacket.class, PacketIds.START_RIDING, NetworkDirection.PLAY_TO_CLIENT)
                .encoder(ClientboundStartRidingPacket::encode)
                .decoder(ClientboundStartRidingPacket::decode)
                .consumerMainThread(ClientboundStartRidingPacket::handle)
                .add();

        CHANNEL.messageBuilder(ClientboundSyncScriptsPacket.class, PacketIds.SYNC_SCRIPTS, NetworkDirection.PLAY_TO_CLIENT)
                .encoder(ClientboundSyncScriptsPacket::encode)
                .decoder(ClientboundSyncScriptsPacket::decode)
                .consumerMainThread(ClientboundSyncScriptsPacket::handle)
                .add();

        CHANNEL.messageBuilder(ClientboundStartRidingOtherPlayerPacket.class, PacketIds.START_RIDING_OTHER, NetworkDirection.PLAY_TO_CLIENT)
                .encoder(ClientboundStartRidingOtherPlayerPacket::encode)
                .decoder(ClientboundStartRidingOtherPlayerPacket::decode)
                .consumerMainThread(ClientboundStartRidingOtherPlayerPacket::handle)
                .add();

        CHANNEL.messageBuilder(ClientboundSyncCarryDataPacket.class, PacketIds.SYNC_CARRY_DATA, NetworkDirection.PLAY_TO_CLIENT)
                .encoder(ClientboundSyncCarryDataPacket::encode)
                .decoder(ClientboundSyncCarryDataPacket::decode)
                .consumerMainThread(ClientboundSyncCarryDataPacket::handle)
                .add();
    }

    public static void sendToPlayer(ServerPlayer player, Object packet) {
        CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), packet);
    }

    public static void sendToTrackingAndSelf(ServerPlayer player, Object packet) {
        CHANNEL.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> player), packet);
    }

    public static void sendToAllPlayers(ServerLevel level, Object packet) {
        for (ServerPlayer player : level.players()) {
            sendToPlayer(player, packet);
        }
    }
}
