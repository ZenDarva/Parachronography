package com.gmail.zendarva.parachronology.item;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.gmail.zendarva.parachronology.Configuration.ConfigManager;
import com.gmail.zendarva.parachronology.Configuration.domain.BlockReference;
import com.gmail.zendarva.parachronology.Parachronology;
import com.gmail.zendarva.parachronology.Configuration.ConfigurationHolder;
import com.gmail.zendarva.parachronology.utility.BlockVector;
import com.gmail.zendarva.parachronology.utility.MultiBlockHelper;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
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
		//GameRegistry.register(this);
		
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
		int amount = 8;
		switch (stack.getItemDamage()) {

			case 1:
				amount = 15;
				break;
			case 2:
				amount = 31;
		}
		BlockReference target = BlockReference.fromBlockWorld(pos, worldIn);
		List<BlockReference> targets = ConfigManager.getDislocates(target);
		if (!targets.isEmpty()) {
			stack.shrink(1);
			return transformUse(worldIn, pos, amount, targets);
		}
		return EnumActionResult.FAIL;
	}
	public static EnumActionResult transformUse(World world, BlockPos target, int amount, List<BlockReference> possibleResults){
		Random r = new Random();
		possibleResults.get(r.nextInt(possibleResults.size())).setBlockInWorld(world,target);
		spread(world,target,amount--);
		return EnumActionResult.SUCCESS;
	}

	private static int spread(World world, BlockPos target, int amount){
		Random r = new Random(System.currentTimeMillis());

		for (EnumFacing face : EnumFacing.values()){
			if (amount == 0)
				return 0;
			BlockReference ref = BlockReference.fromBlockWorld(target.offset(face), world);
			List<BlockReference> targets = ConfigManager.getDislocates(ref);
			if (!targets.isEmpty()){
				targets.get(r.nextInt(targets.size())).setBlockInWorld(world,target.offset(face));
				amount--;
				amount = spread(world,target.offset(face),amount);
			}
		}
		return amount;
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


		EntityLiving result = ConfigManager.getTransformResult(entity,stack.getItemDamage());
		if (result == null)
				return false;
		for (int i = 0; i < 25; i++) {
			entity.world.spawnParticle(EnumParticleTypes.PORTAL, entity.posX - .5 + r.nextFloat(), entity.posY,
					entity.posZ - .5 + +r.nextFloat(), r.nextFloat() * .5, r.nextFloat() * .5, r.nextFloat() * .5);
		}
		if (player.world.isRemote)
			return true;
		stack.shrink(1);
		result.setPosition(entity.posX, entity.posY, entity.posZ);
		result.setRotationYawHead(entity.getRotationYawHead());
		player.world.spawnEntity(result);

		entity.setDead();

		return false;

	}



	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if (tab == Parachronology.TAB) {
			items.add(new ItemStack(this, 1, 0));
			items.add(new ItemStack(this, 1, 1));
			items.add(new ItemStack(this, 1, 2));
		}

	}
}
