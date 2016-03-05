package com.darva.parachronology.entity;

import com.darva.parachronology.BlockReference;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

/**
 * Created by James on 9/12/2015.
 */
public class DisplacerEntity extends TileEntity {
    public BlockReference getAgainst() {
        return against;
    }

    public void setAgainst(BlockReference against) {
        this.against = against;
    }

    public BlockReference getTowards() {
        return towards;
    }

    public void setTowards(BlockReference towards) {
        this.towards = towards;
    }

    private BlockReference against;
    private BlockReference towards;

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        if (against !=null)
            compound.setString("against", against.toString());
        if (towards !=null)
            compound.setString("towards",towards.toString());
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        if (compound.hasKey("against"))
        {
            against = BlockReference.readBlockFromString(compound.getString("against"));
        }
        if (compound.hasKey("towards"))
        {
            towards = BlockReference.readBlockFromString(compound.getString("towards"));
        }
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound tag = new NBTTagCompound();
        this.writeToNBT(tag);
        return new S35PacketUpdateTileEntity(getPos(), 1, tag);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet) {
        NBTTagCompound tag = packet.getNbtCompound();
        this.readFromNBT(tag);
    }
}
