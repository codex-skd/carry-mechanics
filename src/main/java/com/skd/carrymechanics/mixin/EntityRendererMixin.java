package com.skd.carrymechanics.mixin;

import com.skd.carrymechanics.carry.CarryData;
import com.skd.carrymechanics.carry.CarryDataManager;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityRenderer.class)
public abstract class EntityRendererMixin<T extends Entity> {

    @Inject(method = "shouldShowName(Lnet/minecraft/world/entity/Entity;)Z",
            at = @At("HEAD"), cancellable = true)
    private void onShouldShowName(Entity entity, CallbackInfoReturnable<Boolean> cir) {
        if (entity.getVehicle() instanceof Player carrier) {
            CarryData data = CarryDataManager.getCarryData(carrier);
            if (data.isCarrying(CarryData.CarryType.ENTITY) || data.isCarrying(CarryData.CarryType.PLAYER)) {
                cir.setReturnValue(false);
            }
        }
        if (entity instanceof Player player) {
            CarryData data = CarryDataManager.getCarryData(player);
            if (data.isCarrying()) {
                cir.setReturnValue(false);
            }
        }
    }
}
