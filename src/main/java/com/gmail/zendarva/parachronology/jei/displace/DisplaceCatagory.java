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
            index++;
        }

        int y=0;
        int x=168-18;
        if (output.size() > 5)
            x-=18;
        for(int i =0; i< output.size();i++){
            if (i == 5){
                x+=18;
                y=0;
            }

            displayStacks.init(index,false,x,y);
            index++;
            y+=18;
        }

        displayStacks.init(index,false,168/2-9,128/2-9);
        index++;
        displayStacks.init(index,false,168/2-9,128/2-9-18);

        displayStacks.set(index-1,new ItemStack(Parachronology.displacer));
        displayStacks.set(index,new ItemStack(Parachronology.moment,1,0));

        displayStacks.set(iIngredients);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void drawExtras(Minecraft minecraft) {




    }

    public DisplaceCatagory(IGuiHelper helper) {

        background = helper.createBlankDrawable(168,128);
        ResourceLocation location = new ResourceLocation("parachronology","textures/items/simplemoment.png");
        //ResourceLocation model = new ResourceLocation("parachronology","blockstates/displacer.json");

        icon = helper.createDrawable(location,0,0,16,16,16,16);
        //icon=helper.createDrawable(model,0,0,16,16);

    }
}
