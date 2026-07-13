package com.skd.carrymechanics.events;

import com.skd.carrymechanics.CarryMechanics;
import com.skd.carrymechanics.carry.CarryData;
import com.skd.carrymechanics.carry.CarryDataManager;
import com.skd.carrymechanics.client.keybinds.CarryKeybinds;
import com.skd.carrymechanics.networking.ServerboundCarryKeyPressedPacket;
import net.minecraft.client.Minecraft;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;

@EventBusSubscriber(modid = CarryMechanics.MODID, value = Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Pre event) {
        Minecraft mc = Minecraft.getInstance();
        var player = mc.player;
        if (player == null) return;

        if (CarryKeybinds.CARRY_KEY.consumeClick()) {
            ClientPacketDistributor.sendToServer(new ServerboundCarryKeyPressedPacket(CarryKeybinds.CARRY_KEY.isDown()));
        }

        CarryData data = CarryDataManager.getCarryData(player);
        if (data.isCarrying()) {
            player.getInventory().setSelectedSlot(data.getSelected());
        }
    }
}