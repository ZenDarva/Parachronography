package com.darva.parachronology.generation;

import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.chunk.IChunkProvider;

/**
 * Created by James on 9/7/2015.
 */
public class VoidWorldType extends WorldType {
    public VoidWorldType() {
        super("void");
    }

    @Override
    public WorldChunkManager getChunkManager(World world) {


        return new VoidWorldChunkManager(world);
    }

    @Override
    public IChunkProvider getChunkGenerator(World world, String generatorOptions) {
        return new VoidChunk(world, 0, true);
    }

    @Override
    public int getSpawnFuzz() {
        return 1;
    }
}
