package com.skd.carrymechanics;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = CarryMechanics.MODID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = CarryMechanics.MODID, value = Dist.CLIENT)
public class CarryMechanicsClient {

    public CarryMechanicsClient(ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    @SubscribeEvent
    static void onClientSetup(FMLClientSetupEvent event) {
        CarryMechanics.LOGGER.info("Carry Mechanics client initialized.");
    }
}