package com.teseting.betterlootballs.registry;

import com.teseting.betterlootballs.BetterLootBall;
import com.teseting.betterlootballs.LootBallBlock;
import com.teseting.betterlootballs.LootBallBlockEntity;
import com.teseting.betterlootballs.LootBallBlockRenderer;
import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.client.rendering.BlockEntityRendererRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class BetterLootBallBlocks
{
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(BetterLootBall.MOD_ID, Registries.CREATIVE_MODE_TAB);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(BetterLootBall.MOD_ID, Registries.ITEM);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(BetterLootBall.MOD_ID, Registries.BLOCK);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(BetterLootBall.MOD_ID, Registries.BLOCK_ENTITY_TYPE);

    public static final RegistrySupplier<CreativeModeTab> TEST_TAB = TABS.register("test_tab", () ->
            CreativeTabRegistry.create(Component.translatable("category.architectury_test"),
                    () -> new ItemStack(BetterLootBallBlocks.TEST_ITEM.get())));


    public static final RegistrySupplier<Item> TEST_ITEM = ITEMS.register("test_item", () ->
            new Item(new Item.Properties().arch$tab(TEST_TAB)));

    public static final RegistrySupplier<Block> TEST_BLOCK = BLOCKS.register("test_block", () ->
            new LootBallBlock(BlockBehaviour.Properties.ofLegacyCopy(Blocks.STONE)));

    public static final RegistrySupplier<Item> TEST_BLOCK_ITEM = ITEMS.register("test_block", () ->
            new BlockItem(TEST_BLOCK.get(), new Item.Properties().arch$tab(TEST_TAB)));

    public static final RegistrySupplier<BlockEntityType<LootBallBlockEntity>> TEST_BLOCK_ENTITY = BLOCK_ENTITY_TYPES.register("test_block_entity",
            () -> BlockEntityType.Builder.of(LootBallBlockEntity::new, TEST_BLOCK.get()).build(null));

    public static void initialize() {
        TABS.register();
        BLOCKS.register();
        ITEMS.register();
        BLOCK_ENTITY_TYPES.register();

    }

}
