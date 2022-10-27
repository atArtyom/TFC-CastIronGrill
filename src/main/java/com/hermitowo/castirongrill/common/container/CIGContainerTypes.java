package com.hermitowo.castirongrill.common.container;

import java.util.function.Supplier;
import com.hermitowo.castirongrill.common.blockentities.CIGBlockEntities;
import com.hermitowo.castirongrill.common.blockentities.CastIronGrillBlockEntity;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import net.dries007.tfc.common.blockentities.InventoryBlockEntity;
import net.dries007.tfc.common.container.BlockEntityContainer;
import net.dries007.tfc.util.registry.RegistrationHelpers;

import static com.hermitowo.castirongrill.CastIronGrill.*;

public class CIGContainerTypes
{
    public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, MOD_ID);

    public static final RegistryObject<MenuType<CastIronGrillContainer>> CAST_IRON_GRILL = CIGContainerTypes.<CastIronGrillBlockEntity, CastIronGrillContainer>registerBlock("cast_iron_grill", CIGBlockEntities.CAST_IRON_GRILL_BLOCKENTITY, CastIronGrillContainer::create);

    private static <T extends InventoryBlockEntity<?>, C extends BlockEntityContainer<T>> RegistryObject<MenuType<C>> registerBlock(String name, Supplier<BlockEntityType<T>> type, BlockEntityContainer.Factory<T, C> factory)
    {
        return RegistrationHelpers.registerBlockEntityContainer(CONTAINERS, name, type, factory);
    }
}

