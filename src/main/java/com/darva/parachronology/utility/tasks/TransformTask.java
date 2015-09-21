package com.darva.parachronology.utility.tasks;

import com.darva.parachronology.BlockReference;
import net.minecraft.block.BlockMobSpawner;
import net.minecraft.tileentity.MobSpawnerBaseLogic;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.world.World;

import java.lang.reflect.Field;
import java.util.Random;

/**
 * Created by James on 9/19/2015.
 */
public class TransformTask implements Runnable {
    private String[] spawnableList = {"Enderman", "Zombie", "Skeleton", "Spider", "Blaze"};
    private World world;
    int x,y,z;
    BlockReference block;
    @Override
    public void run() {
        Random r = new Random();
        world.func_147480_a(x, y, z, false);

        block.placeInWorld(world, x, y, z);

        if (block.targBlock instanceof BlockMobSpawner) {
            for (Field f : TileEntityMobSpawner.class.getDeclaredFields()) {
                if (f.getGenericType() == MobSpawnerBaseLogic.class) {
                    world.markBlockForUpdate(x, y, z);
                    f.setAccessible(true);

                    TileEntityMobSpawner spawner = (TileEntityMobSpawner) world.getTileEntity(x, y, z);
                    try {
                        MobSpawnerBaseLogic logic = (MobSpawnerBaseLogic) f.get(spawner);
                        logic.setEntityName(spawnableList[r.nextInt(spawnableList.length)]);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
        world.markBlockForUpdate(x, y, z);
    }

    public TransformTask(World world, int x, int y, int z, BlockReference block)
    {
        this.world =world;
        this.x=x;
        this.y=y;
        this.z=z;
        this.block=block;
    }
}
