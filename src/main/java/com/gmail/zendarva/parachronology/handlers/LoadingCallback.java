package com.gmail.zendarva.parachronology.handlers;

import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager;

import java.util.List;

/**
 * Created by James on 8/21/2017.
 */
public class LoadingCallback implements ForgeChunkManager.LoadingCallback {
    @Override
    public void ticketsLoaded(List<ForgeChunkManager.Ticket> list, World world) {
        for (ForgeChunkManager.Ticket ticket : list){
            ForgeChunkManager.releaseTicket(ticket);
        }
    }
}
