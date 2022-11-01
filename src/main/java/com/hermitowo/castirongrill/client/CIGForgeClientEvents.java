package com.hermitowo.castirongrill.client;

import java.util.Arrays;
import com.hermitowo.castirongrill.common.blocks.CIGBlocks;
import com.hermitowo.castirongrill.common.items.CIGItems;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockModelShaper;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.event.DrawSelectionEvent;
import net.minecraftforge.client.model.data.EmptyModelData;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;

import net.dries007.tfc.client.ClientHelpers;
import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.util.Helpers;

public class CIGForgeClientEvents
{
    public static void init()
    {
        final IEventBus bus = MinecraftForge.EVENT_BUS;

        bus.addListener(CIGForgeClientEvents::onHighlightBlockEvent);
    }

    public static void onHighlightBlockEvent(DrawSelectionEvent.HighlightBlock event)
    {
        final Camera camera = event.getCamera();
        final PoseStack stack = event.getPoseStack();
        final Entity entity = camera.getEntity();
        final Level level = entity.level;
        final BlockHitResult hit = event.getTarget();
        final BlockPos pos = hit.getBlockPos();
        final BlockPos lookingAt = new BlockPos(pos);
        final MultiBufferSource buffer = event.getMultiBufferSource();
        if (entity instanceof Player player)
        {
            BlockState stateAt = level.getBlockState(lookingAt);
            Block blockAt = stateAt.getBlock();

            if (blockAt == TFCBlocks.FIREPIT.get() && Helpers.isItem(player.getMainHandItem(), CIGItems.CAST_IRON_GRILL.get()))
            {
                Minecraft mc = Minecraft.getInstance();
                BlockState state = CIGBlocks.CAST_IRON_GRILL_FIREPIT.get().defaultBlockState();
                BlockModelShaper shaper = mc.getBlockRenderer().getBlockModelShaper();
                BakedModel model = shaper.getBlockModel(state);

                ForgeHooksClient.setRenderType(RenderType.translucent());
                VertexConsumer consumer = buffer.getBuffer(RenderType.translucent());

                stack.pushPose();

                final Vec3 Vcamera = mc.gameRenderer.getMainCamera().getPosition();

                stack.translate(-Vcamera.x, -Vcamera.y, -Vcamera.z);
                stack.translate(pos.getX(), pos.getY(), pos.getZ());
                stack.translate(-0.005F, -0.005F, -0.005F);
                stack.scale(1.01F, 1.01F, 1.01F);

                final PoseStack.Pose pose = stack.last();
                float alpha = 0.33F;

                Arrays.stream(ClientHelpers.DIRECTIONS_AND_NULL)
                    .flatMap(dir -> model.getQuads(state, dir, level.random, EmptyModelData.INSTANCE).stream())
                    .forEach(quad -> consumer.putBulkData(pose, quad, 1.0F, 1.0F, 1.0F, alpha, LevelRenderer.getLightColor(level, state, pos), OverlayTexture.NO_OVERLAY));

                ((MultiBufferSource.BufferSource) buffer).endBatch(RenderType.translucent());
                ForgeHooksClient.setRenderType(null);

                stack.popPose();
                event.setCanceled(true);
            }
        }
    }
}

