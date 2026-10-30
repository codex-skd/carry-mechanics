package com.skd.carrymechanics.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.skd.carrymechanics.carry.CarryData;
import com.skd.carrymechanics.carry.ModelOverrideHandler;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;

public final class CarryRenderHelper {

    private CarryRenderHelper() {
    }

    public static void applyGeneralTransformations(Player player, PoseStack matrix) {
        Pose pose = player.getPose();
        matrix.scale(0.6F, 0.6F, 0.6F);
        matrix.translate(0.0, 0.0, -1.35);
        if (doSneakCheck(player)) {
            matrix.translate(0.0, -0.4, 0.0);
        }
        if (pose == Pose.SWIMMING || pose == Pose.FALL_FLYING) {
            matrix.translate(0.0, 0.0, 2.5);
            matrix.mulPose(Axis.XP.rotationDegrees(90.0F));
        }
        matrix.translate(0.0, -0.5, 0.65);
    }

    public static void applyBlockTransformations(Player player, PoseStack matrix, BlockState state) {
        matrix.mulPose(Axis.ZN.rotationDegrees(180.0F));
        applyGeneralTransformations(player, matrix);
        float height = getRenderHeight(player, state);
        float offset = (height - 1.0F) / 1.2F;
        matrix.translate(0.0F, -offset, 0.0F);
    }

    public static void setupBlockTransformations(Player player, PoseStack matrix, BlockState state, boolean firstPerson) {
        if (firstPerson) {
            matrix.scale(2.5F, 2.5F, 2.5F);
            matrix.translate(0.0, -0.5, -1.0);
            matrix.mulPose(Axis.XP.rotationDegrees(8.0F));
        } else {
            applyBlockTransformations(player, matrix, state);
        }
    }

    public static void setupEntityTransformations(Player player, PoseStack matrix, CarryData data, boolean firstPerson) {
        Entity entity = data.getEntity(player.level());
        if (entity == null) return;

        float entityHeight = entity.getBbHeight();
        float entityWidth = entity.getBbWidth();

        if (firstPerson) {
            matrix.mulPose(Axis.YP.rotationDegrees(180.0F));
            matrix.scale(0.8F, 0.8F, 0.8F);
            matrix.translate(0.0, -entityHeight - 0.2, entityWidth * 1.3 + 0.1);
        } else {
            applyEntityTransformations(player, matrix, entity);
        }
    }

    public static void applyEntityTransformations(Player player, PoseStack matrix, Entity entity) {
        Pose pose = player.getPose();
        float entityHeight = entity.getBbHeight();
        float entityWidth = entity.getBbWidth();

        applyGeneralTransformations(player, matrix);
        matrix.mulPose(Axis.XP.rotationDegrees(180.0F));
        matrix.translate(0.0, -3.1, -0.65);
        matrix.scale(1.666F, 1.666F, 1.666F);

        entity.yo = 0;
        entity.yRotO = 0;
        entity.setYHeadRot(0);
        entity.xo = 0;
        entity.xRotO = 0;

        float scaleFactor = Math.min(9.9F, entityHeight * entityWidth);
        float s = (10.0F - scaleFactor) * 0.08F;
        matrix.scale(s, s, s);

        double yOffset = 1.0 + entityHeight / 2.0 - entityHeight / 4.0;
        double xOffset = entityWidth - 0.1;
        if (xOffset < 0.7) {
            xOffset = 0.7 - (0.7 - xOffset);
        }
        matrix.translate(0.0, yOffset, xOffset);

        if (doSneakCheck(player)) {
            matrix.translate(0.0, -0.4, 0.0);
        }

        if (pose == Pose.SWIMMING || pose == Pose.FALL_FLYING) {
            matrix.mulPose(Axis.XN.rotationDegrees(180.0F));
            matrix.translate(0.0, 0.2 * entityHeight - 2.0, -0.5);
        }
    }

    public static ItemStack getRenderItemStack(CarryData data) {
        BlockState state = data.getBlock();
        return ModelOverrideHandler.getOverrideStack(state.getBlock()).orElseGet(() -> new ItemStack(state.getBlock()));
    }

    public static float getRenderHeight(Player player, BlockState state) {
        VoxelShape shape = state.getShape(player.level(), player.blockPosition());
        if (shape != null && !shape.isEmpty()) {
            return (float) Math.abs(shape.bounds().maxY - shape.bounds().minY);
        }
        return 1.0F;
    }

    public static float getRenderWidth(Player player, BlockState state) {
        VoxelShape shape = state.getShape(player.level(), player.blockPosition());
        if (shape != null && !shape.isEmpty()) {
            return (float) Math.abs(shape.bounds().maxX - shape.bounds().minX);
        }
        return 1.0F;
    }

    public static boolean doSneakCheck(Player player) {
        return !player.getAbilities().flying && (player.isShiftKeyDown() || player.isCrouching());
    }
}
