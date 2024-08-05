package ml.unbreakinggold.datapackinstaller.server;

import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.nio.file.Path;

@Environment(EnvType.SERVER)
public class DatapackInstallerServer implements DedicatedServerModInitializer {
    public static final Path MAIN_PATH = FabricLoader.getInstance().getGameDir().resolve("datapacks");

    @Override
    public void onInitializeServer() {
        File mainFile = MAIN_PATH.toFile();

        if (!mainFile.exists()) mainFile.mkdirs();
    }
}
