package com.skd.carrymechanics.mixin;

import com.skd.carrymechanics.carry.CarryDataManager;
import com.skd.carrymechanics.client.render.ICarryOnRenderState;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AvatarRenderer.class)
public class AvatarExtractorMixin {

    @Inject(method = "extractRenderState",
            at = @At("TAIL"))
    private void onExtractRenderState(AbstractClientPlayer entity, AvatarRenderState state, float partialTicks, CallbackInfo ci) {
        var data = CarryDataManager.getCarryData(entity);
        ((ICarryOnRenderState) state).carry_mechanics$setCarryData(data);
    }
}