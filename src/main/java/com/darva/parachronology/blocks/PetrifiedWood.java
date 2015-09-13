package com.darva.parachronology.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

/**
 * Created by James on 9/12/2015.
 */
public class PetrifiedWood extends Block {
    @Override
    public String getUnlocalizedName() {
        return "parachronology:petrifiedwood";
    }

    public PetrifiedWood(Material p_i45394_1_) {
        super(p_i45394_1_);
        this.setHardness(1.2f);
        this.setLightOpacity(1);
        this.setResistance(5f);
        this.setStepSound(Block.soundTypeStone);
        this.setHarvestLevel("pickaxe", 0);
        this.setBlockTextureName("parachronology:petrifiedwood");
        this.setCreativeTab(CreativeTabs.tabBlock);

    }


}
