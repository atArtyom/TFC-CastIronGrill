package com.hermitowo.castirongrill.common.blocks;

import java.util.function.ToIntFunction;
import com.hermitowo.castirongrill.common.blockentities.CIGBlockEntities;
import com.hermitowo.castirongrill.common.blocks.devices.CastIronGrillBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import net.dries007.tfc.common.blockentities.AbstractFirepitBlockEntity;
import net.dries007.tfc.common.blocks.ExtendedProperties;

import static com.hermitowo.castirongrill.CastIronGrill.*;

public class CIGBlocks
{
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MOD_ID);

    public static final RegistryObject<Block> CAST_IRON_GRILL_FIREPIT = BLOCKS.register("cast_iron_grill_firepit", () -> new CastIronGrillBlock(ExtendedProperties.of(Material.DIRT).strength(0.4F, 0.4F).sound(SoundType.NETHER_WART).randomTicks().noOcclusion().lightLevel(litBlockEmission()).blockEntity(CIGBlockEntities.CAST_IRON_GRILL_BLOCKENTITY).pathType(BlockPathTypes.DAMAGE_FIRE).<AbstractFirepitBlockEntity<?>>serverTicks(AbstractFirepitBlockEntity::serverTick)));

    private static ToIntFunction<BlockState> litBlockEmission()
    {
        return (state) -> state.getValue(BlockStateProperties.LIT) ? 15 : 0;
    }
}
