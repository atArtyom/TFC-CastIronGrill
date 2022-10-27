package com.hermitowo.castirongrill.client;

import com.hermitowo.castirongrill.client.render.blockentity.CastIronGrillBlockEntityRenderer;
import com.hermitowo.castirongrill.client.screen.CastIronGrillScreen;
import com.hermitowo.castirongrill.common.blockentities.CIGBlockEntities;
import com.hermitowo.castirongrill.common.container.CIGContainerTypes;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
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

    public static void clientSetup(FMLClientSetupEvent event)
    {
        MenuScreens.register(CIGContainerTypes.CAST_IRON_GRILL.get(), CastIronGrillScreen::new);
    }

    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event)
    {
        event.registerBlockEntityRenderer(CIGBlockEntities.CAST_IRON_GRILL_BLOCKENTITY.get(), ctx -> new CastIronGrillBlockEntityRenderer());
    }
}
