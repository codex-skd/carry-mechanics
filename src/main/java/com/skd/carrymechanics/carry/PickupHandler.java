package com.skd.carrymechanics.carry;

import com.skd.carrymechanics.networking.ClientboundStartRidingOtherPlayerPacket;
import com.skd.carrymechanics.networking.NetworkHelper;
import com.skd.carrymechanics.scripting.CarryScript;
import com.skd.carrymechanics.scripting.ScriptManager;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerPlayerGameMode;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.storage.TagValueOutput;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

public class PickupHandler {

    public static boolean canCarryGeneral(ServerPlayer player, Vec3 targetPos) {
        if (!player.getMainHandItem().isEmpty() || !player.getOffhandItem().isEmpty()) return false;
        if (player.position().distanceTo(targetPos) > ConfigAccess.MAX_DISTANCE.get()) return false;

        CarryData data = CarryDataManager.getCarryData(player);
        if (data.isCarrying()) return false;
        if (!data.isKeyPressed()) return false;
        if (player.tickCount == data.getTick()) return false;

        GameType mode = player.gameMode.getGameModeForPlayer();
        if (mode == GameType.SPECTATOR || mode == GameType.ADVENTURE) return false;

        return true;
    }

    public static boolean tryPickUpBlock(ServerPlayer player, BlockPos pos, Level level,
                                         BiFunction<BlockState, BlockPos, Boolean> customHook) {
        if (!canCarryGeneral(player, Vec3.atCenterOf(pos))) return false;

        CarryData data = CarryDataManager.getCarryData(player);
        BlockEntity blockEntity = level.getBlockEntity(pos);
        BlockState state = level.getBlockState(pos);

        CompoundTag tileTag = null;
        if (blockEntity != null) {
            ProblemReporter reporter = new ProblemReporter.ScopedCollector(CarryMechanicsAccess.LOGGER);
            TagValueOutput output = TagValueOutput.createWithContext(reporter, level.registryAccess());
            blockEntity.saveWithId(output);
            tileTag = output.buildResult();
        }

        if (!ListHandler.isPermitted(state.getBlock())) return false;

        if (hasPropertyOfType(state, BlockStateProperties.DOUBLE_BLOCK_HALF)) return false;

        float destroySpeed = state.getDestroySpeed(level, pos);
        if (destroySpeed == -1.0f && !player.isCreative()
                && !ConfigAccess.PICKUP_UNBREAKABLE_BLOCKS.get()) return false;

        if (blockEntity == null && !ConfigAccess.PICKUP_ALL_BLOCKS.get()) return false;

        if (blockEntity != null && tileTag != null && tileTag.contains("Lock")) return false;

        if (customHook != null && !customHook.apply(state, pos)) return false;

        if (ConfigAccess.USE_SCRIPTS.get()) {
            Optional<CarryScript> script = ScriptManager.inspectBlock(state, level, pos, tileTag);
            if (script.isPresent()) {
                CarryScript s = script.get();
                if (!s.fulfillsConditions(player)) return false;
                data.setActiveScript(s);
                String initCmd = s.effects().commandInit();
                if (!initCmd.isEmpty()) {
                    executeCommand(player, initCmd);
                }
            }
        }

        data.setBlock(state, blockEntity, player, pos);
        level.removeBlockEntity(pos);
        level.removeBlock(pos, false);
        CarryDataManager.setCarryData(player, data);

        level.playSound(null, pos, state.getSoundType().getHitSound(), SoundSource.BLOCKS, 1.0f, 0.5f);
        player.swing(InteractionHand.MAIN_HAND, true);

        applySlowness(player, data);

        return true;
    }

    public static boolean tryPickupEntity(ServerPlayer player, Entity entity,
                                          Function<Entity, Boolean> customHook) {
        if (!canCarryGeneral(player, entity.position())) return false;

        if (entity.invulnerableTime != 0) return false;
        if (entity.isRemoved()) return false;

        if (entity instanceof TamableAnimal tamable) {
            var owner = tamable.getOwnerReference();
            if (owner != null && !owner.getUUID().equals(player.getGameProfile().id())) return false;
        }

        if (!ListHandler.isPermitted(entity)) {
            if (entity instanceof AgeableMob baby && ConfigAccess.ALLOW_BABIES.get() && baby.isBaby()) {
            } else {
                return false;
            }
        }

        if (!player.isCreative() && !ConfigAccess.PICKUP_HOSTILE_MOBS.get()
                && entity.getType().getCategory() == MobCategory.MONSTER) return false;

        if (ConfigAccess.MAX_ENTITY_HEIGHT.get() < entity.getBbHeight()
                || ConfigAccess.MAX_ENTITY_WIDTH.get() < entity.getBbWidth()) return false;

        if (customHook != null && !customHook.apply(entity)) return false;

        CarryData data = CarryDataManager.getCarryData(player);
        ServerLevel serverLevel = (ServerLevel) player.level();

        if (ConfigAccess.USE_SCRIPTS.get()) {
            Optional<CarryScript> script = ScriptManager.inspectEntity(entity);
            if (script.isPresent()) {
                CarryScript s = script.get();
                if (!s.fulfillsConditions(player)) return false;
                data.setActiveScript(s);
                String initCmd = s.effects().commandInit();
                if (!initCmd.isEmpty()) executeCommand(player, initCmd);
            }
        }

        if (entity instanceof Player targetPlayer) {
            if (!ConfigAccess.PICKUP_PLAYERS.get()) return false;
            if (!player.isCreative() && targetPlayer.isCreative()) return false;

            targetPlayer.ejectPassengers();
            targetPlayer.stopRiding();

            targetPlayer.startRiding(player, true, false);
            var pkt = new ClientboundStartRidingOtherPlayerPacket(
                    player.getId(), targetPlayer.getId(), true);
            NetworkHelper.sendToAllPlayers(serverLevel, pkt);

            data.setCarryingPlayer(targetPlayer);
        } else {
            entity.ejectPassengers();
            entity.stopRiding();

            if (entity instanceof Animal anim) anim.dropLeash();

            data.setEntity(entity);
            entity.remove(Entity.RemovalReason.UNLOADED_WITH_PLAYER);
        }

        player.swing(InteractionHand.MAIN_HAND, true);

        if (!(entity instanceof Player)) {
            serverLevel.playSound(null, player.getOnPos(), SoundEvents.ARMOR_EQUIP_GENERIC.value(),
                    SoundSource.AMBIENT, 1.0f, 0.5f);
        }

        CarryDataManager.setCarryData(player, data);
        applySlowness(player, data);

        return true;
    }

    private static void applySlowness(ServerPlayer player, CarryData data) {
        if (!player.isCreative() || ConfigAccess.SLOWNESS_IN_CREATIVE.get()) {
            int level = potionLevel(data, (ServerLevel) player.level());
            if (level < 0) level = 0;
            player.addEffect(new MobEffectInstance(MobEffects.SLOWNESS, 100000000, level, false, false));
        }
    }

    public static int potionLevel(CarryData data, Level level) {
        if (data.isCarrying(CarryData.CarryType.PLAYER)) return 1;
        if (data.isCarrying(CarryData.CarryType.ENTITY)) {
            Entity entity = data.getEntity(level);
            int size = (int)(entity.getBbHeight() * entity.getBbWidth());
            if (size > 4) size = 4;
            if (!ConfigAccess.HEAVY_ENTITIES.get()) size = 1;
            return (int)(size * ConfigAccess.ENTITY_SLOWNESS_MULTIPLIER.get());
        }
        if (data.isCarrying(CarryData.CarryType.BLOCK)) {
            int size = data.getFullNbt().toString().length() / 500;
            if (size > 4) size = 4;
            if (!ConfigAccess.HEAVY_TILES.get()) size = 1;
            return (int)(size * ConfigAccess.BLOCK_SLOWNESS_MULTIPLIER.get());
        }
        return 0;
    }

    private static void executeCommand(ServerPlayer player, String command) {
        var server = ((ServerLevel) player.level()).getServer();
        var source = server.createCommandSourceStack()
                .withPosition(player.position())
                .withEntity(player);
        String formatted = command.replace("@p", player.getGameProfile().name());
        server.getCommands().performPrefixedCommand(source, formatted);
    }

    private static boolean hasPropertyOfType(BlockState state, Property<?> targetProp) {
        for (Property<?> prop : state.getProperties()) {
            if (prop.getValueClass().equals(targetProp.getValueClass())) return true;
        }
        return false;
    }
}