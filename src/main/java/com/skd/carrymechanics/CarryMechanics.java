package com.skd.carrymechanics;

import com.mojang.logging.LogUtils;
import com.skd.carrymechanics.carry.CarryDataCapability;
import com.skd.carrymechanics.config.CarryConfig;
import com.skd.carrymechanics.networking.NetworkHelper;
import com.skd.carrymechanics.scripting.ScriptReloadListener;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.event.server.ServerStartingEvent;
import org.slf4j.Logger;

@Mod(CarryMechanics.MODID)
public class CarryMechanics {
    public static final String MODID = "carry_mechanics";
    public static final String MOD_NAME = "Carry Mechanics";
    public static final Logger LOGGER = LogUtils.getLogger();

    public CarryMechanics() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(CarryDataCapability::register);

        CarryConfig.init();
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CarryConfig.COMMON_SPEC);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, CarryConfig.CLIENT_SPEC);

        MinecraftForge.EVENT_BUS.register(this);

        ScriptReloadListener.register();

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> CarryMechanicsClient::init);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(NetworkHelper::registerPackets);
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("Carry Mechanics loaded on server.");
    }
}
