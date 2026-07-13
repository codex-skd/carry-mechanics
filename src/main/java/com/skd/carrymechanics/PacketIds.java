package com.skd.carrymechanics;

import com.skd.carrymechanics.networking.ServerboundCarryKeyPressedPacket;
import com.skd.carrymechanics.networking.ClientboundStartRidingPacket;
import com.skd.carrymechanics.networking.ClientboundSyncScriptsPacket;
import com.skd.carrymechanics.networking.ClientboundStartRidingOtherPlayerPacket;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public class PacketIds {
    public static final CustomPacketPayload.Type<ServerboundCarryKeyPressedPacket> KEY_PRESSED = id("key_pressed");
    public static final CustomPacketPayload.Type<ClientboundStartRidingPacket> START_RIDING = id("start_riding");
    public static final CustomPacketPayload.Type<ClientboundSyncScriptsPacket> SYNC_SCRIPTS = id("sync_scripts");
    public static final CustomPacketPayload.Type<ClientboundStartRidingOtherPlayerPacket> START_RIDING_OTHER = id("start_riding_other");

    private static <T extends CustomPacketPayload> CustomPacketPayload.Type<T> id(String path) {
        return new CustomPacketPayload.Type<>(Identifier.parse(CarryMechanics.MODID + ":" + path));
    }
}