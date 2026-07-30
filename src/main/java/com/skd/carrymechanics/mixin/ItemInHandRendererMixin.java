package com.skd.carrymechanics.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.skd.carrymechanics.carry.CarryData;
import com.skd.carrymechanics.carry.CarryDataManager;
import com.skd.carrymechanics.client.render.CarriedBlockRenderer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemInHandRenderer.class)
public class ItemInHandRendererMixin {

    @Inject(method = "renderHandsWithItems", at = @At("TAIL"))
    private void onRenderHandsWithItems(float partialTicks, PoseStack poseStack, SubmitNodeCollector collector,
                                        LocalPlayer player, int lightCoords, CallbackInfo ci) {
        CarryData data = CarryDataManager.getCarryData(player);
        if (!data.isCarrying(CarryData.CarryType.BLOCK)) return;

        poseStack.pushPose();
        poseStack.translate(0.55F, -0.65F, -0.9F);
        poseStack.mulPose(Axis.XP.rotationDegrees(-20.0F));
        poseStack.mulPose(Axis.YP.rotationDegrees(35.0F));
        poseStack.scale(0.5F, 0.5F, 0.5F);
        CarriedBlockRenderer.submit(data, poseStack, collector, lightCoords);
        poseStack.popPose();
    }
}
