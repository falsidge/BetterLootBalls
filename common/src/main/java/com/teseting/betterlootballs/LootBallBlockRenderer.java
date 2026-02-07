package com.teseting.betterlootballs;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.SkullModel;
import net.minecraft.client.model.SkullModelBase;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.AbstractSkullBlock;
import net.minecraft.world.level.block.SkullBlock;
import net.minecraft.world.level.block.WallSkullBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.RotationSegment;

public class LootBallBlockRenderer implements BlockEntityRenderer<LootBallBlockEntity>
{
    private SkullModelBase base;

    public LootBallBlockRenderer(BlockEntityRendererProvider.Context context) {
        this.base = new SkullModel(context.getModelSet().bakeLayer(ModelLayers.CREEPER_HEAD));
    }

    @Override
    public void render(LootBallBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        BlockState blockstate = blockEntity.getBlockState();
//        boolean flag = blockstate.getBlock() instanceof WallSkullBlock;
//        Direction direction = flag ? blockstate.getValue(WallSkullBlock.FACING) : null;
//        int i = flag ? RotationSegment.convertToSegment(direction.getOpposite()) : (Integer)blockstate.getValue(SkullBlock.ROTATION);
        int i;
        try {
            i = (Integer) blockstate.getValue(LootBallBlock.ROTATION);
        }

        catch (IllegalArgumentException e)
        {
            i = 0;
        }
        float f1 = RotationSegment.convertToDegrees(i);

        RenderType rendertype = RenderType.entityCutoutNoCullZOffset(ResourceLocation.withDefaultNamespace("textures/entity/creeper/creeper.png"));
        renderSkull(f1, 0, poseStack, bufferSource, packedLight, this.base, rendertype);
    }

    public static void renderSkull(
            float yRot,
            float mouthAnimation,
            PoseStack poseStack,
            MultiBufferSource bufferSource,
            int packedLight,
            SkullModelBase model,
            RenderType renderType
    ) {
        poseStack.pushPose();
        poseStack.translate(0.5F, 0.0F, 0.5F);
        poseStack.scale(-1.0F, -1.0F, 1.0F);
        VertexConsumer vertexconsumer = bufferSource.getBuffer(renderType);
        model.setupAnim(mouthAnimation, yRot, 0.0F);
        model.renderToBuffer(poseStack, vertexconsumer, packedLight, OverlayTexture.NO_OVERLAY);
        poseStack.popPose();
    }
}
