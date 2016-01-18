package com.darva.parachronology.generation;

import net.minecraft.block.Block;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderGenerate;

/**
 * Created by James on 9/6/2015.
 */
public class VoidChunk extends ChunkProviderGenerate {
    private World world;

    @Override
    public void populate(IChunkProvider p_73153_1_, int x, int z) {
        if (x == 0 && z == 0 && !world.getWorldInfo().isInitialized()) {
            world.setSpawnPoint(new BlockPos(2,64,2));
        }
    }


    @Override
    public void setBlocksInChunk(int p_180518_1_, int p_180518_2_, ChunkPrimer p_180518_3_) {

    }

    @Override
    public void replaceBlocksForBiome(int p_180517_1_, int p_180517_2_, ChunkPrimer p_180517_3_, BiomeGenBase[] p_180517_4_) {

    }



    public VoidChunk(World worldIn, long p_i45636_2_, boolean p_i45636_4_, String p_i45636_5_, World world) {
        super(worldIn, p_i45636_2_, p_i45636_4_, p_i45636_5_);
        this.world = world;
    }
}
