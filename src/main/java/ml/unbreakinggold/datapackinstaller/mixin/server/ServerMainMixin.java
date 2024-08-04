package ml.unbreakinggold.datapackinstaller.mixin.server;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.server.Main;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Main.class)
@Environment(EnvType.SERVER)
public class ServerMainMixin {
}
