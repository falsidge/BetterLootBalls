package com.teseting.betterlootballs;

import com.cobblemon.mod.common.client.render.models.blockbench.pokeball.PosablePokeBallModel;
import com.cobblemon.mod.common.client.render.models.blockbench.repository.RenderContext;
import com.cobblemon.mod.common.client.render.models.blockbench.repository.VaryingModelRepository;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.RotationSegment;

public class LootBallBlockRenderer implements BlockEntityRenderer<LootBallBlockEntity> {
    private final PosablePokeBallModel model;

    public LootBallBlockRenderer(BlockEntityRendererProvider.Context context) {
        this.model = new PosablePokeBallModel();
    }

    @Override
    public void render(LootBallBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        BlockState blockstate = blockEntity.getBlockState();

        int i;
        try {
            i = (Integer) blockstate.getValue(LootBallBlock.ROTATION);
        } catch (IllegalArgumentException e) {
            i = 0;
        }
        float f1 = RotationSegment.convertToDegrees(i);
        PokeballDelegate emptyDelegate = blockEntity.emptyDelegate;

        this.model.getContext().put(RenderContext.Companion.getPOSABLE_STATE(), emptyDelegate);

        var model = VaryingModelRepository.INSTANCE.getPoser(ResourceLocation.fromNamespaceAndPath("cobblemon", "poke_ball"), emptyDelegate);
        this.model.posableModel = model;
        this.model.posableModel.context = this.model.getContext();
        this.model.getContext().put(RenderContext.Companion.getRENDER_STATE(), RenderContext.RenderState.WORLD);

        RenderType renderType = RenderType.entityCutout(blockEntity.pokeballType.withPrefix("textures/poke_balls/").withSuffix(".png"));
        poseStack.pushPose();
        poseStack.translate(0.5F, 0.0F, 0.5F);
        poseStack.mulPose(Axis.YN.rotationDegrees(f1));

        poseStack.scale(-1.0F, -1.0F, 1.0F);
        VertexConsumer vertexconsumer = ItemRenderer.getFoilBufferDirect(bufferSource, renderType, false, false);
        emptyDelegate.updatePartialTicks(partialTick);
        this.model.posableModel.applyAnimations(null, emptyDelegate, 0f, 0F, partialTick + blockEntity.tickCount, 0f, 0f);
        this.model.renderToBuffer(poseStack, vertexconsumer, packedLight, OverlayTexture.NO_OVERLAY);

        model.setGreen(1F);
        model.setBlue(1F);
        model.setRed(1F);

        model.resetLayerContext();

        poseStack.popPose();
    }
}
