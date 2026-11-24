package com.skd.carrymechanics.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.skd.carrymechanics.client.compat.FirstPersonCompat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.player.Player;

public class CarryingItemRenderLayer extends RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {

    public CarryingItemRenderLayer(RenderLayerParent<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> parent) {
        super(parent);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource collector, int lightCoords,
                       AbstractClientPlayer player, float limbSwing, float limbSwingAmount,
                       float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        // First Person Model renders the local player's body from the camera; the carried
        // object must use the body-attached transform so it appears held by the body.
        if (FirstPersonCompat.isActive() && FirstPersonCompat.isRenderingBody()
                && player == Minecraft.getInstance().player) {
            CarriedObjectRender.drawFirstPersonBody((Player) player, poseStack, lightCoords, collector);
            return;
        }
        CarriedObjectRender.draw((Player) player, poseStack, lightCoords, collector, false);
    }
}
