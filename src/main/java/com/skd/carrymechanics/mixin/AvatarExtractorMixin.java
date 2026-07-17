package com.skd.carrymechanics.mixin;

import com.skd.carrymechanics.carry.CarryDataManager;
import com.skd.carrymechanics.client.render.ICarryOnRenderState;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.world.entity.Avatar;
import net.minecraft.world.entity.player.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AvatarRenderer.class)
public class AvatarExtractorMixin {
    private static final Logger LOGGER = LoggerFactory.getLogger("CarryMechanics");

    @Inject(method = "extractRenderState",
            at = @At("TAIL"))
    private void onExtractRenderState(Avatar entity, AvatarRenderState state, float partialTicks, CallbackInfo ci) {
        LOGGER.info("[DEBUG] extractRenderState called. entity={} stateClass={}", entity, state.getClass().getName());
        if (entity instanceof Player player) {
            var data = CarryDataManager.getCarryData(player);
            LOGGER.info("[DEBUG] CarryData: type={} isCarrying={}", data.getType(), data.isCarrying());
            if (state instanceof ICarryOnRenderState carryState) {
                carryState.carry_mechanics$setCarryData(data);
                LOGGER.info("[DEBUG] CarryData set on render state");
            } else {
                LOGGER.info("[DEBUG] State does NOT implement ICarryOnRenderState! class={}", state.getClass().getName());
            }
        }
    }
}