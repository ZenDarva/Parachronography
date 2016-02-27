package com.darva.parachronology.jei;

import com.darva.parachronology.BlockReference;
import com.darva.parachronology.DisplaceRecipe;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;
import java.util.*;

/**
 * Created by James on 1/18/2016.
 */
public class DisplacerWrapper implements IRecipeWrapper {

    private DisplaceRecipe recipe;
    private List<ItemStack> inputs = new LinkedList<ItemStack>();
    private List<ItemStack> outputs = new LinkedList<ItemStack>();

    public DisplacerWrapper(DisplaceRecipe recipe) {
    this.recipe = recipe;
        if (recipe.from.metadata != -1)
            this.inputs.add(recipe.from.getStack());
        else
        {
            for (int i=0; i<16; i++)
            {
                this.inputs.add(new ItemStack(recipe.from.targBlock,1,i));
            }
        }

        for (BlockReference ref : recipe.getDisplacement(2))
        {
            outputs.add(ref.getStack());
        }
    }

    @Override
    public List getInputs() {
        return inputs;
    }

    @Override
    public List getOutputs() {
        return outputs;
    }

    @Override
    public List<FluidStack> getFluidInputs() {
        return null;
    }

    @Override
    public List<FluidStack> getFluidOutputs() {
        return null;
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight) {

    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {

    }

    @Override
    public void drawAnimations(Minecraft minecraft, int recipeWidth, int recipeHeight) {

    }

    @Nullable
    @Override
    public List<String> getTooltipStrings(int mouseX, int mouseY) {
        return null;
    }

    @Override
    public boolean handleClick(Minecraft minecraft, int mouseX, int mouseY, int mouseButton) {
        return false;
    }
}

