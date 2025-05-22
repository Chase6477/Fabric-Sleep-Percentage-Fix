## Problem:

- You have one or more bot players on your Minecraft server to keep chunks loaded.

- You can't go to sleep without adjusting the playerSleepingPercentage gamerule.

- The number of real players on the server changes, so setting a fixed value isn't practical.

## Solution:

- A command allows you to specify the number of bot players.

- The playerSleepingPercentage gamerule is automatically adjusted based on the number of real players.

- You can define what percentage of real players must sleep.

- The gamerule is updated automatically whenever someone joins, leaves, or changes the config.

- Operator permissions are required to change the configuration.
