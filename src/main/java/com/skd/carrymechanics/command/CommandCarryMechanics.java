package com.skd.carrymechanics.command;

import com.skd.carrymechanics.carry.*;
import com.skd.carrymechanics.config.CarryConfig;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CommandCarryMechanics {

    private static final SuggestionProvider<CommandSourceStack> BLOCK_ID_SUGGESTIONS = (ctx, builder) -> {
        List<String> suggestions = new ArrayList<>();
        for (Identifier id : BuiltInRegistries.BLOCK.keySet()) {
            suggestions.add(id.toString());
        }
        for (String namespace : BuiltInRegistries.BLOCK.keySet().stream().map(Identifier::getNamespace).collect(Collectors.toSet())) {
            suggestions.add(namespace + ":*");
        }
        return SharedSuggestionProvider.suggest(suggestions, builder);
    };

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("carrymechanics")
                .then(Commands.literal("debug")
                        .executes(ctx -> {
                            ServerPlayer player = ctx.getSource().getPlayerOrException();
                            CarryData data = CarryDataManager.getCarryData(player);
                            ctx.getSource().sendSuccess(() ->
                                    Component.literal("CarryData: " + data.getType()
                                            + " keyPressed=" + data.isKeyPressed()
                                            + " tick=" + data.getTick()
                                            + " script=" + data.getActiveScript().map(s -> s.object().name()).orElse("none")), false);
                            return 1;
                        })
                )
                .then(Commands.literal("clear")
                        .executes(ctx -> {
                            ServerPlayer player = ctx.getSource().getPlayerOrException();
                            CarryData data = CarryDataManager.getCarryData(player);
                            data.clear();
                            CarryDataManager.setCarryData(player, data);
                            ctx.getSource().sendSuccess(() -> Component.literal("Carry data cleared."), true);
                            return 1;
                        })
                        .then(Commands.argument("target", EntityArgument.player())
                                .executes(ctx -> {
                                    ServerPlayer target = EntityArgument.getPlayer(ctx, "target");
                                    CarryData data = CarryDataManager.getCarryData(target);
                                    data.clear();
                                    CarryDataManager.setCarryData(target, data);
                                    ctx.getSource().sendSuccess(() ->
                                            Component.literal("Cleared carry data for " + target.getDisplayName().getString()), true);
                                    return 1;
                                })
                        )
                )
                .then(Commands.literal("place")
                        .executes(ctx -> {
                            ServerPlayer player = ctx.getSource().getPlayerOrException();
                            PlacementHandler.placeCarried(player);
                            ctx.getSource().sendSuccess(() -> Component.literal("Carried item placed."), true);
                            return 1;
                        })
                        .then(Commands.argument("target", EntityArgument.player())
                                .executes(ctx -> {
                                    ServerPlayer target = EntityArgument.getPlayer(ctx, "target");
                                    PlacementHandler.placeCarried(target);
                                    ctx.getSource().sendSuccess(() ->
                                            Component.literal("Placed carried item for " + target.getDisplayName().getString()), true);
                                    return 1;
                                })
                        )
                )
                .then(Commands.literal("blacklist")
                        .requires(Commands.hasPermission(Commands.LEVEL_GAMEMASTERS))
                        .then(Commands.literal("add").then(Commands.argument("block", StringArgumentType.greedyString()).suggests(BLOCK_ID_SUGGESTIONS)
                                .executes(ctx -> modifyList(ctx.getSource(), ConfigAccess.BLOCKED_BLOCKS, StringArgumentType.getString(ctx, "block"), true, "blockedBlocks"))))
                        .then(Commands.literal("remove").then(Commands.argument("block", StringArgumentType.greedyString()).suggests(BLOCK_ID_SUGGESTIONS)
                                .executes(ctx -> modifyList(ctx.getSource(), ConfigAccess.BLOCKED_BLOCKS, StringArgumentType.getString(ctx, "block"), false, "blockedBlocks"))))
                        .then(Commands.literal("list")
                                .executes(ctx -> listEntries(ctx.getSource(), ConfigAccess.BLOCKED_BLOCKS, "blockedBlocks")))
                )
                .then(Commands.literal("whitelist")
                        .requires(Commands.hasPermission(Commands.LEVEL_GAMEMASTERS))
                        .then(Commands.literal("add").then(Commands.argument("block", StringArgumentType.greedyString()).suggests(BLOCK_ID_SUGGESTIONS)
                                .executes(ctx -> modifyList(ctx.getSource(), ConfigAccess.ALLOWED_BLOCKS, StringArgumentType.getString(ctx, "block"), true, "allowedBlocks"))))
                        .then(Commands.literal("remove").then(Commands.argument("block", StringArgumentType.greedyString()).suggests(BLOCK_ID_SUGGESTIONS)
                                .executes(ctx -> modifyList(ctx.getSource(), ConfigAccess.ALLOWED_BLOCKS, StringArgumentType.getString(ctx, "block"), false, "allowedBlocks"))))
                        .then(Commands.literal("list")
                                .executes(ctx -> listEntries(ctx.getSource(), ConfigAccess.ALLOWED_BLOCKS, "allowedBlocks")))
                )
        );
    }

    private static int modifyList(CommandSourceStack source, List<String> list, String rawEntry, boolean add, String name) {
        String entry = rawEntry.trim();
        if (entry.isEmpty()) {
            source.sendFailure(Component.literal("Invalid block entry: empty"));
            return 0;
        }
        if (add) {
            if (!isValidBlockEntry(entry)) {
                source.sendFailure(Component.literal("Invalid block id (not \"modid:*\" and not a registered block): " + entry));
                return 0;
            }
            if (list.contains(entry)) {
                source.sendFailure(Component.literal(name + " already contains: " + entry));
                return 0;
            }
            list.add(entry);
            CarryConfig.save();
            source.sendSuccess(() -> Component.literal("Added to " + name + ": " + entry), true);
            return 1;
        } else {
            if (!list.remove(entry)) {
                source.sendFailure(Component.literal(name + " does not contain: " + entry));
                return 0;
            }
            CarryConfig.save();
            source.sendSuccess(() -> Component.literal("Removed from " + name + ": " + entry), true);
            return 1;
        }
    }

    private static int listEntries(CommandSourceStack source, List<String> entries, String name) {
        if (entries == null || entries.isEmpty()) {
            source.sendSuccess(() -> Component.literal(name + ": (empty)"), false);
            return 1;
        }
        source.sendSuccess(() -> Component.literal(name + " (" + entries.size() + "): " + String.join(", ", entries)), false);
        return 1;
    }

    private static boolean isValidBlockEntry(String entry) {
        if (entry.endsWith(":*")) {
            String namespace = entry.substring(0, entry.length() - 2);
            Identifier probe = Identifier.tryParse(namespace + ":probe");
            if (probe == null) return false;
            for (Identifier id : BuiltInRegistries.BLOCK.keySet()) {
                if (id.getNamespace().equals(namespace)) return true;
            }
            return false;
        }
        Identifier id = Identifier.tryParse(entry);
        return id != null && BuiltInRegistries.BLOCK.containsKey(id);
    }
}