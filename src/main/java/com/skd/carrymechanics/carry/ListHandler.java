package com.skd.carrymechanics.carry;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;
import java.util.Optional;

public class ListHandler {

    public static final TagKey<Block> BLOCK_WHITELIST = createBlockTag("block_whitelist");
    public static final TagKey<Block> BLOCK_BLACKLIST = createBlockTag("block_blacklist");
    public static final TagKey<EntityType<?>> ENTITY_WHITELIST = createEntityTag("entity_whitelist");
    public static final TagKey<EntityType<?>> ENTITY_BLACKLIST = createEntityTag("entity_blacklist");
    public static final TagKey<EntityType<?>> STACKING_WHITELIST = createEntityTag("stacking_whitelist");
    public static final TagKey<EntityType<?>> STACKING_BLACKLIST = createEntityTag("stacking_blacklist");
    public static final TagKey<EntityType<?>> HOSTILE_PICKUP_WHITELIST = createEntityTag("hostile_pickup_whitelist");

    private static TagKey<Block> createBlockTag(String name) {
        return TagKey.create(BuiltInRegistries.BLOCK.key(),
                new ResourceLocation("carry_mechanics", name));
    }

    private static TagKey<EntityType<?>> createEntityTag(String name) {
        return TagKey.create(BuiltInRegistries.ENTITY_TYPE.key(),
                new ResourceLocation("carry_mechanics", name));
    }

    public static boolean isPermitted(Block block) {
        var holder = block.builtInRegistryHolder();
        boolean useWhitelist = ConfigAccess.COMMON_USE_WHITELIST_BLOCKS.get();
        if (useWhitelist) {
            return holder.is(BLOCK_WHITELIST);
        }
        return !holder.is(BLOCK_BLACKLIST);
    }

    /**
     * Context-aware block permission check (server-side authoritative). Order:
     * blockedBlocks -> allowedBlocks -> existing tag/whitelist logic -> two-tall rule -> large rule.
     * blockedBlocks wins over allowedBlocks. allowedBlocks overrides allowTwoTall/allowLarge and datapack blacklist.
     */
    public static boolean isPermitted(Level level, BlockPos pos, BlockState state) {
        ResourceLocation id = BuiltInRegistries.BLOCK.getKey(state.getBlock());
        if (matchesAny(ConfigAccess.BLOCKED_BLOCKS.get(), id)) {
            return false;
        }
        if (matchesAny(ConfigAccess.ALLOWED_BLOCKS.get(), id)) {
            return true;
        }
        if (!isPermitted(state.getBlock())) {
            return false;
        }

        Optional<MultiBlockHelper.Pair> pair = MultiBlockHelper.findVerticalPair(level, pos);
        boolean twoTall = pair.isPresent() || MultiBlockHelper.hasHalfLikeProperty(state);
        if (twoTall) {
            return ConfigAccess.ALLOW_TWO_TALL_BLOCKS.get();
        }

        if (MultiBlockHelper.isLargeBlock(level, pos, state)) {
            return ConfigAccess.ALLOW_LARGE_BLOCKS.get();
        }

        return true;
    }

    private static boolean matchesAny(List<? extends String> entries, ResourceLocation id) {
        if (entries == null || id == null) return false;
        for (String raw : entries) {
            if (matchesEntry(raw, id)) return true;
        }
        return false;
    }

    private static boolean matchesEntry(String raw, ResourceLocation id) {
        if (raw == null) return false;
        String entry = raw.trim();
        if (entry.isEmpty()) return false;
        if (entry.endsWith(":*")) {
            String namespace = entry.substring(0, entry.length() - 2);
            return namespace.equals(id.getNamespace());
        }
        ResourceLocation parsed = ResourceLocation.tryParse(entry);
        if (parsed == null) return false;
        return parsed.equals(id);
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

    public static boolean isHostilePickupExempt(Entity entity) {
        return entity.getType().builtInRegistryHolder().is(HOSTILE_PICKUP_WHITELIST);
    }
}