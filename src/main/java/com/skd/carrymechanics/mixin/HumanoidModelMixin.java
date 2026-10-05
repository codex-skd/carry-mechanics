package com.skd.carrymechanics.mixin;

import com.skd.carrymechanics.carry.CarryData;
import com.skd.carrymechanics.client.render.CarryRenderHelper;
import com.skd.carrymechanics.client.render.CarryMechanicsRenderState;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HumanoidModel.class)
public class HumanoidModelMixin {
    @Shadow public ModelPart rightArm;
    @Shadow public ModelPart leftArm;

    @Inject(method = "setupAnim(Lnet/minecraft/client/renderer/entity/state/HumanoidRenderState;)V", at = @At("RETURN"))
    private void onSetupAnim(HumanoidRenderState state, CallbackInfo ci) {
        if (!(state instanceof CarryMechanicsRenderState carryState)) return;

        CarryData data = carryState.carry_mechanics$getCarryData();
        Player player = carryState.carry_mechanics$getPlayer();
        if (data == null || player == null) return;
        if (!data.isCarrying()) return;
        if (state.isVisuallySwimming || state.isFallFlying) return;

        if (data.isCarrying(CarryData.CarryType.BLOCK)) {
            BlockState blockState = data.getBlock();
            boolean sneaking = state.isCrouching;
            float x = 1.0F + (sneaking ? 0.2F : 0.0F);
            float z = 0.05F;
            float width = CarryRenderHelper.getRenderWidth(player, blockState);
            float offset = Math.min((width - 1.0F) / 1.5F, 0.2F);

            changeRotation(this.rightArm, -x, offset, -z);
            changeRotation(this.leftArm, -x, -offset, z);
        } else {
            changeRotation(this.rightArm, -1.0F, 0.3F, -0.05F);
            changeRotation(this.leftArm, -1.0F, -0.3F, 0.05F);
        }
    }

    @Unique
    private void changeRotation(ModelPart part, float x, float y, float z) {
        part.xRot = x;
        part.yRot = y;
        part.zRot = z;
    }
}
