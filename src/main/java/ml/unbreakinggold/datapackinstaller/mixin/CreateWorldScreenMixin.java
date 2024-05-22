package ml.unbreakinggold.datapackinstaller.mixin;

import ml.unbreakinggold.datapackinstaller.client.DatapackInstallerClient;
import net.minecraft.client.gui.screen.world.CreateWorldScreen;
import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;
import java.nio.file.Path;

@Mixin(CreateWorldScreen.class)
public abstract class CreateWorldScreenMixin {
    @Shadow @Nullable protected abstract Path method_29693();

    @Inject(at = @At("HEAD"), method = "method_29694")
    private void init(CallbackInfo info) {
        Path tempPath = this.method_29693();
        try {
            FileUtils.copyDirectory(DatapackInstallerClient.globalDatapackPath.toFile(), tempPath.toFile());
        } catch(IOException exception) {
            exception.printStackTrace();
        }
    }
}
