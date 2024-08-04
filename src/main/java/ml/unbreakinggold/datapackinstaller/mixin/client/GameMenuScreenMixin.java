package ml.unbreakinggold.datapackinstaller.mixin.client;

import ml.unbreakinggold.datapackinstaller.client.DatapackInstallerClient;
import ml.unbreakinggold.datapackinstaller.mixin.MinecraftServerAccessor;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.pack.PackScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.GridWidget;
import net.minecraft.text.Text;
import net.minecraft.util.WorldSavePath;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;
import java.util.function.Supplier;

@Mixin(GameMenuScreen.class)
public abstract class GameMenuScreenMixin extends Screen {
    @Unique private final Logger LOGGER = LogManager.getLogger(GameMenuScreenMixin.class);
    @Unique private static final Text DATA_PACK_TEXT = Text.translatable("dataPack.title");

    @Shadow private ButtonWidget createButton(Text text, Supplier<Screen> screenSupplier) { return null; };

    private GameMenuScreenMixin(Text title) {
        super(title);
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/MinecraftClient;isInSingleplayer()Z"), method = "initWidgets", locals = LocalCapture.CAPTURE_FAILHARD)
    private void onInitWidgets(CallbackInfo ci, GridWidget gridWidget, GridWidget.Adder adder) {
        if (!this.client.isIntegratedServerRunning() || this.client.getServer().isRemote()) return;

        adder.add(this.createButton(DATA_PACK_TEXT, () -> {
            Path worldDataPackPath = ((MinecraftServerAccessor)this.client.getServer()).getSession().getDirectory(WorldSavePath.DATAPACKS);
            File modDatapackDir = DatapackInstallerClient.MAIN_PATH.toFile();
            File worldDataPackDir = worldDataPackPath.toFile();
            try {
                FileUtils.copyDirectory(modDatapackDir, worldDataPackDir);
            } catch(IOException exception) {
                LOGGER.error("Unable to install new data packs to world. Data packs not yet seen by this world may be missing.", exception);
            }

            return new PackScreen(this.client.getServer().getDataPackManager(), (dataPackManager) -> {
                Collection<String> enabledProfiles = dataPackManager.getEnabledIds();

                this.client.getServer().reloadResources(enabledProfiles);

                this.client.setScreen(this);
            }, DatapackInstallerClient.MAIN_PATH, DATA_PACK_TEXT);
        }));
    }
}
