//package com.darva.parachronology.entity;
//
//import com.darva.parachronology.storage.PlayerStorage;
//import com.darva.parachronology.storage.StorageData;
//import net.minecraft.nbt.NBTTagCompound;
//import net.minecraft.tileentity.TileEntity;
//import net.minecraft.world.World;
//
///**
// * Created by James on 1/23/2016.
// */
//public class StorageEntity extends TileEntity {
//    StorageData storageData;
//    PlayerStorage storage;
//    int ID;
//
//    public StorageEntity(World world) {
//        storage = PlayerStorage.get(world);
//    }
//
//
//    @Override
//    public void writeToNBT(NBTTagCompound compound) {
//        super.writeToNBT(compound);
//        compound.setInteger("id", ID);
//    }
//
//    @Override
//    public void readFromNBT(NBTTagCompound compound) {
//        super.readFromNBT(compound);
//        ID = compound.getInteger("id");
//        storageData = storage.getByID(ID);
//    }
//}
