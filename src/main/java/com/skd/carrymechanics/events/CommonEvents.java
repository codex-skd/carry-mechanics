package com.skd.carrymechanics.events;

import com.skd.carrymechanics.carry.CarryData;
import com.skd.carrymechanics.carry.CarryDataManager;
import com.skd.carrymechanics.carry.ConfigAccess;
import com.skd.carrymechanics.carry.PickupHandler;
import com.skd.carrymechanics.carry.PlacementHandler;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

public class CommonEvents {

    public static void register() {
        UseBlockCallback.EVENT.register(CommonEvents::onBlockClick);
        UseEntityCallback.EVENT.register(CommonEvents::onEntityRightClick);
        ServerTickEvents.END_SERVER_TICK.register(CommonEvents::onServerTick);
        PlayerBlockBreakEvents.BEFORE.register(CommonEvents::onBreakSpeed);
        AttackEntityCallback.EVENT.register(CommonEvents::onAttackEntity);
        ServerPlayConnectionEvents.DISCONNECT.register((handler, server) ->
                CarryDataManager.remove(handler.getPlayer().getUUID()));
    }

    private static InteractionResult onBlockClick(Player player, Level level, InteractionHand hand, BlockHitResult hitResult) {
        if (level.isClientSide()) return InteractionResult.PASS;

        CarryData data = CarryDataManager.getCarryData(player);
        if (data.isCarrying()) {
            boolean placed;
            if (data.isCarrying(CarryData.CarryType.BLOCK)) {
                placed = PlacementHandler.tryPlaceBlock((ServerPlayer) player, hitResult.getBlockPos(), hitResult.getDirection(), (bp, bs) -> true);
            } else {
                placed = PlacementHandler.tryPlaceEntity((ServerPlayer) player, hitResult.getBlockPos(), hitResult.getDirection(), (v, e) -> true);
            }
            if (placed) return InteractionResult.SUCCESS;
        } else {
            boolean picked = PickupHandler.tryPickUpBlock((ServerPlayer) player, hitResult.getBlockPos(), level, (state, bp) -> true);
            if (picked) return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    private static InteractionResult onEntityRightClick(Player player, Level level, InteractionHand hand, Entity target, EntityHitResult hitResult) {
        if (level.isClientSide()) return InteractionResult.PASS;

        CarryData data = CarryDataManager.getCarryData(player);
        if (!data.isCarrying()) {
            if (PickupHandler.tryPickupEntity((ServerPlayer) player, target, e -> true)) {
                return InteractionResult.SUCCESS;
            }
        } else if (data.isCarrying(CarryData.CarryType.ENTITY) || data.isCarrying(CarryData.CarryType.PLAYER)) {
            PlacementHandler.tryStackEntity((ServerPlayer) player, target);
        }
        return InteractionResult.PASS;
    }

    private static void onServerTick(MinecraftServer server) {
        for (ServerPlayer player : server.getPlayerList().getPlayers()) {
            CarryData data = CarryDataManager.getCarryData(player);
            if (!data.isCarrying()) continue;
            if (player.tickCount == 1) CarryDataManager.setCarryData(player, data);
            data.getActiveScript().ifPresent(script -> {
                String loopCmd = script.effects().commandLoop();
                if (!loopCmd.isEmpty()) {
                    var srv = ((ServerLevel) player.level()).getServer();
                    var source = srv.createCommandSourceStack().withPosition(player.position()).withEntity(player);
                    srv.getCommands().performPrefixedCommand(source, loopCmd.replace("@p", player.getGameProfile().name()));
                }
            });
            player.getInventory().setSelectedSlot(data.getSelected());
            PickupHandler.refreshSlowness(player);
            // Prevent jumping by canceling upward motion
            var motion = player.getDeltaMovement();
            if (motion.y > 0) {
                player.setDeltaMovement(motion.x, 0, motion.z);
                player.hurtMarked = true;
            }
        }
    }

    private static boolean onBreakSpeed(Level level, Player player, BlockPos pos, BlockState state, BlockEntity blockEntity) {
        if (player != null && CarryDataManager.getCarryData(player).isCarrying() && !ConfigAccess.HIT_WHILE_CARRYING) {
            return false;
        }
        return true;
    }

    private static InteractionResult onAttackEntity(Player player, Level level, InteractionHand hand, Entity target, EntityHitResult hitResult) {
        if (CarryDataManager.getCarryData(player).isCarrying() && !ConfigAccess.HIT_WHILE_CARRYING) {
            return InteractionResult.FAIL;
        }
        return InteractionResult.PASS;
    }
}
