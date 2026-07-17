package com.skd.carrymechanics.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.skd.carrymechanics.carry.CarryData;
import com.skd.carrymechanics.carry.CarryDataManager;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemInHandRenderer.class)
public class ItemInHandRendererMixin {

    @Inject(method = "renderArmWithItem", at = @At("HEAD"), cancellable = true)
    private void onRenderArmWithItem(AbstractClientPlayer player, float frameInterp, float xRot,
                                     InteractionHand hand, float attack, ItemStack itemStack,
                                     float inverseArmHeight, PoseStack poseStack,
                                     SubmitNodeCollector collector, int lightCoords, CallbackInfo ci) {
        if (!itemStack.isEmpty()) return;

        CarryData data = CarryDataManager.getCarryData(player);
        if (!data.isCarrying()) return;

        if (data.isCarrying(CarryData.CarryType.BLOCK)) {
            var state = data.getBlock();
            if (state.isAir()) return;

            ItemStack carryStack = new ItemStack(state.getBlock().asItem());
            if (!carryStack.isEmpty()) {
                boolean isRightHand = hand == InteractionHand.MAIN_HAND;
                ItemDisplayContext ctx = isRightHand
                        ? ItemDisplayContext.FIRST_PERSON_RIGHT_HAND
                        : ItemDisplayContext.FIRST_PERSON_LEFT_HAND;
                ((ItemInHandRenderer)(Object)this).renderItem(player, carryStack, ctx, poseStack, collector, lightCoords);
            }
            ci.cancel();
        }
    }
}