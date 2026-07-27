package com.skd.carrymechanics.client.keybinds;

import net.minecraft.client.KeyMapping;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.settings.KeyConflictContext;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import com.skd.carrymechanics.CarryMechanics;
import com.mojang.blaze3d.platform.InputConstants;
import org.lwjgl.glfw.GLFW;

@EventBusSubscriber(modid = CarryMechanics.MODID, value = Dist.CLIENT)
public class CarryKeybinds {

    public static final KeyMapping CARRY_KEY = new KeyMapping(
            "key.carry_mechanics.carry",
            GLFW.GLFW_KEY_LEFT_SHIFT,
            KeyMapping.Category.MISC
    );

    @SubscribeEvent
    public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(CARRY_KEY);
    }
}