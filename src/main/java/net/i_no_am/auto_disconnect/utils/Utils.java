package net.i_no_am.auto_disconnect.utils;

import net.fabricmc.loader.api.FabricLoader;
import net.i_no_am.auto_disconnect.Global;
import net.i_no_am.auto_disconnect.config.Config;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.RespawnAnchorBlock;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Colors;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;

public class Utils implements Global {

    public static boolean playerInvalid() {
        return mc.player == null;
    }

    public static ClientPlayerEntity player() {
        return mc.player;
    }

    public static void disconnectPlayer(String reason) {
        MutableText text = Text.literal("§3Auto-Disconnect was triggered§r");
        text = text.append(Text.literal("\n\n" + reason).withColor(Colors.RED));
        mc.getNetworkHandler().sendPacket(new UpdateSelectedSlotC2SPacket(228));
        Utils.player().networkHandler.getConnection().disconnect(text);
        if (FabricLoader.getInstance().isDevelopmentEnvironment())
            System.out.println(PREFIX + ": disconnected because" + reason);
        else if (Config.autoDisable.getVal())
            Config.autoDisable.setVal("enable", false);
    }

    public static boolean anchorWithCharges(int charges_num, BlockPos blockPos) {
        if (mc.crosshairTarget != null && mc.crosshairTarget.getType() == HitResult.Type.BLOCK) {
            BlockState blockState = player().getWorld().getBlockState(blockPos);
            if (blockState.getBlock() == Blocks.RESPAWN_ANCHOR) {
                int anchorCharges = blockState.get(RespawnAnchorBlock.CHARGES);
                return anchorCharges == charges_num;
            }
        }
        return false;
    }
}