package com.hermitowo.castirongrill;

import com.hermitowo.castirongrill.common.blocks.CIGBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;

import net.dries007.tfc.common.blockentities.AbstractFirepitBlockEntity;
import net.dries007.tfc.util.events.StartFireEvent;

public class ForgeEventHandler
{
    public static void init()
    {
        final IEventBus bus = MinecraftForge.EVENT_BUS;

        bus.addListener(ForgeEventHandler::onFireStart);
    }

    public static void onFireStart(StartFireEvent event)
    {
        Level level = event.getLevel();
        BlockPos pos = event.getPos();
        BlockState state = event.getState();
        Block block = state.getBlock();

        if (block == CIGBlocks.CAST_IRON_GRILL_FIREPIT.get())
        {
            final BlockEntity entity = level.getBlockEntity(pos);
            if (entity instanceof AbstractFirepitBlockEntity<?> firepit && firepit.light(state))
            {
                event.setCanceled(true);
            }
        }
    }
}