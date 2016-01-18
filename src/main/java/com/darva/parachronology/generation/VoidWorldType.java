package com.darva.parachronology.generation;

import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import java.util.Hashtable;

/**
 * Created by James on 9/7/2015.
 */
public class VoidWorldType extends WorldType {
    public VoidWorldType() {
        super("void");
    }
    Hashtable<Integer, Class<? extends WorldProvider>> providers = ReflectionHelper.getPrivateValue(DimensionManager.class, null, "providers");
    @Override
    public WorldChunkManager getChunkManager(World world) {
       return new VoidWorldChunkManager(world);
    }

    @Override
    public IChunkProvider getChunkGenerator(World world, String generatorOptions) {


        return new VoidChunk(world,world.getSeed(),false,"",world);
    }

    @Override
    public WorldType getWorldTypeForGeneratorVersion(int version) {
        if (providers.get(0) != VoidWorld.class)
            providers.put(0, VoidWorld.class);
        return this;
    }

    @Override
    public int getSpawnFuzz() {
        return 1;
    }

    @Override
    public void onGUICreateWorldPress() {
        if (providers.get(0) != VoidWorld.class)
            providers.put(0, VoidWorld.class);
        super.onGUICreateWorldPress();
    }
}
