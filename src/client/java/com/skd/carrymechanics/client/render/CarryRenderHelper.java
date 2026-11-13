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

    // ===== First Person Model compat: body-render transform =============================
    // Used by CarryingItemRenderLayer when First Person Model draws the local player's
    // body in first person. The carried object must be attached to the body (third-person
    // style) instead of the camera, or it would overlap the camera. All tunable values
    // live here; the defaults mirror the existing third-person transform so the object
    // starts out held in front of the body and can be calibrated in-game.
    // NOTE: these values were calibrated for the 1.21.1 build and are kept as starting
    // values for Fabric 26.2; they still need in-game calibration on this render architecture.
    // Block path: scale then (x, y, z) offset then pitch, after the vanilla Z 180 rotation.
    private static final float FP_BODY_BLOCK_SCALE = 0.6F;
    private static final double FP_BODY_BLOCK_OFFSET_X = 0.0;
    private static final double FP_BODY_BLOCK_OFFSET_Y = -0.5;
    private static final double FP_BODY_BLOCK_OFFSET_Z = -0.7;
    private static final float FP_BODY_BLOCK_PITCH = 0.0F;
    // Sneak-only extra offset for the block path, applied right after the offset above and
    // in the same pre-pitch local frame (so it is also affected by the 0.6 scale). -Y lowers
    // the object and -Z pushes it further away from the chest, keeping the camera out of the
    // block while crouched. Calibrate these in game. Standing behaviour is untouched.
    private static final double FP_BODY_BLOCK_SNEAK_OFFSET_Y = -0.3;
    private static final double FP_BODY_BLOCK_SNEAK_OFFSET_Z = -0.5;
    // Entity path: scale then (x, y, z) offset then pitch, before the per-entity scaling.
    private static final float FP_BODY_ENTITY_SCALE = 0.6F;
    private static final double FP_BODY_ENTITY_OFFSET_X = 0.0;
    private static final double FP_BODY_ENTITY_OFFSET_Y = -3.6;
    private static final double FP_BODY_ENTITY_OFFSET_Z = -1.35;
    private static final float FP_BODY_ENTITY_PITCH = 180.0F;
    // Sneak-only extra offset for the entity path, same frame/order as the block variant.
    // Smaller than the block values (entities are already offset further out); calibrate in
    // game. Standing behaviour is untouched.
    private static final double FP_BODY_ENTITY_SNEAK_OFFSET_Y = -0.15;
    private static final double FP_BODY_ENTITY_SNEAK_OFFSET_Z = -0.25;
    private static final float FP_BODY_ENTITY_SCALE_MULT = 1.666F;
    // ==================================================================================

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
        applyBlockTransformations(player, matrix, state, null);
    }

    public static void applyBlockTransformations(Player player, PoseStack matrix, BlockState state, BlockState secondState) {
        matrix.mulPose(Axis.ZN.rotationDegrees(180.0F));
        applyGeneralTransformations(player, matrix);
        float height = getRenderHeight(player, state, secondState);
        float offset = (height - 1.0F) / 1.2F;
        matrix.translate(0.0F, -offset, 0.0F);
    }

    public static void setupBlockTransformations(Player player, PoseStack matrix, BlockState state, boolean firstPerson) {
        setupBlockTransformations(player, matrix, state, firstPerson, null);
    }

    public static void setupBlockTransformations(Player player, PoseStack matrix, BlockState state, boolean firstPerson,
                                                 BlockState secondState) {
        if (firstPerson) {
            matrix.scale(2.5F, 2.5F, 2.5F);
            matrix.translate(0.0, -0.5, -1.0);
            matrix.mulPose(Axis.XP.rotationDegrees(8.0F));
            if (secondState != null) {
                float height = getRenderHeight(player, state, secondState);
                float offset = (height - 1.0F) / 1.2F;
                matrix.translate(0.0F, -offset, 0.0F);
            }
        } else {
            applyBlockTransformations(player, matrix, state, secondState);
        }
    }

    public static void setupFirstPersonBodyBlockTransformations(Player player, PoseStack matrix, BlockState state) {
        setupFirstPersonBodyBlockTransformations(player, matrix, state, null);
    }

    public static void setupFirstPersonBodyBlockTransformations(Player player, PoseStack matrix, BlockState state,
                                                                BlockState secondState) {
        matrix.mulPose(Axis.ZN.rotationDegrees(180.0F));
        matrix.scale(FP_BODY_BLOCK_SCALE, FP_BODY_BLOCK_SCALE, FP_BODY_BLOCK_SCALE);
        matrix.translate(FP_BODY_BLOCK_OFFSET_X, FP_BODY_BLOCK_OFFSET_Y, FP_BODY_BLOCK_OFFSET_Z);
        if (doSneakCheck(player)) {
            matrix.translate(0.0, FP_BODY_BLOCK_SNEAK_OFFSET_Y, FP_BODY_BLOCK_SNEAK_OFFSET_Z);
        }
        matrix.mulPose(Axis.XP.rotationDegrees(FP_BODY_BLOCK_PITCH));
        float height = getRenderHeight(player, state, secondState);
        float offset = (height - 1.0F) / 1.2F;
        matrix.translate(0.0F, -offset, 0.0F);
    }

    public static void setupFirstPersonBodyEntityTransformations(Player player, PoseStack matrix, CarryData data) {
        Entity entity = data.getEntity(player.level());
        if (entity == null) return;

        float entityHeight = entity.getBbHeight();
        float entityWidth = entity.getBbWidth();

        matrix.scale(FP_BODY_ENTITY_SCALE, FP_BODY_ENTITY_SCALE, FP_BODY_ENTITY_SCALE);
        matrix.translate(FP_BODY_ENTITY_OFFSET_X, FP_BODY_ENTITY_OFFSET_Y, FP_BODY_ENTITY_OFFSET_Z);
        if (doSneakCheck(player)) {
            matrix.translate(0.0, FP_BODY_ENTITY_SNEAK_OFFSET_Y, FP_BODY_ENTITY_SNEAK_OFFSET_Z);
        }
        matrix.mulPose(Axis.XP.rotationDegrees(FP_BODY_ENTITY_PITCH));
        matrix.scale(FP_BODY_ENTITY_SCALE_MULT, FP_BODY_ENTITY_SCALE_MULT, FP_BODY_ENTITY_SCALE_MULT);

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
        return getRenderItemStack(data.getBlock());
    }

    public static ItemStack getRenderItemStack(BlockState state) {
        return ModelOverrideHandler.getOverrideStack(state.getBlock()).orElseGet(() -> new ItemStack(state.getBlock()));
    }

    public static float getRenderHeight(Player player, BlockState state) {
        return getRenderHeight(player, state, null);
    }

    public static float getRenderHeight(Player player, BlockState state, BlockState secondState) {
        float height = getSingleRenderHeight(player, state);
        if (secondState != null) {
            height += getSingleRenderHeight(player, secondState);
        }
        return height;
    }

    private static float getSingleRenderHeight(Player player, BlockState state) {
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
