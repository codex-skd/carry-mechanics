package com.skd.carrymechanics.mixin;

import com.skd.carrymechanics.carry.CarryDataManager;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @Inject(method = "setJumping", at = @At("HEAD"), cancellable = true)
    private void onSetJumping(boolean jumping, CallbackInfo ci) {
        if (jumping && (Object) this instanceof Player player
                && CarryDataManager.getCarryData(player).isCarrying()) {
            ci.cancel();
        }
    }

    @Inject(method = "jumpFromGround", at = @At("HEAD"), cancellable = true)
    private void onJumpFromGround(CallbackInfo ci) {
        if ((Object) this instanceof Player player
                && CarryDataManager.getCarryData(player).isCarrying()) {
            ci.cancel();
        }
    }
}
