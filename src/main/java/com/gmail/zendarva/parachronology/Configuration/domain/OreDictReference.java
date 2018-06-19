package com.gmail.zendarva.parachronology.Configuration.domain;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;

/**
 * Created by James on 4/13/2018.
 */
public class OreDictReference extends BaseBlockReference {
    @Override
    public boolean matches(BaseBlockReference target) {
        if (target instanceof OreDictReference){
            OreDictReference targRef = (OreDictReference) target;
            return (targRef.oreDictName.equals(oreDictName));
        }
        if (target instanceof  BlockReference){
            BlockReference targRef = (BlockReference) target;

            NonNullList<ItemStack> dict = OreDictionary.getOres(oreDictName);
            NonNullList<ItemStack> stupid = NonNullList.create();
            stupid.add(targRef.getItemStack());
            //Rewrite containsMatch to not be stupid.
            //return OreDictionary.containsMatch(false,stupid,dict.toArray(new ItemStack[0]));
            return containsMatch(false,dict,targRef.getItemStack());
        }
        return false;
    }

    protected OreDictReference() {
    }

    @Override
    public String getDisplayName() {
        return oreDictName;
    }

    protected OreDictReference(String oreDictName){
        this.oreDictName = oreDictName;
    }

    public String oreDictName;


    private boolean containsMatch(boolean strict, NonNullList<ItemStack> dict, ItemStack... targets){
        for (ItemStack input : dict)
        {
            for (ItemStack target : targets)
            {
                if (OreDictionary.itemMatches(input, target, strict))
                {
                    return true;
                }
            }
        }
        return false;
    }

}
