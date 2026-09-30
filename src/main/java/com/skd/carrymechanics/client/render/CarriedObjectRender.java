package com.skd.carrymechanics.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.skd.carrymechanics.carry.CarryData;
import com.skd.carrymechanics.carry.CarryDataManager;
import com.skd.carrymechanics.carry.CarryMechanicsAccess;
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

    private static boolean renderErrorLogged;

    private CarriedObjectRender() {
    }

    public static boolean draw(Player player, PoseStack matrix, int light, SubmitNodeCollector collector, boolean firstPerson) {
        if (player == null) return false;

        CarryData carry = CarryDataManager.getCarryData(player);
        if (carry.isCarrying(CarryData.CarryType.BLOCK)) {
            try {
                drawBlock(player, matrix, light, carry, collector, firstPerson);
            } catch (Exception e) {
                logRenderError("carried block", carry.getBlock().getBlock().getDescriptionId(), e);
            }
        } else if (carry.isCarrying(CarryData.CarryType.ENTITY)) {
            try {
                drawEntity(player, matrix, light, collector, firstPerson, carry);
            } catch (Exception e) {
                logRenderError("carried entity", getCarriedEntityId(player, carry), e);
            }
        }
        return carry.isCarrying();
    }

    private static String getCarriedEntityId(Player player, CarryData carry) {
        try {
            Entity entity = carry.getEntity(player.level());
            return entity != null ? entity.getType().getDescriptionId() : "null";
        } catch (Exception ex) {
            return "unknown";
        }
    }

    private static void logRenderError(String what, String id, Exception e) {
        if (renderErrorLogged) return;
        renderErrorLogged = true;
        CarryMechanicsAccess.LOGGER.error("Failed to render {} ({}) - carried object will not be visible. " +
                "This error is logged once per session.", what, id, e);
    }

    /**
     * Renders on a throwaway copy of the caller's pose stack so that a failed
     * render (or an exception thrown inside EntityRenderDispatcher.submit, which
     * leaves the pose stack unbalanced) can never corrupt the caller's stack.
     */
    private static void drawBlock(Player player, PoseStack matrix, int light, CarryData carry,
                                   SubmitNodeCollector collector, boolean firstPerson) {
        BlockState state = carry.getBlock();

        PoseStack local = new PoseStack();
        local.last().set(matrix.last());
        CarryRenderHelper.setupBlockTransformations(player, local, state, firstPerson);

        ItemStack renderStack = CarryRenderHelper.getRenderItemStack(carry);
        ItemStackRenderState renderState = new ItemStackRenderState();
        Minecraft.getInstance().getItemModelResolver().updateForTopItem(renderState, renderStack, ItemDisplayContext.NONE, player.level(), null, 0);
        renderState.submit(local, collector, light, OverlayTexture.NO_OVERLAY, 0);
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

        PoseStack local = new PoseStack();
        local.last().set(matrix.last());
        CarryRenderHelper.setupEntityTransformations(player, local, carry, firstPerson);

        if (entity instanceof LivingEntity living) {
            living.hurtTime = 0;
        }

        EntityRenderDispatcher dispatcher = Minecraft.getInstance().getEntityRenderDispatcher();
        EntityRenderState renderState = dispatcher.extractEntity(entity, 0.0F);
        renderState.shadowPieces.clear();
        renderState.lightCoords = light;

        dispatcher.submit(renderState, new CameraRenderState(), 0, 0, 0, local, collector);
    }
}
