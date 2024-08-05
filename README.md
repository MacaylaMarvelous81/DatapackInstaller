# Datapack Installer
A Minecraft (Fabric) mod. When installed on the client, data packs put into the
data pack folder will be available for selection during world creation as well
as while paused in Singleplayer. When installed on the server, data packs put
into the data pack folder will be copied to the active world when the server
starts.

### Advantages
On the client, this mod works with the ingame resource and data pack selector.
While creating a world, or while paused in Singleplayer, you can choose which
data packs to  enable or disable with an interactive screen.

On the server, all that happens is that the data packs are copied from the
mod's data pack folder to the world data pack folder whenever the server
starts. If you are switching worlds or deleting them to regenerate often, all
you will need to do is switch or delete the world, and the data packs will
carry on if you leave the mod's folder intact.

### Disadvantages
This mod cannot keep data packs that require feature flags enabled or disabled.
For example, if you did not create a world with the built-in bundle data pack
enabled, and you enable the data pack with the mod, the data pack will only be
temporarily enabled until you quit the world.

### Usage
#### Client
1. Insert your desired data pack(s) into `datapacks` in the game's data folder
   1. If you have started the game with this mod installed, it should already
   be present.
2. Begin world creation
   1. In the game, click Singleplayer, then click Create New World.
   2. Alternatively, if you already have a world, open and pause the game.
3. Manage data packs
   1. Click the Data Packs button. On later versions, you may need to check
   the 'More' tab.
   2. The data packs should be available on the selector!
#### Server
1. Insert your desired data pack(s) into `datapacks` in the server data folder.
   1. This is probably the root, where eula.txt and server.properties are
   located.
2. Start the server
   1. The data packs will be copied to the active world.