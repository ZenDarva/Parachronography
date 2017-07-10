package com.gmail.zendarva.parachronology.item;

import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.List;

/**
 * Created by James on 8/23/2015.
 */
public class DiplacerItemBlock extends ItemBlock {
	
	public DiplacerItemBlock(Block block) {
		super(block);
		setHasSubtypes(true);
		this.setRegistryName("displacer");
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


	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		if (stack.hasTagCompound())
		{
			NBTTagCompound tag = stack.getTagCompound();
			if (tag.hasKey("towards"))
			{
				tooltip.add("Biased Towards: " + tag.getString("towards"));
			}
			if (tag.hasKey("towards"))
			{
				tooltip.add("Biased Against: " + tag.getString("against"));
			}
		}
		super.addInformation(stack, worldIn,tooltip, flagIn);
	}
}
