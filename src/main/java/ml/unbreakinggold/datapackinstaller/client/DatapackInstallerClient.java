package ml.unbreakinggold.datapackinstaller.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Path;

@Environment(EnvType.CLIENT)
public class DatapackInstallerClient implements ClientModInitializer {
    public static Path globalDatapackPath;
    @Override
    public void onInitializeClient() {
        globalDatapackPath = FabricLoader.getInstance().getGameDir().resolve("installed_datapacks");
        if (!globalDatapackPath.toFile().exists()) {
            globalDatapackPath.toFile().mkdirs();
        }
    }
}
