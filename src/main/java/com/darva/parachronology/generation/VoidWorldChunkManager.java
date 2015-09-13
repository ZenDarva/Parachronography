package com.darva.parachronology.generation;

import net.minecraft.init.Blocks;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.biome.WorldChunkManager;

import java.util.List;
import java.util.Random;

/**
 * Created by James on 9/10/2015.
 */
public class VoidWorldChunkManager extends WorldChunkManager{

    private World world;

    public VoidWorldChunkManager(World world)
    {
        super(world);
        this.world = world;
    }

    @Override
    public ChunkPosition findBiomePosition(int x, int z, int range, @SuppressWarnings("rawtypes") List biomes, Random rand)
    {
        ChunkPosition ret = super.findBiomePosition(x, z, range, biomes, rand);
        if (x == 0 && z == 0 && !world.getWorldInfo().isInitialized())
        {
            if (ret == null)
            {
                ret = new ChunkPosition(1,64,1);
            }

            world.setBlock(0, 64, 0, Blocks.grass);

            for (int y = 63; y<65; y++)
            {
                for (int lx = 0; lx<5; lx++)
                {
                    for (int lz = 0; lz<5; lz++)
                    {
                        if (y == 64)
                            world.setBlock(lx,y, lz, Blocks.grass);
                        else
                            world.setBlock(lx,y, lz, Blocks.hardened_clay);
                    }
                }
            }

            world.setSpawnLocation(1,64,1);
        }
        return ret;
    }
}
