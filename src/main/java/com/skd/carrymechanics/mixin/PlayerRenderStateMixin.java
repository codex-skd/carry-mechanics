package com.skd.carrymechanics.mixin;

import com.skd.carrymechanics.carry.CarryData;
import com.skd.carrymechanics.client.render.ICarryOnRenderState;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(AvatarRenderState.class)
public class PlayerRenderStateMixin implements ICarryOnRenderState {
    @Unique private CarryData carry_mechanics$data;
    @Unique private float carry_mechanics$width;

    @Override
    public CarryData carry_mechanics$getCarryData() { return carry_mechanics$data; }
    @Override
    public void carry_mechanics$setCarryData(CarryData data) { this.carry_mechanics$data = data; }
    @Override
    public float carry_mechanics$getRenderWidth() { return carry_mechanics$width; }
    @Override
    public void carry_mechanics$setRenderWidth(float w) { this.carry_mechanics$width = w; }
}