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
        if (player == null) return false;

        CarryData carry = CarryDataManager.getCarryData(player);
        if (carry.isCarrying(CarryData.CarryType.BLOCK)) {
            try {
                drawBlock(player, matrix, light, carry, collector, firstPerson);
            } catch (Exception ignored) {
            }
        } else if (carry.isCarrying(CarryData.CarryType.ENTITY)) {
            try {
                drawEntity(player, matrix, light, collector, firstPerson, carry);
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

    private static void drawEntity(Player player, PoseStack matrix, int light,
                                    SubmitNodeCollector collector, boolean firstPerson, CarryData carry) {
        Level level = player.level();
        Entity entity = carry.getEntity(level);
        if (entity == null) return;

        entity.setPos(player.getX(), player.getY(), player.getZ());
        entity.xRotO = 0;
        entity.yRotO = 0;
        entity.setYHeadRot(0);

        matrix.pushPose();
        CarryRenderHelper.setupEntityTransformations(player, matrix, carry, firstPerson);

        if (entity instanceof LivingEntity living) {
            living.hurtTime = 0;
        }

        EntityRenderDispatcher dispatcher = Minecraft.getInstance().getEntityRenderDispatcher();
        float partialTick = Minecraft.getInstance().getDeltaTracker().getGameTimeDeltaPartialTick(true);
        EntityRenderState renderState = dispatcher.extractEntity(entity, partialTick);
        renderState.shadowPieces.clear();
        renderState.lightCoords = light;

        dispatcher.submit(renderState, new CameraRenderState(), 0, 0, 0, matrix, collector);

        matrix.popPose();
    }
}
