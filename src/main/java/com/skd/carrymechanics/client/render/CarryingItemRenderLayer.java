package com.skd.carrymechanics.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.skd.carrymechanics.carry.CarryData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.player.PlayerModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class CarryingItemRenderLayer extends RenderLayer<AvatarRenderState, PlayerModel> {

    public CarryingItemRenderLayer(RenderLayerParent<AvatarRenderState, PlayerModel> parent) {
        super(parent);
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector collector, int lightCoords,
                       AvatarRenderState renderState, float partialTick, float unknown) {
        if (!(renderState instanceof ICarryOnRenderState carryState)) return;

        CarryData data = carryState.carry_mechanics$getCarryData();
        if (data == null || !data.isCarrying()) return;

        if (data.isCarrying(CarryData.CarryType.BLOCK)) {
            var blockState = data.getBlock();
            if (blockState.isAir()) return;

            var mc = Minecraft.getInstance();
            Player player = mc.player;
            if (player == null) return;

            ItemStackRenderState itemState = new ItemStackRenderState();
            var layer = itemState.newLayer();
            ItemStack stack = new ItemStack(blockState.getBlock().asItem());
            if (stack.isEmpty()) return;

            mc.getItemModelResolver().updateForTopItem(itemState, stack,
                    ItemDisplayContext.NONE, player.level(), null, 0);

            poseStack.pushPose();
            // Position in front of the player's chest area (third-person style)
            poseStack.translate(0.0, 0.2, -0.1);
            itemState.submit(poseStack, collector, lightCoords, OverlayTexture.NO_OVERLAY, 0);
            poseStack.popPose();
        }
    }
}