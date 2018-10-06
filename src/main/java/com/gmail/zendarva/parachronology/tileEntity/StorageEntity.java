package com.gmail.zendarva.parachronology.tileEntity;

import com.gmail.zendarva.parachronology.Parachronology;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

/**
 * Created by James on 7/30/2017.
 */
public class StorageEntity extends TileEntity {


    public static final int SIZE = 27;
    private String ownerName;
    private int ticksRemaining;
    private ForgeChunkManager.Ticket ticket;

    ItemStackHandler inventory = new ItemStackHandler(SIZE){
        @Override
        protected void onContentsChanged(int slot) {
            StorageEntity.this.markDirty();
        }
    };

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {

        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY){
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(inventory);
        }

        return super.getCapability(capability, facing);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY){
            return true;
        }
        return super.hasCapability(capability, facing);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        if (compound.hasKey("items")) {
            inventory.deserializeNBT((NBTTagCompound) compound.getTag("items"));
        }
        ownerName=compound.getString("owner");

    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setTag("items", inventory.serializeNBT());
        compound.setString("owner",ownerName);
        return compound;
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate) {
        return false;
    }


    public void setChunkloading(EntityPlayer placer){
        ownerName = placer.getName();
        ticket = ForgeChunkManager.requestPlayerTicket(Parachronology.instance,ownerName,this.world, ForgeChunkManager.Type.NORMAL);
        ForgeChunkManager.forceChunk(ticket,this.getWorld().getChunkFromBlockCoords(this.getPos()).getPos());
        ticksRemaining+=200;
    }

    public void tickChunkloading(){
        EntityPlayer player = this.world.getPlayerEntityByName(ownerName);
        if (ticket != null){
            if (player == null){
                ticksRemaining--;
                if (ticksRemaining <=0){
                    ForgeChunkManager.releaseTicket(ticket);
                    ticket=null;
                    return;
                }
            }
        }
        if (ticket == null && player !=null){
            setChunkloading(player);
        }
    }
}
