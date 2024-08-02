package ml.unbreakinggold.datapackinstaller.mixin;

import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import ml.unbreakinggold.datapackinstaller.client.DatapackInstallerClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.pack.PackScreen;
import net.minecraft.client.gui.screen.world.EditWorldScreen;
import net.minecraft.client.gui.screen.world.WorldCreator;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.DirectionalLayoutWidget;
import net.minecraft.resource.DataConfiguration;
import net.minecraft.resource.ResourcePackManager;
import net.minecraft.resource.VanillaDataPackProvider;
import net.minecraft.text.Text;
import net.minecraft.world.level.storage.LevelStorage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;

@Mixin(EditWorldScreen.class)
public class EditWorldScreenMixin extends Screen {
    @Shadow @Final private DirectionalLayoutWidget layout;
    @Shadow @Final private LevelStorage.Session storageSession;
    @Unique private final Logger LOGGER = LogManager.getLogger(EditWorldScreenMixin.class);
    @Unique private static final Text SELECT_DATAPACKS_TEXT = Text.translatable("dataPack.title");
    @Unique @Nullable private ResourcePackManager packManager;
    @Unique @Nullable private DataConfiguration dataConfiguration;

    protected EditWorldScreenMixin(Text title) {
        super(title);
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/widget/EmptyWidget;<init>(II)V", ordinal = 1), method = "<init>")
    private void addButton(CallbackInfo info) {
        this.layout.add(ButtonWidget.builder(SELECT_DATAPACKS_TEXT, (button) -> {
            try {
                this.dataConfiguration = this.storageSession.getLevelSummary(this.storageSession.readLevelProperties()).getLevelInfo().getDataConfiguration();
            } catch (IOException primaryException) {
                LOGGER.warn("Failed to load world data from main level.dat. Attempting to load from fallback.", primaryException);

                try {
                    this.dataConfiguration = this.storageSession.getLevelSummary(this.storageSession.readOldLevelProperties()).getLevelInfo().getDataConfiguration();
                } catch (IOException fallbackException) {
                    LOGGER.error("Failed to load world data from fallback level.dat.", fallbackException);
                }
            }

            if (this.packManager == null) {
                this.packManager = VanillaDataPackProvider.createManager(DatapackInstallerClient.MAIN_PATH, client.getSymlinkFinder());
                this.packManager.scanPacks();
            }

            this.packManager.setEnabledProfiles(this.dataConfiguration.dataPacks().getEnabled());

            this.client.setScreen(new PackScreen(this.packManager, (resourcePackManager) -> {}, DatapackInstallerClient.MAIN_PATH, Text.translatable("dataPack.title")));
        }).width(200).build());

        // Reduce spacing so vanilla buttons remain on screen.
        this.layout.spacing(1);
    }
}
