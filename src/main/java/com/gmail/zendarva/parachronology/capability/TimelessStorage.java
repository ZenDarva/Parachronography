package com.gmail.zendarva.parachronology.capability;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

/**
 * Created by James on 7/30/2017.
 */
public class TimelessStorage implements Capability.IStorage<ITimeless> {
    @Nullable
    @Override
    public NBTBase writeNBT(Capability<ITimeless> capability, ITimeless instance, EnumFacing side) {
        NBTTagCompound compound = new NBTTagCompound();
        compound.setInteger("curEnergy", instance.getCurrentEnergy());
        compound.setInteger("maxEnergy",instance.getMaxEnergy());
        compound.setInteger("tier",instance.getTier());
        writeBlockPos(compound,"target",instance.getTarget());
        compound.setInteger("worldId",instance.getWorldId());
        compound.setInteger("slot",instance.getSelectedSlot());
        return compound;
    }

    @Override
    public void readNBT(Capability<ITimeless> capability, ITimeless instance, EnumFacing side, NBTBase nbt) {
        NBTTagCompound compound = (NBTTagCompound) nbt;
        instance.setMaxEnergy(compound.getInteger("maxEnergy"));
        instance.setEnergy(compound.getInteger("curEnergy"));
        instance.setTier(compound.getInteger("tier"));
        instance.setTarget(getBlockPos(compound,"target"));
        instance.setWorldId(compound.getInteger("worldId"));
        instance.setSelectedSlot(compound.getInteger("slot"));
    }


    private void writeBlockPos(NBTTagCompound tag, String name, BlockPos pos) {
        if (pos == null)
            return;
        NBTTagCompound posTag = new NBTTagCompound();
        posTag.setInteger("x", pos.getX());
        posTag.setInteger("y", pos.getY());
        posTag.setInteger("z", pos.getZ());
        tag.setTag(name, posTag);
    }

    private BlockPos getBlockPos(NBTTagCompound tag, String name) {
        NBTTagCompound posTag = tag.getCompoundTag(name);
        if ((posTag == null) || (posTag.hasNoTags()))
            return null;
        BlockPos pos = new BlockPos(posTag.getInteger("x"), posTag.getInteger("y"), posTag.getInteger("z"));
        return pos;
    }
}
