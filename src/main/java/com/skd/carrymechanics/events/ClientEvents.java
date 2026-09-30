package com.skd.carrymechanics.events;

import com.skd.carrymechanics.CarryMechanics;
import com.skd.carrymechanics.carry.CarryData;
import com.skd.carrymechanics.carry.CarryDataManager;
import com.skd.carrymechanics.client.render.CarriedObjectRender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.InteractionHand;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.event.TickEvent;

@Mod.EventBusSubscriber(modid = CarryMechanics.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.START) return;
        Minecraft mc = Minecraft.getInstance();
        var player = mc.player;
        if (player == null) return;

        CarryData data = CarryDataManager.getCarryData(player);
        if (data.isCarrying()) {
            player.getInventory().selected = data.getSelected();
        }
    }

    @SubscribeEvent
    public static void onRenderHand(RenderHandEvent event) {
        Minecraft mc = Minecraft.getInstance();
        var player = mc.player;
        if (player == null) return;

        CarryData data = CarryDataManager.getCarryData(player);
        if (!data.isCarrying()) return;

        if (event.getHand() == InteractionHand.MAIN_HAND) {
            MultiBufferSource buffer = event.getMultiBufferSource();
            CarriedObjectRender.draw(player, event.getPoseStack(), event.getPackedLight(), buffer, true);
        }
        event.setCanceled(true);
    }
}
