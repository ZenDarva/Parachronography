package com.gmail.zendarva.parachronology.item;

import java.util.List;
import java.util.Random;

import com.gmail.zendarva.parachronology.Parachronology;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by James on 9/3/2015.
 */
public class CapturedMoment extends Item {

	public CapturedMoment() {
		String name = "capturedmoment";
		this.setMaxStackSize(1);
		this.setMaxDamage(0);
		this.setUnlocalizedName(Parachronology.MODID + "." + name);
		this.setRegistryName(name);
		this.setCreativeTab(Parachronology.TAB);

		GameRegistry.register(this);
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List<String> list, boolean unknown) {

		if (stack.getTagCompound() != null)
			list.add("Contains a " + stack.getTagCompound().getString("captured"));
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand,
			EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (worldIn.isRemote)
			return EnumActionResult.SUCCESS;
		ItemStack stack = player.getHeldItem(hand);
		Random r = new Random();
		Block block = worldIn.getBlockState(pos).getBlock();

		BlockPos targPos = new BlockPos(pos.getX() + facing.getFrontOffsetX(),
				pos.getY() + facing.getFrontOffsetY() + .5, pos.getZ() + facing.getFrontOffsetZ());
		double d0 = 0.0D;

		Entity newEntity = EntityList.createEntityByID(stack.getTagCompound().getInteger("id"), worldIn);
		newEntity.readFromNBT(stack.getTagCompound().getCompoundTag("mob"));
		newEntity.setPosition(targPos.getX() + .5, targPos.getY(), targPos.getZ() + .5);

		worldIn.spawnEntity(newEntity);
		stack.shrink(1);
		return EnumActionResult.SUCCESS;
	}

	@SideOnly(Side.CLIENT)
	public void registerModel() {
		ModelLoader.setCustomModelResourceLocation(this, 0,
				new ModelResourceLocation(this.getRegistryName(), "inventory"));
	}

	public boolean setStored(ItemStack stack, Entity entity, EntityPlayer player) {
		if (entity instanceof EntityDragon || entity instanceof EntityWither || entity instanceof EntityPlayer) {
			return false;
		}
		NBTTagCompound compound = new NBTTagCompound();
		NBTTagCompound targMob = new NBTTagCompound();
		entity.writeToNBT(targMob);

		compound.setString("captured", entity.getDisplayName().getFormattedText());
		compound.setTag("mob", targMob);

		compound.setInteger("id", entity.getEntityId());

		stack.setTagCompound(compound);

		entity.setDead();

		return true;
	}
}
