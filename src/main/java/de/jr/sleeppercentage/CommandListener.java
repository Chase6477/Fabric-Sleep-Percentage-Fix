package de.jr.sleeppercentage;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class CommandListener {

    public void command(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(literal("csp")
                .requires(source -> source.hasPermissionLevel(4))
                .executes(context -> 0)
                .then(literal("setBots")
                        .executes(context -> {
                            context.getSource().sendFeedback(() -> Text.literal("No value provided"), false);
                            return 0;
                        })
                        .then(argument("value", IntegerArgumentType.integer(0))
                                .executes(context -> {
                                    final int value = IntegerArgumentType.getInteger(context, "value");
                                    context.getSource().sendFeedback(() -> Text.literal("BotCount was set to: " + value), false);
                                    ConfigManager.config.botCount = value;
                                    ConfigManager.saveConfig();
                                    MinecraftServer server = context.getSource().getServer();
                                    PlayerEventListener.reload(server.getPlayerManager().getPlayerNames().length, server);
                                    return 1;
                                })
                        ))
                .then(literal("setPercentage")
                        .executes(context -> {
                            context.getSource().sendFeedback(() -> Text.literal("No value provided"), false);
                            return 0;
                        })
                        .then(argument("value", IntegerArgumentType.integer(0,100))
                                .executes(context -> {
                                    final int value = IntegerArgumentType.getInteger(context, "value");
                                    context.getSource().sendFeedback(() -> Text.literal("Percentage was set to: " + value), false);
                                    ConfigManager.config.playerSleepPercentage = value;
                                    ConfigManager.saveConfig();
                                    MinecraftServer server = context.getSource().getServer();
                                    PlayerEventListener.reload(server.getPlayerManager().getPlayerNames().length, server);
                                    return 1;
                                })
                        ))

                        .then(literal("reload")
                        .executes(context -> {
                            context.getSource().sendFeedback(() -> Text.literal("reloading complete"), false);
                            MinecraftServer server = context.getSource().getServer();
                            PlayerEventListener.reload(server.getPlayerManager().getPlayerNames().length, server);
                            return 1;
                        })
                ));
    }
}
