package com.gmail.zendarva.parachronology.interop.jei.dislocate;

import com.gmail.zendarva.parachronology.Configuration.domain.BaseBlockReference;
import com.gmail.zendarva.parachronology.Configuration.domain.BlockReference;
import com.gmail.zendarva.parachronology.Configuration.domain.OreDictReference;
import com.gmail.zendarva.parachronology.Parachronology;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.wrapper.ICustomCraftingRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by James on 6/17/2018.
 */
public class DislocateRecipe implements ICustomCraftingRecipeWrapper {

    private final BaseBlockReference in;
    public final List<BlockReference> out;

    public DislocateRecipe(BaseBlockReference in, List<BlockReference> out) {
        this.in = in;
        this.out = out;
    }

    @Override
    public void setRecipe(IRecipeLayout iRecipeLayout, IIngredients iIngredients) {



    }


    @Override
    public void getIngredients(IIngredients iIngredients) {
        List<List<ItemStack>> whyJEIWhy= new LinkedList<>();
        if (in instanceof OreDictReference){
            List<ItemStack> inList = OreDictionary.getOres(in.getDisplayName());
            whyJEIWhy.add(inList);

        }
        else {
            List<ItemStack> inList = new LinkedList<>();
            BlockReference inRef = (BlockReference) in;
            inList.add(inRef.getItemStack());
            whyJEIWhy.add(inList);
        }
        List<ItemStack> momentList = new LinkedList<>();
        momentList.add(new ItemStack(Parachronology.moment,1,0));
        momentList.add(new ItemStack(Parachronology.moment,1,1));
        momentList.add(new ItemStack(Parachronology.moment,1,2));
        momentList.add(new ItemStack(Parachronology.basicMoment,1,0));
        whyJEIWhy.add(momentList);
        iIngredients.setInputLists(ItemStack.class,whyJEIWhy);
        iIngredients.setOutputs(ItemStack.class, out.stream().map(f->f.getItemStack()).collect(Collectors.toList()));
    }
}
