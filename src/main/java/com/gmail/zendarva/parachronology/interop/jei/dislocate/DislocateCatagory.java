package com.gmail.zendarva.parachronology.interop.jei.dislocate;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Created by James on 6/17/2018.
 */
public class DislocateCatagory implements IRecipeCategory<DislocateRecipe>{

    IDrawable background;
    IDrawable icon;

    @Override
    public String getUid() {
        return "parachronology.dislocate";
    }

    @Override
    public String getTitle() {
        return "Dislocate";
    }

    @Override
    public String getModName() {
        return "Parachronology";
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Nullable
    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayout iRecipeLayout, DislocateRecipe dislocateRecipe, IIngredients iIngredients) {
        IGuiItemStackGroup displayStacks = iRecipeLayout.getItemStacks();
        int index = 0;

        List<List<ItemStack>> input =iIngredients.getInputs(ItemStack.class);
        List<List<ItemStack>> output = iIngredients.getOutputs(ItemStack.class);;


        displayStacks.init(index,true,16,0);
        if (input.get(0).size()== 1)
            displayStacks.set(index,input.get(0).get(0));
        else
            displayStacks.set(index,input.get(0));
        index++;


        displayStacks.init(index,true,168/2-9,128/2-9-18);
        displayStacks.set(index,input.get(1));
        index++;

        int x = 110;
        int y = 0;
        int count=0;
        for (List<ItemStack> l : output) {
            displayStacks.init(index,false,x,y);
            displayStacks.set(index,l.get(0));

            index++;
            y+=18;
            count++;
            if (count >= 5){
                count = 0;
                x+=25;
                y=0;
            }
        }


    }

    public DislocateCatagory(IGuiHelper helper){
        background = helper.createBlankDrawable(168,128);
        ResourceLocation location = new ResourceLocation("parachronology","textures/items/simplemoment.png");

        icon = helper.createDrawable(location,0,0,16,16,16,16);
    }
}
