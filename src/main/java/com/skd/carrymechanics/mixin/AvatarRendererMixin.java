package com.skd.carrymechanics.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.skd.carrymechanics.carry.CarryData;
import com.skd.carrymechanics.carry.CarryDataManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AvatarRenderer.class)
public class AvatarRendererMixin {

    @Inject(method = "renderRightHand(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;ILnet/minecraft/resources/Identifier;Z)V",
            at = @At("TAIL"))
    private void onRenderRightHand(PoseStack poseStack, SubmitNodeCollector collector,
                                   int lightCoords, Identifier skinTexture, boolean hasSleeve, CallbackInfo ci) {
        Player player = Minecraft.getInstance().player;
        if (player == null) return;

        CarryData data = CarryDataManager.getCarryData(player);
        if (!data.isCarrying()) return;

        // TODO: Render carried object using SubmitNodeCollector API
        // This Minecraft version uses a completely new render system (SubmitNodeCollector)
        // Actual rendering will be implemented in a future update
    }
}