package com.skd.carrymechanics.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.skd.carrymechanics.carry.CarryMechanicsAccess;
import com.skd.carrymechanics.carry.ConfigAccess;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class CarryConfig {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_PATH =
            FabricLoader.getInstance().getConfigDir().resolve("carry_mechanics.json");

    private CarryConfig() {
    }

    public static void load() {
        if (!Files.exists(CONFIG_PATH)) {
            save();
            return;
        }
        try {
            ConfigData data = GSON.fromJson(Files.readString(CONFIG_PATH), ConfigData.class);
            if (data == null) {
                save();
                return;
            }
            apply(data);
        } catch (Exception e) {
            CarryMechanicsAccess.LOGGER.error("Failed to load config, using defaults: {}", e.getMessage());
            save();
        }
    }

    public static void save() {
        try {
            Files.writeString(CONFIG_PATH, GSON.toJson(current()));
        } catch (IOException e) {
            CarryMechanicsAccess.LOGGER.error("Failed to save config: {}", e.getMessage());
        }
    }

    private static void apply(ConfigData d) {
        ConfigAccess.MAX_DISTANCE = d.maxDistance;
        ConfigAccess.MAX_ENTITY_HEIGHT = d.maxEntityHeight;
        ConfigAccess.MAX_ENTITY_WIDTH = d.maxEntityWidth;
        ConfigAccess.HEAVY_TILES = d.heavyTiles;
        ConfigAccess.HEAVY_ENTITIES = d.heavyEntities;
        ConfigAccess.PICKUP_ALL_BLOCKS = d.pickupAllBlocks;
        ConfigAccess.PICKUP_HOSTILE_MOBS = d.pickupHostileMobs;
        ConfigAccess.SLOWNESS_IN_CREATIVE = d.slownessInCreative;
        ConfigAccess.ALLOW_BABIES = d.allowBabies;
        ConfigAccess.STACKABLE_ENTITIES = d.stackableEntities;
        ConfigAccess.MAX_ENTITY_STACK_LIMIT = d.maxEntityStackLimit;
        ConfigAccess.HIT_WHILE_CARRYING = d.hitWhileCarrying;
        ConfigAccess.DROP_CARRIED_WHEN_HIT = d.dropCarriedWhenHit;
        ConfigAccess.PICKUP_UNBREAKABLE_BLOCKS = d.pickupUnbreakableBlocks;
        ConfigAccess.USE_SCRIPTS = d.useScripts;
        ConfigAccess.ENTITY_SIZE_MATTERS_STACKING = d.entitySizeMattersStacking;
        ConfigAccess.BLOCK_SLOWNESS_MULTIPLIER = d.blockSlownessMultiplier;
        ConfigAccess.ENTITY_SLOWNESS_MULTIPLIER = d.entitySlownessMultiplier;
        ConfigAccess.COMMON_USE_WHITELIST_BLOCKS = d.useWhitelistBlocks;
        ConfigAccess.COMMON_USE_WHITELIST_ENTITIES = d.useWhitelistEntities;
        ConfigAccess.COMMON_USE_WHITELIST_STACKING = d.useWhitelistStacking;
        ConfigAccess.ALLOW_TWO_TALL_BLOCKS = d.allowTwoTallBlocks;
        ConfigAccess.ALLOW_LARGE_BLOCKS = d.allowLargeBlocks;
        ConfigAccess.ALLOWED_BLOCKS = d.allowedBlocks != null ? new java.util.ArrayList<>(d.allowedBlocks) : new java.util.ArrayList<>();
        ConfigAccess.BLOCKED_BLOCKS = d.blockedBlocks != null ? new java.util.ArrayList<>(d.blockedBlocks) : new java.util.ArrayList<>();
        ConfigAccess.RENDER_ARMS = d.renderArms;
        ConfigAccess.RENDER_NAME_TAG = d.renderNameTag;
    }

    private static ConfigData current() {
        ConfigData d = new ConfigData();
        d.maxDistance = ConfigAccess.MAX_DISTANCE;
        d.maxEntityHeight = ConfigAccess.MAX_ENTITY_HEIGHT;
        d.maxEntityWidth = ConfigAccess.MAX_ENTITY_WIDTH;
        d.heavyTiles = ConfigAccess.HEAVY_TILES;
        d.heavyEntities = ConfigAccess.HEAVY_ENTITIES;
        d.pickupAllBlocks = ConfigAccess.PICKUP_ALL_BLOCKS;
        d.pickupHostileMobs = ConfigAccess.PICKUP_HOSTILE_MOBS;
        d.slownessInCreative = ConfigAccess.SLOWNESS_IN_CREATIVE;
        d.allowBabies = ConfigAccess.ALLOW_BABIES;
        d.stackableEntities = ConfigAccess.STACKABLE_ENTITIES;
        d.maxEntityStackLimit = ConfigAccess.MAX_ENTITY_STACK_LIMIT;
        d.hitWhileCarrying = ConfigAccess.HIT_WHILE_CARRYING;
        d.dropCarriedWhenHit = ConfigAccess.DROP_CARRIED_WHEN_HIT;
        d.pickupUnbreakableBlocks = ConfigAccess.PICKUP_UNBREAKABLE_BLOCKS;
        d.useScripts = ConfigAccess.USE_SCRIPTS;
        d.entitySizeMattersStacking = ConfigAccess.ENTITY_SIZE_MATTERS_STACKING;
        d.blockSlownessMultiplier = ConfigAccess.BLOCK_SLOWNESS_MULTIPLIER;
        d.entitySlownessMultiplier = ConfigAccess.ENTITY_SLOWNESS_MULTIPLIER;
        d.useWhitelistBlocks = ConfigAccess.COMMON_USE_WHITELIST_BLOCKS;
        d.useWhitelistEntities = ConfigAccess.COMMON_USE_WHITELIST_ENTITIES;
        d.useWhitelistStacking = ConfigAccess.COMMON_USE_WHITELIST_STACKING;
        d.allowTwoTallBlocks = ConfigAccess.ALLOW_TWO_TALL_BLOCKS;
        d.allowLargeBlocks = ConfigAccess.ALLOW_LARGE_BLOCKS;
        d.allowedBlocks = new java.util.ArrayList<>(ConfigAccess.ALLOWED_BLOCKS);
        d.blockedBlocks = new java.util.ArrayList<>(ConfigAccess.BLOCKED_BLOCKS);
        d.renderArms = ConfigAccess.RENDER_ARMS;
        d.renderNameTag = ConfigAccess.RENDER_NAME_TAG;
        return d;
    }

    private static ConfigData defaults() {
        return new ConfigData();
    }
}
