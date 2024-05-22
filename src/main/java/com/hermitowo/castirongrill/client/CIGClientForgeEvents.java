package com.hermitowo.castirongrill.client;

import com.hermitowo.castirongrill.common.blocks.CIGBlocks;
import com.hermitowo.castirongrill.common.items.CIGItems;
import net.minecraft.client.Camera;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.client.event.RenderHighlightEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;

import net.dries007.tfc.client.RenderHelpers;
import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.util.Helpers;

public class CIGClientForgeEvents
{
    public static void init()
    {
        final IEventBus bus = MinecraftForge.EVENT_BUS;

        bus.addListener(CIGClientForgeEvents::onHighlightBlockEvent);
    }

    public static void onHighlightBlockEvent(RenderHighlightEvent.Block event)
    {
        final Camera camera = event.getCamera();
        final Entity entity = camera.getEntity();
        final Level level = entity.level();
        final BlockHitResult hit = event.getTarget();
        final BlockPos pos = hit.getBlockPos();

        if (entity instanceof Player player)
        {
            final BlockState stateAt = level.getBlockState(pos);
            final Block blockAt = stateAt.getBlock();

            if (blockAt == TFCBlocks.FIREPIT.get() && Helpers.isItem(player.getMainHandItem(), CIGItems.CAST_IRON_GRILL.get()))
            {
                final BlockState state = CIGBlocks.CAST_IRON_GRILL_FIREPIT.get().defaultBlockState().setValue(BlockStateProperties.LIT, stateAt.getValue(BlockStateProperties.LIT));
                if (level.isClientSide)
                {
                    if (RenderHelpers.renderGhostBlock(level, state, pos, event.getPoseStack(), event.getMultiBufferSource(), true, Mth.floor(0.33F * 255)))
                    {
                        event.setCanceled(true);
                    }
                }
            }
        }
    }
}
