package com.gmail.zendarva.parachronology.Configuration.domain;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

/**
 * Created by James on 4/12/2018.
 */
public class BlockReference extends BaseBlockReference {



    public int metadata;
    public String blockName;
    public String domain;
    public NBTTagCompound compound;


    private ItemStack myStack = ItemStack.EMPTY;

    public BlockReference() {
        System.out.println("Noop");
    }

    @Override
    public String toString() {
        return String.format("%s:%s:%d", domain,blockName,metadata);
    }

    public static BlockReference fromItemStack(ItemStack stack){
        BlockReference reference = new BlockReference();
        reference.metadata = stack.getMetadata();
        reference.blockName = stack.getItem().getRegistryName().getResourcePath();
        reference.domain = stack.getItem().getRegistryName().getResourceDomain();
        if (stack.hasTagCompound()){
            reference.compound = stack.getTagCompound();
        }

        return reference;
    }

    public static BlockReference fromBlockWorld(BlockPos pos, World world){
        BlockReference reference = new BlockReference();
        Block block = world.getBlockState(pos).getBlock();

        reference.metadata = world.getBlockState(pos).getBlock().getMetaFromState(world.getBlockState(pos));
        reference.blockName=block.getRegistryName().getResourcePath();
        reference.domain=block.getRegistryName().getResourceDomain();
        TileEntity entity = world.getTileEntity(pos);
        if (entity != null){
            reference.compound=entity.serializeNBT();
        }

        reference.register();
        return reference;
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
        }
        return myStack;
    }
    public Block getBlock(){
        return Block.getBlockFromName(domain+":"+blockName);
    }

}
