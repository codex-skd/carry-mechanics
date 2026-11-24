package com.skd.carrymechanics.scripting;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class ScriptManager {
    public static final List<CarryScript> SCRIPTS = new ArrayList<>();

    public static void setScripts(List<CarryScript> scripts) {
        SCRIPTS.clear();
        SCRIPTS.addAll(scripts);
        SCRIPTS.sort(Comparator.comparingInt(CarryScript::priority).reversed());
    }

    public static Optional<CarryScript> inspectBlock(BlockState state, Level level, BlockPos pos, CompoundTag tileNbt) {
        for (CarryScript script : SCRIPTS) {
            if (script.object().type() == CarryScript.ObjectType.BLOCK) {
                String id = state.getBlock().builtInRegistryHolder().key().toString();
                if (scriptMatches(script, id)) return Optional.of(script);
            }
        }
        return Optional.empty();
    }

    public static Optional<CarryScript> inspectEntity(Entity entity) {
        for (CarryScript script : SCRIPTS) {
            if (script.object().type() == CarryScript.ObjectType.ENTITY) {
                String id = entity.getType().builtInRegistryHolder().key().toString();
                if (scriptMatches(script, id)) return Optional.of(script);
            }
        }
        return Optional.empty();
    }

    private static boolean scriptMatches(CarryScript script, String id) {
        String match = script.object().matchExpression();
        if (match.contains("[")) {
            return id.startsWith(match.substring(0, match.indexOf("[")));
        }
        return id.equals(match) || match.equals("*");
    }
}