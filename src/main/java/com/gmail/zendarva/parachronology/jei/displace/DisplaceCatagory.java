package com.gmail.zendarva.parachronology.jei.displace;

import com.gmail.zendarva.parachronology.Parachronology;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by James on 8/20/2017.
 */
public class DisplaceCatagory extends BlankRecipeCategory<DisplaceRecipeWrapper> {
    IDrawable background;
    IDrawable icon;
    @Override
    public String getUid() {
        return "parachronology.displace";
    }

    @Override
    public String getTitle() {
        return "Displacements";
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
    public void setRecipe(IRecipeLayout iRecipeLayout, DisplaceRecipeWrapper displaceRecipeWrapper, IIngredients iIngredients) {
        IGuiItemStackGroup displayStacks = iRecipeLayout.getItemStacks();

        int index = 0;

        List input =iIngredients.getInputs(ItemStack.class);
        List output = iIngredients.getOutputs(ItemStack.class);;

        for(int i =0; i< input.size();i++){
            displayStacks.init(index,true,16,i*18);
            if (input.get(i) instanceof  ItemStack)
                displayStacks.set(index, (ItemStack) input.get(i));
            else
                displayStacks.set(index, (List<ItemStack>) input.get(i));
            index++;
        }
        displayStacks.init(index,false,168/2-9,128/2-9);
        index++;
        displayStacks.init(index,false,168/2-9,128/2-9-18);

        displayStacks.set(index-1,new ItemStack(Parachronology.displacer));
        displayStacks.set(index,new ItemStack(Parachronology.moment,1,0));

        ArrayList<LinkedList<ItemStack>> tiers = new ArrayList<>(3);
        for (int tier =0;tier<3;tier++){
            int x = 100 + (27*tier);
            int y = 0;
            tiers.add(tier,new LinkedList<>());
            for (ItemStack stack : displaceRecipeWrapper.getOutputForTier(tier)){
                if (tier == 0 || (tier > 0 && !isDupe(stack,displaceRecipeWrapper,tier-1))) {
                    tiers.get(tier).add(stack);
                    displayStacks.init(index, false, x, y);
                    displayStacks.set(index, stack);
                    index++;
                    y += 18;
                }
            }
            y=18;
        }
        displayStacks.addTooltipCallback(new TooltipCallback(tiers));
    }

    public boolean isDupe(ItemStack targ, DisplaceRecipeWrapper wrapper, int tier){
        for (ItemStack stack : wrapper.getOutputForTier(tier)){
            if (stack.isItemEqual(targ) && stack.getMetadata() == targ.getMetadata())
                return true;
        }
        return false;
    }

    public DisplaceCatagory(IGuiHelper helper) {

        background = helper.createBlankDrawable(168,128);
        ResourceLocation location = new ResourceLocation("parachronology","textures/items/simplemoment.png");

        icon = helper.createDrawable(location,0,0,16,16,16,16);
    }
}
