package com.hermitowo.castirongrill.common.items;

import com.hermitowo.castirongrill.CastIronGrill;
import com.hermitowo.castirongrill.common.blocks.CIGBlocks;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@SuppressWarnings("unused")
public class CIGItems
{
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, CastIronGrill.MOD_ID);

    public static final RegistryObject<Item> CAST_IRON_GRILL = ITEMS.register("cast_iron_grill", () -> new CastIronGrillItem(new Item.Properties()));

    public static final RegistryObject<Item> CAST_IRON_GRILL_FIREPIT = fromBlock(CIGBlocks.CAST_IRON_GRILL_FIREPIT);

    public static <B extends Block> RegistryObject<Item> fromBlock(RegistryObject<B> block)
    {
        return ITEMS.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties()));
    }

}
