package com.gmail.zendarva.parachronology.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

/**
 * Created by James on 8/23/2015.
 */
public class DiplacerItemBlock extends ItemBlock {
	
	public DiplacerItemBlock(Block block) {
		super(block);
		setHasSubtypes(true);
	}
	
	@Override
    public String getUnlocalizedName(ItemStack stack)
    {
        return this.block.getUnlocalizedName() + "." + stack.getMetadata();
    }

	@Override
	public int getMetadata(int damage) {
		return damage;
	}
}
