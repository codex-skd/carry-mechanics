package com.skd.carrymechanics.carry;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

public class CarryDataSyncHandler {

    public static final StreamCodec<FriendlyByteBuf, CarryData> STREAM_CODEC =
            StreamCodec.of(
                    (buf, data) -> buf.writeNbt(data.getFullNbt()),
                    buf -> {
                        CompoundTag tag = buf.readNbt();
                        return tag != null ? new CarryData(tag) : new CarryData();
                    }
            );
}