package com.skd.carrymechanics.carry;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import java.util.*;

public class ModelOverrideHandler {
    public static final List<ModelOverride> OVERRIDES = new ArrayList<>();

    public record ModelOverride(Block block, Item renderItem, String type) {}

    public static void parseAndAdd(String input) {
        try {
            String[] parts = input.split("->");
            if (parts.length < 2) return;
            String blockPart = parts[0].trim();
            String renderPart = parts[1].trim();

            Block block = null;
            var blockOpt = BuiltInRegistries.BLOCK.getOptional(Identifier.parse(blockPart));
            if (blockOpt.isEmpty()) return;
            block = blockOpt.get();

            String[] renderSplit = renderPart.split(":");
            Item item = null;
            String type = "item";
            if (renderSplit.length >= 2) {
                var itemOpt = BuiltInRegistries.ITEM.getOptional(
                        Identifier.parse(renderSplit[0] + ":" + renderSplit[1]));
                if (itemOpt.isEmpty()) return;
                item = itemOpt.get();
                if (renderSplit.length > 2) type = renderSplit[2];
            } else {
                var itemOpt = BuiltInRegistries.ITEM.getOptional(Identifier.parse(renderPart));
                if (itemOpt.isEmpty()) return;
                item = itemOpt.get();
                type = "item";
            }

            OVERRIDES.add(new ModelOverride(block, item, type));
        } catch (Exception e) {
        }
    }

    public static Optional<ItemStack> getOverrideStack(Block block) {
        for (ModelOverride override : OVERRIDES) {
            if (override.block() == block) {
                return Optional.of(new ItemStack(override.renderItem()));
            }
        }
        return Optional.empty();
    }
}