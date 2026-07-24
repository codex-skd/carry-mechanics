package com.skd.carrymechanics.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.skd.carrymechanics.carry.CarryData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.player.PlayerModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.block.BlockModelRenderState;
import net.minecraft.client.renderer.block.model.BlockDisplayContext;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CarryingItemRenderLayer extends RenderLayer<AvatarRenderState, PlayerModel> {
    private static final Logger LOGGER = LoggerFactory.getLogger("CarryMechanics");

    public CarryingItemRenderLayer(RenderLayerParent<AvatarRenderState, PlayerModel> parent) {
        super(parent);
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector collector, int lightCoords,
                       AvatarRenderState renderState, float yRot, float xRot) {
        if (!(renderState instanceof ICarryOnRenderState carryState)) {
            LOGGER.info("[RENDER] State does NOT implement ICarryOnRenderState");
            return;
        }

        CarryData data = carryState.carry_mechanics$getCarryData();
        LOGGER.info("[RENDER] CarryData from state: {} isCarrying={}", data, data != null && data.isCarrying());

        if (data == null || !data.isCarrying()) return;

        var player = Minecraft.getInstance().player;
        if (player == null) return;

        if (data.isCarrying(CarryData.CarryType.BLOCK)) {
            var blockState = data.getBlock();
            if (blockState.isAir()) return;

            LOGGER.info("[RENDER] Rendering block: {}", blockState);

            poseStack.pushPose();
            poseStack.translate(0.0, 1.0, 0.0);

            var resolver = Minecraft.getInstance().getBlockModelResolver();
            var blockRenderState = new BlockModelRenderState();
            resolver.update(blockRenderState, blockState, BlockDisplayContext.create());

            if (!blockRenderState.isEmpty()) {
                blockRenderState.submit(poseStack, collector, lightCoords, OverlayTexture.NO_OVERLAY, 0);
                LOGGER.info("[RENDER] Block submitted successfully");
            } else {
                LOGGER.info("[RENDER] Block render state is EMPTY");
            }

            poseStack.popPose();
        }
    }
}
