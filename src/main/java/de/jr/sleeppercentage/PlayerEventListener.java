package de.jr.sleeppercentage;

import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.GameRules;

public class PlayerEventListener {


    public void register() {
        // Player join
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            reload(server.getPlayerManager().getPlayerNames().length + 1, server);
        });

        // Player leave
        ServerPlayConnectionEvents.DISCONNECT.register((handler, server) -> {
            reload(server.getPlayerManager().getPlayerNames().length, server);
        });
    }

    public static void reload(int count, MinecraftServer server) {
        System.out.println(count);

        int botCount = ConfigManager.config.botCount;
        int playerCount = Math.max(count - botCount, 0);
        int playerSleepPercentage = ConfigManager.config.playerSleepPercentage;
        if (count > 0) {

            float newSleepPercentage = (1.0f /  (float) count) * (float) playerSleepPercentage *  (float) playerCount;


            for (ServerWorld world : server.getWorlds()) {
                world.getGameRules().get(GameRules.PLAYERS_SLEEPING_PERCENTAGE).set((int) newSleepPercentage, server);
            }
        }
    }
}
