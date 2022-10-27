package com.hermitowo.castirongrill.client.screen;

import com.hermitowo.castirongrill.common.blockentities.CastIronGrillBlockEntity;
import com.hermitowo.castirongrill.common.container.CastIronGrillContainer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import net.dries007.tfc.client.screen.BlockEntityScreen;
import net.dries007.tfc.common.capabilities.heat.Heat;

import static com.hermitowo.castirongrill.CastIronGrill.*;

public class CastIronGrillScreen extends BlockEntityScreen<CastIronGrillBlockEntity, CastIronGrillContainer>
{
    private static final ResourceLocation BACKGROUND = new ResourceLocation(MOD_ID, "textures/gui/cast_iron_grill_firepit.png");

    public CastIronGrillScreen(CastIronGrillContainer container, Inventory playerInventory, Component name)
    {
        super(container, playerInventory, name, BACKGROUND);
        inventoryLabelY += 20;
        imageHeight += 20;
    }

    @Override
    protected void renderBg(PoseStack poseStack, float partialTicks, int mouseX, int mouseY)
    {
        super.renderBg(poseStack, partialTicks, mouseX, mouseY);
        int temp = (int) (51 * blockEntity.getTemperature() / Heat.maxVisibleTemperature());
        if (temp > 0)
        {
            blit(poseStack, leftPos + 30, topPos + 76 - Math.min(51, temp), 176, 0, 15, 5);
        }
    }
}
