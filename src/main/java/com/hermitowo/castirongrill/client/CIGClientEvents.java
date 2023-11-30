package com.hermitowo.castirongrill.client;

import com.hermitowo.castirongrill.client.render.blockentity.CastIronGrillBlockEntityRenderer;
import com.hermitowo.castirongrill.client.render.blockentity.StovetopCastIronGrillBlockEntityRenderer;
import com.hermitowo.castirongrill.client.screen.CastIronGrillScreen;
import com.hermitowo.castirongrill.client.screen.StovetopCastIronGrillScreen;
import com.hermitowo.castirongrill.common.blockentities.CIGBlockEntities;
import com.hermitowo.castirongrill.common.blocks.CIGBlocks;
import com.hermitowo.castirongrill.common.compat.FirmalifeCompat;
import com.hermitowo.castirongrill.common.container.CIGContainerTypes;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class CIGClientEvents
{
    public static void init()
    {
        final IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        bus.addListener(CIGClientEvents::clientSetup);
        bus.addListener(CIGClientEvents::registerEntityRenderers);
    }

    @SuppressWarnings("deprecation")
    public static void clientSetup(FMLClientSetupEvent event)
    {
        event.enqueueWork(() -> {
            MenuScreens.register(CIGContainerTypes.CAST_IRON_GRILL.get(), CastIronGrillScreen::new);

            // Should specify in block model json instead but be consistent with TFC for now
            ItemBlockRenderTypes.setRenderLayer(CIGBlocks.CAST_IRON_GRILL_FIREPIT.get(), RenderType.cutout());

            if (ModList.get().isLoaded("firmalife"))
            {
                MenuScreens.register(FirmalifeCompat.getStovetopCastIronGrillContainer().get(), StovetopCastIronGrillScreen::new);
                ItemBlockRenderTypes.setRenderLayer(FirmalifeCompat.getStovetopCastIronGrillBlock().get(), RenderType.cutout());
            }
        });
    }

    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event)
    {
        event.registerBlockEntityRenderer(CIGBlockEntities.CAST_IRON_GRILL.get(), ctx -> new CastIronGrillBlockEntityRenderer());

        if (ModList.get().isLoaded("firmalife"))
        {
            event.registerBlockEntityRenderer(FirmalifeCompat.getStovetopCastIronGrillBlockEntity().get(), ctx -> new StovetopCastIronGrillBlockEntityRenderer());
        }
    }
}
