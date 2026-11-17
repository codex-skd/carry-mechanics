package com.skd.carrymechanics.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.skd.carrymechanics.carry.CarryData;
import com.skd.carrymechanics.carry.CarryDataManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public final class CarriedObjectRender {

    private CarriedObjectRender() {
    }

    public static boolean draw(Player player, PoseStack matrix, int light, SubmitNodeCollector collector, boolean firstPerson) {
        return draw(player, matrix, light, collector, firstPerson, false);
    }

    public static boolean drawFirstPersonBody(Player player, PoseStack matrix, int light, SubmitNodeCollector collector) {
        return draw(player, matrix, light, collector, false, true);
    }

    private static boolean draw(Player player, PoseStack matrix, int light, SubmitNodeCollector collector,
                                boolean firstPerson, boolean firstPersonBody) {
        if (player == null) return false;

        CarryData carry = CarryDataManager.getCarryData(player);
        if (carry.isCarrying(CarryData.CarryType.BLOCK)) {
            try {
                drawBlock(player, matrix, light, carry, collector, firstPerson, firstPersonBody);
            } catch (Exception ignored) {
            }
        } else if (carry.isCarrying(CarryData.CarryType.ENTITY)) {
            try {
                drawEntity(player, matrix, light, collector, firstPerson, firstPersonBody, carry);
            } catch (Exception ignored) {
            }
        }
        return carry.isCarrying();
    }

    private static void drawBlock(Player player, PoseStack matrix, int light, CarryData carry,
                                   SubmitNodeCollector collector, boolean firstPerson, boolean firstPersonBody) {
        BlockState state = carry.getBlock();
        BlockState secondState = carry.hasSecondBlock() ? carry.getSecondBlock() : null;

        matrix.pushPose();
        if (firstPersonBody) {
            CarryRenderHelper.setupFirstPersonBodyBlockTransformations(player, matrix, state, secondState);
        } else {
            CarryRenderHelper.setupBlockTransformations(player, matrix, state, firstPerson, secondState);
        }

        ItemStack renderStack = CarryRenderHelper.getRenderItemStack(state);
        ItemStackRenderState renderState = new ItemStackRenderState();
        Minecraft.getInstance().getItemModelResolver().updateForTopItem(renderState, renderStack, ItemDisplayContext.NONE, player.level(), null, 0);
        renderState.submit(matrix, collector, light, OverlayTexture.NO_OVERLAY, 0);

        if (secondState != null) {
            matrix.pushPose();
            matrix.translate(0.0F, 1.0F, 0.0F);
            ItemStack upperStack = CarryRenderHelper.getRenderItemStack(secondState);
            ItemStackRenderState upperState = new ItemStackRenderState();
            Minecraft.getInstance().getItemModelResolver().updateForTopItem(upperState, upperStack, ItemDisplayContext.NONE, player.level(), null, 0);
            upperState.submit(matrix, collector, light, OverlayTexture.NO_OVERLAY, 0);
            matrix.popPose();
        }

        matrix.popPose();
    }

    private static void drawEntity(Player player, PoseStack matrix, int light,
                                    SubmitNodeCollector collector, boolean firstPerson, boolean firstPersonBody,
                                    CarryData carry) {
        Level level = player.level();
        Entity entity = carry.getEntity(level);
        if (entity == null) return;

        entity.setPos(player.getX(), player.getY(), player.getZ());
        entity.xRotO = 0;
        entity.yRotO = 0;
        entity.setYHeadRot(0);

        matrix.pushPose();
        if (firstPersonBody) {
            CarryRenderHelper.setupFirstPersonBodyEntityTransformations(player, matrix, carry);
        } else {
            CarryRenderHelper.setupEntityTransformations(player, matrix, carry, firstPerson);
        }

        if (entity instanceof LivingEntity living) {
            living.hurtTime = 0;
        }

        EntityRenderDispatcher dispatcher = Minecraft.getInstance().getEntityRenderDispatcher();
        EntityRenderState renderState = dispatcher.extractEntity(entity, 0.0F);
        renderState.shadowPieces.clear();
        renderState.lightCoords = light;

        dispatcher.submit(renderState, new CameraRenderState(), 0, 0, 0, matrix, collector);

        matrix.popPose();
    }
}
