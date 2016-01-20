package com.darva.parachronology.jei;

import com.darva.parachronology.DisplaceListBuilder;
import com.darva.parachronology.DisplaceRecipe;
import mezz.jei.api.*;

import java.util.LinkedList;

/**
 * Created by James on 1/18/2016.
 */
@JEIPlugin
public class PluginParachronology implements IModPlugin {
    IJeiHelpers jeiHelpers;
    @Override
    public void onJeiHelpersAvailable(IJeiHelpers jeiHelpers) {
            this.jeiHelpers=jeiHelpers;
    }

    @Override
    public void onItemRegistryAvailable(IItemRegistry itemRegistry) {

    }

    @Override
    public void register(IModRegistry registry) {
        registry.addRecipeHandlers(new DisplacerHandler());
        registry.addRecipeCategories(new DisplacerCategory(jeiHelpers.getGuiHelper()));
        LinkedList<DisplaceRecipe> recipes = new LinkedList<DisplaceRecipe>();
        for (DisplaceRecipe rec : DisplaceListBuilder.displaceRecipes.values())
            recipes.add(rec);
        registry.addRecipes(recipes);
    }

    @Override
    public void onRecipeRegistryAvailable(IRecipeRegistry recipeRegistry) {

    }
}
