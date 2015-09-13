package com.darva.parachronology.items;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Facing;
import net.minecraft.world.World;

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
        this.setTextureName("parachronology:stolenmoment");
        GameRegistry.registerItem(this, "capturedmoment");
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean unknown) {
        if (stack.stackTagCompound != null)
            list.add("Contains a " + stack.stackTagCompound.getString("captured"));
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float p_77648_8_, float p_77648_9_, float p_77648_10_) {
        if (world.isRemote)
            return true;
        Random r = new Random();
        Block block = world.getBlock(x, y, z);
        x += Facing.offsetsXForSide[side];
        y += Facing.offsetsYForSide[side];
        z += Facing.offsetsZForSide[side];
        double d0 = 0.0D;

        Entity newEntity = EntityList.createEntityByID(stack.stackTagCompound.getInteger("id"), world);
        newEntity.readFromNBT(stack.stackTagCompound.getCompoundTag("mob"));
        newEntity.setPosition(x + .5, y, z + .5);
        world.spawnEntityInWorld(newEntity);
        stack.stackSize--;
        return true;

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
        compound.setString("captured", entity.getCommandSenderName());
        compound.setTag("mob", targMob);

        compound.setInteger("id", EntityList.getEntityID(entity));

        stack.stackTagCompound = compound;

        entity.setDead();

        return true;
    }
}
