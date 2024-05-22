package com.hermitowo.castirongrill;

import com.hermitowo.castirongrill.client.CIGClientEvents;
import com.hermitowo.castirongrill.client.CIGClientForgeEvents;
import com.hermitowo.castirongrill.common.CIGCreativeTabs;
import com.hermitowo.castirongrill.common.CIGInteractionManager;
import com.hermitowo.castirongrill.common.blockentities.CIGBlockEntities;
import com.hermitowo.castirongrill.common.blocks.CIGBlocks;
import com.hermitowo.castirongrill.common.compat.FirmalifeCompatBouncer;
import com.hermitowo.castirongrill.common.container.CIGContainerTypes;
import com.hermitowo.castirongrill.common.items.CIGItems;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import org.slf4j.Logger;

@Mod(CastIronGrill.MOD_ID)
public class CastIronGrill
{
    public static final String MOD_ID = "castirongrill";
    public static final Logger LOGGER = LogUtils.getLogger();

    public CastIronGrill()
    {
        final IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        bus.addListener(this::setup);

        CIGBlocks.BLOCKS.register(bus);
        CIGItems.ITEMS.register(bus);
        CIGBlockEntities.BLOCK_ENTITIES.register(bus);
        CIGContainerTypes.CONTAINERS.register(bus);
        CIGCreativeTabs.CREATIVE_TABS.register(bus);

        CIGForgeEvents.init();

        if (ModList.get().isLoaded("firmalife"))
        {
            FirmalifeCompatBouncer.Blocks.init();
            FirmalifeCompatBouncer.BlockEntities.init();
            FirmalifeCompatBouncer.ContainerTypes.init();
        }

        if (FMLEnvironment.dist == Dist.CLIENT)
        {
            CIGClientEvents.init();
            CIGClientForgeEvents.init();
        }
    }

    public void setup(FMLCommonSetupEvent event)
    {
        event.enqueueWork(() -> {
            if (ModList.get().isLoaded("firmalife"))
            {
                CIGInteractionManager.init();
            }
        });
    }

    public static ResourceLocation rl(String name)
    {
        return new ResourceLocation(MOD_ID, name);
    }
}
