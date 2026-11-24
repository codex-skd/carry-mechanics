package com.skd.carrymechanics;

import com.skd.carrymechanics.client.gui.CarryConfigScreen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = CarryMechanics.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class CarryMechanicsClient {

    public static void init() {
        ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class,
                () -> new ConfigScreenHandler.ConfigScreenFactory((mc, parent) -> new CarryConfigScreen(parent)));
    }

    @SubscribeEvent
    static void onClientSetup(FMLClientSetupEvent event) {
        CarryMechanics.LOGGER.info("Carry Mechanics client initialized.");
    }
}
