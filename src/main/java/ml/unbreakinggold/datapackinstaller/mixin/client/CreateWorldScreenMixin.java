package ml.unbreakinggold.datapackinstaller.mixin.client;

import ml.unbreakinggold.datapackinstaller.client.DatapackInstallerClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.world.CreateWorldCallback;
import net.minecraft.client.gui.screen.world.CreateWorldScreen;
import net.minecraft.client.world.GeneratorOptionsHolder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.nio.file.Path;
import java.util.Optional;
import java.util.OptionalLong;

@Mixin(CreateWorldScreen.class)
public abstract class CreateWorldScreenMixin {
    @Unique
    private static final Logger LOGGER = LogManager.getLogger(CreateWorldScreenMixin.class);

    @Shadow
    @Nullable
    private Path dataPackTempDir;

    /**
     * Inject after CreateWorldScreen.create(...), replaces the data pack directory with thr persistent one.
     */
    @Inject(
            method = "<init>",
            at = @At("TAIL")
    )
    private void onInit(MinecraftClient client, Screen parent, GeneratorOptionsHolder generatorOptionsHolder, Optional defaultWorldType, OptionalLong seed, CreateWorldCallback callback, CallbackInfo ci) {
        this.dataPackTempDir = DatapackInstallerClient.MAIN_PATH;
    }

    /**
     * @author Jomar Milan - July 31st, 2024 - Minecraft 1.20.5
     * @reason Because dataPackTempDir is now persistent and not temporary, the directory should no longer be cleared.
     */
    @Overwrite
    private void clearDataPackTempDir() {
        LOGGER.info("Suppressing attempt to clear data pack directory");
    }
}
