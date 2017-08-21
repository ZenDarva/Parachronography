package com.gmail.zendarva.parachronology.item;

import java.util.LinkedList;
import java.util.List;

import com.gmail.zendarva.parachronology.BlockReference;
import com.gmail.zendarva.parachronology.Parachronology;
import com.gmail.zendarva.parachronology.entity.DisplacerEntity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

/**
 * Created by James on 2/27/2016.
 */
public class Bias extends Item {
	private LinkedList<ItemStack> subItems = new LinkedList<ItemStack>();

	public Bias() {
		String name = "bias";
		this.setMaxStackSize(1);
		this.setRegistryName(name);
		this.setCreativeTab(Parachronology.TAB);
		this.setUnlocalizedName(Parachronology.MODID + "." + name);
		this.setHasSubtypes(true);
		this.setMaxDamage(0);

	}

	public void addSubItem(ItemStack item) {
		subItems.add(item);
	}


	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if (tab == Parachronology.TAB) {
			items.addAll(this.subItems);
		}
	}

	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		BlockReference ref;
		if (stack.getTagCompound() != null) {
			if (stack.getTagCompound().hasKey("against")) {
				ref = BlockReference.readBlockFromString(stack.getTagCompound().getString("against"));
				tooltip.add("Biased against: " + ref.getStack().getDisplayName());
			}
			if (stack.getTagCompound().hasKey("towards")) {
				ref = BlockReference.readBlockFromString(stack.getTagCompound().getString("towards"));
				tooltip.add("Biased towards: " + ref.getStack().getDisplayName());
			}
		}
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		IBlockState block = worldIn.getBlockState(pos);
		if (block.getBlock() != Parachronology.displacer)
			return EnumActionResult.FAIL;
		ItemStack stack = player.getHeldItem(hand);
		TileEntity entity = worldIn.getTileEntity(pos);
		DisplacerEntity displacer = (DisplacerEntity) entity;

		if (stack.getTagCompound().hasKey("against")) {
			displacer.setAgainst(BlockReference.readBlockFromString(stack.getTagCompound().getString("against")));
			stack.shrink(1);
			return EnumActionResult.SUCCESS;
		}
		if (stack.getTagCompound().hasKey("towards")) {
			displacer.setTowards(BlockReference.readBlockFromString(stack.getTagCompound().getString("towards")));
			stack.shrink(1);
			return EnumActionResult.SUCCESS;
		}

		return EnumActionResult.FAIL;
	}



	@SideOnly(Side.CLIENT)
	public void registerModel() {
		ModelLoader.setCustomModelResourceLocation(this, 0,
				new ModelResourceLocation(this.getRegistryName(), "inventory"));

	}
}
