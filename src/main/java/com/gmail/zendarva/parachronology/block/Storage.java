package com.gmail.zendarva.parachronology.block;

import com.gmail.zendarva.parachronology.Parachronology;
import com.gmail.zendarva.parachronology.capability.ITimeless;
import com.gmail.zendarva.parachronology.capability.TimelessProvider;
import com.gmail.zendarva.parachronology.entity.StorageEntity;
import com.gmail.zendarva.parachronology.item.TimelessPickaxe;
import com.gmail.zendarva.parachronology.item.TimelessWand;
import com.google.common.base.Predicate;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.Random;

/**
 * Created by James on 7/26/2017.
 */
public class Storage extends Block implements ITileEntityProvider {

    public static final int GUI_ID =1;
    public static final PropertyBool stateProperty= PropertyBool.create("open");
    public static final PropertyDirection FACING = PropertyDirection.create("facing", new Predicate<EnumFacing>(){
        public boolean apply(@Nullable EnumFacing p_apply_1_)
        {
            return p_apply_1_ != EnumFacing.UP && p_apply_1_ != EnumFacing.DOWN;
        }
    });

    @Override
    public void randomTick(World worldIn, BlockPos pos, IBlockState state, Random random) {
        super.randomTick(worldIn, pos, state, random);
        if (worldIn.isRemote)
                return;
        int num = random.nextInt(2);
        if (num == 0)
        {
            worldIn.setBlockState(pos,blockState.getBaseState().withProperty(stateProperty, true).withProperty(FACING,EnumFacing.EAST));
        }
        if (num == 1)
        {
            worldIn.setBlockState(pos,blockState.getBaseState().withProperty(stateProperty, false).withProperty(FACING,EnumFacing.EAST));
        }

        worldIn.markChunkDirty(pos, null);
        this.needsRandomTick=true;
        TileEntity entity = worldIn.getTileEntity(pos);
        if (!(entity instanceof StorageEntity)) //What the fuck?
            return;
        StorageEntity storage = (StorageEntity) entity;
        storage.tickChunkloading();
    }

    public Storage() {
        super(Material.WOOD);
        String name = "timelessstorage";
        this.setHardness(1.2f);
        this.setLightOpacity(1);
        this.setResistance(5f);
        this.blockSoundType = SoundType.WOOD;
        this.setHarvestLevel("hand", 0);
        this.setCreativeTab(Parachronology.TAB);
        this.setRegistryName(name);
        this.setUnlocalizedName(Parachronology.MODID + "." + name);
        this.setCreativeTab(Parachronology.TAB);
        setDefaultState(blockState.getBaseState().withProperty(stateProperty, true).withProperty(FACING,EnumFacing.EAST));
        this.needsRandomTick=true;
    }

    @SideOnly(Side.CLIENT)
    public void registerModel() {

        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0,
                new ModelResourceLocation(getRegistryName(), "inventory"));
    }


    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

        TileEntity entity = worldIn.getTileEntity(pos);
        NBTTagCompound tag;

        if (!(entity instanceof StorageEntity))
            return false;

        if (playerIn.getHeldItemMainhand().getItem() instanceof TimelessPickaxe ||
                playerIn.getHeldItemMainhand().getItem() instanceof TimelessWand) {
            ITimeless timeless = playerIn.getHeldItemMainhand().getCapability(TimelessProvider.timeless,null);
            ItemStack targ = playerIn.getHeldItemMainhand();
            if (timeless.getTarget() == null || (targ.getTagCompound() != null && !targ.getTagCompound().getBoolean("linked"))) {
                timeless.setTarget(pos);
                timeless.setWorldId(playerIn.dimension);
                if (targ.getTagCompound() == null) {
                    tag = new NBTTagCompound();
                } else
                {
                    tag = targ.getTagCompound();
                }
                    tag.setBoolean("linked", true);
                    targ.setTagCompound(tag);

                return true;
            }
        }
        playerIn.openGui(Parachronology.instance, GUI_ID,worldIn,pos.getX(),pos.getY(),pos.getZ());
        return true;
    }



    public static boolean isOpen(int meta)
    {
        return (meta & 8) != 8;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new StorageEntity();
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(FACING, getFacing(meta)).withProperty(stateProperty, Boolean.valueOf(isOpen(meta)));
    }
    public static EnumFacing getFacing(int meta)
    {
        return EnumFacing.getFront(meta & 7);
    }

    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        return this.getDefaultState().withProperty(FACING,placer.getHorizontalFacing().getOpposite()).withProperty(stateProperty,true);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        int i = 0;
        i = i | ((EnumFacing)state.getValue(FACING)).getIndex();

        if (!((Boolean)state.getValue(stateProperty)).booleanValue())
        {
            i |= 8;
        }
        return i;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {FACING, stateProperty});
    }
    @Override
    public boolean rotateBlock(World world, BlockPos pos, EnumFacing axis) {
        return super.rotateBlock(world, pos, axis);
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
        if (worldIn.isRemote)
                return;
        TileEntity entity = worldIn.getTileEntity(pos);
        if (!(entity instanceof StorageEntity)) //What the fuck?
            return;
        StorageEntity storage = (StorageEntity) entity;
        if (!(placer instanceof EntityPlayer))
            return;
        storage.setChunkloading((EntityPlayer) placer);
    }
}
