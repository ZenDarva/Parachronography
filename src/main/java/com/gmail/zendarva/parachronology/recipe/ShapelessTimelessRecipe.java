package com.gmail.zendarva.parachronology.recipe;

import com.gmail.zendarva.parachronology.capability.ITimeless;
import com.gmail.zendarva.parachronology.capability.Timeless;
import com.gmail.zendarva.parachronology.capability.TimelessProvider;
import com.gmail.zendarva.parachronology.item.Moment;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nonnull;

/**
 * Created by James on 8/9/2017.
 */
public class ShapelessTimelessRecipe extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe{


    ItemStack moment = ItemStack.EMPTY;
    ItemStack timelessObject = ItemStack.EMPTY;

    @Override
    public boolean isHidden() {
        return true;
    }

    @Override
    public boolean canFit(int width, int height) {
        return true;
    }

    @Nonnull
    @Override
    public ItemStack getRecipeOutput() {

        return timelessObject.copy();
    }

    @Override
    public boolean matches(@Nonnull InventoryCrafting var1, @Nonnull World world) {
        for (int i = 0; i<var1.getSizeInventory(); i++){
            if (var1.getStackInSlot(i).hasCapability(TimelessProvider.timeless,null))
                timelessObject = var1.getStackInSlot(i);
            if (var1.getStackInSlot(i).getItem() instanceof Moment)
                moment = var1.getStackInSlot(i);
        }
        return (!timelessObject.isEmpty() && !moment.isEmpty());
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv)
    {
        ItemStack copy = timelessObject.copy();
        ITimeless timeless = copy.getCapability(TimelessProvider.timeless,null);
        if (timeless != null)
            timeless.addEnergy(300);
        return copy;
    }


}
