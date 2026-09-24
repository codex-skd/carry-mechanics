package com.skd.carrymechanics;

import com.mojang.logging.LogUtils;
import com.skd.carrymechanics.carry.CarryData;
import com.skd.carrymechanics.config.CarryConfig;
import com.skd.carrymechanics.networking.ClientboundStartRidingPacket;
import com.skd.carrymechanics.networking.ClientboundSyncScriptsPacket;
import com.skd.carrymechanics.networking.ClientboundStartRidingOtherPlayerPacket;
import com.skd.carrymechanics.scripting.ScriptReloadListener;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.slf4j.Logger;
import java.util.function.Supplier;

@Mod(CarryMechanics.MODID)
public class CarryMechanics {
    public static final String MODID = "carry_mechanics";
    public static final String MOD_NAME = "Carry Mechanics";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES =
            DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, MODID);

    public static final Supplier<AttachmentType<CarryData>> CARRY_DATA =
            ATTACHMENT_TYPES.register("carry_data",
                    () -> AttachmentType.<CarryData>builder((Supplier<CarryData>) CarryData::new)
                            .serialize(CarryData.CODEC)
                            .sync(CarryData.STREAM_CODEC)
                            .build());

    public CarryMechanics(IEventBus modEventBus, ModContainer modContainer) {
        ATTACHMENT_TYPES.register(modEventBus);

        modEventBus.addListener(RegisterPayloadHandlersEvent.class, this::registerPackets);

        CarryConfig.init();
        modContainer.registerConfig(ModConfig.Type.COMMON, CarryConfig.COMMON_SPEC);
        modContainer.registerConfig(ModConfig.Type.CLIENT, CarryConfig.CLIENT_SPEC);

        NeoForge.EVENT_BUS.register(this);

        ScriptReloadListener.register();
    }

    private void registerPackets(final RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar("1.0");

        registrar.playToClient(
                ClientboundStartRidingPacket.TYPE,
                ClientboundStartRidingPacket.STREAM_CODEC,
                ClientboundStartRidingPacket::handle
        );

        registrar.playToClient(
                ClientboundSyncScriptsPacket.TYPE,
                ClientboundSyncScriptsPacket.STREAM_CODEC,
                ClientboundSyncScriptsPacket::handle
        );

        registrar.playToClient(
                ClientboundStartRidingOtherPlayerPacket.TYPE,
                ClientboundStartRidingOtherPlayerPacket.STREAM_CODEC,
                ClientboundStartRidingOtherPlayerPacket::handle
        );
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("Carry Mechanics loaded on server.");
    }
}