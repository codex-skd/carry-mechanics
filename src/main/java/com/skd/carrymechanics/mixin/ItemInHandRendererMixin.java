package com.skd.carrymechanics.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.skd.carrymechanics.carry.CarryData;
import com.skd.carrymechanics.carry.CarryDataManager;
import com.skd.carrymechanics.client.render.CarriedBlockRenderer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemDisplayContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemInHandRenderer.class)
public class ItemInHandRendererMixin {

    @Unique
    private final ItemStackRenderState carry_mechanics$itemState = new ItemStackRenderState();

    @Inject(method = "renderHandsWithItems", at = @At("TAIL"))
    private void onRenderHandsWithItems(float partialTicks, PoseStack poseStack, SubmitNodeCollector collector,
                                        LocalPlayer player, int lightCoords, CallbackInfo ci) {
        CarryData data = CarryDataManager.getCarryData(player);
        if (!data.isCarrying(CarryData.CarryType.BLOCK)) return;

        CarriedBlockRenderer.resolve(carry_mechanics$itemState, data, ItemDisplayContext.FIRST_PERSON_RIGHT_HAND, player);
        if (carry_mechanics$itemState.isEmpty()) return;

        // Matches vanilla ItemInHandRenderer#applyItemArmTransform (right arm, inverseArmHeight = 0):
        // the FIRST_PERSON_RIGHT_HAND display context already bakes in the correct scale/rotation.
        poseStack.pushPose();
        poseStack.translate(0.56F, -0.52F, -0.72F);
        carry_mechanics$itemState.submit(poseStack, collector, lightCoords, OverlayTexture.NO_OVERLAY, 0);
        poseStack.popPose();
    }
}
