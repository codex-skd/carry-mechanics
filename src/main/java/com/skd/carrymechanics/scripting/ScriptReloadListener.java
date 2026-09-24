package com.skd.carrymechanics.scripting;

import com.skd.carrymechanics.CarryMechanics;
import com.skd.carrymechanics.networking.ClientboundSyncScriptsPacket;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManager;

public class ScriptReloadListener {

    public static void register() {
        ResourceManagerHelper.get(PackType.SERVER_DATA)
                .registerReloadListener(new SimpleSynchronousResourceReloadListener() {
                    @Override
                    public Identifier getFabricId() {
                        return Identifier.parse("carry_mechanics:scripts");
                    }

                    @Override
                    public void onResourceManagerReload(ResourceManager manager) {
                        ScriptReloadListener.onResourceManagerReload(manager);
                    }
                });

        ServerLifecycleEvents.SYNC_DATA_PACK_CONTENTS.register((player, joined) -> syncToClient(player));
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
        ServerPlayNetworking.send(player, new ClientboundSyncScriptsPacket(serialized));
    }
}
