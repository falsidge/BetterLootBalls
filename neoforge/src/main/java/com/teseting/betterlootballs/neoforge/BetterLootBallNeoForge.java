package com.teseting.betterlootballs.neoforge;

import net.neoforged.fml.common.Mod;

import com.teseting.betterlootballs.BetterLootBall;

@Mod(BetterLootBall.MOD_ID)
public final class BetterLootBallNeoForge {
    public BetterLootBallNeoForge() {
        // Run our common setup.
        BetterLootBall.init();
    }
}
