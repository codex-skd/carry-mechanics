package com.skd.carrymechanics.client.keybinds;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;

public class CarryKeybinds {

    public static final KeyMapping CARRY_KEY = new KeyMapping(
            "key.carry_mechanics.carry",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_LEFT_SHIFT,
            KeyMapping.Category.MISC
    );
}
