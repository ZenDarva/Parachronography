package com.gmail.zendarva.parachronology.entity;

import javax.annotation.Nullable;

import com.gmail.zendarva.parachronology.BlockReference;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
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
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		if (against != null)
			compound.setString("against", against.toString());
		if (towards != null)
			compound.setString("towards", towards.toString());
		return compound;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		if (compound.hasKey("against")) {
			against = BlockReference.readBlockFromString(compound.getString("against"));
		}
		if (compound.hasKey("towards")) {
			towards = BlockReference.readBlockFromString(compound.getString("towards"));
		}
	}

	@Nullable
	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		NBTTagCompound tag = new NBTTagCompound();
		this.writeToNBT(tag);
		return new SPacketUpdateTileEntity(getPos(), 1, tag);
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		NBTTagCompound tag = pkt.getNbtCompound();
		this.readFromNBT(tag);
	}

}
