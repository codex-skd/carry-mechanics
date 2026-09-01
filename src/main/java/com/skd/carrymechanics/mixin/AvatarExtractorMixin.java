package com.skd.carrymechanics.mixin;

import com.skd.carrymechanics.carry.CarryData;
import com.skd.carrymechanics.carry.CarryDataManager;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerRenderer.class)
public class AvatarExtractorMixin {

    @Inject(method = "render(Lnet/minecraft/world/entity/player/Player;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
            at = @At("TAIL"))
    private void onRenderTail(Player player, float yRot, float xRot,
                               com.mojang.blaze3d.vertex.PoseStack poseStack,
                               net.minecraft.client.renderer.MultiBufferSource bufferSource,
                               int packedLight, CallbackInfo ci) {
        CarryData data = CarryDataManager.getCarryData(player);
        if (data.isCarrying()) {
            player.setPose(net.minecraft.world.entity.Pose.CROUCHING);
        }
    }
}
