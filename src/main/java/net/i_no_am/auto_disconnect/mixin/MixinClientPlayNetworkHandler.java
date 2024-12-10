package net.i_no_am.auto_disconnect.mixin;

import net.fabricmc.loader.api.FabricLoader;
import net.i_no_am.auto_disconnect.AutoDisconnect;
import net.i_no_am.auto_disconnect.Global;
import net.i_no_am.auto_disconnect.utils.PlayerUtils;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.s2c.play.GameJoinS2CPacket;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class MixinClientPlayNetworkHandler implements Global {

   @Unique private boolean sent = false;

    @Inject(method = "onGameJoin", at = @At("RETURN"))
    private void onInit(GameJoinS2CPacket packet, CallbackInfo ci) {
        if (!sent && AutoDisconnect.isOutdated && !FabricLoader.getInstance().isDevelopmentEnvironment()) {
            PlayerUtils.player().sendMessage(
                    Text.of("")
                            .copy().append(Text.literal("You are using an outdated version of ").formatted(Formatting.RED))
                            .append(Text.literal(AutoDisconnect.PREFIX).formatted(Formatting.RED, Formatting.BOLD))
                            .append(Text.literal(". Please download the latest version from "))
                            .append(Text.literal("Modrinth").styled(style ->
                                    style.withColor(Formatting.GREEN)
                                            .withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://modrinth.com/mod/auto-disconnect/versions"))
                                            .withUnderline(true)
                            ))
                            .append(Text.literal("."))
                    , false
            );
            sent = true;
        }
    }
}
