package de.jr.sleeppercentage;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityWorldChangeEvents;

public class Sleeppercentage implements ModInitializer {

    @Override
    public void onInitialize() {


        Gson.loadConfig();

        new PlayerEventListener().onPlayerConnection();

        ServerEntityWorldChangeEvents.AFTER_PLAYER_CHANGE_WORLD.register(PlayerEventListener::onPlayerChangedWorld);

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            new Command().command(dispatcher);
        });


    }
}
