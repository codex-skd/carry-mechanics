package com.skd.carrymechanics.pickupcondition;

import net.minecraft.core.registries.BuiltInRegistries;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.*;

public class PickupConditionHandler {
    private static final Map<BlockState, PickupCondition> BLOCK_CONDITIONS = new HashMap<>();
    private static final Map<net.minecraft.world.entity.EntityType<?>, PickupCondition> ENTITY_CONDITIONS = new HashMap<>();

    public static void add(String input) {
        try {
            PickupCondition cond = PickupCondition.parse(input);
            if (cond.isBlock()) {
                for (BlockState state : cond.getBlockStates()) {
                    BLOCK_CONDITIONS.put(state, cond);
                }
            } else {
                for (var entry : cond.getEntityTypes()) {
                    ENTITY_CONDITIONS.put(entry, cond);
                }
            }
        } catch (Exception e) {
        }
    }

    public static Optional<PickupCondition> getPickupCondition(BlockState state) {
        return Optional.ofNullable(BLOCK_CONDITIONS.getOrDefault(state, null));
    }

    public static Optional<PickupCondition> getPickupCondition(Entity entity) {
        return Optional.ofNullable(ENTITY_CONDITIONS.get(entity.getType()));
    }
}