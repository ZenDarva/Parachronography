package com.darva.parachronology.jei;

import com.darva.parachronology.DisplaceRecipe;
import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

import javax.annotation.Nonnull;

/**
 * Created by James on 1/18/2016.
 */
public class DisplacerHandler implements IRecipeHandler<DisplaceRecipe> {


    @Nonnull
    @Override
    public Class<DisplaceRecipe> getRecipeClass() {
        return DisplaceRecipe.class;
    }

    @Nonnull
    @Override
    public String getRecipeCategoryUid() {
        return "parachronology.displacer";
    }

    @Nonnull
    @Override
    public IRecipeWrapper getRecipeWrapper(@Nonnull DisplaceRecipe recipe) {
        return new DisplacerWrapper(recipe);
    }

    @Override
    public boolean isRecipeValid(@Nonnull DisplaceRecipe recipe) {
        return true;
    }
}
