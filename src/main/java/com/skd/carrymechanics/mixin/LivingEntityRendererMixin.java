package com.skd.carrymechanics.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.skd.carrymechanics.carry.CarryData;
import com.skd.carrymechanics.carry.CarryDataManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.block.BlockModelRenderState;
import net.minecraft.client.renderer.block.model.BlockDisplayContext;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntityRenderer.class)
public class LivingEntityRendererMixin {

    @Inject(method = "submit(Lnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;Lnet/minecraft/client/renderer/state/level/CameraRenderState;)V",
            at = @At("TAIL"))
    private void onSubmit(LivingEntityRenderState state, PoseStack poseStack, SubmitNodeCollector collector,
                          CameraRenderState camera, CallbackInfo ci) {
        if (!(state instanceof AvatarRenderState)) return;

        var player = Minecraft.getInstance().player;
        if (player == null) return;

        CarryData data = CarryDataManager.getCarryData(player);
        if (!data.isCarrying()) return;

        if (data.isCarrying(CarryData.CarryType.BLOCK)) {
            var blockState = data.getBlock();
            if (blockState.isAir()) return;

            poseStack.pushPose();
            poseStack.translate(0.0, 1.0, 0.0);

            var resolver = Minecraft.getInstance().getBlockModelResolver();
            var blockRenderState = new BlockModelRenderState();
            resolver.update(blockRenderState, blockState, BlockDisplayContext.create());

            if (!blockRenderState.isEmpty()) {
                blockRenderState.submit(poseStack, collector, state.lightCoords, OverlayTexture.NO_OVERLAY, 0);
            }

            poseStack.popPose();
        }
    }
}