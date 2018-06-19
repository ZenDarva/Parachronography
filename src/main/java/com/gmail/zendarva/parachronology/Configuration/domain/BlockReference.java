package com.gmail.zendarva.parachronology.Configuration.domain;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Random;

/**
 * Created by James on 4/12/2018.
 */
public class BlockReference extends BaseBlockReference {



    public int metadata;
    public String blockName;
    public String domain;
    public NBTTagCompound compound;


    private ItemStack myStack = ItemStack.EMPTY;

    protected BlockReference() {
        super();
    }

    @Override
    public String getDisplayName() {
        return getItemStack().getDisplayName();
    }

    @Override
    public String toString() {
        return String.format("%s:%s:%d", domain,blockName,metadata);
    }

    public static BlockReference fromItemStack(ItemStack stack){
        BlockReference reference = new BlockReference();
        int metadata = stack.getMetadata();
        String blockName = stack.getItem().getRegistryName().getResourcePath();
        String domain = stack.getItem().getRegistryName().getResourceDomain();
        String nbt= null;
        if (stack.hasTagCompound()){
            nbt = stack.getTagCompound().toString();
        }

        return (BlockReference) BaseBlockReference.getReference(metadata,blockName,domain, nbt);
    }

    public static BlockReference fromBlockWorld(BlockPos pos, World world){
        Block block = world.getBlockState(pos).getBlock();

        int metadata = world.getBlockState(pos).getBlock().getMetaFromState(world.getBlockState(pos));
        String blockName=block.getRegistryName().getResourcePath();
        String domain=block.getRegistryName().getResourceDomain();
        TileEntity entity = world.getTileEntity(pos);
        String compound=null;
        if (entity != null){

            compound = entity.serializeNBT().toString();
        }

        return (BlockReference) BaseBlockReference.getReference(metadata,blockName,domain, compound);
    }

    @Override
    public boolean matches(BaseBlockReference target) {
        if (target instanceof  OreDictReference){
            OreDictReference targRef = (OreDictReference) target;

            NonNullList<ItemStack> dict = OreDictionary.getOres(targRef.oreDictName);
            return OreDictionary.containsMatch(targRef.compareNBT,dict,getItemStack());
        }
        return target==this;
    }

    public void setBlockInWorld(World world, BlockPos target){
        world.destroyBlock(target, false);

        if (metadata != 0) {
            world.setBlockState(target, getBlock().getStateFromMeta(metadata));
        }
        else
            world.setBlockState(target, getBlock().getDefaultState());
        if (compound != null) {
            TileEntity te = world.getTileEntity(target);
            if (te != null){
                te.deserializeNBT(compound);
            }
        }
        world.markChunkDirty(target, null);
    }

    public ItemStack getItemStack(){
        if (myStack.isEmpty()){
            Block targBlock = Block.getBlockFromName(domain+":"+blockName);
            myStack = new ItemStack(targBlock,1,metadata);
            if (myStack.isEmpty()) {
                //Reeds.  I hate reeds.
                myStack = new ItemStack(targBlock.getItemDropped(targBlock.getDefaultState(),new Random(),0),1,metadata);
            }
        }
        return myStack;
    }
    public Block getBlock(){
        return Block.getBlockFromName(domain+":"+blockName);
    }

}
