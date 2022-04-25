# Datapack Installer
A Minecraft (Fabric) mod that allows you to put your datapacks in a folder and
be able to apply them at world creation without copying them to the temporary
folder or the world folder.  
The mod should be installed on the client.

### How does it work?
It's very simple, all it does is copy the files and folders from the
installed_datapacks folder inside the game data directory to the temporary
folder for the datapack selector when creating the world.

### Why should I use this over other "global datapack" mods?
This mod supports both compressed datapacks (.zip) and uncompressed datapacks
(folders), so you won't have trouble with multiple types of datapacks. You also
get to choose what datapacks to apply during world creation instead of having
all of the datapacks in the folder applied when you create the world.

### Usage
Step-by-step instructions to use this mod.
1. Insert your desired datapack(s) into .minecraft/installed_datapacks
   1. If you have started the game with this mod once, it should already be
   in the game+launcher data directory (.minecraft).
2. Begin world creation
   1. In the game, click Singleplayer, then click Create New World.
3. Manage datapacks
   1. Click the Data Packs button. If you've done everything correctly, the
   datapacks will appear on the list on the left!
