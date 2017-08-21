package com.gmail.zendarva.parachronology.jei;

import com.gmail.zendarva.parachronology.DisplaceRecipe;
import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

/**
 * Created by James on 8/20/2017.
 */
public class DisplaceHandler implements IRecipeHandler<DisplaceRecipe> {
    @Override
    public Class<DisplaceRecipe> getRecipeClass() {
        return DisplaceRecipe.class;
    }

    @Override
    public String getRecipeCategoryUid(DisplaceRecipe displaceRecipe) {
        return "parachronology.displace";
    }

    @Override
    public IRecipeWrapper getRecipeWrapper(DisplaceRecipe displaceRecipe) {
        return new DisplaceRecipeWrapper(displaceRecipe);
    }

    @Override
    public boolean isRecipeValid(DisplaceRecipe displaceRecipe) {
        return true;
    }
}
