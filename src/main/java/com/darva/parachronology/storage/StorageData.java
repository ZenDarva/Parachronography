package com.darva.parachronology.storage;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;

/**
 * Created by James on 1/23/2016.
 */
public class StorageData {
    private ItemStack[] myStorage;
    private int[] count;
    private int ID;
    private int slot = 0;

    public StorageData(Integer ID)
    {
        myStorage = new ItemStack[9];
        count = new int[9];
        this.ID = ID;
    }

    public StorageData(NBTTagCompound from)
    {
        myStorage = new ItemStack[9];
        count = new int[9];

        NBTTagList items = from.getTagList("items", Constants.NBT.TAG_COMPOUND);

        for (int i = 0; i<items.tagCount(); i++)
        {
            NBTTagCompound item = items.getCompoundTagAt(i);
            myStorage[i] = ItemStack.loadItemStackFromNBT(item);
        }
        count = from.getIntArray("count");
        ID = from.getInteger("id");
        slot = from.getInteger("slot");
    }

    public void writeToNBT(NBTTagCompound to)
    {
        to.setInteger("id", ID);
        to.setIntArray("count",count);
        to.setInteger("slot",slot);

        NBTTagList list = new NBTTagList();

        for(int i = 0; i< 9; i++)
        {
            NBTTagCompound item = new NBTTagCompound();
            myStorage[i].writeToNBT(item);
            list.appendTag(item);
        }
        to.setTag("items",list);
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getSlot(){
        return slot;
    }
    public void setSlot(int slot)
    {
        this.slot = slot;
    }

}
