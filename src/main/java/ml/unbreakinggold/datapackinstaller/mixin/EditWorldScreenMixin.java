package ml.unbreakinggold.datapackinstaller.mixin;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.world.EditWorldScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.DirectionalLayoutWidget;
import net.minecraft.text.Text;
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
    @Unique private static final Text SELECT_DATAPACKS_TEXT = Text.translatable("selectWorld.edit.datapacks");

    protected EditWorldScreenMixin(Text title) {
        super(title);
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/widget/EmptyWidget;<init>(II)V"), method = "<init>")
    private void addButton(CallbackInfo info) {
        this.layout.add(ButtonWidget.builder(SELECT_DATAPACKS_TEXT, (button) -> {}).width(200).build());
    }
}
