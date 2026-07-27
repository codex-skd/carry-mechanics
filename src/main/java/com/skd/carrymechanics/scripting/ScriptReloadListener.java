package com.skd.carrymechanics.scripting;

import com.skd.carrymechanics.CarryMechanics;
import com.skd.carrymechanics.networking.ClientboundSyncScriptsPacket;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.OnDatapackSyncEvent;
import net.neoforged.neoforge.network.PacketDistributor;

public class ScriptReloadListener {
    public static final ScriptReloadListener INSTANCE = new ScriptReloadListener();

    public static void register() {
        NeoForge.EVENT_BUS.addListener((OnDatapackSyncEvent event) -> {
            if (event.getPlayer() != null) {
                syncToClient(event.getPlayer());
            } else {
                event.getPlayerList().getPlayers().forEach(ScriptReloadListener::syncToClient);
            }
        });
    }

    public static void onResourceManagerReload(ResourceManager manager) {
        ScriptManager.SCRIPTS.clear();
        var scripts = new java.util.ArrayList<CarryScript>();
        var converter = new FileToIdConverter("scripts", ".json");

        converter.listMatchingResources(manager).forEach((id, resource) -> {
            try (var reader = resource.openAsReader()) {
                var element = com.google.gson.JsonParser.parseReader(reader);
                CarryScript script = CarryScript.CODEC.parse(
                        com.mojang.serialization.JsonOps.INSTANCE, element
                ).getOrThrow(msg -> new RuntimeException("Script parse: " + msg));
                scripts.add(script);
            } catch (Exception e) {
                CarryMechanics.LOGGER.error("Failed to load script {}: {}", id, e.getMessage());
            }
        });

        ScriptManager.setScripts(scripts);
        CarryMechanics.LOGGER.info("Loaded {} carry scripts.", scripts.size());
    }

    private static void syncToClient(ServerPlayer player) {
        Tag serialized = CarryScript.CODEC.listOf()
                .encodeStart(NbtOps.INSTANCE, ScriptManager.SCRIPTS)
                .getOrThrow(msg -> new RuntimeException("Failed to sync scripts: " + msg));
        PacketDistributor.sendToPlayer(player, new ClientboundSyncScriptsPacket(serialized));
    }
}