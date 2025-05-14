package net.i_no_am.auto_disconnect.mixin;

import net.i_no_am.auto_disconnect.Global;
import net.i_no_am.auto_disconnect.config.Config;
import net.i_no_am.auto_disconnect.utils.Utils;
import net.minecraft.block.Blocks;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.i_no_am.auto_disconnect.utils.Utils.disconnect;

@Mixin(ClientPlayerEntity.class)
public class MixinClientPlayerEntity implements Global {

    @Inject(method = "tick", at = @At("HEAD"))
    private void onTick(CallbackInfo ci) {
        if (!Config.enable || Utils.isInvalid()) return;

        if (Config.checkPlayerHealth && mc.player.getHealth() <= Config.selfPlayerHealth)
            disconnect("Health too low (" + (int) mc.player.getHealth() + " health points)");

        var ent = Utils.findEntity(EntityType.END_CRYSTAL, Config.range);
        if (Config.checkCrystals)
            if (ent != null && ent.distanceTo(mc.player) <= Config.range) disconnect("End Crystal nearby");

        if (!(Config.checkAnchors || Config.checkGlowstone)) return;

        BlockPos anchorPos = Utils.findBlock(Blocks.RESPAWN_ANCHOR, Config.range);
        if (anchorPos == null) return;

        if (Config.checkGlowstone && Utils.isAnchorLoaded(Config.glowstone_num, anchorPos))
            disconnect("Respawn Anchor loaded nearby");

        else if (Config.checkAnchors) disconnect("Respawn Anchor nearby");
    }
}
