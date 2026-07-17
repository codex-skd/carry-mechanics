package com.skd.carrymechanics.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.skd.carrymechanics.carry.CarryData;
import com.skd.carrymechanics.carry.CarryDataManager;
import com.skd.carrymechanics.client.render.CarryingItemRenderLayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.client.renderer.item.ItemStackRenderState;
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

    @Inject(method = "<init>(Lnet/minecraft/client/renderer/entity/EntityRendererProvider$Context;Z)V",
            at = @At("TAIL"))
    private void onInit(EntityRendererProvider.Context context, boolean slim, CallbackInfo ci) {
        AvatarRenderer self = (AvatarRenderer)(Object)this;
        self.addLayer(new CarryingItemRenderLayer(self));
    }

    @Inject(method = "renderHand(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;ILnet/minecraft/resources/Identifier;Lnet/minecraft/client/model/geom/ModelPart;Z)V",
            at = @At("TAIL"))
    private void onRenderHand(PoseStack poseStack, SubmitNodeCollector collector, int lightCoords,
                              Identifier skinTexture, ModelPart arm, boolean hasSleeve, CallbackInfo ci) {
        Player player = Minecraft.getInstance().player;
        if (player == null) return;

        CarryData data = CarryDataManager.getCarryData(player);
        if (!data.isCarrying() || !data.isCarrying(CarryData.CarryType.BLOCK)) return;

        var state = data.getBlock();
        if (state.isAir()) return;

        ItemStack stack = new ItemStack(state.getBlock().asItem());
        if (stack.isEmpty()) return;

        poseStack.pushPose();
        poseStack.translate(0.0, -0.1, -0.3);
        poseStack.scale(0.3f, 0.3f, 0.3f);

        ItemStackRenderState itemState = new ItemStackRenderState();
        itemState.newLayer();
        Minecraft mc = Minecraft.getInstance();
        mc.getItemModelResolver().updateForTopItem(itemState, stack,
                ItemDisplayContext.NONE, player.level(), null, 0);
        itemState.submit(poseStack, collector, lightCoords, OverlayTexture.NO_OVERLAY, 0);

        poseStack.popPose();
    }
}