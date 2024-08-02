package ml.unbreakinggold.datapackinstaller.mixin;

import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import ml.unbreakinggold.datapackinstaller.client.DatapackInstallerClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.pack.PackScreen;
import net.minecraft.client.gui.screen.world.EditWorldScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.DirectionalLayoutWidget;
import net.minecraft.resource.ResourcePackManager;
import net.minecraft.resource.VanillaDataPackProvider;
import net.minecraft.text.Text;
import net.minecraft.world.level.storage.LevelStorage;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EditWorldScreen.class)
public class EditWorldScreenMixin extends Screen {
    @Shadow @Final private DirectionalLayoutWidget layout;
    @Unique private static final Text SELECT_DATAPACKS_TEXT = Text.translatable("dataPack.title");
    @Unique @Nullable private ResourcePackManager packManager;

    protected EditWorldScreenMixin(Text title) {
        super(title);
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/widget/EmptyWidget;<init>(II)V", ordinal = 1), method = "<init>")
    private void addButton(CallbackInfo info) {
        this.layout.add(ButtonWidget.builder(SELECT_DATAPACKS_TEXT, (button) -> {
            if (this.packManager == null) {
                this.packManager = VanillaDataPackProvider.createManager(DatapackInstallerClient.MAIN_PATH, client.getSymlinkFinder());
                this.packManager.scanPacks();
            }

            this.client.setScreen(new PackScreen((ResourcePackManager) this.packManager, (resourcePackManager) -> {}, DatapackInstallerClient.MAIN_PATH, Text.translatable("dataPack.title")));
        }).width(200).build());

        // Reduce spacing so vanilla buttons remain on screen.
        this.layout.spacing(1);
    }
}
