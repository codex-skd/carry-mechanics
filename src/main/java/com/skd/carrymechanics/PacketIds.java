package com.skd.carrymechanics;

import net.minecraft.resources.ResourceLocation;

public class PacketIds {
    public static final ResourceLocation CHANNEL = new ResourceLocation(CarryMechanics.MODID, "main");
    public static final String PROTOCOL_VERSION = "1.0";

    public static final int START_RIDING = 0;
    public static final int SYNC_SCRIPTS = 1;
    public static final int START_RIDING_OTHER = 2;
    public static final int SYNC_CARRY_DATA = 3;
}
