package com.gmail.zendarva.parachronology.interop.jei.displace;

import com.gmail.zendarva.parachronology.Configuration.domain.DisplaceResult;
import com.gmail.zendarva.parachronology.Parachronology;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.List;

/**
 * Created by James on 8/20/2017.
 */
public class DisplaceCatagory implements IRecipeCategory<DisplaceRecipe> {
    IDrawable background;
    IDrawable icon;
    private int tier;

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
    public void setRecipe(IRecipeLayout iRecipeLayout, DisplaceRecipe displaceRecipeWrapper, IIngredients iIngredients) {
        IGuiItemStackGroup displayStacks = iRecipeLayout.getItemStacks();
        tier = displaceRecipeWrapper.getTier();
        int index = 0;

        List input = iIngredients.getInputs(ItemStack.class);
        List<List<ItemStack>> output = iIngredients.getOutputs(ItemStack.class);
        ;
        List<ItemStack> targList;
        for (int i = 0; i < input.size(); i++) {
            displayStacks.init(index, true, 16, i * 18);
            if (input.get(i) instanceof ItemStack)
                displayStacks.set(index, (ItemStack) input.get(i));
            else {
                targList = (List<ItemStack>) input.get(i);
                if (((ItemStack)targList.get(0)).getItem() == Parachronology.moment)
                    continue;
                displayStacks.set(index, (List<ItemStack>) input.get(i));
                index++;
            }

        }
        displayStacks.init(index, false, 168 / 2 - 9, 128 / 2 - 9);
        index++;
        displayStacks.init(index, false, 168 / 2 - 9, 128 / 2 - 9 - 18);

        displayStacks.set(index - 1, new ItemStack(Parachronology.displacer));
        displayStacks.set(index, new ItemStack(Parachronology.moment, 1, 0));

        int x = 110;
        int y = 0;
        int count = 0;
        displayStacks.addTooltipCallback(new WeightToolTip(displaceRecipeWrapper));
        for (List<ItemStack> l : output) {
            displayStacks.init(index, false, x, y);
            displayStacks.set(index, l.get(0));

            index++;
            y += 18;
            count++;
            if (count >= 5) {
                count = 0;
                x += 25;
                y = 0;
            }
        }
    }


    @Override
    public void drawExtras(Minecraft minecraft) {
        minecraft.currentScreen.drawString(minecraft.fontRenderer, "Tier: " + tier, 168 / 2 - 15, 20, Color.WHITE.getRGB());
    }

    public DisplaceCatagory(IGuiHelper helper) {

        background = helper.createBlankDrawable(168, 128);
        ResourceLocation location = new ResourceLocation("parachronology", "textures/items/simplemoment.png");

        icon = helper.createDrawable(location, 0, 0, 16, 16, 16, 16);
    }
}
