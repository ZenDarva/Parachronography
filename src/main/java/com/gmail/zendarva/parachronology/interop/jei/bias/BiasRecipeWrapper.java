package com.gmail.zendarva.parachronology.interop.jei.bias;

import com.gmail.zendarva.parachronology.recipe.BiasRecipe;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.wrapper.IShapedCraftingRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by James on 2/5/2018.
 */
public class BiasRecipeWrapper implements IShapedCraftingRecipeWrapper {
    private BiasRecipe biasRecipe;
    public BiasRecipeWrapper(BiasRecipe biasRecipe) {
       this.biasRecipe=biasRecipe;
    }

    @Override
    public void getIngredients(IIngredients iIngredients) {
        List<ItemStack> outputs = new LinkedList<>();
        List<ItemStack> inputs = new LinkedList<>();

        for (Ingredient ing: biasRecipe.getIngredients()){
            inputs.add(ing.getMatchingStacks()[0]);
        }
        iIngredients.setInputs(ItemStack.class,inputs);
        outputs.add(biasRecipe.getRecipeOutput());
        iIngredients.setOutput(ItemStack.class,outputs);

    }

    @Override
    public int getWidth() {
        return 3;
    }

    @Override
    public int getHeight() {
        return 3;
    }
}
