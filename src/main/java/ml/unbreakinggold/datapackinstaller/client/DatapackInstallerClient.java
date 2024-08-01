package ml.unbreakinggold.datapackinstaller.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

@Environment(EnvType.CLIENT)
public class DatapackInstallerClient implements ClientModInitializer {
    private static final Logger LOGGER = LogManager.getLogger(DatapackInstallerClient.class);
    public static final Path MAIN_PATH = FabricLoader.getInstance().getGameDir().resolve("datapacks");
    public static final Path LEGACY_PATH = FabricLoader.getInstance().getGameDir().resolve("installed_datapacks");

    @Override
    public void onInitializeClient() {
        File mainFile = MAIN_PATH.toFile();
        File legacyFile = LEGACY_PATH.toFile();
        if (!mainFile.exists() && !legacyFile.exists() && legacyFile.isDirectory() && !mainFile.mkdirs())
            LOGGER.warn("User intervention recommended: default search path 'datapacks' could not be created. Please create it manually.");

        if (legacyFile.exists() && legacyFile.isDirectory()) {
            try {
                FileUtils.moveDirectory(legacyFile, mainFile);
                LOGGER.info("Migrated from installed_datapacks to datapacks.");
            } catch (IOException e) {
                LOGGER.error("User intervention recommended: Failed to migrate from installed_datapacks to datapacks. Please move datapacks manually.", e);
            }
        }
    }
}
