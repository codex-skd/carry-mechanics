package com.skd.carrymechanics.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.skd.carrymechanics.carry.CarryData;
import com.skd.carrymechanics.carry.CarryDataManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemInHandRenderer.class)
public class ItemInHandRendererMixin {

    @Inject(method = "renderItem", at = @At("HEAD"), cancellable = true)
    private void onRenderItem(LivingEntity entity, ItemStack itemStack, ItemDisplayContext type,
                              PoseStack poseStack, SubmitNodeCollector collector, int lightCoords, CallbackInfo ci) {
        if (!itemStack.isEmpty()) return;
        if (!(entity instanceof net.minecraft.world.entity.player.Player player)) return;

        CarryData data = CarryDataManager.getCarryData(player);
        if (!data.isCarrying()) return;

        if (data.isCarrying(CarryData.CarryType.BLOCK)) {
            var state = data.getBlock();
            if (state.isAir()) return;

            ItemStack carryStack = new ItemStack(state.getBlock().asItem());
            if (!carryStack.isEmpty()) {
                var renderState = new ItemStackRenderState();
                var mc = Minecraft.getInstance();
                mc.getItemModelResolver().updateForTopItem(renderState, carryStack, type, player.level(), player, player.getId() + type.ordinal());
                if (!renderState.isEmpty()) {
                    renderState.submit(poseStack, collector, lightCoords, OverlayTexture.NO_OVERLAY, 0);
                }
            }
            ci.cancel();
        }
    }
}