package com.gmail.zendarva.parachronology.capability;

import net.minecraft.util.math.BlockPos;

/**
 * Created by James on 7/30/2017.
 */
public class Timeless implements ITimeless {

    private int curEnergy =900;
    private int maxEnergy=900;
    private int tier=0;
    private BlockPos target;
    private int worldId;
    private int slot=0;
    @Override
    public int getCurrentEnergy() {
        return curEnergy;
    }

    @Override
    public void addEnergy(int amount) {
        curEnergy+=amount;
    }

    @Override
    public void setEnergy(int amount) {
        this.curEnergy=amount;
    }

    @Override
    public int getMaxEnergy() {
        return maxEnergy;
    }

    @Override
    public void setMaxEnergy(int amount) {
        maxEnergy=amount;
    }

    @Override
    public int getTier() {
        return tier;
    }

    @Override
    public void setTier(int tier) {
        this.tier=tier;
    }

    @Override
    public BlockPos getTarget() {
        return target;
    }

    @Override
    public void setTarget(BlockPos target) {
        this.target = target;
    }

    @Override
    public int getWorldId() {
        return worldId;
    }

    @Override
    public void setWorldId(int id) {
        worldId = id;
    }

    @Override
    public int getSelectedSlot() {
        return slot;
    }

    @Override
    public void setSelectedSlot(int slot) {
        this.slot = slot;
    }
}
