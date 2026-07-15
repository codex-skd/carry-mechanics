package com.skd.carrymechanics.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.skd.carrymechanics.carry.CarryData;
import com.skd.carrymechanics.carry.CarryDataManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.block.BlockModelRenderState;
import net.minecraft.client.renderer.block.model.BlockDisplayContext;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AvatarRenderer.class)
public class AvatarRendererMixin {

    @Inject(method = "renderRightHand(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;ILnet/minecraft/resources/Identifier;Z)V",
            at = @At("TAIL"))
    private void onRenderRightHand(PoseStack poseStack, SubmitNodeCollector collector,
                                   int lightCoords, Identifier skinTexture, boolean hasSleeve, CallbackInfo ci) {
        Player player = Minecraft.getInstance().player;
        if (player == null) return;

        CarryData data = CarryDataManager.getCarryData(player);
        if (!data.isCarrying()) return;

        if (data.isCarrying(CarryData.CarryType.BLOCK)) {
            var state = data.getBlock();
            if (state.isAir()) return;

            poseStack.pushPose();
            poseStack.translate(1.2, -0.3, -1.5);
            poseStack.scale(0.5f, 0.5f, 0.5f);

            // Try item render first
            ItemStack stack = new ItemStack(state.getBlock().asItem());
            if (!stack.isEmpty()) {
                var mc = Minecraft.getInstance();
                var itemState = new ItemStackRenderState();
                mc.getItemModelResolver().updateForLiving(itemState, stack, ItemDisplayContext.FIRST_PERSON_RIGHT_HAND, player);
                if (!itemState.isEmpty()) {
                    itemState.submit(poseStack, collector, lightCoords, OverlayTexture.NO_OVERLAY, 0);
                }
            }

            poseStack.popPose();
        }
    }
}