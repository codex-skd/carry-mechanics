package com.skd.carrymechanics.config;

import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.fml.config.ModConfig;
import java.util.List;

public class CarryConfig {

    public static void init() {}

    public static final ConfigData COMMON = new ConfigData();
    public static final ConfigData CLIENT = new ConfigData();

    public static ModConfigSpec COMMON_SPEC;
    public static ModConfigSpec CLIENT_SPEC;

    static {
        ModConfigSpec.Builder commonBuilder = new ModConfigSpec.Builder();
        ModConfigSpec.Builder clientBuilder = new ModConfigSpec.Builder();

        COMMON_SPEC = buildCommon(commonBuilder);
        CLIENT_SPEC = buildClient(clientBuilder);
    }

    private static ModConfigSpec buildCommon(ModConfigSpec.Builder builder) {
        builder.comment("Carry Mechanics Configuration");

        builder.push("settings");
        ConfigData.COMMON_MAX_DISTANCE = builder
                .comment("Maximum distance to pick up blocks/entities")
                .defineInRange("maxDistance", 2.5, 0.0, 10.0);
        ConfigData.COMMON_MAX_ENTITY_HEIGHT = builder
                .comment("Maximum height of entities that can be picked up")
                .defineInRange("maxEntityHeight", 2.5, 0.0, 10.0);
        ConfigData.COMMON_MAX_ENTITY_WIDTH = builder
                .comment("Maximum width of entities that can be picked up")
                .defineInRange("maxEntityWidth", 1.5, 0.0, 10.0);
        ConfigData.COMMON_BLOCK_SLOWNESS_MULTIPLIER = builder
                .comment("Slowness multiplier for carrying blocks")
                .defineInRange("blockSlownessMultiplier", 1.0, 0.0, 10.0);
        ConfigData.COMMON_ENTITY_SLOWNESS_MULTIPLIER = builder
                .comment("Slowness multiplier for carrying entities")
                .defineInRange("entitySlownessMultiplier", 1.0, 0.0, 10.0);
        ConfigData.COMMON_HEAVY_TILES = builder
                .comment("Whether heavy tiles cause more slowness")
                .define("heavyTiles", true);
        ConfigData.COMMON_HEAVY_ENTITIES = builder
                .comment("Whether bigger entities cause more slowness")
                .define("heavyEntities", true);
        ConfigData.COMMON_PICKUP_ALL_BLOCKS = builder
                .comment("Allow picking up blocks without tile entities")
                .define("pickupAllBlocks", false);
        ConfigData.COMMON_PICKUP_HOSTILE_MOBS = builder
                .comment("Allow picking up hostile mobs")
                .define("pickupHostileMobs", false);
        ConfigData.COMMON_PICKUP_PLAYERS = builder
                .comment("Allow picking up other players")
                .define("pickupPlayers", true);
        ConfigData.COMMON_SLOWNESS_IN_CREATIVE = builder
                .comment("Apply slowness in creative mode")
                .define("slownessInCreative", true);
        ConfigData.COMMON_ALLOW_BABIES = builder
                .comment("Allow picking up baby entities")
                .define("allowBabies", false);
        ConfigData.COMMON_STACKABLE_ENTITIES = builder
                .comment("Allow stacking entities on each other")
                .define("stackableEntities", true);
        ConfigData.COMMON_MAX_ENTITY_STACK_LIMIT = builder
                .comment("Maximum entities in a stack")
                .defineInRange("maxEntityStackLimit", 10, 1, 100);
        ConfigData.COMMON_HIT_WHILE_CARRYING = builder
                .comment("Allow hitting while carrying")
                .define("hitWhileCarrying", false);
        ConfigData.COMMON_DROP_CARRIED_WHEN_HIT = builder
                .comment("Drop carried object when hit")
                .define("dropCarriedWhenHit", false);
        ConfigData.COMMON_PICKUP_UNBREAKABLE_BLOCKS = builder
                .comment("Allow picking up unbreakable blocks")
                .define("pickupUnbreakableBlocks", false);
        ConfigData.COMMON_USE_SCRIPTS = builder
                .comment("Use the scripting system")
                .define("useScripts", true);
        ConfigData.COMMON_ENTITY_SIZE_MATTERS_STACKING = builder
                .comment("Entity size matters when stacking")
                .define("entitySizeMattersStacking", true);
        builder.pop();

        builder.push("whitelist");
        ConfigData.COMMON_USE_WHITELIST_BLOCKS = builder.comment("Use block whitelist instead of blacklist").define("useWhitelistBlocks", false);
        ConfigData.COMMON_USE_WHITELIST_ENTITIES = builder.comment("Use entity whitelist instead of blacklist").define("useWhitelistEntities", false);
        ConfigData.COMMON_USE_WHITELIST_STACKING = builder.comment("Use stacking whitelist instead of blacklist").define("useWhitelistStacking", false);
        builder.pop();

        return builder.build();
    }

    private static ModConfigSpec buildClient(ModConfigSpec.Builder builder) {
        builder.push("client");
        ConfigData.CLIENT_RENDER_ARMS = builder.comment("Render arms when carrying").define("renderArms", true);
        ConfigData.CLIENT_RENDER_NAME_TAG = builder.comment("Render name tag of carried entity").define("renderNameTag", true);
        builder.pop();
        return builder.build();
    }
}