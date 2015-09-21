package com.darva.parachronology.blocks;

import com.darva.parachronology.BlockReference;
import com.darva.parachronology.DisplaceListBuilder;
import com.darva.parachronology.Parachronology;
import com.darva.parachronology.entity.DisplacerEntity;
import com.darva.parachronology.items.Moment;
import com.darva.parachronology.utility.tasks.TransformTask;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Created by James on 8/23/2015.
 */
public class Displacer extends BlockContainer {

    private String[] spawnableList = {"Enderman", "Zombie", "Skeleton", "Spider", "Blaze"};

    @Override
    public int onBlockPlaced(World world, int x, int y, int z, int side, float p_149660_6_, float p_149660_7_, float p_149660_8_, int p_149660_9_) {
        world.scheduleBlockUpdate(x, y, z, world.getBlock(x, y, z), 20);
        return super.onBlockPlaced(world, x, y, z, side, p_149660_6_, p_149660_7_, p_149660_8_, p_149660_9_);
    }

    public Displacer(Material p_i45394_1_) {
        super(Material.rock);
        this.setHardness(1);
        this.setLightOpacity(1);
        this.setTickRandomly(true);
    }

    @Override
    public boolean shouldSideBeRendered(IBlockAccess p_149646_1_, int p_149646_2_, int p_149646_3_, int p_149646_4_, int p_149646_5_) {
        return false;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public String getUnlocalizedName() {
        return "parachronology:displacer";
    }


    @Override
    public int damageDropped(int p_149692_1_) {
        return p_149692_1_;
    }


    @Override
    public void updateTick(World world, int x, int y, int z, Random r) {
        AxisAlignedBB bb;
        bb = AxisAlignedBB.getBoundingBox(x - 5, y - 5, z - 5, x + 5, y + 5, z + 5);

        List<EntityItem> items = world.getEntitiesWithinAABB(EntityItem.class, bb);


        for (EntityItem item : items) {
            if (item.getEntityItem().getItem() instanceof Moment) {
                item.getEntityItem().stackSize--;
                this.transform(world, x, y, z);
                world.scheduleBlockUpdate(x, y, z, world.getBlock(x, y, z), 20);
                return;
            }
        }
        world.scheduleBlockUpdate(x, y, z, world.getBlock(x, y, z), 20);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item unknown, CreativeTabs tab, List subItems) {
        for (int ix = 0; ix < 3; ix++) {
            subItems.add(new ItemStack(this, 1, ix));
        }
    }

    private void transform(World world, int tx, int ty, int tz) {
        if (world.isRemote)
            return;
        int tier = world.getBlockMetadata(tx, ty, tz) + 1;
        HashMap<BlockReference, ArrayList<BlockReference>> transforms = DisplaceListBuilder.Instance().getDisplacements(tier);
        Random r = new Random();
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                for (int z = -1; z <= 1; z++) {
                    Block block = world.getBlock(x + tx, y + ty, z + tz);
                    BlockReference ref = BlockReference.readBlockFromString(Block.blockRegistry.getNameForObject(block) + ":" + world.getBlockMetadata(x, y, z));
                    if (ref != null && transforms.containsKey(ref)) {
                        BlockReference to = transforms.get(ref).get(r.nextInt(transforms.get(ref).size()));

                        TransformTask tranform = new TransformTask(world,x+tx,y+ty,z+tz,to);
                        Parachronology.proxy.getScheduler().schedule(r.nextInt(15),tranform,Side.SERVER);
                    }
                }
            }
        }

    }

    @Override
    public int getRenderType() {
        return Parachronology.renderId;
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new DisplacerEntity();
    }
}
