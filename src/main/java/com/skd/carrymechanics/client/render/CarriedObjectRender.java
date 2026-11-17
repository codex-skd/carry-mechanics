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
        return draw(player, matrix, light, collector, firstPerson, false);
    }

    public static boolean drawFirstPersonBody(Player player, PoseStack matrix, int light, MultiBufferSource collector) {
        return draw(player, matrix, light, collector, false, true);
    }

    private static boolean draw(Player player, PoseStack matrix, int light, MultiBufferSource collector,
                                boolean firstPerson, boolean firstPersonBody) {
        if (player == null) return false;

        CarryData carry = CarryDataManager.getCarryData(player);
        if (carry.isCarrying(CarryData.CarryType.BLOCK)) {
            try {
                drawBlock(player, matrix, light, carry, collector, firstPerson, firstPersonBody);
            } catch (Exception e) {
                logRenderError("carried block", carry.getBlock().getBlock().getDescriptionId(), e);
            }
        } else if (carry.isCarrying(CarryData.CarryType.ENTITY)) {
            try {
                drawEntity(player, matrix, light, collector, firstPerson, firstPersonBody, carry);
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
                                   MultiBufferSource collector, boolean firstPerson, boolean firstPersonBody) {
        BlockState state = carry.getBlock();
        BlockState secondState = carry.hasSecondBlock() ? carry.getSecondBlock() : null;

        PoseStack local = new PoseStack();
        local.last().pose().set(matrix.last().pose());
        local.last().normal().set(matrix.last().normal());
        if (firstPersonBody) {
            CarryRenderHelper.setupFirstPersonBodyBlockTransformations(player, local, state, secondState);
        } else {
            CarryRenderHelper.setupBlockTransformations(player, local, state, firstPerson, secondState);
        }

        ItemStack renderStack = CarryRenderHelper.getRenderItemStack(state);
        ItemRenderer renderer = Minecraft.getInstance().getItemRenderer();
        BakedModel model = renderer.getModel(renderStack, player.level(), player, 0);
        renderer.render(renderStack, ItemDisplayContext.NONE, false, local, collector, light, OverlayTexture.NO_OVERLAY, model);

        if (secondState != null) {
            PoseStack upper = new PoseStack();
            upper.last().pose().set(local.last().pose());
            upper.last().normal().set(local.last().normal());
            upper.translate(0.0F, 1.0F, 0.0F);

            ItemStack upperStack = CarryRenderHelper.getRenderItemStack(secondState);
            BakedModel upperModel = renderer.getModel(upperStack, player.level(), player, 0);
            renderer.render(upperStack, ItemDisplayContext.NONE, false, upper, collector, light,
                    OverlayTexture.NO_OVERLAY, upperModel);
        }
    }

    private static void drawEntity(Player player, PoseStack matrix, int light,
                                    MultiBufferSource collector, boolean firstPerson, boolean firstPersonBody,
                                    CarryData carry) {
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
        if (firstPersonBody) {
            CarryRenderHelper.setupFirstPersonBodyEntityTransformations(player, local, carry);
        } else {
            CarryRenderHelper.setupEntityTransformations(player, local, carry, firstPerson);
        }

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
