package com.skd.carrymechanics.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.skd.carrymechanics.carry.CarryData;
import com.skd.carrymechanics.scripting.CarryScript;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;

public class CarryRenderHelper {

    public static void setupBlockTransformations(Player player, PoseStack poseStack, CarryData data, boolean useItemRenderer) {
        if (useItemRenderer) {
            poseStack.pushPose();
            poseStack.scale(2.5f, 2.5f, 2.5f);
            poseStack.translate(0.0, -0.5, -1.0);
            Block block = data.getBlock().getBlock();
            if (!isChestBlock(block)) {
                poseStack.mulPose(Axis.YP.rotationDegrees(180.0f));
                poseStack.mulPose(Axis.XN.rotationDegrees(8.0f));
            } else {
                poseStack.mulPose(Axis.XP.rotationDegrees(8.0f));
            }
            applyScriptRenderScale(poseStack, data);
        } else {
            applyBlockTransformations(player, poseStack, data.getBlock().getBlock());
            applyScriptRenderScale(poseStack, data);
        }
    }

    public static void setupEntityTransformations(Player player, PoseStack poseStack, CarryData data, boolean useItemRenderer) {
        Entity entity = data.getEntity(player.level());
        if (entity == null) return;

        float height = entity.getBbHeight();

        poseStack.pushPose();
        float scale = Math.max(0.2f, Math.min(3.0f / Math.max(height, 1.0f), 1.0f));

        if (useItemRenderer) {
            poseStack.scale(scale, scale, scale);
            poseStack.translate(0.0, 0.2 / scale, -1.0);
            poseStack.mulPose(Axis.YP.rotationDegrees(180.0f));
        } else {
            poseStack.translate(0.0, 0.2, -1.08);
            poseStack.scale(scale * 0.7f, scale * 0.7f, scale * 0.7f);
            poseStack.mulPose(Axis.XP.rotationDegrees(-15.0f));
            poseStack.mulPose(Axis.YP.rotationDegrees(180.0f));
        }

        applyScriptRenderScale(poseStack, data);
    }

    private static void applyBlockTransformations(Player player, PoseStack poseStack, Block block) {
        poseStack.pushPose();
        poseStack.mulPose(Axis.ZN.rotationDegrees(180.0f));
        applyGeneralTransformations(player, poseStack);
        if (!isChestBlock(block)) {
            poseStack.mulPose(Axis.YP.rotationDegrees(180.0f));
        }
        float renderHeight = getRenderHeight(player);
        float yOffset = (renderHeight - 1.0f) / 1.2f;
        poseStack.translate(0.0f, -yOffset, 0.0f);
    }

    private static void applyGeneralTransformations(Player player, PoseStack poseStack) {
        Pose pose = player.getPose();
        poseStack.scale(0.6f, 0.6f, 0.6f);
        poseStack.translate(0.0, 0.0, -1.35);
        if (player.isShiftKeyDown()) {
            poseStack.translate(0.0, -0.4, 0.0);
        }
        if (pose == Pose.SWIMMING || pose == Pose.FALL_FLYING) {
            poseStack.translate(0.0, 0.0, 2.5);
            poseStack.mulPose(Axis.XP.rotationDegrees(90.0f));
        }
        poseStack.translate(0.0, -0.5, 0.65);
    }

    private static void applyScriptRenderScale(PoseStack poseStack, CarryData data) {
        data.getActiveScript().ifPresent(script -> {
            int scale = script.render().renderScale();
            if (scale != 100) {
                float s = scale / 100.0f;
                poseStack.scale(s, s, s);
            }
        });
    }

    private static float getRenderHeight(Player player) {
        return player.isShiftKeyDown() ? player.getEyeHeight() - 0.3f : player.getBbHeight() - 0.2f;
    }

    private static boolean isChestBlock(Block block) {
        return block instanceof ChestBlock;
    }
}