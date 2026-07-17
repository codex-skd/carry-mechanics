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
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AvatarRenderer.class)
public class AvatarRendererMixin {
    private static final Logger LOGGER = LoggerFactory.getLogger("CarryMechanics");

    @Inject(method = "<init>(Lnet/minecraft/client/renderer/entity/EntityRendererProvider$Context;Z)V",
            at = @At("TAIL"))
    private void onInit(EntityRendererProvider.Context context, boolean slim, CallbackInfo ci) {
        AvatarRenderer self = (AvatarRenderer)(Object)this;
        boolean added = self.addLayer(new CarryingItemRenderLayer(self));
        LOGGER.info("[DEBUG] CarryingItemRenderLayer added to AvatarRenderer: {}", added);
    }
}