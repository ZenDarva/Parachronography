package com.darva.parachronology.generation;

import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.WorldChunkManager;

import java.util.List;
import java.util.Random;

/**
 * Created by James on 9/10/2015.
 */
public class VoidWorldChunkManager extends WorldChunkManager {

    private World world;

    public VoidWorldChunkManager(World world) {
        super(world);
        this.world = world;
    }



    @Override
    public BlockPos findBiomePosition(int x, int z, int range, @SuppressWarnings("rawtypes") List biomes, Random rand) {
        BlockPos ret = super.findBiomePosition(x, z, range, biomes, rand);
        if (x == 0 && z == 0 && !world.getWorldInfo().isInitialized()) {
            if (ret == null) {
                ret = new BlockPos(1, 64, 1);
            }

            BlockPos target = new BlockPos(0,63,0);

            //world.setBlockState(0, 64, 0, Blocks.grass.getDefaultState());


            for (int y = 0; y < 2; y++) {
                for (int lx = 0; lx < 5; lx++) {
                    for (int lz = 0; lz < 5; lz++) {
                        if (y == 1)
                            world.setBlockState(target.add(lx, y, lz), Blocks.grass.getDefaultState());
                        else
                            world.setBlockState(target.add(lx,y,lz), Blocks.hardened_clay.getDefaultState());
                    }
                }
            }

            world.setSpawnPoint(target.add(2,2,2));
        }
        return ret;
    }
}
