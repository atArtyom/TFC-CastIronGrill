package com.hermitowo.castirongrill.common.compat;

import com.hermitowo.castirongrill.common.blockentities.StovetopCastIronGrillBlockEntity;
import com.hermitowo.castirongrill.common.container.StovetopCastIronGrillContainer;
import javax.annotation.Nullable;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.RegistryObject;

public class FirmalifeCompat
{
    @Nullable
    public static RegistryObject<Block> getStovetopCastIronGrillBlock()
    {
        return isModLoaded() ? FirmalifeCompatBouncer.Blocks.STOVETOP_CAST_IRON_GRILL : null;
    }

    @Nullable
    public static RegistryObject<BlockEntityType<StovetopCastIronGrillBlockEntity>> getStovetopCastIronGrillBlockEntity()
    {
        return isModLoaded() ? FirmalifeCompatBouncer.BlockEntities.STOVETOP_CAST_IRON_GRILL : null;
    }

    @Nullable
    public static RegistryObject<MenuType<StovetopCastIronGrillContainer>> getStovetopCastIronGrillContainer()
    {
        return isModLoaded() ? FirmalifeCompatBouncer.ContainerTypes.STOVETOP_CAST_IRON_GRILL : null;
    }

    private static boolean isModLoaded()
    {
        return ModList.get().isLoaded("firmalife");
    }
}
