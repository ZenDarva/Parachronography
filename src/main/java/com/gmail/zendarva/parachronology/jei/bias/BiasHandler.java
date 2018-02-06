package com.gmail.zendarva.parachronology.jei.bias;

import com.gmail.zendarva.parachronology.DisplaceRecipe;
import com.gmail.zendarva.parachronology.recipe.BiasRecipe;
import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;

/**
 * Created by James on 2/5/2018.
 */
public class BiasHandler implements IRecipeHandler<BiasRecipe> {
    @Override
    public Class<BiasRecipe> getRecipeClass() {
        return BiasRecipe.class;
    }

    @Override
    public String getRecipeCategoryUid(BiasRecipe biasRecipe) {
        return VanillaRecipeCategoryUid.CRAFTING;
    }

    @Override
    public IRecipeWrapper getRecipeWrapper(BiasRecipe biasRecipe) {
        return new BiasRecipeWrapper(biasRecipe);
    }

    @Override
    public boolean isRecipeValid(BiasRecipe biasRecipe) {
        return true;
    }
}
