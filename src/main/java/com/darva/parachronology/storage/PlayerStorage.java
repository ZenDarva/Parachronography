package com.darva.parachronology.storage;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import net.minecraftforge.common.IExtendedEntityProperties;
import net.minecraftforge.common.util.Constants;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by James on 1/23/2016.
 */
public class PlayerStorage extends WorldSavedData {

    private final static String ID = "parachronology:Storage";

    private Map<Integer, StorageData> Storage;


    private PlayerStorage() {
        super(ID);
        Storage = new HashMap<Integer, StorageData>();
    }

    public static PlayerStorage get(World world)
    {
        PlayerStorage data = (PlayerStorage)world.loadItemData(PlayerStorage.class, ID);
        if (data == null) {
            data = new PlayerStorage();
            world.setItemData(ID, data);
        }
        return data;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        NBTTagList tags = nbt.getTagList("storages", Constants.NBT.TAG_COMPOUND);

        for (int i = 0; i < tags.tagCount(); i++)
        {
            StorageData data = new StorageData(tags.getCompoundTagAt(i));
            Storage.put(data.getID(),data);
        }

    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        NBTTagList list = new NBTTagList();

        for (Integer i : Storage.keySet())
        {
            NBTTagCompound comp = new NBTTagCompound();

            Storage.get(i).writeToNBT(comp);
            list.appendTag(comp);
        }
        nbt.setTag("storage",list);

    }


    public StorageData getByID(Integer id) {
        if (Storage.containsKey(id))
        {
            return Storage.get(id);
        }
        StorageData data = new StorageData(id);
        Storage.put(id,data);
        return data;
    }
}
