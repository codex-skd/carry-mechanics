package com.skd.carrymechanics;

import com.skd.carrymechanics.command.CommandCarryMechanics;
import com.skd.carrymechanics.config.CarryConfig;
import com.skd.carrymechanics.events.CommonEvents;
import com.skd.carrymechanics.networking.ClientboundStartRidingOtherPlayerPacket;
import com.skd.carrymechanics.networking.ClientboundStartRidingPacket;
import com.skd.carrymechanics.networking.ClientboundSyncCarryDataPacket;
import com.skd.carrymechanics.networking.ClientboundSyncScriptsPacket;
import com.skd.carrymechanics.scripting.ScriptReloadListener;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CarryMechanics implements ModInitializer {
    public static final String MODID = "carry_mechanics";
    public static final String MOD_NAME = "Carry Mechanics";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

    @Override
    public void onInitialize() {
        CarryConfig.load();

        PayloadTypeRegistry.clientboundPlay().register(ClientboundStartRidingPacket.TYPE, ClientboundStartRidingPacket.STREAM_CODEC);
        PayloadTypeRegistry.clientboundPlay().register(ClientboundStartRidingOtherPlayerPacket.TYPE, ClientboundStartRidingOtherPlayerPacket.STREAM_CODEC);
        PayloadTypeRegistry.clientboundPlay().register(ClientboundSyncScriptsPacket.TYPE, ClientboundSyncScriptsPacket.STREAM_CODEC);
        PayloadTypeRegistry.clientboundPlay().register(ClientboundSyncCarryDataPacket.TYPE, ClientboundSyncCarryDataPacket.STREAM_CODEC);

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) ->
                CommandCarryMechanics.register(dispatcher));

        CommonEvents.register();

        ScriptReloadListener.register();

        ServerLifecycleEvents.SERVER_STARTING.register(server ->
                LOGGER.info("Carry Mechanics loaded on server."));

        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) ->
                com.skd.carrymechanics.carry.CarryDataSyncHandler.syncOnJoin(handler.getPlayer()));
    }
}
