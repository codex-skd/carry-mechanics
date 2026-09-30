package com.skd.carrymechanics.client.keybinds;

import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import com.skd.carrymechanics.CarryMechanics;
import com.mojang.blaze3d.platform.InputConstants;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(modid = CarryMechanics.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class CarryKeybinds {

    public static final KeyMapping CARRY_KEY = new KeyMapping(
            "key.carry_mechanics.carry",
            GLFW.GLFW_KEY_LEFT_SHIFT,
            "key.categories.misc"
    );

    @SubscribeEvent
    public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(CARRY_KEY);
    }
}