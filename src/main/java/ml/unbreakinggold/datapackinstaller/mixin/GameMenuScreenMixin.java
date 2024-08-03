package ml.unbreakinggold.datapackinstaller.mixin;

import ml.unbreakinggold.datapackinstaller.client.DatapackInstallerClient;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.pack.PackScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.GridWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Collection;
import java.util.function.Supplier;

@Mixin(GameMenuScreen.class)
public abstract class GameMenuScreenMixin extends Screen {
    @Shadow private ButtonWidget createButton(Text text, Supplier<Screen> screenSupplier) { return null; };
    @Unique private static final Text DATA_PACK_TEXT = Text.translatable("dataPack.title");

    private GameMenuScreenMixin(Text title) {
        super(title);
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/MinecraftClient;isInSingleplayer()Z"), method = "initWidgets", locals = LocalCapture.CAPTURE_FAILHARD)
    private void onInitWidgets(CallbackInfo ci, GridWidget gridWidget, GridWidget.Adder adder) {
        if (!this.client.isIntegratedServerRunning() || this.client.getServer().isRemote()) return;

        adder.add(this.createButton(DATA_PACK_TEXT, () -> {
            return new PackScreen(this.client.getServer().getDataPackManager(), (dataPackManager) -> {
                Collection<String> enabledProfiles = dataPackManager.getEnabledIds();

                this.client.getServer().reloadResources(enabledProfiles);

                this.client.setScreen(this);
            }, DatapackInstallerClient.MAIN_PATH, DATA_PACK_TEXT);
        }));
    }
}
