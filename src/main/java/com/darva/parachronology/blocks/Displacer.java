package com.darva.parachronology.blocks;

import com.darva.parachronology.BlockReference;
import com.darva.parachronology.DisplaceListBuilder;
import com.darva.parachronology.Parachronology;
import com.darva.parachronology.entity.DisplacerEntity;
import com.darva.parachronology.items.DiplacerItemBlock;
import com.darva.parachronology.items.Moment;
import com.darva.parachronology.utility.tasks.TransformTask;
import com.google.common.primitives.Ints;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Created by James on 8/23/2015.
 */
public class Displacer extends Block implements ITileEntityProvider {

    private String[] spawnableList = {"Enderman", "Zombie", "Skeleton", "Spider", "Blaze"};
    public static final PropertyEnum TIER = PropertyEnum.create("tier",Displacer.EnumTier.class);

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        worldIn.scheduleUpdate(pos, worldIn.getBlockState(pos).getBlock(), 20);
    }

    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] {TIER});
    }

    public Displacer(Material p_i45394_1_) {
        super(Material.rock);
        this.setHardness(1);
        this.setLightOpacity(1);
        this.setTickRandomly(true);
        this.setRegistryName("displacer");
        setDefaultState(blockState.getBaseState().withProperty(TIER, EnumTier.TIER1));
        GameRegistry.registerBlock(this, DiplacerItemBlock.class, "displacer");

    }

    @Override
    public boolean isVisuallyOpaque() {
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
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
        AxisAlignedBB bb;

        bb = AxisAlignedBB.fromBounds(pos.getX() - 3, pos.getY() - 3, pos.getZ() - 3, pos.getX() + 3, pos.getY() + 3, pos.getZ() + 3);

        List<EntityItem> items = world.getEntitiesWithinAABB(EntityItem.class, bb);


        for (EntityItem item : items) {
            if (item.getEntityItem().getItem() instanceof Moment) {
                item.getEntityItem().stackSize--;
                this.transform(world, pos);
                world.scheduleUpdate(pos, world.getBlockState(pos).getBlock(), 20);
                return;
            }



            if (Ints.contains(OreDictionary.getOreIDs(item.getEntityItem()), OreDictionary.getOreID("logWood")))
            {
                ItemStack petrified = new ItemStack(Parachronology.petrifiedWood,item.getEntityItem().stackSize);
                EntityItem newItem = new EntityItem(world,item.posX,item.posY,item.posZ,petrified);
                world.spawnEntityInWorld(newItem);
                newItem.motionX =0;
                newItem.motionY=0;
                item.setDead();
            }
        }
        world.scheduleUpdate(pos, world.getBlockState(pos).getBlock(), 20);


    }

    @SideOnly(Side.CLIENT)
    public void registerModel() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 1, new ModelResourceLocation(getRegistryName(), "tier=tier2"));
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 2, new ModelResourceLocation(getRegistryName(), "tier=tier3"));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item unknown, CreativeTabs tab, List subItems) {
        for (int ix = 0; ix < 3; ix++) {
            subItems.add(new ItemStack(this, 1, ix));
        }
    }

    @Override
    public int damageDropped(IBlockState state) {
        return getMetaFromState(state);
    }

    private void transform(World world, BlockPos pos)
    {
        if (world.isRemote)
            return;
        int tier = getMetaFromState(world.getBlockState(pos));
        Random r = new Random();
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                for (int z = -1; z <= 1; z++) {
                    BlockPos tempPos=pos.add(x,y,z);
                    Block block = world.getBlockState(tempPos).getBlock();
                    BlockReference ref = BlockReference.readBlockFromString(Block.blockRegistry.getNameForObject(block) + ":" + block.getMetaFromState(world.getBlockState(tempPos)));
                    ArrayList<BlockReference> transforms = DisplaceListBuilder.Instance().getDisplacements(tier, ref);
                    if (transforms != null && transforms.size() >0) {
                        BlockReference to = transforms.get(r.nextInt(transforms.size()));

                        TransformTask tranform = new TransformTask(world,tempPos.getX(),tempPos.getY(),tempPos.getZ(),to);
                        Parachronology.proxy.getScheduler().schedule(r.nextInt(15),tranform,Side.SERVER);
                    }
                }
            }
        }
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new DisplacerEntity();
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        switch (meta)
        {
            case 0: return getDefaultState().withProperty(TIER,EnumTier.TIER1);
            case 1: return getDefaultState().withProperty(TIER,EnumTier.TIER2);
            case 2: return getDefaultState().withProperty(TIER,EnumTier.TIER3);

        }
        return getDefaultState();
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        EnumTier type = (EnumTier) state.getValue(TIER);
        return type.getID();
    }

    public enum EnumTier implements IStringSerializable {
        TIER1(0, "tier1"),
        TIER2(1, "tier2"),
        TIER3(2, "tier3");

        private int ID;
        private String name;

        private EnumTier(int ID, String name) {
            this.ID = ID;
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }

        public int getID() {
            return ID;
        }
    }
}
