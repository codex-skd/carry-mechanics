package com.skd.carrymechanics.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.skd.carrymechanics.carry.CarryData;
import net.minecraft.client.model.player.PlayerModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.HumanoidArm;

public class CarryingItemRenderLayer extends RenderLayer<AvatarRenderState, PlayerModel> {

    public CarryingItemRenderLayer(RenderLayerParent<AvatarRenderState, PlayerModel> parent) {
        super(parent);
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector collector, int lightCoords,
                       AvatarRenderState renderState, float yRot, float xRot) {
        if (!(renderState instanceof ICarryOnRenderState carryState)) return;
        CarryData data = carryState.carry_mechanics$getCarryData();
        if (data == null || !data.isCarrying(CarryData.CarryType.BLOCK)) return;

        ItemStackRenderState itemState = carryState.carry_mechanics$getCarriedBlockItemState();
        if (itemState.isEmpty()) return;

        // Same pattern as vanilla ItemInHandLayer#submitArmWithItem (non-baby offsets, right hand):
        // translateToHand anchors to the arm bone, then the small 1/16-scale offset matches the
        // item's own resting position in the hand instead of a hand-picked full-block offset.
        poseStack.pushPose();
        this.getParentModel().translateToHand(renderState, HumanoidArm.RIGHT, poseStack);
        poseStack.mulPose(Axis.XP.rotationDegrees(-90.0F));
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
        poseStack.translate(1.0F / 16.0F, 2.0F / 16.0F, -10.0F / 16.0F);
        itemState.submit(poseStack, collector, lightCoords, OverlayTexture.NO_OVERLAY, 0);
        poseStack.popPose();
    }
}
