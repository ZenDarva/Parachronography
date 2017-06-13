package com.gmail.zendarva.parachronology.item;

import java.util.ArrayList;
import java.util.Random;

import com.gmail.zendarva.parachronology.Parachronology;
import com.gmail.zendarva.parachronology.TransformListBuilder;
import com.gmail.zendarva.parachronology.Configuration.ConfigurationHolder;
import com.gmail.zendarva.parachronology.utility.BlockVector;
import com.gmail.zendarva.parachronology.utility.MultiBlockHelper;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

/**
 * Created by James on 8/23/2015.
 */
public class Moment extends Item {
	public Moment() {
		String name = "moment";
		this.setMaxStackSize(64);
		this.setRegistryName(name);
		this.setMaxDamage(0);
		this.setCreativeTab(Parachronology.TAB);
		this.setUnlocalizedName(Parachronology.MODID + "." + name);
		GameRegistry.register(this);
		
	}

	@SideOnly(Side.CLIENT)
	public void registerModel() {
		ModelLoader.setCustomModelResourceLocation(this, 0,
				new ModelResourceLocation("parachronology:simplemoment", "inventory"));
		ModelLoader.setCustomModelResourceLocation(this, 1,
				new ModelResourceLocation("parachronology:moment", "moment"));
		ModelLoader.setCustomModelResourceLocation(this, 2,
				new ModelResourceLocation("parachronology:complexmoment", "complexmoment"));
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand,
			EnumFacing facing, float hitX, float hitY, float hitZ) {
		ItemStack stack = player.getHeldItem(hand);
		int amount = 7;
		switch (stack.getItemDamage()) {

		case 1:
			amount = 15;
			break;
		case 2:
			amount = 31;
		}
		return transformUse(player, worldIn, stack, pos, amount);
	}

	@Override
    public String getUnlocalizedName(ItemStack stack)
    {
        return getUnlocalizedName() + "." + stack.getMetadata();
    }

	@Override
	public boolean getHasSubtypes() {
		return true;
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

		if (block == Blocks.END_STONE && stack.getItemDamage() == 2
				&& ConfigurationHolder.getInstance().isGenerateEndPortal()) {
			BlockVector corner = MultiBlockHelper.findSouthWestCorner(world, targ.getX(), targ.getY(), targ.getZ());
			if (corner != null) {
				if (MultiBlockHelper.checkMultiblock(corner)) {
					corner = corner.East().North();
					corner.setBlock(Blocks.END_PORTAL);
					corner.North().setBlock(Blocks.END_PORTAL);
					corner.East().setBlock(Blocks.END_PORTAL);
					corner.East().North().setBlock(Blocks.END_PORTAL);
				}
			}
			stack.shrink(1);
			return EnumActionResult.SUCCESS;
		}
		return EnumActionResult.FAIL;
	}

	@Override
	public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase entity,
			EnumHand hand) {

		Random r = player.world.rand;
		if (stack.getItemDamage() < 1)
			return false;
		if (player.isSneaking()) {
			if (player.world.isRemote) {
				return true;
			}

			if (!entity.isNonBoss()) {
				return false;
			}
			ItemStack captured = new ItemStack(Parachronology.capturedMoment, 1);

			if (Parachronology.capturedMoment.setStored(captured, entity, player)) {
				stack.shrink(1);
				player.inventory.addItemStackToInventory(captured);
				return true;
			}
		}
		ArrayList<String> transforms = TransformListBuilder.Instance().getTransforms(stack.getItemDamage(),
				EntityList.getEntityString(entity));
		if (transforms.size() == 0)
			return false;
		for (int i = 0; i < 25; i++) {
			entity.world.spawnParticle(EnumParticleTypes.PORTAL, entity.posX - .5 + r.nextFloat(), entity.posY,
					entity.posZ - .5 + +r.nextFloat(), r.nextFloat() * .5, r.nextFloat() * .5, r.nextFloat() * .5);
		}
		if (player.world.isRemote)
			return true;
		stack.shrink(1);

		//EntityLiving newEntity = (EntityLiving) EntityList.createEntityByName(transforms.get(r.nextInt(transforms.size())), player.getEntityWorld());
		//newEntity.setPosition(entity.posX, entity.posY, entity.posZ);
		//newEntity.setRotationYawHead(entity.getRotationYawHead());
		//player.world.spawnEntity(newEntity);

		entity.setDead();

		return false;

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
	public void getSubItems(Item itemIn, CreativeTabs tab, NonNullList<ItemStack> subItems) {
		subItems.add(new ItemStack(this, 1, 0));
		subItems.add(new ItemStack(this, 1, 1));
		subItems.add(new ItemStack(this, 1, 2));
	}
}
