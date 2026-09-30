package com.skd.carrymechanics.events;

import com.skd.carrymechanics.CarryMechanics;
import com.skd.carrymechanics.carry.*;
import com.skd.carrymechanics.command.CommandCarryMechanics;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.entity.EntityLeaveLevelEvent;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;

@EventBusSubscriber(modid = CarryMechanics.MODID)
public class CommonEvents {

    @SubscribeEvent
    public static void onBlockClick(PlayerInteractEvent.RightClickBlock event) {
        if (event.isCanceled()) return;
        Player player = event.getEntity();
        Level level = event.getLevel();
        if (level.isClientSide()) return;

        CarryData data = CarryDataManager.getCarryData(player);

        if (data.isCarrying()) {
            boolean placed = false;
            if (data.isCarrying(CarryData.CarryType.BLOCK)) {
                placed = PlacementHandler.tryPlaceBlock((ServerPlayer) player, event.getPos(), event.getFace(), (bp, bs) -> true);
            } else {
                placed = PlacementHandler.tryPlaceEntity((ServerPlayer) player, event.getPos(), event.getFace(), (v, e) -> true);
            }
            if (placed) {
                event.setUseBlock(net.neoforged.neoforge.common.util.TriState.FALSE);
                event.setUseItem(net.neoforged.neoforge.common.util.TriState.FALSE);
                event.setCancellationResult(InteractionResult.SUCCESS);
                event.setCanceled(true);
            }
        } else {
            boolean picked = PickupHandler.tryPickUpBlock((ServerPlayer) player, event.getPos(), level, (state, bp) -> true);
            if (picked) {
                event.setUseBlock(net.neoforged.neoforge.common.util.TriState.FALSE);
                event.setUseItem(net.neoforged.neoforge.common.util.TriState.FALSE);
                event.setCancellationResult(InteractionResult.SUCCESS);
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void onEntityRightClick(PlayerInteractEvent.EntityInteract event) {
        if (event.isCanceled()) return;
        Player player = event.getEntity();
        Level level = event.getLevel();
        if (level.isClientSide()) return;

        Entity target = event.getTarget();
        CarryData data = CarryDataManager.getCarryData(player);

        if (!data.isCarrying()) {
            if (PickupHandler.tryPickupEntity((ServerPlayer) player, target, e -> true)) {
                event.setCancellationResult(InteractionResult.SUCCESS);
                event.setCanceled(true);
            }
        } else if (data.isCarrying(CarryData.CarryType.ENTITY) || data.isCarrying(CarryData.CarryType.PLAYER)) {
            PlacementHandler.tryStackEntity((ServerPlayer) player, target);
        }
    }

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        CommandCarryMechanics.register(event.getDispatcher());
    }

    @SubscribeEvent
    public static void onServerTick(ServerTickEvent.Post event) {
        for (ServerPlayer player : event.getServer().getPlayerList().getPlayers()) {
            CarryData data = CarryDataManager.getCarryData(player);
            if (!data.isCarrying()) continue;
            if (player.tickCount == 1) CarryDataManager.setCarryData(player, data);
            data.getActiveScript().ifPresent(script -> {
                    String loopCmd = script.effects().commandLoop();
                    if (!loopCmd.isEmpty()) {
                        var server = ((ServerLevel) player.level()).getServer();
                        var source = server.createCommandSourceStack().withPosition(player.position()).withEntity(player);
                        server.getCommands().performPrefixedCommand(source, loopCmd.replace("@p", player.getGameProfile().getName()));
                }
            });
            player.getInventory().selected = data.getSelected();
            PickupHandler.refreshSlowness(player);
            // Prevent jumping by canceling upward motion
            var motion = player.getDeltaMovement();
            if (motion.y > 0) {
                player.setDeltaMovement(motion.x, 0, motion.z);
                player.hurtMarked = true;
            }
        }
    }

    @SubscribeEvent
    public static void onBreakSpeed(PlayerEvent.BreakSpeed event) {
        Player player = event.getEntity();
        if (player == null) return;
        if (CarryDataManager.getCarryData(player).isCarrying() && !ConfigAccess.HIT_WHILE_CARRYING.get())
            event.setCanceled(true);
    }

    @SubscribeEvent
    public static void onAttackEntity(AttackEntityEvent event) {
        Player player = event.getEntity();
        if (player == null) return;
        if (CarryDataManager.getCarryData(player).isCarrying() && !ConfigAccess.HIT_WHILE_CARRYING.get())
            event.setCanceled(true);
    }

    @SubscribeEvent
    public static void onEntityLeave(EntityLeaveLevelEvent event) {
    }
}