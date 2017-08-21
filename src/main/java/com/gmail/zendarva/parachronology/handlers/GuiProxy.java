package com.gmail.zendarva.parachronology.handlers;

import com.gmail.zendarva.parachronology.container.StorageContainer;
import com.gmail.zendarva.parachronology.entity.StorageEntity;
import com.gmail.zendarva.parachronology.gui.StorageGui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import javax.annotation.Nullable;

/**
 * Created by James on 7/30/2017.
 */
public class GuiProxy implements IGuiHandler {
    @Nullable
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof StorageEntity) {
            return new StorageContainer(player.inventory, (StorageEntity) te);
        }
        return null;
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof StorageEntity) {
            StorageEntity containerTileEntity = (StorageEntity) te;
            return new StorageGui(containerTileEntity, new StorageContainer(player.inventory, containerTileEntity));
        }
        return null;
    }
}
