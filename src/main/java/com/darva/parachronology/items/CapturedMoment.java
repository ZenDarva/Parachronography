package com.darva.parachronology.items;

import com.darva.parachronology.Parachronology;
import net.minecraft.block.Block;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.Random;

/**
 * Created by James on 9/3/2015.
 */
public class CapturedMoment extends Item {

    public CapturedMoment() {
        this.setMaxStackSize(1);
        this.setMaxDamage(0);
        this.setUnlocalizedName("parachronology:capturedmoment");
        this.setRegistryName("capturedmoment");

        GameRegistry.registerItem(this, "capturedmoment");
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean unknown) {

        if (stack.getTagCompound() != null)
            list.add("Contains a " + stack.getTagCompound().getString("captured"));
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (worldIn.isRemote)
            return true;
        Random r = new Random();
        Block block = worldIn.getBlockState(pos).getBlock();
        BlockPos targPos = new BlockPos(pos.getX()+ side.getFrontOffsetX(), pos.getY() + side.getFrontOffsetY(), pos.getZ() + side.getFrontOffsetZ());
        double d0 = 0.0D;

        Entity newEntity = EntityList.createEntityByID(stack.getTagCompound().getInteger("id"), worldIn);
        newEntity.readFromNBT(stack.getTagCompound().getCompoundTag("mob"));
        newEntity.setPosition(pos.getX() + .5, pos.getY(), pos.getZ() + .5);
        worldIn.spawnEntityInWorld(newEntity);
        stack.stackSize--;
        return true;
    }

    @SideOnly(Side.CLIENT)
    public void registerModel() {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(this.getRegistryName(), "inventory"));
    }


    public boolean setStored(ItemStack stack, EntityLivingBase entity, EntityPlayer player) {
        if (entity instanceof EntityDragon ||
                entity instanceof EntityWither ||
                entity instanceof EntityPlayer) {
            return false;
        }
        NBTTagCompound compound = new NBTTagCompound();
        NBTTagCompound targMob = new NBTTagCompound();
        entity.writeEntityToNBT(targMob);
        compound.setString("captured", entity.getDisplayName().getFormattedText());
        compound.setTag("mob", targMob);

        compound.setInteger("id", EntityList.getEntityID(entity));

        stack.setTagCompound(compound);

        entity.setDead();

        return true;
    }
}
