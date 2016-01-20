package com.darva.parachronology.jei;

import com.darva.parachronology.DisplaceRecipe;
import com.darva.parachronology.Parachronology;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.Drawable;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.List;

/**
 * Created by James on 1/18/2016.
 */
public class DisplacerCategory implements IRecipeCategory {
    IDrawable background;

    public DisplacerCategory(IGuiHelper helper)
    {
        background = helper.createBlankDrawable(168, 64);
    }
    @Nonnull
    @Override
    public String getUid() {
        return "parachronology.displacer";
    }

    @Nonnull
    @Override
    public String getTitle() {
        return "Displacements";
    }

    @Nonnull
    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public void drawExtras(Minecraft minecraft) {

    }

    @Override
    public void drawAnimations(Minecraft minecraft) {

    }

    @Override
    public void setRecipe(@Nonnull IRecipeLayout recipeLayout, @Nonnull IRecipeWrapper recipeWrapper) {
        if (!(recipeWrapper instanceof DisplacerWrapper))
            return;
        DisplacerWrapper recipe = (DisplacerWrapper) recipeWrapper;

        int index =0;
        recipeLayout.getItemStacks().init(index, true, 40, 10);
        recipeLayout.getItemStacks().set(index, (ItemStack) recipe.getInputs().get(0));
        index++;
        recipeLayout.getItemStacks().init(index, true, 70, 10);
        recipeLayout.getItemStacks().set(index, new ItemStack(Parachronology.displacer, 1, 2));
        index++;

//        recipeLayout.getItemStacks().init(index, false, 100, -32);
//        recipeLayout.getItemStacks().set(index, ((List<ItemStack>) recipe.getOutputs()));
        addBlocksToOutput(recipeLayout,recipe.getOutputs(),index);

    }

    private void addBlocksToOutput(IRecipeLayout recipeLayout, List<ItemStack> output, int index)
    {
        int y;
        int x = 100;
        int startY;

        if ( 6 % output.size() >1) //We're using the fulls zpace.
        {
            startY=-32;
        }
        else
        {
            startY = 10  -(18 * ((output.size()-1)/2));
        }
        y=startY;
        for (ItemStack stack : output)
        {
            recipeLayout.getItemStacks().init(index, false, x, y);
            recipeLayout.getItemStacks().set(index, stack);
            index++;
            y+=18;
            if (y> 18*6 +-32)
            {
                x+=16;
                y=startY;
            }
        }
    }
}
