package com.gmail.zendarva.parachronology.capability;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by James on 7/30/2017.
 */
public class TimelessProvider implements ICapabilitySerializable<NBTBase> {

    @CapabilityInject(ITimeless.class)
    public static final Capability<ITimeless> timeless = null;

    private ITimeless instance = (ITimeless) timeless.getDefaultInstance();

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == timeless;
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        return (T)(capability == timeless ? timeless.cast(this.instance) : null);
    }

    @Override
    public NBTBase serializeNBT() {
        return timeless.getStorage().writeNBT(timeless,this.instance,null);
    }

    @Override
    public void deserializeNBT(NBTBase nbt) {
            timeless.getStorage().readNBT(timeless,instance,null,nbt);
    }
}
