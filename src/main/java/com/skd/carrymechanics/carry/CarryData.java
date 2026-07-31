package com.skd.carrymechanics.carry;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.skd.carrymechanics.scripting.CarryScript;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.storage.TagValueInput;
import net.minecraft.world.level.storage.TagValueOutput;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

import java.util.Optional;

public class CarryData {
    private CarryType type = CarryType.INVALID;
    private CompoundTag nbt = new CompoundTag();
    private boolean keyPressed;
    private int selectedSlot;
    private CarryScript dataActiveScript;

    public static final Codec<CarryData> FULL_CODEC = CompoundTag.CODEC.flatXmap(
            tag -> { try { return com.mojang.serialization.DataResult.success(new CarryData(tag)); }
                     catch (Exception e) { return com.mojang.serialization.DataResult.error(() -> "CarryData: " + e.getMessage()); }},
            data -> { try { return com.mojang.serialization.DataResult.success(data.getFullNbt()); }
                     catch (Exception e) { return com.mojang.serialization.DataResult.error(() -> "CarryData: " + e.getMessage()); }});

    public static final MapCodec<CarryData> CODEC = FULL_CODEC.fieldOf("CarryData");

    public static final StreamCodec<RegistryFriendlyByteBuf, CarryData> STREAM_CODEC =
            ByteBufCodecs.fromCodecWithRegistries(FULL_CODEC);

    public enum CarryType { INVALID, BLOCK, ENTITY, PLAYER }

    public CarryData() { this(new CompoundTag()); }

    public CarryData(CompoundTag tag) {
        this.nbt = tag;
        this.type = tag.contains("carryType")
                ? CarryType.valueOf(tag.getStringOr("carryType", "INVALID"))
                : CarryType.INVALID;
        this.keyPressed = tag.getBooleanOr("keyPressed", false);
        this.selectedSlot = tag.getIntOr("selectedSlot", 0);
        if (tag.contains("activeScript")) {
            try {
                this.dataActiveScript = CarryScript.CODEC.parse(NbtOps.INSTANCE, tag.get("activeScript"))
                        .getOrThrow(msg -> new RuntimeException("Script: " + msg));
            } catch (Exception e) { this.dataActiveScript = null; }
        }
        if (getTick() < 0) setTick(0);
    }

    public CarryType getType() { return type; }

    public CompoundTag getFullNbt() {
        nbt.putString("carryType", type.toString());
        nbt.putBoolean("keyPressed", keyPressed);
        nbt.putInt("selectedSlot", selectedSlot);
        if (dataActiveScript != null) {
            Tag scriptTag = CarryScript.CODEC.encodeStart(NbtOps.INSTANCE, dataActiveScript)
                    .getOrThrow(msg -> new RuntimeException("Encode: " + msg));
            nbt.put("activeScript", scriptTag);
        }
        return nbt;
    }

    public void setBlock(BlockState state, BlockEntity blockEntity, ServerPlayer player, BlockPos pos) {
        this.type = CarryType.BLOCK;
        if (state.hasProperty(BlockStateProperties.WATERLOGGED))
            state = state.setValue(BlockStateProperties.WATERLOGGED, false);
        nbt.put("block", NbtUtils.writeBlockState(state));
        if (blockEntity != null) {
            var reporter = new ProblemReporter.ScopedCollector(CarryMechanicsAccess.LOGGER);
            var output = TagValueOutput.createWithContext(reporter, player.registryAccess());
            blockEntity.saveWithId(output);
            var tileTag = output.buildResult();
            nbt.put("tile", tileTag);
        }
    }

    public BlockState getBlock() {
        if (type != CarryType.BLOCK) throw new IllegalStateException("Not block: " + type);
        return NbtUtils.readBlockState(BuiltInRegistries.BLOCK, nbt.getCompoundOrEmpty("block"));
    }

    public BlockEntity getBlockEntity(BlockPos pos, HolderLookup.Provider registries) {
        if (type != CarryType.BLOCK || !nbt.contains("tile")) return null;
        return BlockEntity.loadStatic(pos, getBlock(), nbt.getCompoundOrEmpty("tile"), registries);
    }

    public void setEntity(Entity entity) {
        this.type = CarryType.ENTITY;
        var reporter = new ProblemReporter.ScopedCollector(CarryMechanicsAccess.LOGGER);
        var output = TagValueOutput.createWithContext(reporter, entity.registryAccess());
        entity.save(output);
        nbt.put("entity", output.buildResult());
    }

    public Entity getEntity(Level level) {
        if (type != CarryType.ENTITY) throw new IllegalStateException("Not entity: " + type);
        var reporter = new ProblemReporter.ScopedCollector(CarryMechanicsAccess.LOGGER);
        var input = TagValueInput.create(reporter, level.registryAccess(), nbt.getCompoundOrEmpty("entity"));
        var entity = EntityType.create(input, level, new EntitySpawnRequest(EntitySpawnReason.BUCKET, false));
        if (entity.isPresent()) return entity.get();
        CarryMechanicsAccess.LOGGER.error("Failed to create entity from: {}", nbt);
        clear();
        return new AreaEffectCloud(level, 0, 0, 0);
    }

    public void setCarryingPlayer(Player player) {
        this.type = CarryType.PLAYER;
        nbt.putString("player", player.getStringUUID());
    }

    public Player getCarryingPlayer(Level level) {
        if (type != CarryType.PLAYER) throw new IllegalStateException("Not player: " + type);
        if (!nbt.contains("player")) return null;
        return level.getServer().getPlayerList().getPlayer(java.util.UUID.fromString(nbt.getString("player").get()));
    }

    public boolean isCarrying() { return type != CarryType.INVALID; }
    public boolean isCarrying(CarryType t) { return type == t; }
    public boolean isKeyPressed() { return keyPressed; }
    public void setKeyPressed(boolean v) { keyPressed = v; nbt.putBoolean("keyPressed", v); }
    public void setSelected(int s) { selectedSlot = s; }
    public int getSelected() { return selectedSlot; }
    public Optional<CarryScript> getActiveScript() { return Optional.ofNullable(dataActiveScript); }
    public void setActiveScript(CarryScript script) { this.dataActiveScript = script; }
    public int getTick() { return nbt.getIntOr("tick", -1); }
    public void setTick(int t) { nbt.putInt("tick", t); }

    public void clear() {
        type = CarryType.INVALID;
        nbt = new CompoundTag();
        dataActiveScript = null;
    }

    public CarryData clone() { return new CarryData(nbt.copy()); }
}