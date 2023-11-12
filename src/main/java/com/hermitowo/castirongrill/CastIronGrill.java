package com.hermitowo.castirongrill;

import com.hermitowo.castirongrill.client.CIGClientEvents;
import com.hermitowo.castirongrill.common.blockentities.CIGBlockEntities;
import com.hermitowo.castirongrill.common.blocks.CIGBlocks;
import com.hermitowo.castirongrill.common.container.CIGContainerTypes;
import com.hermitowo.castirongrill.common.items.CIGItems;
import com.mojang.logging.LogUtils;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import org.slf4j.Logger;

import net.dries007.tfc.common.TFCCreativeTabs;

@Mod(CastIronGrill.MOD_ID)
public class CastIronGrill
{
    public static final String MOD_ID = "castirongrill";
    public static final Logger LOGGER = LogUtils.getLogger();

    public CastIronGrill()
    {
        final IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        bus.addListener(EventPriority.LOWEST, CastIronGrill::fillTab);

        CIGBlocks.BLOCKS.register(bus);
        CIGItems.ITEMS.register(bus);
        CIGBlockEntities.BLOCK_ENTITIES.register(bus);
        CIGContainerTypes.CONTAINERS.register(bus);

        CIGForgeEvents.init();

        if (FMLEnvironment.dist == Dist.CLIENT)
        {
            CIGClientEvents.init();
        }
    }

    private static void fillTab(BuildCreativeModeTabContentsEvent event)
    {
        CreativeModeTab tab = event.getTab();
        if (tab == TFCCreativeTabs.METAL.tab().get() || tab == TFCCreativeTabs.MISC.tab().get())
            event.accept(CIGItems.CAST_IRON_GRILL);
        if (tab == TFCCreativeTabs.DECORATIONS.tab().get())
            event.accept(CIGItems.CAST_IRON_GRILL_FIREPIT);
    }
}
