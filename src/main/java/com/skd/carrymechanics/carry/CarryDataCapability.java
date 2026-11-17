package com.skd.carrymechanics.carry;

import com.skd.carrymechanics.CarryMechanics;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.common.util.LazyOptional;

/**
 * Forge replacement for the NeoForge carry_data attachment.
 */
public class CarryDataCapability {

    public static final ResourceLocation ID = new ResourceLocation(CarryMechanics.MODID, "carry_data");

    public static final Capability<CarryDataHolder> CARRY_DATA = CapabilityManager.get(new CapabilityToken<>() {});

    public static void register(RegisterCapabilitiesEvent event) {
        event.register(CarryDataHolder.class);
    }

    public static class Provider implements ICapabilitySerializable<CompoundTag> {
        private final CarryDataHolder holder = new CarryDataHolder();
        private final LazyOptional<CarryDataHolder> optional = LazyOptional.of(() -> holder);

        @Override
        public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
            return CARRY_DATA.orEmpty(cap, optional);
        }

        @Override
        public CompoundTag serializeNBT() {
            return holder.get().getFullNbt().copy();
        }

        @Override
        public void deserializeNBT(CompoundTag tag) {
            holder.set(new CarryData(tag));
        }

        public void invalidate() {
            optional.invalidate();
        }
    }
}
