package com.skd.carrymechanics.mixin;

import com.skd.carrymechanics.carry.CarryDataManager;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public class ServerPlayerMixin {

    @Inject(method = "jumpFromGround", at = @At("HEAD"), cancellable = true)
    private void onJumpFromGround(CallbackInfo ci) {
        ServerPlayer self = (ServerPlayer) (Object) this;
        if (CarryDataManager.getCarryData(self).isCarrying()) {
            ci.cancel();
        }
    }
}