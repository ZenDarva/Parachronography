package com.gmail.zendarva.parachronology.utility;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemBlock;

/**
 * Created by James on 7/16/2018.
 */
public class ItemBlockOverride extends ItemBlock {

    public ItemBlockOverride(Block block) {
        super(block);
    }

    @Override
    public int getMetadata(int damage) {
        if (isValidMetadata(damage))
            return damage;
        return block.getMetaFromState(block.getDefaultState());
    }
    private boolean isValidMetadata(int damage){
        for (IBlockState iBlockState : block.getBlockState().getValidStates()) {
            if (block.getMetaFromState(iBlockState) == damage)
                return true;
        }
        return false;
    }
}
