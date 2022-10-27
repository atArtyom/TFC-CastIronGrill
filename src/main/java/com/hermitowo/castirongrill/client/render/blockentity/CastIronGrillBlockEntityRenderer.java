package com.hermitowo.castirongrill.client.render.blockentity;

import com.hermitowo.castirongrill.common.blockentities.CastIronGrillBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.world.item.ItemStack;

import net.dries007.tfc.common.capabilities.Capabilities;

import static com.hermitowo.castirongrill.common.blockentities.CastIronGrillBlockEntity.*;

public class CastIronGrillBlockEntityRenderer implements BlockEntityRenderer<CastIronGrillBlockEntity>
{
    @Override
    public void render(CastIronGrillBlockEntity grill, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int combinedLight, int combinedOverlay)
    {
        grill.getCapability(Capabilities.ITEM).ifPresent(cap ->
        {
            for (int i = SLOT_EXTRA_INPUT_START; i <= SLOT_EXTRA_INPUT_END; i++)
            {
                ItemStack item = cap.getStackInSlot(i);
                if (!item.isEmpty())
                {
                    float translateAmount = -1.4F;
                    float yOffset = 0.625f;
                    poseStack.pushPose();
                    poseStack.translate(0.3, 0.003125D + yOffset, 0.49);
                    poseStack.scale(0.3f, 0.3f, 0.3f);
                    poseStack.mulPose(Vector3f.XP.rotationDegrees(90F));
                    poseStack.mulPose(Vector3f.ZP.rotationDegrees(180F));

                    int ordinal = i - SLOT_EXTRA_INPUT_START;
                    if (ordinal == 1)
                    {
                        poseStack.translate(translateAmount, 0, 0);
                    }

                    Minecraft.getInstance().getItemRenderer().renderStatic(item, ItemTransforms.TransformType.FIXED, combinedLight, combinedOverlay, poseStack, buffer, 0);
                    poseStack.popPose();
                }
            }
        });
    }
}
