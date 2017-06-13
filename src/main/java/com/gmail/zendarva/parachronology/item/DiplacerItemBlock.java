package com.gmail.zendarva.parachronology.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

/**
 * Created by James on 8/23/2015.
 */
public class DiplacerItemBlock extends ItemBlock {
    @Override
    public String getUnlocalizedName(ItemStack p_77667_1_) {
        switch (p_77667_1_.getItemDamage()) {
            case 0:
                return "parachronology:displacer";
            case 1:
                return "parachronology:displacer";
            case 2:
                return "parachronology:displacer";
        }
        return "Groot";
    }

    public DiplacerItemBlock(Block p_i45328_1_) {
        super(p_i45328_1_);
        this.setRegistryName("displaceritemblock");
        setHasSubtypes(true);

    }

    @Override
    public int getMetadata(int p_77647_1_) {
        return p_77647_1_;
    }
}
