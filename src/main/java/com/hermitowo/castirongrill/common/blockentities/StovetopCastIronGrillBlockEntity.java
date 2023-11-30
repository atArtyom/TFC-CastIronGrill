package com.hermitowo.castirongrill.common.blockentities;

import com.eerussianguy.firmalife.common.blockentities.ApplianceBlockEntity;
import com.hermitowo.castirongrill.CastIronGrill;
import com.hermitowo.castirongrill.common.compat.FirmalifeCompatBouncer;
import com.hermitowo.castirongrill.common.container.StovetopCastIronGrillContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import net.dries007.tfc.common.blockentities.InventoryBlockEntity;
import net.dries007.tfc.common.capabilities.PartialItemHandler;
import net.dries007.tfc.common.capabilities.food.FoodCapability;
import net.dries007.tfc.common.capabilities.food.FoodTraits;
import net.dries007.tfc.common.capabilities.heat.HeatCapability;
import net.dries007.tfc.common.recipes.HeatingRecipe;
import net.dries007.tfc.common.recipes.inventory.ItemStackInventory;
import net.dries007.tfc.util.Helpers;

public class StovetopCastIronGrillBlockEntity extends ApplianceBlockEntity<StovetopCastIronGrillBlockEntity.GrillInventory>
{
    @SuppressWarnings("unused")
    public static void serverTick(Level level, BlockPos pos, BlockState state, StovetopCastIronGrillBlockEntity grill)
    {
        grill.checkForLastTickSync();
        grill.checkForCalendarUpdate();

        if (grill.needsRecipeUpdate)
        {
            grill.updateCachedRecipe();
        }

        grill.tickTemperature();
        grill.handleCooking();
    }

    public static final int SLOTS = 2;

    private static final Component NAME = Component.translatable(CastIronGrill.MOD_ID + ".block_entity.stovetop_cast_iron_grill");

    private final HeatingRecipe[] cachedRecipes;
    private boolean needsRecipeUpdate = true;

    public StovetopCastIronGrillBlockEntity(BlockPos pos, BlockState state)
    {
        super(FirmalifeCompatBouncer.BlockEntities.STOVETOP_CAST_IRON_GRILL.get(), pos, state, GrillInventory::new, NAME);

        sidedInventory
            .on(new PartialItemHandler(inventory).insert(0, 1), Direction.UP)
            .on(new PartialItemHandler(inventory).extract(0, 1), Direction.Plane.HORIZONTAL);
        cachedRecipes = new HeatingRecipe[SLOTS];
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int containerId, Inventory inventory, Player player)
    {
        return StovetopCastIronGrillContainer.create(this, inventory, containerId);
    }

    @Override
    public int getSlotStackLimit(int slot)
    {
        return 1;
    }

    @Override
    public boolean isItemValid(int slot, ItemStack stack)
    {
        return Helpers.mightHaveCapability(stack, HeatCapability.CAPABILITY);
    }

    @Override
    public void loadAdditional(CompoundTag nbt)
    {
        super.loadAdditional(nbt);
        needsRecipeUpdate = true;
    }

    @Override
    public void setAndUpdateSlots(int slot)
    {
        super.setAndUpdateSlots(slot);
        needsRecipeUpdate = true;
    }

    protected void handleCooking()
    {
        assert level != null;
        for (int slot = 0; slot < SLOTS; slot++)
        {
            final ItemStack inputStack = inventory.getStackInSlot(slot);
            final int finalSlot = slot;
            inputStack.getCapability(HeatCapability.CAPABILITY, null).ifPresent(cap -> {
                HeatCapability.addTemp(cap, temperature);
                final HeatingRecipe recipe = cachedRecipes[finalSlot];
                if (recipe != null && recipe.isValidTemperature(cap.getTemperature()))
                {
                    ItemStack output = recipe.assemble(new ItemStackInventory(inputStack), level.registryAccess());
                    FoodCapability.applyTrait(output, FoodTraits.WOOD_GRILLED);
                    inventory.setStackInSlot(finalSlot, output);
                    markForSync();
                }
            });
        }
    }

    protected void updateCachedRecipe()
    {
        assert level != null;
        for (int slot = 0; slot < SLOTS; slot++)
        {
            final ItemStack stack = inventory.getStackInSlot(slot);
            cachedRecipes[slot] = stack.isEmpty() ? null : HeatingRecipe.getRecipe(new ItemStackInventory(stack));
        }
    }

    public static class GrillInventory extends ApplianceBlockEntity.ApplianceInventory
    {
        public GrillInventory(InventoryBlockEntity<?> entity)
        {
            super(entity, SLOTS);
        }
    }
}
