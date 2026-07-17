package com.skd.carrymechanics.client.render;

import com.skd.carrymechanics.carry.CarryData;

public interface ICarryOnRenderState {
    CarryData carry_mechanics$getCarryData();
    void carry_mechanics$setCarryData(CarryData data);
    float carry_mechanics$getRenderWidth();
    void carry_mechanics$setRenderWidth(float width);
}
