package ml.unbreakinggold.datapackinstaller.mixin.server;

import com.llamalad7.mixinextras.sugar.Local;
import ml.unbreakinggold.datapackinstaller.server.DatapackInstallerServer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.server.Main;
import net.minecraft.util.WorldSavePath;
import net.minecraft.world.level.storage.LevelStorage;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

@Mixin(Main.class)
@Environment(EnvType.SERVER)
public class ServerMainMixin {
    @Unique
    private static final Logger LOGGER = LogManager.getLogger(ServerMainMixin.class);

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/resource/ResourcePackManager;<init>(Lnet/minecraft/resource/ResourcePackProfile$Factory;[Lnet/minecraft/resource/ResourcePackProvider;)V"), method = "main")
    private static void onMain(CallbackInfo ci, @Local LevelStorage.Session session) {
        Path worldDataPackPath = session.getDirectory(WorldSavePath.DATAPACKS);
        File worldDataPackDir = worldDataPackPath.toFile();
        File modDataPackDir = DatapackInstallerServer.MAIN_PATH.toFile();
        try {
            FileUtils.copyDirectory(modDataPackDir, worldDataPackDir);
        } catch(IOException exception) {
            LOGGER.error("Failed to copy data packs to the world.", exception);
        }
    }
}
