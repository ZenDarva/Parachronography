package com.gmail.zendarva.parachronology.item;

import java.util.LinkedList;
import java.util.List;

import com.gmail.zendarva.parachronology.BlockReference;
import com.gmail.zendarva.parachronology.Parachronology;
import com.gmail.zendarva.parachronology.entity.DisplacerEntity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by James on 2/27/2016.
 */
public class Bias extends Item {
	private LinkedList<ItemStack> subItems = new LinkedList<ItemStack>();

	public Bias() {
		this.setMaxStackSize(1);
		this.setRegistryName("bias");

		this.setUnlocalizedName("parachronology:bias");
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
		GameRegistry.register(this);
	}

	public void addSubItem(ItemStack item) {
		subItems.add(item);
	}

	@Override
	public void getSubItems(Item itemIn, CreativeTabs tab, NonNullList<ItemStack> subItems) {
		subItems.addAll(this.subItems);
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean unknown) {

		BlockReference ref;
		if (stack.getTagCompound() != null) {
			if (stack.getTagCompound().hasKey("against")) {
				ref = BlockReference.readBlockFromString(stack.getTagCompound().getString("against"));
				list.add("Biased against: " + ref.getStack().getDisplayName());
			}
			if (stack.getTagCompound().hasKey("towards")) {
				ref = BlockReference.readBlockFromString(stack.getTagCompound().getString("towards"));
				list.add("Biased towards: " + ref.getStack().getDisplayName());
			}
		}
	}

	public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side,
			float hitX, float hitY, float hitZ) {
		IBlockState block = worldIn.getBlockState(pos);

		if (block.getBlock() != Parachronology.displacer)
			return false;
		TileEntity entity = worldIn.getTileEntity(pos);
		DisplacerEntity displacer = (DisplacerEntity) entity;

		if (stack.getTagCompound().hasKey("against")) {
			displacer.setAgainst(BlockReference.readBlockFromString(stack.getTagCompound().getString("against")));
			return true;
		}
		if (stack.getTagCompound().hasKey("towards")) {
			displacer.setTowards(BlockReference.readBlockFromString(stack.getTagCompound().getString("towards")));
			return true;
		}

		return false;
	}

	@SideOnly(Side.CLIENT)
	public void registerModel() {
		ModelLoader.setCustomModelResourceLocation(this, 0,
				new ModelResourceLocation(this.getRegistryName(), "inventory"));

	}
}
