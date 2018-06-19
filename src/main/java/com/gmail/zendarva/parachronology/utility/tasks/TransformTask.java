package com.gmail.zendarva.parachronology.utility.tasks;

import java.lang.reflect.Field;
import java.util.Random;


import com.gmail.zendarva.parachronology.Configuration.domain.BlockReference;
import net.minecraft.block.BlockMobSpawner;
import net.minecraft.tileentity.MobSpawnerBaseLogic;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Created by James on 9/19/2015.
 */
public class TransformTask implements Runnable {
	private String[] spawnableList = { "Enderman", "Zombie", "Skeleton", "Spider", "Blaze" };
	private World world;
	BlockPos target;
	BlockReference block;

	@Override
	public void run() {
		Random r = new Random();

		world.destroyBlock(target, false);
		block.setBlockInWorld(world,target);
		world.markChunkDirty(target, null);
	}

	public TransformTask(World world, int x, int y, int z, BlockReference block) {
		this.world = world;
		target = new BlockPos(x, y, z);
		this.block = block;
	}
}
