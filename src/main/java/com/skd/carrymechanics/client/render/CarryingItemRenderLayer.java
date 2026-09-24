package com.skd.carrymechanics.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.player.PlayerModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.world.entity.player.Player;

public class CarryingItemRenderLayer extends RenderLayer<AvatarRenderState, PlayerModel> {

    public CarryingItemRenderLayer(RenderLayerParent<AvatarRenderState, PlayerModel> parent) {
        super(parent);
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector collector, int lightCoords,
                       AvatarRenderState renderState, float yRot, float xRot) {
        if (!(renderState instanceof CarryMechanicsRenderState carryState)) return;
        Player player = carryState.carry_mechanics$getPlayer();
        if (player == null) return;

        CarriedObjectRender.draw(player, poseStack, lightCoords, collector, false);
    }
}
