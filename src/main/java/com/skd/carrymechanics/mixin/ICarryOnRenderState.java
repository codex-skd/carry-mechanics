package com.skd.carrymechanics.mixin;

import com.skd.carrymechanics.carry.CarryData;

public interface ICarryOnRenderState {
    CarryData carry_mechanics$getCarryData();
    void carry_mechanics$setCarryData(CarryData data);
}