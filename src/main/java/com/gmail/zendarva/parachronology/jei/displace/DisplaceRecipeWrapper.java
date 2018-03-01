package com.gmail.zendarva.parachronology.jei.displace;

import com.gmail.zendarva.parachronology.BlockReference;
import com.gmail.zendarva.parachronology.DisplaceRecipe;
import com.gmail.zendarva.parachronology.Parachronology;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by James on 8/20/2017.
 */
public class DisplaceRecipeWrapper extends BlankRecipeWrapper {
    private DisplaceRecipe wrappedRecipe;


    @Override
    public void getIngredients(IIngredients iIngredients) {
        List<ItemStack> inputs = new LinkedList<>();
        List<ItemStack> outputs = new LinkedList<>();
        if (wrappedRecipe.from.metadata != -1){
            inputs.add(wrappedRecipe.from.getStack());
            iIngredients.setInputs(ItemStack.class,inputs);
        }
        else {

            ItemStack from = wrappedRecipe.from.getStack().copy();
            NonNullList<ItemStack> items = NonNullList.create();

            for (CreativeTabs tab : CreativeTabs.CREATIVE_TAB_ARRAY) {
                from.getItem().getSubItems(tab,items);
                for (ItemStack item : items) {
                    inputs.add(item);
                }
                items.clear();
            }
            List<List<ItemStack>> inputList = new LinkedList<>();
            inputList.add(inputs);
            iIngredients.setInputLists(ItemStack.class,inputList);
        }
        for (BlockReference ref : wrappedRecipe.getDisplacement(2)){
            if (ref.getStack() == null) {
                System.out.println("Error in recipe");
            }
            outputs.add(ref.getStack());
        }

        iIngredients.setOutputs(ItemStack.class,outputs);

    }
    public List<ItemStack> getOutputForTier(int tier){
        List<ItemStack> outputs = new LinkedList<>();
        for (BlockReference ref : wrappedRecipe.getDisplacement(tier)){
            if (ref.getStack() == null) {
                System.out.println("Error in recipe");
            }
            outputs.add(ref.getStack());
        }
        return outputs;
    }

    public DisplaceRecipeWrapper(DisplaceRecipe wrappedRecipe) {
        this.wrappedRecipe = wrappedRecipe;
    }
}
