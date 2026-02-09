package com.teseting.betterlootballs;

import com.teseting.betterlootballs.registry.BetterLootBallBlocks;
import dev.architectury.event.events.client.ClientLifecycleEvent;
import dev.architectury.registry.client.rendering.BlockEntityRendererRegistry;
import dev.architectury.utils.Env;
import dev.architectury.utils.EnvExecutor;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public final class BetterLootBall {
    public static final String MOD_ID = "betterlootballs";

    public static void init() {
        // Write common init code here.
        BetterLootBallBlocks.initialize();

        EnvExecutor.runInEnv(Env.CLIENT, () -> Client::initializeClient);
    }

    @Environment(EnvType.CLIENT)
    public static class Client {
        @Environment(EnvType.CLIENT)
        public static void initializeClient() {
            ClientLifecycleEvent.CLIENT_SETUP.register((client) -> {
                Client.setupClient();
            });
        }

        public static void setupClient() {
            BlockEntityRendererRegistry.register(BetterLootBallBlocks.TEST_BLOCK_ENTITY.get(), LootBallBlockRenderer::new);
        }
    }
}
