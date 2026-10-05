package com.skd.carrymechanics.command;

import com.skd.carrymechanics.carry.*;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class CommandCarryMechanics {

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
        );
    }
}