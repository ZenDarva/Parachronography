package com.darva.parachronology.generation;

import net.minecraft.util.BlockPos;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.IChunkProvider;

/**
 * Created by James on 9/6/2015.
 */
public class VoidWorld extends WorldProvider {
    private long seed;
    @Override
    public String getDimensionName() {
        return "void";
    }

    @Override
    public String getInternalNameSuffix() {
        return null;
    }

    @Override
    public boolean canCoordinateBeSpawn(int x, int z) {
        return true;
    }

    @Override
    public BlockPos getSpawnCoordinate() {
        return new BlockPos(1,64,1);
    }

    @Override
    public IChunkProvider createChunkGenerator() {
        return new VoidChunk(worldObj, worldObj.getSeed(), false, "", worldObj);
    }

    @Override
    public BlockPos getRandomizedSpawnPoint() {
        BlockPos spawn = new BlockPos(2,64,2);
        spawn = worldObj.getTopSolidOrLiquidBlock(spawn);
        return spawn;
    }

    @Override
    public long getSeed() {
        return this.seed;
    }

    public VoidWorld() {
        super();
    }
    public VoidWorld(long seed) {
        this.seed = seed;
    }

    @Override
    protected void registerWorldChunkManager()
    {
            worldChunkMgr = new VoidWorldChunkManager(worldObj);
    }


}
