package com.skd.carrymechanics.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.skd.carrymechanics.carry.CarryData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.block.BlockModelRenderState;
import net.minecraft.client.renderer.block.model.BlockDisplayContext;
import net.minecraft.client.renderer.texture.OverlayTexture;

public final class CarriedBlockRenderer {

    private CarriedBlockRenderer() {
    }

    public static void submit(CarryData data, PoseStack poseStack, SubmitNodeCollector collector, int lightCoords) {
        if (data == null || !data.isCarrying(CarryData.CarryType.BLOCK)) return;

        var blockState = data.getBlock();
        if (blockState.isAir()) return;

        var resolver = Minecraft.getInstance().getBlockModelResolver();
        var blockRenderState = new BlockModelRenderState();
        resolver.update(blockRenderState, blockState, BlockDisplayContext.create());

        if (!blockRenderState.isEmpty()) {
            blockRenderState.submit(poseStack, collector, lightCoords, OverlayTexture.NO_OVERLAY, 0);
        }
    }
}
