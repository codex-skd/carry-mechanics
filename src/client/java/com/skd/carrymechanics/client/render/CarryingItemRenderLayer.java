package com.skd.carrymechanics.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.skd.carrymechanics.client.compat.FirstPersonCompat;
import net.minecraft.client.Minecraft;
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

        // First Person Model renders the local player's body from the camera; the carried
        // object must use the body-attached transform so it appears held by the body.
        // submit() runs after the extract phase, so FirstPersonAPI.isRenderingPlayer() is not
        // reliable here: "local player && isActive()" is enough (the local player body is only
        // rendered in first person when First Person Model draws it).
        if (FirstPersonCompat.isActive() && player == Minecraft.getInstance().player) {
            CarriedObjectRender.drawFirstPersonBody(player, poseStack, lightCoords, collector);
            return;
        }

        CarriedObjectRender.draw(player, poseStack, lightCoords, collector, false);
    }
}
