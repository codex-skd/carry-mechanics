package com.skd.carrymechanics.pickupcondition;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.state.BlockState;
import java.util.*;

public class PickupCondition {
    private final String raw;
    private final boolean isBlock;

    public PickupCondition(String raw, boolean isBlock) {
        this.raw = raw;
        this.isBlock = isBlock;
    }

    public static PickupCondition parse(String input) {
        boolean block = input.startsWith("block:");
        String conditionPart = input;
        if (block && conditionPart.contains("[")) {
            conditionPart = conditionPart.substring(conditionPart.indexOf("["));
        } else {
            conditionPart = input.substring(input.indexOf(":") + 1);
        }
        return new PickupCondition(conditionPart, block);
    }

    public boolean isBlock() { return isBlock; }

    public List<BlockState> getBlockStates() { return List.of(); }
    public List<EntityType<?>> getEntityTypes() { return List.of(); }

    public boolean isFulfilled(ServerPlayer player) {
        return true;
    }
}