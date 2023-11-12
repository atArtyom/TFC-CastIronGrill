package com.hermitowo.castirongrill.common.blocks;

import java.util.function.ToIntFunction;
import com.hermitowo.castirongrill.CastIronGrill;
import com.hermitowo.castirongrill.common.blockentities.CIGBlockEntities;
import com.hermitowo.castirongrill.common.blocks.devices.CastIronGrillBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import net.dries007.tfc.common.blockentities.AbstractFirepitBlockEntity;
import net.dries007.tfc.common.blocks.ExtendedProperties;

@SuppressWarnings("SameParameterValue")
public class CIGBlocks
{
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, CastIronGrill.MOD_ID);

    public static final RegistryObject<Block> CAST_IRON_GRILL_FIREPIT = BLOCKS.register("cast_iron_grill_firepit", () -> new CastIronGrillBlock(ExtendedProperties.of(MapColor.DIRT).strength(0.4F, 0.4F).sound(SoundType.NETHER_WART).randomTicks().noOcclusion().lightLevel(litBlockEmission(15)).blockEntity(CIGBlockEntities.CAST_IRON_GRILL_BLOCKENTITY).pathType(BlockPathTypes.DAMAGE_FIRE).<AbstractFirepitBlockEntity<?>>ticks(AbstractFirepitBlockEntity::serverTick, AbstractFirepitBlockEntity::clientTick)));

    private static ToIntFunction<BlockState> litBlockEmission(int lightValue)
    {
        return (state) -> state.getValue(BlockStateProperties.LIT) ? lightValue : 0;
    }
}
