package com.skd.carrymechanics.mixin;

import com.skd.carrymechanics.carry.CarryData;
import com.skd.carrymechanics.client.render.CarryRenderHelper;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.skd.carrymechanics.carry.CarryDataManager;

@Mixin(HumanoidModel.class)
public class HumanoidModelMixin {
    @Shadow public ModelPart rightArm;
    @Shadow public ModelPart leftArm;

    @Inject(method = "setupAnim(Lnet/minecraft/world/entity/LivingEntity;FFFFF)V", at = @At("RETURN"))
    private void onSetupAnim(LivingEntity living, float f1, float f2, float f3, float f4, float f5, CallbackInfo ci) {
        if (!(living instanceof Player player)) return;

        CarryData data = CarryDataManager.getCarryData(player);
        if (!data.isCarrying()) return;
        if (player.isVisuallySwimming() || player.isFallFlying()) return;

        if (data.isCarrying(CarryData.CarryType.BLOCK)) {
            BlockState blockState = data.getBlock();
            boolean sneaking = !player.getAbilities().flying && player.isShiftKeyDown() || player.isCrouching();
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
