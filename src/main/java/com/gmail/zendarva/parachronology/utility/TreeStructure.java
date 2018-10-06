package com.gmail.zendarva.parachronology.utility;

import com.gmail.zendarva.parachronology.Configuration.domain.BaseBlockReference;
import com.gmail.zendarva.parachronology.Configuration.domain.BlockReference;
import com.gmail.zendarva.parachronology.entity.FadingBlock;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by James on 7/10/2018.
 */
public class TreeStructure {
    private static final int maxSize=1000;
    public List<BlockPos> trunk;
    public List<BlockPos> leaves;
    public List<BlockPos> roots;
    public World world;
    private final ItemStack possibleDrop;
    private int dropChance =13;

    private Queue<BlockPos> open;
    private static final BaseBlockReference treeLeaves = BlockReference.getReference("treeLeaves");
    private static final BaseBlockReference treeWood = BlockReference.getReference("logWood");
    private BlockReference trunkBlock;
    private BlockReference leavesBlock;

    public TreeStructure(World world, BlockPos pos, ItemStack possibleDrop){
        this.world = world;
        this.possibleDrop = possibleDrop;
        trunk = new LinkedList<>();
            leaves = new LinkedList<>();
            roots= new LinkedList<>();
            open = new LinkedList<>();
        if (checkWood(pos) || checkLeaves(pos)){
            open.add(pos);
        }
            findTree();
    }


    private void findTree() {
        while (open.size() > 0 && getSize() < maxSize){
            BlockPos ref = open.remove();
            for (int x = -1; x<2;x++){
                for (int y = -1; y <2; y++){
                    for (int z = -1; z<2;z++) {
                        BlockPos pos = ref.add(x,y,z);
                        if (trunk.contains(pos) || leaves.contains(pos) || open.contains(pos))
                            continue;
                        if (checkWood(pos)) {
                            trunk.add(pos);
                            if (trunkBlock== null)
                                trunkBlock= BlockReference.fromBlockWorld(pos,world);
                            open.add(pos);
                        }
                        if (checkLeaves(pos)) {
                            if (leavesBlock==null)
                                leavesBlock=BlockReference.fromBlockWorld(pos,world);
                            open.add(pos);
                            leaves.add(pos);
                        }

                    }
                }
            }
        }
    }

    public void poof(){
        leaves.forEach(this::replaceWithEntity);
        trunk.forEach(this::replaceWithEntity);
        leaves.clear();
        trunk.clear();
    }
    public void poof(int dropChance){
        this.dropChance=dropChance;
        leaves.forEach(this::replaceWithEntity);
        trunk.forEach(this::replaceWithEntity);
        leaves.clear();
        trunk.clear();

    }

    public int getSize(){
        return trunk.size()+leaves.size();
    }

    private boolean checkWood(BlockPos pos) {
        BlockReference target = BlockReference.fromBlockWorld(pos,world);
        return (treeWood.matches(target));

    }
    private boolean checkLeaves(BlockPos pos) {
        BlockReference target = BlockReference.fromBlockWorld(pos,world);
        return (treeLeaves.matches(target));
    }

    private void replaceWithEntity(BlockPos target){
        FadingBlock entity = new FadingBlock(world,target,world.getBlockState(target), possibleDrop,dropChance);
        world.setBlockToAir(target);
        world.spawnEntity(entity);
    }

    private boolean checkDirt(BlockPos pos) {
        IBlockState state = world.getBlockState(pos);
        //state.getBlock().canPlaceBlockOnSide()
        //state.getBlock().canSustainPlant(state,world,pos, EnumFacing.UP,Items.);
        return true;
    }
}
