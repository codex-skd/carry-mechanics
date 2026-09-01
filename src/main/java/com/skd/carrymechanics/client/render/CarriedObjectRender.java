package com.skd.carrymechanics.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.skd.carrymechanics.carry.CarryData;
import com.skd.carrymechanics.carry.CarryDataManager;
import com.skd.carrymechanics.carry.CarryMechanicsAccess;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
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

    public static boolean draw(Player player, PoseStack matrix, int light, MultiBufferSource collector, boolean firstPerson) {
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

    private static void drawBlock(Player player, PoseStack matrix, int light, CarryData carry,
                                   MultiBufferSource collector, boolean firstPerson) {
        BlockState state = carry.getBlock();

        PoseStack local = new PoseStack();
        local.last().pose().set(matrix.last().pose());
        local.last().normal().set(matrix.last().normal());
        CarryRenderHelper.setupBlockTransformations(player, local, state, firstPerson);

        ItemStack renderStack = CarryRenderHelper.getRenderItemStack(carry);
        ItemRenderer renderer = Minecraft.getInstance().getItemRenderer();
        BakedModel model = renderer.getModel(renderStack, player.level(), player, 0);
        renderer.render(renderStack, ItemDisplayContext.NONE, false, local, collector, light, OverlayTexture.NO_OVERLAY, model);
    }

    private static void drawEntity(Player player, PoseStack matrix, int light,
                                    MultiBufferSource collector, boolean firstPerson, CarryData carry) {
        Level level = player.level();
        Entity entity = carry.getEntity(level);
        if (entity == null) return;

        entity.setPos(player.getX(), player.getY(), player.getZ());
        entity.xRotO = 0;
        entity.yRotO = 0;
        entity.setYHeadRot(0);

        PoseStack local = new PoseStack();
        local.last().pose().set(matrix.last().pose());
        local.last().normal().set(matrix.last().normal());
        CarryRenderHelper.setupEntityTransformations(player, local, carry, firstPerson);

        if (entity instanceof LivingEntity living) {
            living.hurtTime = 0;
        }

        EntityRenderDispatcher dispatcher = Minecraft.getInstance().getEntityRenderDispatcher();
        dispatcher.setRenderShadow(false);
        try {
            dispatcher.render(entity, 0, 0, 0, 0f, 0, local, collector, light);
        } catch (Exception ignored) {
        }
        dispatcher.setRenderShadow(true);
    }
}
