package com.gmail.zendarva.parachronology.capability;

import net.minecraft.util.math.BlockPos;

/**
 * Created by James on 7/30/2017.
 */
public interface ITimeless {

    public int getCurrentEnergy();
    public void addEnergy(int amount);
    public void setEnergy(int amount);
    public int getMaxEnergy();
    public void setMaxEnergy(int amount);
    public int getTier();
    public void setTier(int tier);
    public BlockPos getTarget();
    public void setTarget(BlockPos target);
    public int getWorldId();
    public void setWorldId(int id);
    public int getSelectedSlot();
    public void setSelectedSlot(int slot);
    public int getExtraData();
    public void setExtraData(int data);

}
