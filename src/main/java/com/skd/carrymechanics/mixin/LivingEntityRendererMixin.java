package com.skd.carrymechanics.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.skd.carrymechanics.carry.CarryData;
import com.skd.carrymechanics.carry.CarryDataManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.block.BlockModelRenderState;
import net.minecraft.client.renderer.block.model.BlockDisplayContext;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntityRenderer.class)
public class LivingEntityRendererMixin {
    private static final Logger LOGGER = LoggerFactory.getLogger("CarryMechanics");
    @Unique private Entity carryMechanics$currentEntity;

    @Inject(method = "extractRenderState(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;F)V",
            at = @At("HEAD"))
    private void onExtractRenderState(LivingEntity entity, LivingEntityRenderState state, float partialTicks, CallbackInfo ci) {
        LOGGER.info("[MIXIN] extractRenderState called: entity={} class={}", entity, entity.getClass().getName());
        this.carryMechanics$currentEntity = entity;
    }

    @Inject(method = "submit(Lnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;Lnet/minecraft/client/renderer/state/level/CameraRenderState;)V",
            at = @At("TAIL"))
    private void onSubmit(LivingEntityRenderState state, PoseStack poseStack, SubmitNodeCollector collector,
                          CameraRenderState camera, CallbackInfo ci) {
        LOGGER.info("[MIXIN] submit called: stateClass={} entity={}", state.getClass().getName(), this.carryMechanics$currentEntity);
        if (!(this.carryMechanics$currentEntity instanceof Player player)) {
            LOGGER.info("[MIXIN] Not a player: {}", this.carryMechanics$currentEntity);
            return;
        }

        CarryData data = CarryDataManager.getCarryData(player);
        LOGGER.info("[MIXIN] Player={} isCarrying={} type={}", player.getScoreboardName(), data.isCarrying(), data.getType());

        if (!data.isCarrying()) return;

        if (data.isCarrying(CarryData.CarryType.BLOCK)) {
            var blockState = data.getBlock();
            LOGGER.info("[MIXIN] Block state: {} isAir={}", blockState, blockState.isAir());
            if (blockState.isAir()) return;

            poseStack.pushPose();
            poseStack.translate(0.0, state.boundingBoxHeight, 0.0);

            var resolver = Minecraft.getInstance().getBlockModelResolver();
            var blockRenderState = new BlockModelRenderState();
            resolver.update(blockRenderState, blockState, BlockDisplayContext.create());

            LOGGER.info("[MIXIN] BlockRenderState isEmpty={}", blockRenderState.isEmpty());

            if (!blockRenderState.isEmpty()) {
                blockRenderState.submit(poseStack, collector, state.lightCoords, OverlayTexture.NO_OVERLAY, 0);
                LOGGER.info("[MIXIN] Block submitted!");
            }

            poseStack.popPose();
        }
    }
}