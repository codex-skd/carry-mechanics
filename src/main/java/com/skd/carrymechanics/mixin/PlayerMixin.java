package com.skd.carrymechanics.mixin;

import com.skd.carrymechanics.carry.CarryData;
import com.skd.carrymechanics.carry.CarryDataManager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public abstract class PlayerMixin {

    @Inject(method = "drop(Lnet/minecraft/world/item/ItemStack;ZZ)Lnet/minecraft/world/entity/item/ItemEntity;",
            at = @At("HEAD"), cancellable = true)
    private void onDrop(ItemStack stack, boolean bl, boolean bl2, CallbackInfoReturnable<?> cir) {
        Player self = (Player) (Object) this;
        CarryData data = CarryDataManager.getCarryData(self);
        if (data.isCarrying()) {
            cir.setReturnValue(null);
        }
    }

    @Inject(method = "canBeCollidedWith", at = @At("HEAD"), cancellable = true)
    private void onCanBeCollidedWith(CallbackInfoReturnable<Boolean> cir) {
        Player self = (Player) (Object) this;
        if (self.getVehicle() != null && self.getVehicle() instanceof Player carrier) {
            CarryData data = CarryDataManager.getCarryData(carrier);
            if (data.isCarrying(CarryData.CarryType.PLAYER)) {
                cir.setReturnValue(false);
            }
        }
    }
}