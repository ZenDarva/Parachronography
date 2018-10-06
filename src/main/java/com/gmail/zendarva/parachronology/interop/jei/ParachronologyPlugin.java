package com.gmail.zendarva.parachronology.interop.jei;

import com.gmail.zendarva.parachronology.Configuration.ConfigManager;
import com.gmail.zendarva.parachronology.Configuration.ConfigurationHolder;
import com.gmail.zendarva.parachronology.Parachronology;
import com.gmail.zendarva.parachronology.Configuration.domain.DropData;
import com.gmail.zendarva.parachronology.interop.jei.displace.DisplaceCatagory;
import com.gmail.zendarva.parachronology.interop.jei.displace.DisplaceRecipe;
import com.gmail.zendarva.parachronology.interop.jei.dislocate.DislocateCatagory;
import com.gmail.zendarva.parachronology.interop.jei.dislocate.DislocateRecipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by James on 8/20/2017.
 */
@JEIPlugin
public class ParachronologyPlugin implements IModPlugin {

    public static List<DisplaceRecipe> displaceRecipes = new ArrayList<>();
    public static List<DislocateRecipe> dislocateRecipes = new ArrayList<>();
    private static IModRegistry registry;

    @Override
    public void register(IModRegistry registry) {
        this.registry = registry;
        registry.addRecipes(displaceRecipes, "parachronology.displace");
        registry.addRecipes(dislocateRecipes, "parachronology.dislocate");

        registry.addDescription(new ItemStack(Parachronology.displacer), "Place in the world, and surround it in a 3x3 block with appropriate blocks", "Throw a simple moment at it", "Enjoy!");

        registry.addDescription(new ItemStack(Parachronology.pickaxe), "Link with a Timeless Storage by right clicking it.", "Mined blocks will appear in the storage.");
        registry.addDescription(new ItemStack(Parachronology.wand), "Link with a Timeless Storage by right clicking it.", "Will place blocks from linked storage.");
        registry.addDescription(new ItemStack(Parachronology.storage), "Single chunk, chunkloader.", "Links with timeless tools.");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        registry.addRecipeCategories(new DisplaceCatagory(registry.getJeiHelpers().getGuiHelper()));
        registry.addRecipeCategories(new DislocateCatagory(registry.getJeiHelpers().getGuiHelper()));
    }


}
