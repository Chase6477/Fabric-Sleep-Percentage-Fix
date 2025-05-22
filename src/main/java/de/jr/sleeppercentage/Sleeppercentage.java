package de.jr.sleeppercentage;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

public class Sleeppercentage implements ModInitializer {

    @Override
    public void onInitialize() {

        ConfigManager.loadConfig();

        if (!ConfigManager.config.isEnabled) {
            throw new RuntimeException("Mod disabled via config.");
        }

        new PlayerEventListener().register();

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            new CommandListener().command(dispatcher);
        });


    }
}
