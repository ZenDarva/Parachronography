package com.darva.parachronology;

import net.minecraft.block.Block;
import net.minecraft.world.World;

import java.util.HashMap;

/**
 * Created by James on 9/12/2015.
 */
public class BlockReference {

    public String blockName;
    public int metadata;
    public Block targBlock;
    private static HashMap<String, BlockReference> allRefs = new HashMap<String, BlockReference>();

    private BlockReference() {
    }

    ;

    public static BlockReference readBlockFromString(String str) {
        //Example.  minecraft:sapling:1

        int metadata;
        Block targBlock;
        String blockName;

        String[] parts = str.split(":");
        if (parts.length < 2)
            return null;
        blockName = parts[0] + ":" + parts[1];
        if (parts.length > 2)
            metadata = Integer.parseInt(parts[2]);
        else
            metadata = 0;
        targBlock = Block.getBlockFromName(blockName);
        if (allRefs.containsKey(blockName + ":" + metadata))
            return allRefs.get(blockName + ":" + metadata);

        BlockReference ref = new BlockReference();
        ref.targBlock = targBlock;
        ref.blockName = blockName;
        ref.metadata = metadata;
        allRefs.put(ref.blockName + ":" + ref.metadata, ref);
        return ref;
    }

    public void placeInWorld(World world, int x, int y, int z) {
        world.setBlock(x, y, z, targBlock, metadata, 3);
    }
}