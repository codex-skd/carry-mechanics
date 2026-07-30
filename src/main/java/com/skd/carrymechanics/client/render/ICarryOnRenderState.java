package com.skd.carrymechanics.client.render;

import com.skd.carrymechanics.carry.CarryData;
import net.minecraft.client.renderer.item.ItemStackRenderState;

public interface ICarryOnRenderState {
    CarryData carry_mechanics$getCarryData();
    void carry_mechanics$setCarryData(CarryData data);
    ItemStackRenderState carry_mechanics$getCarriedBlockItemState();
}
