package com.skd.carrymechanics.mixin;

import com.skd.carrymechanics.client.render.CarryingItemRenderLayer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AvatarRenderer.class)
public class AvatarRendererMixin {

    @Inject(method = "<init>(Lnet/minecraft/client/renderer/entity/EntityRendererProvider$Context;Z)V",
            at = @At("TAIL"))
    private void onInit(EntityRendererProvider.Context context, boolean slim, CallbackInfo ci) {
        AvatarRenderer self = (AvatarRenderer)(Object)this;
        self.addLayer(new CarryingItemRenderLayer(self));
    }
}
