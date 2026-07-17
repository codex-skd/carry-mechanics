package com.skd.carrymechanics.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.skd.carrymechanics.carry.CarryData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.player.PlayerModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CarryingItemRenderLayer extends RenderLayer<AvatarRenderState, PlayerModel> {
    private static final Logger LOGGER = LoggerFactory.getLogger("CarryMechanics");

    public CarryingItemRenderLayer(RenderLayerParent<AvatarRenderState, PlayerModel> parent) {
        super(parent);
        LOGGER.info("[DEBUG] CarryingItemRenderLayer constructed");
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector collector, int lightCoords,
                       AvatarRenderState renderState, float yRot, float xRot) {
        LOGGER.info("[DEBUG] CarryingItemRenderLayer.submit() called!");
        LOGGER.info("[DEBUG] renderState class: {} implements ICarryOnRenderState: {}",
                renderState.getClass().getName(),
                renderState instanceof ICarryOnRenderState);

        if (!(renderState instanceof ICarryOnRenderState carryState)) {
            LOGGER.info("[DEBUG] ICarryOnRenderState NOT implemented - aborting");
            return;
        }

        CarryData data = carryState.carry_mechanics$getCarryData();
        LOGGER.info("[DEBUG] CarryData from state: {} isCarrying={}", data, data != null && data.isCarrying());

        if (data == null || !data.isCarrying()) {
            LOGGER.info("[DEBUG] Not carrying - aborting");
            return;
        }

        LOGGER.info("[DEBUG] Carrying type: {}", data.getType());

        // Render text test
        poseStack.pushPose();
        poseStack.translate(0.0, 2.0, 0.0);
        FormattedCharSequence text = Component.literal("CARRYING").getVisualOrderText();
        collector.submitText(poseStack, 0, 0, text, true, net.minecraft.client.gui.Font.DisplayMode.SEE_THROUGH,
                lightCoords, 0xFFFFFFFF, 0x80000000, 0);
        LOGGER.info("[DEBUG] Text submitted to collector");
        poseStack.popPose();
    }
}