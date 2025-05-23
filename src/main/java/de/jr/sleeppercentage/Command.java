package de.jr.sleeppercentage;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.UUID;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class Command {

    public void command(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(literal("csp").requires(source -> source.hasPermissionLevel(4))
                .executes(context -> 0)

                .then(literal("addBot")
                        .executes(context -> {
                            context.getSource().sendFeedback(() -> Text.literal("No value provided"), false);
                            return 0;})

                        .then(argument("player", EntityArgumentType.player()).executes(context -> {
                            ServerPlayerEntity player = EntityArgumentType.getPlayer(context, "player");
                            if (!ConfigManager.config.bots.containsKey(player.getUuid())) {
                                context.getSource().sendFeedback(() -> Text.literal(player.getName().getLiteralString() + " was added to bots"), false);
                                ConfigManager.config.bots.put(player.getUuid(), player.getName().getLiteralString());
                                ConfigManager.saveConfig();
                                MinecraftServer server = context.getSource().getServer();
                                PlayerEventListener.reload(server);
                            } else context.getSource().sendFeedback(() -> Text.literal(player.getName().getLiteralString() + " was already added to bots"), false);
                            return 1;})))

                .then(literal("removeBot")
                        .executes(context -> {
                            context.getSource().sendFeedback(() -> Text.literal("No value provided"), false);
                            return 0;})

                        .then(argument("player", EntityArgumentType.player()).suggests((context, builder) -> {
                            for (UUID uuid : ConfigManager.config.bots.keySet()) {
                                builder.suggest(ConfigManager.config.bots.get(uuid));
                            }
                            return builder.buildFuture();})
                                .executes(context -> {
                                    ServerPlayerEntity player = EntityArgumentType.getPlayer(context, "player");
                                    if (ConfigManager.config.bots.containsKey(player.getUuid())) {
                                        context.getSource().sendFeedback(() -> Text.literal(player.getName().getLiteralString() + " was removed from bots"), false);
                                        ConfigManager.config.bots.remove(player.getUuid());
                                        ConfigManager.saveConfig();
                                        MinecraftServer server = context.getSource().getServer();
                                        PlayerEventListener.reload(server);
                                    } else context.getSource().sendFeedback(() -> Text.literal(player.getName().getLiteralString() + " was not added to bots"), false);
                                    return 1;})))

                .then(literal("setPercentage")
                        .executes(context ->

                        {
                            context.getSource().sendFeedback(() -> Text.literal("No value provided"), false);
                            return 0;
                        })
                        .then(argument("value", IntegerArgumentType.integer(0, 100))
                                .executes(context ->

                                {
                                    int value = IntegerArgumentType.getInteger(context, "value");
                                    context.getSource().sendFeedback(() -> Text.literal("Percentage was set to: " + value), false);
                                    ConfigManager.config.playerSleepPercentage = value;
                                    ConfigManager.saveConfig();
                                    MinecraftServer server = context.getSource().getServer();
                                    PlayerEventListener.reload(server);
                                    return 1;})))

                .then(literal("reload")
                        .executes(context ->


                        {
                            context.getSource().sendFeedback(() -> Text.literal("reloading complete"), false);
                            MinecraftServer server = context.getSource().getServer();
                            PlayerEventListener.reload(server);
                            return 1;
                        })));
    }
}
