package com.skd.carrymechanics.carry;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;

public class CarryDataSyncHandler {

    public static void write(FriendlyByteBuf buf, CompoundTag data) {
        buf.writeNbt(data);
    }

    public static CompoundTag read(FriendlyByteBuf buf) {
        CompoundTag tag = buf.readNbt();
        return tag != null ? tag : new CompoundTag();
    }
}
