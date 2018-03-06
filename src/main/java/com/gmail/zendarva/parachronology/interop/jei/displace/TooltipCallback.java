package com.gmail.zendarva.parachronology.interop.jei.displace;

import mezz.jei.api.gui.ITooltipCallback;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by James on 2/28/2018.
 */
public class TooltipCallback implements ITooltipCallback<ItemStack> {


    private final ArrayList<LinkedList<ItemStack>> tier;

    @Override
    public void onTooltip(int blah, boolean b, ItemStack stack, List<String> list) {
        for(int i = 0; i < tier.size(); i++){
            for (ItemStack targ : tier.get(i)){
                if (targ.isItemEqual(stack) && targ.getMetadata() == stack.getMetadata()){
                    list.add("Requires a Tier "+ (i+1) + " Displacer");
                    return;
                }

            }
        }
    }

    public TooltipCallback(ArrayList<LinkedList<ItemStack>> tier){
        this.tier = tier;
    }
}
