package com.gmail.zendarva.parachronology.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

/**
 * Created by James on 8/23/2015.
 */
public class DiplacerItemBlock extends ItemBlock {

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		switch (stack.getItemDamage()) {
		case 0:
			return "parachronology:displacer";
		case 1:
			return "parachronology:displacer";
		case 2:
			return "parachronology:displacer";
		}
		return "Groot";
	}

	public DiplacerItemBlock(Block block) {
		super(block);
		setHasSubtypes(true);
	}

	@Override
	public int getMetadata(int damage) {
		return damage;
	}
}
