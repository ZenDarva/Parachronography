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
        //registry.addRecipeHandlers(new DisplaceHandler());

        //registry.addRecipes(DisplaceListBuilder.displaceRecipes.values());
        this.registry = registry;
        registry.addRecipes(displaceRecipes, "parachronology.displace");
        registry.addRecipes(dislocateRecipes, "parachronology.dislocate");
        //registry.addRecipeCatalyst(Parachronology.basicMoment, "parachronology.displace");

        registry.addDescription(new ItemStack(Parachronology.displacer), "Place in the world, and surround it in a 3x3 block with appropriate blocks", "Throw a simple moment at it", "Enjoy!");

        //Redo this. It's ugly.
        registry.addDescription(new ItemStack(Parachronology.moment), getDropedByInfo(new ItemStack(Parachronology.moment)));
        registry.addDescription(new ItemStack(Parachronology.moment, 1, 1), getDropedByInfo(new ItemStack(Parachronology.moment, 1, 1)));
        registry.addDescription(new ItemStack(Parachronology.moment, 1, 2), getDropedByInfo(new ItemStack(Parachronology.moment, 1, 2)));
        registry.addDescription(new ItemStack(Parachronology.pickaxe), "Link with a Timeless Storage by right clicking it.", "Mined blocks will appear in the storage.");
        registry.addDescription(new ItemStack(Parachronology.wand), "Link with a Timeless Storage by right clicking it.", "Will place blocks from linked storage.");
        registry.addDescription(new ItemStack(Parachronology.storage), "Single chunk, chunkloader.", "Links with timeless tools.");
    }

    public String[] getDropedByInfo(ItemStack stack) {
        ArrayList<String> result = new ArrayList<>();
        result.add("Dropped Chance:");
        for (DropData data : ConfigManager.mobDrops.values()) {
            if (data.momentChance > 0 && stack.getMetadata() == 1)
                result.add(String.format("%-20s%-2d%%",data.mobName,data.momentChance));
            if (data.simpleMomentChance > 0 && stack.getMetadata() == 0)
                result.add(String.format("%-20s%d%%",data.mobName,data.simpleMomentChance));
            if (data.complexMomentChance > 0 && stack.getMetadata() == 2)
                result.add(String.format("%-20s%-2d%%",data.mobName,data.complexMomentChance));
        }
        return result.toArray(new String[0]);
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        registry.addRecipeCategories(new DisplaceCatagory(registry.getJeiHelpers().getGuiHelper()));
        registry.addRecipeCategories(new DislocateCatagory(registry.getJeiHelpers().getGuiHelper()));
    }


}
