package com.skd.carrymechanics.carry;

import com.skd.carrymechanics.scripting.CarryScript;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.equine.Horse;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;

import java.util.function.BiFunction;

public class PlacementHandler {

    public static void placeCarried(ServerPlayer player) {
        CarryData data = CarryDataManager.getCarryData(player);
        if (!data.isCarrying()) return;

        if (data.isCarrying(CarryData.CarryType.BLOCK)) {
            tryPlaceBlock(player, player.blockPosition(), Direction.UP, (bp, bs) -> true);
        } else {
            tryPlaceEntity(player, player.blockPosition(), Direction.UP, (v, e) -> true);
        }
    }

    public static boolean tryPlaceBlock(ServerPlayer player, BlockPos pos, Direction face,
                                        BiFunction<BlockPos, BlockState, Boolean> customHook) {
        CarryData data = CarryDataManager.getCarryData(player);
        if (!data.isCarrying(CarryData.CarryType.BLOCK)) return false;
        if (player.tickCount == data.getTick()) return false;

        ServerLevel level = (ServerLevel) player.level();
        BlockState carriedState = data.getBlock();

        BlockPlaceContext ctx = new BlockPlaceContext(player, InteractionHand.MAIN_HAND,
                ItemStack.EMPTY, BlockHitResult.miss(player.position(), face, pos));

        if (!level.getBlockState(pos).canBeReplaced(ctx)) {
            pos = pos.relative(face);
            ctx = new BlockPlaceContext(player, InteractionHand.MAIN_HAND,
                    ItemStack.EMPTY, BlockHitResult.miss(player.position(), face, pos));
        }

        BlockEntity blockEntity = data.getBlockEntity(pos, level.registryAccess());
        carriedState = getPlacementState(carriedState, player, ctx, pos);

        boolean canPlace = carriedState.canSurvive(level, pos)
                && level.mayInteract(player, pos)
                && level.getBlockState(pos).canBeReplaced(ctx)
                && level.isUnobstructed(carriedState, pos, CollisionContext.of(player));

        if (!canPlace) {
            level.playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.LAVA_POP, SoundSource.PLAYERS, 0.5f, 0.5f);
            return false;
        }

        if (customHook != null && !customHook.apply(pos, carriedState)) return false;
        if (level.isOutsideBuildHeight(pos)) return false;

        executePlaceCommand(player, data);

        level.setBlockAndUpdate(pos, carriedState);

        if (blockEntity != null) {
            blockEntity.setBlockState(carriedState);
            level.setBlockEntity(blockEntity);
        }

        level.updateNeighborsAt(pos.below(), level.getBlockState(pos.below()).getBlock());

        data.clear();
        CarryDataManager.setCarryData(player, data);

        player.playSound(carriedState.getSoundType().getPlaceSound(), 1.0f, 0.5f);
        level.playSound(null, pos, carriedState.getSoundType().getPlaceSound(), SoundSource.BLOCKS, 1.0f, 0.5f);
        player.swing(InteractionHand.MAIN_HAND, true);
        player.removeEffect(MobEffects.SLOWNESS);

        return true;
    }

    public static boolean tryPlaceEntity(ServerPlayer player, BlockPos pos, Direction face,
                                         BiFunction<Vec3, Entity, Boolean> customHook) {
        CarryData data = CarryDataManager.getCarryData(player);
        if (!data.isCarrying(CarryData.CarryType.ENTITY))
            return false;
        if (player.tickCount == data.getTick()) return false;

        ServerLevel level = (ServerLevel) player.level();

        BlockPlaceContext ctx = new BlockPlaceContext(player, InteractionHand.MAIN_HAND,
                ItemStack.EMPTY, BlockHitResult.miss(player.position(), face, pos));

        if (!level.getBlockState(pos).canBeReplaced(ctx)) {
            pos = pos.relative(face);
            ctx = new BlockPlaceContext(player, InteractionHand.MAIN_HAND,
                    ItemStack.EMPTY, BlockHitResult.miss(player.position(), face, pos));
        }

        if (!level.getBlockState(pos).canBeReplaced(ctx)) {
            level.playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.LAVA_POP, SoundSource.PLAYERS, 0.5f, 0.5f);
            return false;
        }

        Vec3 placementPos = Vec3.atBottomCenterOf(pos);

        Entity entity = data.getEntity(level);
        if (entity == null) {
            data.clear();
            CarryDataManager.setCarryData(player, data);
            return false;
        }

        entity.setPos(placementPos);

        if (customHook != null && !customHook.apply(placementPos, entity)) return false;

        executePlaceCommand(player, data);

        level.addFreshEntity(entity);
        if (entity instanceof Mob mob) mob.playAmbientSound();

        player.swing(InteractionHand.MAIN_HAND, true);
        data.clear();
        CarryDataManager.setCarryData(player, data);
        player.removeEffect(MobEffects.SLOWNESS);

        return true;
    }

    public static void tryStackEntity(ServerPlayer player, Entity targetEntity) {
        if (!ConfigAccess.STACKABLE_ENTITIES.get()) return;

        CarryData data = CarryDataManager.getCarryData(player);
        if (!data.isCarrying(CarryData.CarryType.ENTITY)) return;

        ServerLevel level = (ServerLevel) player.level();
        Entity carriedEntity = data.getEntity(level);
        if (carriedEntity == null) return;

        double carriedSize = carriedEntity.getBbHeight() * carriedEntity.getBbWidth();
        Entity rootVehicle = targetEntity.getRootVehicle();
        int passengerCount = getPassengerCount(rootVehicle);

        if (passengerCount >= ConfigAccess.MAX_ENTITY_STACK_LIMIT.get() - 1) return;

        Entity topPassenger = getTopPassenger(rootVehicle);
        if (topPassenger == carriedEntity) return;
        if (!ListHandler.isStackingPermitted(topPassenger)) return;

        double topSize = topPassenger.getBbHeight() * topPassenger.getBbWidth();
        if (ConfigAccess.ENTITY_SIZE_MATTERS_STACKING.get() && carriedSize > topSize) return;

        if (topPassenger instanceof Horse horse) {
            horse.setTamed(true);
        }

        double distSq = targetEntity.blockPosition().distSqr(player.blockPosition());

        if (distSq < 6.0) {
            carriedEntity.setPos(targetEntity.getX(), targetEntity.getY() + 2.6, targetEntity.getZ());
            level.addFreshEntity(carriedEntity);
            carriedEntity.startRiding(topPassenger, true, false);
        } else {
            carriedEntity.setPos(targetEntity.getX(), targetEntity.getY(), targetEntity.getZ());
            level.addFreshEntity(carriedEntity);
            carriedEntity.startRiding(topPassenger, true, false);
        }

        executePlaceCommand(player, data);
        data.clear();
        CarryDataManager.setCarryData(player, data);
        player.removeEffect(MobEffects.SLOWNESS);
    }

    private static BlockState getPlacementState(BlockState carried, ServerPlayer player,
                                                BlockPlaceContext ctx, BlockPos pos) {
        BlockState placement = carried.getBlock().getStateForPlacement(ctx);
        if (placement == null || placement.getBlock() != carried.getBlock()) {
            placement = carried;
        }

        BlockState updated = Block.updateFromNeighbourShapes(placement, (ServerLevel) player.level(), pos);
        if (updated.getBlock() == placement.getBlock()) placement = updated;

        if (carried.hasProperty(BlockStateProperties.WATERLOGGED)
                && placement.hasProperty(BlockStateProperties.WATERLOGGED)) {
            placement = placement.setValue(BlockStateProperties.WATERLOGGED,
                    carried.getValue(BlockStateProperties.WATERLOGGED));
        }

        return placement;
    }

    private static void executePlaceCommand(ServerPlayer player, CarryData data) {
        if (data.getActiveScript().isEmpty()) return;
        CarryScript script = data.getActiveScript().get();
        String cmd = script.effects().commandPlace();
        if (cmd.isEmpty()) return;

        var server = ((ServerLevel) player.level()).getServer();
        var source = server.createCommandSourceStack()
                .withPosition(player.position())
                .withEntity(player);
        String formatted = cmd.replace("@p", player.getGameProfile().name());
        server.getCommands().performPrefixedCommand(source, formatted);
    }

    private static int getPassengerCount(Entity entity) {
        int count = 1;
        for (Entity passenger : entity.getPassengers()) {
            count += getPassengerCount(passenger);
        }
        return count;
    }

    private static Entity getTopPassenger(Entity entity) {
        if (entity.getPassengers().isEmpty()) return entity;
        return getTopPassenger(entity.getPassengers().getFirst());
    }
}