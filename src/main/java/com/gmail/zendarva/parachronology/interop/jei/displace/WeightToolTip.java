package com.gmail.zendarva.parachronology.interop.jei.displace;

import com.gmail.zendarva.parachronology.Configuration.domain.DisplaceResult;
import mezz.jei.api.gui.ITooltipCallback;
import net.minecraft.item.ItemStack;

import java.util.List;

/**
 * Created by James on 6/16/2018.
 */
public class WeightToolTip implements ITooltipCallback<ItemStack> {


    private final DisplaceRecipe recipe;

    public WeightToolTip(DisplaceRecipe recipe) {


        this.recipe = recipe;
    }

    @Override
    public void onTooltip(int i, boolean b, ItemStack stack, List<String> list) {
        for (DisplaceResult displaceResult : recipe.out) {
            if (displaceResult.to.getItemStack() == stack){
                list.add("Displace Weight: " + displaceResult.weight);
                break;
            }
        }
    }
}
