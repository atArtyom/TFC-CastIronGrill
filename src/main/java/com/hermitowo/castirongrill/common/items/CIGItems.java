package com.hermitowo.castirongrill.common.items;

import com.hermitowo.castirongrill.common.blocks.CIGBlocks;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.hermitowo.castirongrill.CastIronGrill.*;
import static net.dries007.tfc.common.TFCItemGroup.*;

public class CIGItems
{
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);

    public static final RegistryObject<Item> CAST_IRON_GRILL = ITEMS.register("cast_iron_grill", () -> new Item(new Item.Properties().tab(MISC)));

    public static final RegistryObject<Item> CAST_IRON_GRILL_FIREPIT = fromBlock(CIGBlocks.CAST_IRON_GRILL_FIREPIT);

    public static <B extends Block> RegistryObject<Item> fromBlock(RegistryObject<B> block)
    {
        return ITEMS.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties().tab(MISC)));
    }

}
