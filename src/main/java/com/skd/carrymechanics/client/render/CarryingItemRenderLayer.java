package com.skd.carrymechanics.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.skd.carrymechanics.carry.CarryData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.player.PlayerModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;

public class CarryingItemRenderLayer extends RenderLayer<AvatarRenderState, PlayerModel> {

    public CarryingItemRenderLayer(RenderLayerParent<AvatarRenderState, PlayerModel> parent) {
        super(parent);
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector collector, int lightCoords,
                       AvatarRenderState renderState, float yRot, float xRot) {
        if (!(renderState instanceof ICarryOnRenderState carryState)) return;

        CarryData data = carryState.carry_mechanics$getCarryData();
        if (data == null || !data.isCarrying()) return;

        var player = Minecraft.getInstance().player;
        if (player == null) return;

        if (data.isCarrying(CarryData.CarryType.BLOCK)) {
            var blockState = data.getBlock();
            if (blockState.isAir()) return;

            poseStack.pushPose();
            poseStack.translate(0.0, 1.0, 0.0);

            var resolver = Minecraft.getInstance().getBlockModelResolver();
            var blockRenderState = new net.minecraft.client.renderer.block.BlockModelRenderState();
            resolver.update(blockRenderState, blockState, net.minecraft.client.renderer.block.model.BlockDisplayContext.create());

            if (!blockRenderState.isEmpty()) {
                blockRenderState.submit(poseStack, collector, lightCoords, net.minecraft.client.renderer.texture.OverlayTexture.NO_OVERLAY, 0);
            }

            poseStack.popPose();
        }
    }
}