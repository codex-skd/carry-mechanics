package com.skd.carrymechanics.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.skd.carrymechanics.carry.CarryData;
import net.minecraft.client.model.player.PlayerModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
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

        poseStack.pushPose();
        this.getParentModel().translateToHand(renderState, HumanoidArm.RIGHT, poseStack);
        poseStack.mulPose(Axis.XP.rotationDegrees(-90.0F));
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
        poseStack.translate(-0.5F, -0.4F, -0.6F);
        poseStack.scale(0.5F, 0.5F, 0.5F);
        CarriedBlockRenderer.submit(data, poseStack, collector, lightCoords);
        poseStack.popPose();
    }
}
