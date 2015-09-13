package com.darva.parachronology;

import net.minecraft.block.Block;
import net.minecraft.world.World;

import java.util.HashMap;

/**
 * Created by James on 9/12/2015.
 */
public class BlockReference {

    String blockName;
    int metadata;
    Block targBlock;
    private static HashMap<String, BlockReference> allRefs = new HashMap<String, BlockReference>();

    private BlockReference() {
    }

    ;

    public static BlockReference readBlockFromString(String str) {
        //Example.  minecraft:sapling:1

        if (allRefs.containsKey(str))
            return allRefs.get(str);
        BlockReference ref = new BlockReference();
        String[] parts = str.split(":");
        if (parts.length < 2)
            return null;
        ref.blockName = parts[0] + parts[1];
        if (parts.length > 2)
            ref.metadata = Integer.parseInt(parts[2]);
        else
            ref.metadata = 0;
        ref.targBlock = Block.getBlockFromName(ref.blockName);
        allRefs.put(str, ref);
        return ref;
    }

    public void placeInWorld(World world, int x, int y, int z) {
        world.setBlock(x, y, z, targBlock, metadata, 3);
    }
}
