package com.skd.carrymechanics.mixin;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class EntityMixin {

    @Inject(method = "isColliding", at = @At("HEAD"), cancellable = true)
    private void onIsColliding(CallbackInfoReturnable<Boolean> cir) {
        if ((Object) this instanceof Player player) {
            var data = com.skd.carrymechanics.carry.CarryDataManager.getCarryData(player);
            if (data.isCarrying()) {
                cir.setReturnValue(false);
            }
        }
    }
}