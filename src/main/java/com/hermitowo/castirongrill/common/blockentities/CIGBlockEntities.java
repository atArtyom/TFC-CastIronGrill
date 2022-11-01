package com.hermitowo.castirongrill.common.blockentities;

import java.util.function.Supplier;
import com.hermitowo.castirongrill.common.blocks.CIGBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import net.dries007.tfc.util.registry.RegistrationHelpers;

import static net.minecraftforge.versions.forge.ForgeVersion.*;

public class CIGBlockEntities
{
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, MOD_ID);

    public static final RegistryObject<BlockEntityType<CastIronGrillBlockEntity>> CAST_IRON_GRILL_BLOCKENTITY = register("cast_iron_grill", CastIronGrillBlockEntity::new, CIGBlocks.CAST_IRON_GRILL_FIREPIT);

    private static <T extends BlockEntity> RegistryObject<BlockEntityType<T>> register(String name, BlockEntityType.BlockEntitySupplier<T> factory, Supplier<? extends Block> block)
    {
        return RegistrationHelpers.register(BLOCK_ENTITIES, name, factory, block);
    }
}
