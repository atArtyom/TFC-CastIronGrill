package com.hermitowo.castirongrill.common.blocks.devices;

import com.hermitowo.castirongrill.common.blocks.CIGBlocks;
import com.hermitowo.castirongrill.common.items.CIGItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

import net.dries007.tfc.common.TFCTags;
import net.dries007.tfc.common.blockentities.AbstractFirepitBlockEntity;
import net.dries007.tfc.common.blockentities.TFCBlockEntities;
import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.blocks.devices.FirepitBlock;
import net.dries007.tfc.common.items.TFCItems;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.advancements.TFCAdvancements;

public class FirepitBlockCompat extends FirepitBlock
{
    public FirepitBlockCompat(ExtendedProperties properties)
    {
        super(properties);
    }

    @Nullable
    @Override
    public BlockState getStateToDraw(Level level, Player player, BlockState lookState, Direction direction, BlockPos pos, double x, double y, double z, ItemStack item)
    {
        if (Helpers.isItem(item, TFCItems.POT.get()))
        {
            return TFCBlocks.POT.get().defaultBlockState().setValue(LIT, lookState.getValue(LIT));
        }
        else if (Helpers.isItem(item, TFCItems.WROUGHT_IRON_GRILL.get()))
        {
            return TFCBlocks.GRILL.get().defaultBlockState().setValue(LIT, lookState.getValue(LIT));
        }
        else if (Helpers.isItem(item, CIGItems.CAST_IRON_GRILL.get()))
        {
            return CIGBlocks.CAST_IRON_GRILL_FIREPIT.get().defaultBlockState().setValue(LIT, lookState.getValue(LIT));
        }
        return null;
    }

    @Override
    @SuppressWarnings("deprecation")
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result)
    {
        final AbstractFirepitBlockEntity<?> firepit = level.getBlockEntity(pos, TFCBlockEntities.FIREPIT.get()).orElse(null);
        if (firepit != null)
        {
            final ItemStack stack = player.getItemInHand(hand);
            if (stack.getItem() == TFCItems.POT.get() || stack.getItem() == TFCItems.WROUGHT_IRON_GRILL.get())
            {
                if (!level.isClientSide)
                {
                    final Block newBlock = stack.getItem() == TFCItems.POT.get() ? TFCBlocks.POT.get() : TFCBlocks.GRILL.get();
                    AbstractFirepitBlockEntity.convertTo(level, pos, state, firepit, newBlock);
                    if (player instanceof ServerPlayer serverPlayer)
                    {
                        TFCAdvancements.FIREPIT_CREATED.trigger(serverPlayer, newBlock.defaultBlockState());
                    }
                    if (!player.isCreative()) stack.shrink(1);
                }
                return InteractionResult.SUCCESS;
            }
            else if (stack.getItem() == CIGItems.CAST_IRON_GRILL.get())
            {
                if (!level.isClientSide)
                {
                    final Block newBlock = CIGBlocks.CAST_IRON_GRILL_FIREPIT.get();
                    AbstractFirepitBlockEntity.convertTo(level, pos, state, firepit, newBlock);
                    if (player instanceof ServerPlayer serverPlayer)
                    {
                        TFCAdvancements.FIREPIT_CREATED.trigger(serverPlayer, newBlock.defaultBlockState());
                    }
                    if (!player.isCreative()) stack.shrink(1);
                }
                return InteractionResult.SUCCESS;
            }
            else if (Helpers.isItem(stack.getItem(), TFCTags.Items.EXTINGUISHER) && state.getValue(LIT))
            {
                firepit.extinguish(state);
                return InteractionResult.SUCCESS;
            }
            else
            {
                if (player instanceof ServerPlayer serverPlayer)
                {
                    Helpers.openScreen(serverPlayer, firepit, pos);
                }
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }
}