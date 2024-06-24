package ml.unbreakinggold.datapackinstaller.mixin;

import ml.unbreakinggold.datapackinstaller.client.DatapackInstallerClient;
import net.minecraft.client.gui.screen.world.CreateWorldScreen;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

@Mixin(CreateWorldScreen.class)
public abstract class CreateWorldScreenMixin {
    private static final Logger LOGGER = LogManager.getLogger(CreateWorldScreenMixin.class);
    @Shadow @Nullable protected abstract Path getDataPackTempDir();

    @Inject(at = @At("HEAD"), method = "openPackScreen")
    private void init(CallbackInfo info) {
        Path tempPath = this.getDataPackTempDir();
        File tempPathFile = tempPath.toFile();

        for (Path searchPath : DatapackInstallerClient.SearchPaths) {
            File searchPathFile = searchPath.toFile();
            if (!searchPathFile.exists()) continue;

            try {
                FileUtils.copyDirectory(searchPathFile, tempPathFile);
            } catch (IOException e) {
                LOGGER.error("Could not copy from search path {}. Message: {}", searchPath, e.getMessage());
            }
        }
    }
}
