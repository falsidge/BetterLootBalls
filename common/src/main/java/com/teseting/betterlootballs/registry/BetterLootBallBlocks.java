package com.teseting.betterlootballs.registry;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.CobblemonItems;
import com.cobblemon.mod.common.api.tags.CobblemonItemTags;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teseting.betterlootballs.BetterLootBall;
import com.teseting.betterlootballs.LootBallBlock;
import com.teseting.betterlootballs.LootBallBlockEntity;
import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Supplier;

public class BetterLootBallBlocks {

    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(BetterLootBall.MOD_ID, Registries.CREATIVE_MODE_TAB);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(BetterLootBall.MOD_ID, Registries.ITEM);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(BetterLootBall.MOD_ID, Registries.BLOCK);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(BetterLootBall.MOD_ID, Registries.BLOCK_ENTITY_TYPE);
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPES = DeferredRegister.create(BetterLootBall.MOD_ID, Registries.DATA_COMPONENT_TYPE);

    public static final RegistrySupplier<DataComponentType<ResourceLocation>> POKE_BALL_COMPONENT = DATA_COMPONENT_TYPES.register(
            "pokeball",
            () -> DataComponentType.<ResourceLocation>builder().persistent(ResourceLocation.CODEC).networkSynchronized(ResourceLocation.STREAM_CODEC).build()
    );
//    public static final Codec<SourcePokeball> BASIC_CODEC = RecordCodecBuilder.create(instance ->
//            instance.group(
//                    ResourceLocation.CO.fieldOf("value1").forGetter(ExampleRecord::value1),
//            ).apply(instance, ExampleSourcePokeballRecord::new)
//    );
//    public static final StreamCodec<ByteBuf, ExampleRecord> BASIC_STREAM_CODEC = StreamCodec.composite(
//            ByteBufCodecs.INT, ExampleRecord::value1,
//            ByteBufCodecs.BOOL, ExampleRecord::value2,
//            ExampleRecord::new
//    );


    public static final RegistrySupplier<CreativeModeTab> TEST_TAB = TABS.register("test_tab", () ->
            CreativeTabRegistry.create(Component.translatable("category.architectury_test"),
                    () -> new ItemStack(BetterLootBallBlocks.TEST_BLOCK_ITEM.get())));


    public static final RegistrySupplier<Item> TEST_ITEM = ITEMS.register("test_item", () ->
            new Item(new Item.Properties().arch$tab(TEST_TAB)));

    public static final RegistrySupplier<Block> TEST_BLOCK = BLOCKS.register("test_block", () ->
            new LootBallBlock(BlockBehaviour.Properties.ofLegacyCopy(Blocks.STONE)));

    public static final RegistrySupplier<Item> TEST_BLOCK_ITEM = ITEMS.register("test_block", () ->
            new BlockItem(TEST_BLOCK.get(), new Item.Properties().component(POKE_BALL_COMPONENT.get(), ResourceLocation.parse("cobblemon:poke_ball"))));

    public static final RegistrySupplier<BlockEntityType<LootBallBlockEntity>> TEST_BLOCK_ENTITY = BLOCK_ENTITY_TYPES.register("test_block_entity",
            () -> BlockEntityType.Builder.of(LootBallBlockEntity::new, TEST_BLOCK.get()).build(null));

    public static void initialize() {
        TABS.register();
        BLOCKS.register();
        ITEMS.register();
        BLOCK_ENTITY_TYPES.register();
        DATA_COMPONENT_TYPES.register();
        CreativeTabRegistry.modify(TEST_TAB, (flags, output, canUseGameMasterBlocks) -> {
            BuiltInRegistries.ITEM.getTag(CobblemonItemTags.POKE_BALLS).ifPresent((items) -> items.stream().forEach(
                    (item) -> {
                        item.unwrapKey().ifPresent((key) ->
                        {
                            ItemStack stack = new ItemStack(TEST_BLOCK_ITEM);
                            stack.set(POKE_BALL_COMPONENT.get(), key.location());
                            output.acceptAfter(TEST_ITEM.get(), stack);
                        });
                    }
            ));
        });

    }

}
