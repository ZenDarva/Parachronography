package com.gmail.zendarva.parachronology.item;

import java.util.Random;

import com.gmail.zendarva.parachronology.Parachronology;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

/**
 * Created by James on 3/22/2016.
 */
public class BasicMoment extends Item {

	public BasicMoment() {
		String name = "basicmoment";
		this.setMaxStackSize(64);
		this.setRegistryName(name);
		this.setCreativeTab(Parachronology.TAB);
		GameRegistry.register(this);
		this.setUnlocalizedName(Parachronology.MODID + "." + name);
		this.setHasSubtypes(false);
		this.setMaxDamage(0);
	}

	@SideOnly(Side.CLIENT)
	public void registerModel() {
		ModelLoader.setCustomModelResourceLocation(this, 0,
				new ModelResourceLocation(getRegistryName(), "inventory"));

	}

	private int spread(World world, BlockPos from, int times) {

		if (times == 0)
			return 0;
		if (OreDictionary.itemMatches(new ItemStack(Blocks.LOG),
				new ItemStack(world.getBlockState(from.add(0, -1, 0)).getBlock()), false)) {
			world.destroyBlock(from.add(0, -1, 0), false);
			world.setBlockState(from.add(0, -1, 0), Parachronology.petrifiedWood.getDefaultState());
			world.markChunkDirty(from, null);
			times--;
			times = spread(world, from.add(0, -1, 0), times);
		}
		if (times == 0)
			return 0;
		if (OreDictionary.itemMatches(new ItemStack(Blocks.LOG),
				new ItemStack(world.getBlockState(from.add(0, 1, 0)).getBlock()), false)) {
			world.destroyBlock(from.add(0, 1, 0), false);
			world.setBlockState(from.add(0, 1, 0), Parachronology.petrifiedWood.getDefaultState());
			world.markChunkDirty(from, null);
			times--;
			times = spread(world, from.add(0, 1, 0), times);
		}
		if (times == 0)
			return 0;
		if (OreDictionary.itemMatches(new ItemStack(Blocks.LOG),
				new ItemStack(world.getBlockState(from.add(-1, 0, 0)).getBlock()), false)) {
			world.destroyBlock(from.add(-1, 0, 0), false);
			world.setBlockState(from.add(-1, 0, 0), Parachronology.petrifiedWood.getDefaultState());
			world.markChunkDirty(from, null);
			times--;
			times = spread(world, from.add(-1, 0, 0), times);
		}
		if (times == 0)
			return 0;
		if (OreDictionary.itemMatches(new ItemStack(Blocks.LOG),
				new ItemStack(world.getBlockState(from.add(1, 0, 0)).getBlock()), false)) {
			world.destroyBlock(from.add(1, 0, 0), false);
			world.setBlockState(from.add(1, 0, 0), Parachronology.petrifiedWood.getDefaultState());
			world.markChunkDirty(from, null);
			times--;
			times = spread(world, from.add(1, 0, 0), times);
		}
		if (times == 0)
			return 0;
		if (OreDictionary.itemMatches(new ItemStack(Blocks.LOG),
				new ItemStack(world.getBlockState(from.add(0, 0, -1)).getBlock()), false)) {
			world.destroyBlock(from.add(0, 0, -1), false);
			world.setBlockState(from.add(0, 0, -1), Parachronology.petrifiedWood.getDefaultState());
			world.markChunkDirty(from, null);
			times--;
			times = spread(world, from.add(0, 0, -1), times);
		}
		if (times == 0)
			return 0;
		if (OreDictionary.itemMatches(new ItemStack(Blocks.LOG),
				new ItemStack(world.getBlockState(from.add(0, 0, 1)).getBlock()), false)) {
			world.destroyBlock(from.add(0, 0, 1), false);
			world.setBlockState(from.add(0, 0, 1), Parachronology.petrifiedWood.getDefaultState());
			world.markChunkDirty(from, null);
			times--;
			times = spread(world, from.add(0, 0, 1), times);
		}
		return times;

	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand,
			EnumFacing facing, float hitX, float hitY, float hitZ) {
		Block block = worldIn.getBlockState(pos).getBlock();
		Random rand = new Random();
		ItemStack stack = player.getHeldItem(hand);

		int amount = 2;

		return transformUse(player, worldIn, stack, pos, amount);
	}

	private EnumActionResult transformUse(EntityPlayer player, World world, ItemStack stack, BlockPos targ,
			int amount) {
		Random r = new Random();
		Block block = world.getBlockState(targ).getBlock();

		if (OreDictionary.itemMatches(new ItemStack(Blocks.LOG), new ItemStack(block), false)) {
			world.destroyBlock(targ, false);
			world.setBlockState(targ, Parachronology.petrifiedWood.getDefaultState());
			world.markChunkDirty(targ, null);
			stack.shrink(1);
			spread(world, targ, amount);
			return EnumActionResult.SUCCESS;
		}
		return EnumActionResult.FAIL;
	}
}
