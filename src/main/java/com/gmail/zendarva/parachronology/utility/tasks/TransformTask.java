package com.gmail.zendarva.parachronology.utility.tasks;

import com.gmail.zendarva.parachronology.BlockReference;
import net.minecraft.block.BlockMobSpawner;
import net.minecraft.tileentity.MobSpawnerBaseLogic;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.lang.reflect.Field;
import java.util.Random;

/**
 * Created by James on 9/19/2015.
 */
public class TransformTask implements Runnable {
    private String[] spawnableList = {"Enderman", "Zombie", "Skeleton", "Spider", "Blaze"};
    private World world;
    BlockPos target;
    BlockReference block;
    @Override
    public void run() {
        Random r = new Random();

        world.destroyBlock(target, false);

        block.placeInWorld(world, target.getX(), target.getY(), target.getZ());


        if (block.targBlock instanceof BlockMobSpawner) {
            for (Field f : TileEntityMobSpawner.class.getDeclaredFields()) {
                if (f.getGenericType() == MobSpawnerBaseLogic.class) {
                    world.markChunkDirty(target, null);
                    f.setAccessible(true);

                    TileEntityMobSpawner spawner = (TileEntityMobSpawner) world.getTileEntity(target);
                    try {
                        MobSpawnerBaseLogic logic = (MobSpawnerBaseLogic) f.get(spawner);
                        //logic.setEntityName(spawnableList[r.nextInt(spawnableList.length)]);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
        world.markChunkDirty(target,null);
    }

    public TransformTask(World world, int x, int y, int z, BlockReference block)
    {
        this.world =world;
        target = new BlockPos(x,y,z);
        this.block=block;
    }
}
