package com.gmail.zendarva.parachronology.utility;

import com.gmail.zendarva.parachronology.capability.ITimeless;
import com.gmail.zendarva.parachronology.capability.TimelessProvider;
import com.gmail.zendarva.parachronology.entity.StorageEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

/**
 * Created by James on 8/10/2017.
 */
public class TimelessUtility {

    public static StorageEntity getTargetStorage(ItemStack stack) {
        if (stack.isEmpty())
            return null; //you're an asshole harry.
        ITimeless timeless = stack.getCapability(TimelessProvider.timeless,null);
        if (timeless == null)
            return null;//Still kind of a prick.
        World world = DimensionManager.getWorld(timeless.getWorldId());

        BlockPos pos = timeless.getTarget();

        if (pos == null)
            return null; //Not linked yet.
        if (world.isBlockLoaded(pos))
            return getStorageAtBlock(world,pos);
        return null;
   }


    public static ItemStack getSelectedStorageSlotStack(World world, ItemStack stack)
    {
        StorageEntity entity = getTargetStorage(stack);
        if (entity == null)
            return ItemStack.EMPTY;
        int slot = getSelectedStorageSlot(stack);
        IItemHandler items = entity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,null);
        return items.getStackInSlot(slot);
    }

    public static int getSelectedStorageSlot(ItemStack stack)
    {
        StorageEntity entity = getTargetStorage(stack);
        if (entity == null)
            return -1;
        ITimeless timeless = getTimeless(stack);
        if (timeless == null)
            return -1;
        return timeless.getSelectedSlot();


    }


    public static StorageEntity getStorageAtBlock(World world, BlockPos pos)
    {
        TileEntity entity=world.getTileEntity(pos);

        if (!(entity instanceof StorageEntity)){
            return null;
        }
        return (StorageEntity) entity;
    }

    public static ItemStack getSelectedStorageSlotStack(World world, BlockPos pos, int slot)
    {
        StorageEntity entity = getStorageAtBlock(world,pos);
        if (entity == null)
            return null;
        IItemHandler items = entity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,null);
        return items.getStackInSlot(slot);
    }

    public static ITimeless getTimeless(ItemStack stack) {
        return stack.getCapability(TimelessProvider.timeless,null);
    }
}
