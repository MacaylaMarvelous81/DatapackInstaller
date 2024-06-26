package ml.unbreakinggold.datapackinstaller.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.nio.file.Path;

@Environment(EnvType.CLIENT)
public class DatapackInstallerClient implements ClientModInitializer {
    private static final Logger LOGGER = LogManager.getLogger(DatapackInstallerClient.class);
    private static final Path MAIN_PATH = FabricLoader.getInstance().getGameDir().resolve("datapacks");
    private static final Path LEGACY_PATH = FabricLoader.getInstance().getGameDir().resolve("installed_datapacks");
    public static Path[] SearchPaths = new Path[]{ MAIN_PATH, LEGACY_PATH };

    @Override
    public void onInitializeClient() {
        File mainFile = MAIN_PATH.toFile();
        if (!mainFile.exists() && !mainFile.mkdirs())
            LOGGER.warn("User intervention recommended: default search path 'datapacks' could not be created.");
    }
}
