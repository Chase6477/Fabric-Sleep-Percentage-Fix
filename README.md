## Problem

- You have one or more bot players on your Minecraft server to keep chunks loaded.

- You can't go to sleep without adjusting the playerSleepingPercentage gamerule.

- The number of real players on the server changes, so setting a fixed value isn't practical.

## Solution

- A command allows you to specify players as bots.
  
  ```/csp addBot name```
  
  ```/csp removeBot name```

- The playerSleepingPercentage gamerule is automatically adjusted based on the number of real players.

- You can define what percentage of real players must sleep.
  
  ```/csp setPercentage value```

- The gamerule is updated automatically whenever someone joins, leaves, changes dimensions or changes the config.

- Permissionslevels can be modified in the Config

## Error Handeling

- When something doesn't seem to work, try to reload the mod.

  ```/csp reload```

- Or delete the *csp.json* to factory-reset it.

## Requires

- A Fabric based Minecraft Server

## Properties
```properties
minecraft_version = 1.21.4
yarn_mappings = 1.21.4+build.8
loader_version = 0.16.9
mod_version = v.1.1.0
fabric_version = 0.111.0+1.21.4
