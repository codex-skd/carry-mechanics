package com.skd.carrymechanics.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.skd.carrymechanics.carry.CarryData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.player.PlayerModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.block.BlockModelRenderState;
import net.minecraft.client.renderer.block.model.BlockDisplayContext;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChestBlock;

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

        if (data.isCarrying(CarryData.CarryType.BLOCK)) {
            var blockState = data.getBlock();
            if (blockState.isAir()) return;

            Player player = Minecraft.getInstance().player;
            if (player == null) return;

            poseStack.pushPose();

            // applyBlockTransformations from original mod
            poseStack.mulPose(Axis.ZN.rotationDegrees(180.0f));

            // applyGeneralTransformations
            Pose playerPose = player.getPose();
            poseStack.scale(0.6f, 0.6f, 0.6f);
            poseStack.translate(0.0, 0.0, -1.35);
            if (!player.getAbilities().flying && (player.isShiftKeyDown() || player.isCrouching()))
                poseStack.translate(0.0, -0.4, 0.0);
            if (playerPose == Pose.SWIMMING || playerPose == Pose.FALL_FLYING) {
                poseStack.translate(0.0, 0.0, 2.5);
                poseStack.mulPose(Axis.XP.rotationDegrees(90.0f));
            }
            poseStack.translate(0.0, -0.5, 0.65);

            boolean isChest = blockState.is(Blocks.CHEST) || blockState.is(Blocks.ENDER_CHEST)
                    || blockState.is(Blocks.TRAPPED_CHEST) || blockState.getBlock() instanceof ChestBlock;
            if (!isChest)
                poseStack.mulPose(Axis.YP.rotationDegrees(180.0f));

            // height adjustment
            var shape = blockState.getShape(player.level(), player.blockPosition());
            float height = 1.0f;
            if (shape != null && !shape.isEmpty())
                height = (float) Math.abs(shape.bounds().maxY - shape.bounds().minY);
            poseStack.translate(0.0f, -(height - 1.0f) / 1.2f, 0.0f);

            // Try block model rendering first (more reliable)
            var resolver = Minecraft.getInstance().getBlockModelResolver();
            var blockRenderState = new BlockModelRenderState();
            resolver.update(blockRenderState, blockState, BlockDisplayContext.create());
            if (!blockRenderState.isEmpty()) {
                blockRenderState.submitMultiLayer(poseStack, collector, lightCoords, OverlayTexture.NO_OVERLAY, 0);
            }

            poseStack.popPose();
        }
    }
}