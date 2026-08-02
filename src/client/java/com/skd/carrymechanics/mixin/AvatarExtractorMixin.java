package com.skd.carrymechanics.mixin;

import com.skd.carrymechanics.carry.CarryDataManager;
import com.skd.carrymechanics.client.render.ICarryOnRenderState;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.world.entity.Avatar;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AvatarRenderer.class)
public class AvatarExtractorMixin {

    @Inject(method = "extractRenderState",
            at = @At("TAIL"))
    private void onExtractRenderState(Avatar entity, AvatarRenderState state, float partialTicks, CallbackInfo ci) {
        if (entity instanceof Player player) {
            var data = CarryDataManager.getCarryData(player);
            if (state instanceof ICarryOnRenderState carryState) {
                carryState.carry_mechanics$setCarryData(data);
                carryState.carry_mechanics$setPlayer(player);
            }
            if (data.isCarrying()) {
                state.pose = Pose.CROUCHING;
                state.isCrouching = true;
            }
        }
    }
}
