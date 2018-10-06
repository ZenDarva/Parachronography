package com.gmail.zendarva.parachronology.interop.jei.displace;

import com.gmail.zendarva.parachronology.Configuration.domain.BaseBlockReference;
import com.gmail.zendarva.parachronology.Configuration.domain.BlockReference;
import com.gmail.zendarva.parachronology.Configuration.domain.DisplaceResult;
import com.gmail.zendarva.parachronology.Configuration.domain.OreDictReference;
import com.gmail.zendarva.parachronology.Parachronology;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.wrapper.ICustomCraftingRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import org.omg.PortableServer.LIFESPAN_POLICY_ID;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by James on 6/16/2018.
 */
public class DisplaceRecipe implements ICustomCraftingRecipeWrapper {
    private final BaseBlockReference in;
    public final List<DisplaceResult> out;
    private final int tier;

    @Override
    public void setRecipe(IRecipeLayout iRecipeLayout, IIngredients iIngredients) {

    }

    @Override
    public void getIngredients(IIngredients iIngredients) {
        List<ItemStack> momentInput = new LinkedList<>();
        momentInput.add(new ItemStack(Parachronology.moment,1,0));
        momentInput.add(new ItemStack(Parachronology.moment,1,1));
        momentInput.add(new ItemStack(Parachronology.moment,1,2));
        if (in instanceof OreDictReference){
            List<ItemStack> inList = OreDictionary.getOres(in.getDisplayName());
            List<List<ItemStack>> whyJEIWhy= new LinkedList<>();
            whyJEIWhy.add(inList);
            whyJEIWhy.add(momentInput);
            iIngredients.setInputLists(ItemStack.class,whyJEIWhy);
        }
        else {
            BlockReference inRef = (BlockReference) in;
            List<ItemStack> inList = new LinkedList<>();
            inList.add(((BlockReference) in).getItemStack());
            List<List<ItemStack>> whyJEIWhy= new LinkedList<>();
            whyJEIWhy.add(inList);
            whyJEIWhy.add(momentInput);
            iIngredients.setInputLists(ItemStack.class,whyJEIWhy );

        }
        iIngredients.setOutputs(ItemStack.class, out.stream().map(f->f.to.getItemStack()).collect(Collectors.toList()));

    }

    public DisplaceRecipe(BaseBlockReference in, List<DisplaceResult> out, int tier){

        this.in = in;
        this.out = out;
        this.tier = tier;
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {

    }

    @Override
    public List<String> getTooltipStrings(int mouseX, int mouseY) {
        return null;
    }

    @Override
    public boolean handleClick(Minecraft minecraft, int mouseX, int mouseY, int mouseButton) {
        return false;
    }


    public int getTier() {
        return tier;
    }
}
