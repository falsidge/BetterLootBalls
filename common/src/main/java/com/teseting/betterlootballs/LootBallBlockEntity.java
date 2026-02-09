package com.teseting.betterlootballs;

import com.cobblemon.mod.common.entity.pokeball.EmptyPokeBallEntity;
import kotlin.Unit;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import static com.teseting.betterlootballs.registry.BetterLootBallBlocks.TEST_BLOCK_ENTITY;

//import static com.teseting.betterlootballs.registry.BetterLootBallBlocks.TEST_BLOCK_ENTITY;

public class LootBallBlockEntity extends BlockEntity {
    public PokeballDelegate emptyDelegate;
    public long tickCount = 0;
    Player opener = null;
    public ResourceLocation pokeballType;

    public LootBallBlockEntity(BlockPos pos, BlockState blockState) {
        super(TEST_BLOCK_ENTITY.get(), pos, blockState);
        emptyDelegate = new PokeballDelegate(this);
        emptyDelegate.initSubscriptions();
    }

    public static void tick(Level level, BlockPos pos, BlockState state, LootBallBlockEntity blockEntity) {
        blockEntity.emptyDelegate.tickNoEntity();
    }

    public static <T extends BlockEntity> void tick(Level level, BlockPos blockPos, BlockState blockState, T t) {
        if (t instanceof LootBallBlockEntity blockEntity) {
            tick(level, blockPos, blockState, blockEntity);
        }
    }

    public void attemptOpen(Player player) {
        if (opener != null) return;
        emptyDelegate.stateEmitter.emit(EmptyPokeBallEntity.CaptureState.SHAKE);
        emptyDelegate.shakeEmitter.emit(Unit.INSTANCE);
        opener = player;
    }
}
