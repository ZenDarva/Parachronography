package com.darva.parachronology.generation;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderGenerate;

/**
 * Created by James on 9/6/2015.
 */
public class VoidChunk extends ChunkProviderGenerate {
    private World world;

    @Override
    public void populate(IChunkProvider p_73153_1_, int x, int z) {
        if (x == 0 && z == 0 && !world.getWorldInfo().isInitialized())
        {
            world.setSpawnLocation(0,65,0);
        }
    }

    @Override
    public void func_147424_a(int p_147424_1_, int p_147424_2_, Block[] p_147424_3_) {

    }

    @Override
    public void replaceBlocksForBiome(int p_147422_1_, int p_147422_2_, Block[] p_147422_3_, byte[] p_147422_4_, BiomeGenBase[] p_147422_5_) {

    }

    public VoidChunk(World p_i2006_1_, long p_i2006_2_, boolean p_i2006_4_) {
        super(p_i2006_1_, p_i2006_2_, p_i2006_4_);
        world = p_i2006_1_;
    }
}
