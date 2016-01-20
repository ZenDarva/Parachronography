package com.darva.parachronology;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by James on 8/24/2015.
 */
public class DisplaceListBuilder {
    private static DisplaceListBuilder instance;
    public static HashMap<BlockReference, DisplaceRecipe> displaceRecipes;

    private DisplaceListBuilder() {
        displaceRecipes = new HashMap<BlockReference,DisplaceRecipe>();
    }

    public static DisplaceListBuilder Instance() {
        if (instance == null) {
            instance = new DisplaceListBuilder();
        }
        return instance;
    }


    public void addDisplacement(int tier, BlockReference from, String[] to)
    {
        DisplaceRecipe recipe;
        if (displaceRecipes.containsKey(from))
        {
            recipe = displaceRecipes.get(from);
        }
        else
        {
            recipe = new DisplaceRecipe(from);
        }
        recipe.addDisplacement(tier,to);
        displaceRecipes.put(from,recipe);
    }

    public ArrayList<BlockReference> getDisplacements(int Tier, BlockReference from)
    {
        if (displaceRecipes.containsKey(from))
        {
            return displaceRecipes.get(from).getDisplacement(Tier);
        }
        else
            return null;
    }

}
