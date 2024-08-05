package ml.unbreakinggold.datapackinstaller.mixin.client;

import ml.unbreakinggold.datapackinstaller.client.DatapackInstallerClient;
import net.minecraft.client.gui.screen.world.CreateWorldScreen;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.nio.file.Path;

@Mixin(CreateWorldScreen.class)
public abstract class CreateWorldScreenMixin {
    @Unique
    private static final Logger LOGGER = LogManager.getLogger(CreateWorldScreenMixin.class);
    @Shadow
    @Nullable
    private Path field_25477;

    /**
     * @author Jomar Milan - July 31st, 2024 - Minecraft 1.20.5
     * @reason Use persistent mod directory instead of vanilla temporary directory
     */
    @Overwrite
    @Nullable
    public Path method_29693() {
        this.field_25477 = DatapackInstallerClient.MAIN_PATH;

        return this.field_25477;
    }

    /**
     * @author Jomar Milan - July 31st, 2024 - Minecraft 1.20.5
     * @reason Because dataPackTempDir is now persistent and not temporary, the directory should no longer be cleared.
     */
    @Overwrite
    private void method_29695() {
        LOGGER.info("Suppressing attempt to clear data pack directory");
    }
}
