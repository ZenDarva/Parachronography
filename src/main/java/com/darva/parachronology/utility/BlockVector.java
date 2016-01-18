package com.darva.parachronology.utility;

import net.minecraft.block.Block;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

/**
 * Created by James on 9/13/2015.
 */
public class BlockVector {
    int x;
    int y;
    int z;
    World world;

    public BlockVector(World world, int X, int Y, int Z) {
        x = X;
        y = Y;
        z = Z;
        this.world = world;
    }

    public BlockVector North() {
        return new BlockVector(world, x, y, z - 1);
    }

    public BlockVector South() {
        return new BlockVector(world, x, y, z + 1);
    }

    public BlockVector East() {
        return new BlockVector(world, x + 1, y, z);
    }

    public BlockVector West() {
        return new BlockVector(world, x - 1, y, z);
    }

    public BlockVector Up() {
        return new BlockVector(world, x, y + 1, z);
    }

    public BlockVector Down() {
        return new BlockVector(world, x, y - 1, z);
    }

    public Block block() {
        return world.getBlockState(new BlockPos(x,y,z)).getBlock();
    }

    public void setBlock(Block block) {
        setBlock(block, 0);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof BlockVector))
            return false;
        BlockVector targ = (BlockVector) obj;
        if (targ.x == x && targ.y == y && targ.z == z && world.equals(targ.world)) {
            return true;
        }
        return false;
    }



    public void setBlock(Block block, int metadata)
    {
        world.setBlockState(new BlockPos(x,y,z), block.getStateFromMeta(metadata));
    }
}

