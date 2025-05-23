package de.jr.sleeppercentage;

import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

import java.util.List;
import java.util.UUID;

public class PlayerEventListener {


    public void onPlayerConnection() {
        // Player join
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            reload(server);
        });

        // Player leave
        ServerPlayConnectionEvents.DISCONNECT.register((handler, server) -> {
            reload(server);
        });
    }
    public static void onPlayerChangedWorld(ServerPlayerEntity player, ServerWorld fromWorld, ServerWorld toWorld) {
        if (player == null)return;
        if ((fromWorld.getRegistryKey() == World.OVERWORLD) || (toWorld.getRegistryKey() == World.OVERWORLD)) {
            reload(player.getServer());
        }
    }

    public static void reload(MinecraftServer server) {
        List<UUID> overworldPlayerUUIDs = server.getPlayerManager().getPlayerList().stream()
                .filter(p -> p.getWorld().getRegistryKey() == World.OVERWORLD)
                .map(ServerPlayerEntity::getUuid)
                .toList();

        int count = overworldPlayerUUIDs.size();
        int botCount = 0;
        for (UUID uuid : overworldPlayerUUIDs) {
            if (ConfigManager.config.bots.containsKey(uuid))
                botCount++;
        }

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
