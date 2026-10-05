package com.skd.carrymechanics.client;

import com.skd.carrymechanics.client.keybinds.CarryKeybinds;
import com.skd.carrymechanics.carry.CarryDataManager;
import com.skd.carrymechanics.networking.ClientboundStartRidingOtherPlayerPacket;
import com.skd.carrymechanics.networking.ClientboundStartRidingPacket;
import com.skd.carrymechanics.networking.ClientboundSyncCarryDataPacket;
import com.skd.carrymechanics.networking.ClientboundSyncScriptsPacket;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public class CarryMechanicsClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        KeyMappingHelper.registerKeyMapping(CarryKeybinds.CARRY_KEY);

        ClientPlayNetworking.registerGlobalReceiver(ClientboundStartRidingPacket.TYPE, (packet, context) ->
                context.client().execute(() -> {
                    Player player = context.player();
                    Entity entity = player.level().getEntity(packet.entityId());
                    if (entity != null) {
                        if (packet.ride()) {
                            entity.startRiding(player, true, false);
                        } else {
                            entity.stopRiding();
                        }
                    }
                }));

        ClientPlayNetworking.registerGlobalReceiver(ClientboundStartRidingOtherPlayerPacket.TYPE, (packet, context) ->
                context.client().execute(() -> {
                    var level = Minecraft.getInstance().level;
                    if (level == null) return;
                    Entity carrier = level.getEntity(packet.carrierId());
                    Entity passenger = level.getEntity(packet.passengerId());
                    if (carrier != null && passenger != null) {
                        if (packet.startRiding()) {
                            passenger.startRiding(carrier, true, false);
                        } else {
                            passenger.stopRiding();
                        }
                    }
                }));

        ClientPlayNetworking.registerGlobalReceiver(ClientboundSyncScriptsPacket.TYPE, (packet, context) ->
                context.client().execute(() -> ClientboundSyncScriptsPacket.apply(packet)));

        ClientPlayNetworking.registerGlobalReceiver(ClientboundSyncCarryDataPacket.TYPE, (packet, context) ->
                context.client().execute(() -> CarryDataManager.set(packet.playerUuid(), packet.data())));

        ClientTickEvents.END_CLIENT_TICK.register(mc -> {
            Player player = mc.player;
            if (player == null) return;
            var data = CarryDataManager.getCarryData(player);
            if (data.isCarrying()) {
                player.getInventory().setSelectedSlot(data.getSelected());
            }
        });
    }
}
