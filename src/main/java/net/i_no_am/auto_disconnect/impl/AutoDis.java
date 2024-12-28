package net.i_no_am.auto_disconnect.impl;

import net.i_no_am.auto_disconnect.Global;
import net.i_no_am.auto_disconnect.config.Config;
import net.i_no_am.auto_disconnect.utils.Utils;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;

import java.util.List;

public class AutoDis implements Global {

    public static int RANGE = Config.range.getVal();
    public final static DisconnectInfo NULL = new DisconnectInfo(false, null);

    public static void init() {
        if (!isEnable()) return;
        BlockPos playerPos = Utils.player().getBlockPos();
        for (int x = -RANGE; x <= RANGE; x++) {
            for (int y = -RANGE; y <= RANGE; y++) {
                for (int z = -RANGE; z <= RANGE; z++) {
                    BlockPos targetPos = playerPos.add(x, y, z);
                    BlockState blockState = Utils.player().getWorld().getBlockState(targetPos);
                    List<DisconnectInfo> logics = List.of(checkAnchor(blockState, playerPos), checkCrystals(playerPos), checkPlayers(), checkPlayers(), checkPlayers(), checkHealth());
                    disconnect(logics);
                }
            }
        }
    }


    private static void disconnect(List<DisconnectInfo> logics) {
        for (DisconnectInfo logic : logics) {
            if (logic != null && logic.state)
                Utils.disconnectPlayer(logic.message);
        }
    }

    private static boolean isEnable() {
        return Config.enable.getVal() && !Utils.playerInvalid();
    }

    private static DisconnectInfo checkHealth() {
        if (Config.checkPlayerHealth.getVal() && Utils.player().isAlive()) {
            float healthThreshold = (Config.selfPlayerHealth.getVal() / 100.0f) * Utils.player().getMaxHealth();
            if (Utils.player().getHealth() < healthThreshold) {
                String message = "Player health is below " + Config.selfPlayerHealth.getVal() + "%";
                return new DisconnectInfo(true, message);
            }
        }
        return NULL;
    }

    private static DisconnectInfo checkCrystals(BlockPos pos) {
        if (Config.checkCrystals.getVal()) {
            for (Entity entity : mc.world.getEntities()) {
                if (entity instanceof EndCrystalEntity crystal) {
                    if (crystal.getBlockPos().equals(pos)) {
                        return new DisconnectInfo(true, "End Crystal is in range");
                    }
                }
            }
        }
        return NULL;
    }

    private static DisconnectInfo checkPlayers() {
        if (Config.checkNewPlayers.getVal() && !(Utils.player().networkHandler.getPlayerList().size() == 1)) {
            for (Entity ent : mc.world.getEntities()) {
                if (!(ent instanceof ClientPlayerEntity) && ent instanceof PlayerEntity) {
                    return new DisconnectInfo(true, "New player detected within render distance");
                }
            }
        }
        return NULL;
    }

    private static DisconnectInfo checkAnchor(BlockState state, BlockPos pos) {
        if (Config.checkAnchors.getVal() && state.getBlock() == Blocks.RESPAWN_ANCHOR) {
            if (Config.checkGlowstone.getVal()) {
                for (int i = 1; i <= 4; i++) {
                    if (Utils.anchorWithCharges(i, pos)) {
                        return new DisconnectInfo(true, "Respawn Anchor is in range and loaded");
                    }
                }
            }
            return new DisconnectInfo(true, "Respawn Anchor is in range");
        }
        return NULL;
    }

    record DisconnectInfo(boolean state, String message) {
    }
}
