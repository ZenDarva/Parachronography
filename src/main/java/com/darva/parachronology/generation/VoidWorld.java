package com.darva.parachronology.generation;

import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.IChunkProvider;

/**
 * Created by James on 9/6/2015.
 */
public class VoidWorld extends WorldProvider {
    @Override
    public String getDimensionName() {
        return "Overworld";
    }

    @Override
    public boolean canCoordinateBeSpawn(int x, int z) {
        return true;
    }

    @Override
    public ChunkCoordinates getRandomizedSpawnPoint() {
        return new ChunkCoordinates(1,64,1);
    }


    @Override
    public IChunkProvider createChunkGenerator()
    {
        return new VoidChunk(worldObj, worldObj.getSeed(), false);
    }
}
