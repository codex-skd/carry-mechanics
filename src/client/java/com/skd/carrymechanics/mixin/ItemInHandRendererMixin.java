package com.skd.carrymechanics.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.skd.carrymechanics.carry.CarryDataManager;
import com.skd.carrymechanics.client.compat.FirstPersonCompat;
import com.skd.carrymechanics.client.render.CarriedObjectRender;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemInHandRenderer.class)
public class ItemInHandRendererMixin {

    @Inject(method = "submitHandsWithItems", at = @At("HEAD"), cancellable = true)
    private void onRenderHand(float partialTick, PoseStack poseStack, SubmitNodeCollector collector,
                              LocalPlayer player, int light, CallbackInfo ci) {
        if (CarryDataManager.getCarryData(player).isCarrying()) {
            // First Person Model already draws the carried object through the player body
            // render layer. Cancelling without drawing here avoids duplicating it in hand
            // space (which would place the body copy against the camera), while still
            // hiding the vanilla hand.
            if (!FirstPersonCompat.isActive()) {
                CarriedObjectRender.draw(player, poseStack, light, collector, true);
            }
            ci.cancel();
        }
    }
}
