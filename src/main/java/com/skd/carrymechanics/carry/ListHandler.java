package com.skd.carrymechanics.carry;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

public class ListHandler {

    public static final TagKey<Block> BLOCK_WHITELIST = createBlockTag("block_whitelist");
    public static final TagKey<Block> BLOCK_BLACKLIST = createBlockTag("block_blacklist");
    public static final TagKey<EntityType<?>> ENTITY_WHITELIST = createEntityTag("entity_whitelist");
    public static final TagKey<EntityType<?>> ENTITY_BLACKLIST = createEntityTag("entity_blacklist");
    public static final TagKey<EntityType<?>> STACKING_WHITELIST = createEntityTag("stacking_whitelist");
    public static final TagKey<EntityType<?>> STACKING_BLACKLIST = createEntityTag("stacking_blacklist");

    private static TagKey<Block> createBlockTag(String name) {
        return TagKey.create(BuiltInRegistries.BLOCK.key(),
                Identifier.fromNamespaceAndPath("carry_mechanics", name));
    }

    private static TagKey<EntityType<?>> createEntityTag(String name) {
        return TagKey.create(BuiltInRegistries.ENTITY_TYPE.key(),
                Identifier.fromNamespaceAndPath("carry_mechanics", name));
    }

    public static boolean isPermitted(Block block) {
        var holder = block.builtInRegistryHolder();
        boolean useWhitelist = ConfigAccess.COMMON_USE_WHITELIST_BLOCKS.get();
        if (useWhitelist) {
            return holder.is(BLOCK_WHITELIST);
        }
        return !holder.is(BLOCK_BLACKLIST);
    }

    public static boolean isPermitted(Entity entity) {
        var holder = entity.getType().builtInRegistryHolder();
        boolean useWhitelist = ConfigAccess.COMMON_USE_WHITELIST_ENTITIES.get();
        if (useWhitelist) {
            return holder.is(ENTITY_WHITELIST);
        }
        return !holder.is(ENTITY_BLACKLIST);
    }

    public static boolean isStackingPermitted(Entity entity) {
        var holder = entity.getType().builtInRegistryHolder();
        boolean useWhitelist = ConfigAccess.COMMON_USE_WHITELIST_STACKING.get();
        if (useWhitelist) {
            return holder.is(STACKING_WHITELIST);
        }
        return !holder.is(STACKING_BLACKLIST);
    }
}