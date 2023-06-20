package ml.unbreakinggold.datapackinstaller.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Path;

@Environment(EnvType.CLIENT)
public class DatapackInstallerClient implements ClientModInitializer {
    private static Path globalDatapackPath;

    public static Path getGlobalDatapackPath() {
        return globalDatapackPath;
    }

    private static void setGlobalDatapackPath(Path path) {
        if (globalDatapackPath != null) {
            throw new IllegalStateException("Global datapack path already set!");
        }

        globalDatapackPath = path;
    }

    @Override
    public void onInitializeClient() {
        Path path = FabricLoader.getInstance().getGameDir().resolve("installed_datapacks");
        setGlobalDatapackPath(path);

        if (!path.toFile().exists()) {
            path.toFile().mkdirs();
        }
    }
}
