package com.skd.carrymechanics.mixin;

import com.skd.carrymechanics.carry.CarryData;
import com.skd.carrymechanics.carry.CarryDataManager;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntityRenderer.class)
public class LivingEntityRendererMixin {
    @Inject(method = "render(Lnet/minecraft/world/entity/LivingEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
            at = @At("HEAD"))
    private void onRenderHead(LivingEntity entity, float yRot, float xRot,
                               com.mojang.blaze3d.vertex.PoseStack poseStack,
                               net.minecraft.client.renderer.MultiBufferSource bufferSource,
                               int packedLight, CallbackInfo ci) {
        if (entity instanceof Player player) {
            CarryData data = CarryDataManager.getCarryData(player);
            if (data.isCarrying()) {
                player.setPose(Pose.CROUCHING);
            }
        }
    }
}
