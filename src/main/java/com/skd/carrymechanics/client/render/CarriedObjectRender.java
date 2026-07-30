package com.skd.carrymechanics.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.skd.carrymechanics.carry.CarryData;
import com.skd.carrymechanics.carry.CarryDataManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public final class CarriedObjectRender {

    private CarriedObjectRender() {
    }

    public static boolean draw(Player player, PoseStack matrix, int light, SubmitNodeCollector collector, boolean firstPerson) {
        if (player == null) return false;

        CarryData carry = CarryDataManager.getCarryData(player);
        if (carry.isCarrying(CarryData.CarryType.BLOCK)) {
            try {
                drawBlock(player, matrix, light, carry, collector, firstPerson);
            } catch (Exception ignored) {
            }
        }
        return carry.isCarrying();
    }

    private static void drawBlock(Player player, PoseStack matrix, int light, CarryData carry,
                                   SubmitNodeCollector collector, boolean firstPerson) {
        BlockState state = carry.getBlock();
        matrix.pushPose();
        CarryRenderHelper.setupBlockTransformations(player, matrix, state, firstPerson);

        ItemStack renderStack = CarryRenderHelper.getRenderItemStack(carry);
        ItemStackRenderState renderState = new ItemStackRenderState();
        Minecraft.getInstance().getItemModelResolver().updateForTopItem(renderState, renderStack, ItemDisplayContext.NONE, player.level(), null, 0);
        renderState.submit(matrix, collector, light, OverlayTexture.NO_OVERLAY, 0);

        matrix.popPose();
    }
}
