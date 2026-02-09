package com.teseting.betterlootballs;

import com.cobblemon.mod.common.api.entity.EntitySideDelegate;
import com.cobblemon.mod.common.api.reactive.Observable;
import com.cobblemon.mod.common.api.reactive.SettableObservable;
import com.cobblemon.mod.common.api.reactive.SimpleObservable;
import com.cobblemon.mod.common.api.scheduling.SchedulingTracker;
import com.cobblemon.mod.common.client.render.pokeball.PokeBallPosableState;
import com.cobblemon.mod.common.entity.pokeball.EmptyPokeBallEntity;
import kotlin.Unit;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static net.minecraft.world.level.block.Block.UPDATE_ALL;

public class PokeballDelegate extends PokeBallPosableState implements EntitySideDelegate<EmptyPokeBallEntity> {
    public SettableObservable<EmptyPokeBallEntity.CaptureState> stateEmitter = new SettableObservable(EmptyPokeBallEntity.CaptureState.NOT);
    public SimpleObservable<Unit> shakeEmitter = new SimpleObservable<Unit>();
    public SchedulingTracker schedulingTracker = new SchedulingTracker();
    public LootBallBlockEntity blockEntity;

    public PokeballDelegate(LootBallBlockEntity blockEntity) {
        super();
        this.blockEntity = blockEntity;
    }

    @Override
    public @NotNull SettableObservable<EmptyPokeBallEntity.CaptureState> getStateEmitter() {
        return stateEmitter;
    }

    @Override
    public @NotNull Observable<Unit> getShakeEmitter() {
        return shakeEmitter;
    }

    @Override
    public @Nullable Entity getEntity() {
        return null;
    }

    @Override
    public void updatePartialTicks(float v) {
        this.setCurrentPartialTicks(v);
    }

    @Override
    public @NotNull SchedulingTracker getSchedulingTracker() {
        return schedulingTracker;
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> data) {
        shakeEmitter.emit(Unit.INSTANCE);
    }

    void tickNoEntity() {
        int previousAge = getAge();
        updateAge(getAge() + 1);
        getSchedulingTracker().update(1 / 20f);

        tickEffects(null, previousAge, getAge());
        var primaryAnimation = getPrimaryAnimation();
        if (primaryAnimation == null) {
            return;
        }
        if (primaryAnimation.getStarted() + primaryAnimation.getDuration() <= getAnimationSeconds()) {
            this.setPrimaryAnimation(null);
            primaryAnimation.getAfterAction().accept(Unit.INSTANCE);
        }
    }

    @Override
    public void initSubscriptions() {
        super.initSubscriptions();
        shakeEmitter.subscribe(state -> {
            after(0.75f, () -> {
                ;
                stateEmitter.emit(EmptyPokeBallEntity.CaptureState.BROKEN_FREE);
                return Unit.INSTANCE;
            });
            after(1.1f, () -> {
                if (blockEntity.opener != null && !blockEntity.opener.level().isClientSide) {
                    blockEntity.opener.addItem(new ItemStack(Items.DIAMOND));
                    blockEntity.opener.level().setBlock(blockEntity.getBlockPos(), Blocks.AIR.defaultBlockState(), UPDATE_ALL);
                }
                return Unit.INSTANCE;
            });
        });
    }
}
