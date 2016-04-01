package com.darva.parachronology;

import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
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

    @Override
    public String toString() {
        return (Block.blockRegistry.getNameForObject(targBlock) + ":" + metadata);
    }

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
        if (targBlock == null)
        {
            System.out.println("Null block in " + this.blockName +"with metadata " + metadata);
             return;
        }
        world.setBlockState(new BlockPos(x, y, z), targBlock.getStateFromMeta(metadata));
    }

    public static BlockReference getReferenceFromBlock(Block block, int meta)
    {
        return BlockReference.readBlockFromString(Block.blockRegistry.getNameForObject(block) + ":" + meta);
    }
    public ItemStack getStack()
    {
        if (targBlock == null)
        {
            System.out.println("Null blockReference " + blockName  );
        }
        ItemStack stack = new ItemStack(targBlock,1, metadata);
        if (stack.getItem()== null)
        {
            return getItemFromBlockStupidly(targBlock);
        }
        return stack;
    }

    private ItemStack getItemFromBlockStupidly(Block block)
    {
        switch (block.getUnlocalizedName())
        {
            case "tile.reeds":
                return new ItemStack(Items.reeds);
        }
        System.out.println("Failure on block: " +targBlock.getUnlocalizedName());
        return null;
    }
}
