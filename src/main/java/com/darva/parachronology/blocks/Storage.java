//package com.darva.parachronology.blocks;
//
//import com.darva.parachronology.entity.StorageEntity;
//import net.minecraft.block.Block;
//import net.minecraft.block.ITileEntityProvider;
//import net.minecraft.block.material.Material;
//import net.minecraft.block.properties.PropertyDirection;
//import net.minecraft.block.state.BlockState;
//import net.minecraft.block.state.IBlockState;
//import net.minecraft.client.resources.model.ModelResourceLocation;
//import net.minecraft.entity.EntityLivingBase;
//import net.minecraft.item.Item;
//import net.minecraft.item.ItemStack;
//import net.minecraft.tileentity.TileEntity;
//import net.minecraft.util.BlockPos;
//import net.minecraft.util.EnumFacing;
//import net.minecraft.world.World;
//import net.minecraftforge.client.model.ModelLoader;
//import net.minecraftforge.fml.common.registry.GameRegistry;
//import net.minecraftforge.fml.relauncher.Side;
//import net.minecraftforge.fml.relauncher.SideOnly;
//
///**
// * Created by James on 1/22/2016.
// */
//public class Storage extends Block implements ITileEntityProvider {
//
//    public static final PropertyDirection FACING = PropertyDirection.create("facing");
//
//    @Override
//    public boolean isVisuallyOpaque() {
//        return false;
//    }
//
//    @Override
//    public boolean isOpaqueCube() {
//        return false;
//    }
//
//    public Storage() {
//        super(Material.wood);
//        setUnlocalizedName("parachronology:storage");
//        setRegistryName("storage");
//        GameRegistry.registerBlock(this);
//
//        setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
//        this.setBlockBounds(.07f,0f,.07f,.91f,.95f,.91f);
//    }
//
//    @SideOnly(Side.CLIENT)
//    public void registerModel()
//    {
//        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
//    }
//
//    @Override
//    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
//        world.setBlockState(pos, state.withProperty(FACING, getFacingFromEntity(pos, placer)), 2);
//    }
//
//    public static EnumFacing getFacingFromEntity(BlockPos clickedBlock, EntityLivingBase entity) {
//        return EnumFacing.getFacingFromVector(
//                (float) (entity.posX - clickedBlock.getX()),
//                (float) (entity.posY - clickedBlock.getY()),
//                (float) (entity.posZ - clickedBlock.getZ()));
//    }
//
//    @Override
//    public IBlockState getStateFromMeta(int meta) {
//        return getDefaultState().withProperty(FACING, EnumFacing.getFront(meta & 7));
//    }
//
//    @Override
//    public int getMetaFromState(IBlockState state) {
//        return state.getValue(FACING).getIndex();
//    }
//
//    @Override
//    protected BlockState createBlockState() {
//        return new BlockState(this, FACING);
//    }
//
//
//    @Override
//    public TileEntity createTileEntity(World world, IBlockState state) {
//        return new StorageEntity(world);
//    }
//
//    @Override
//    public TileEntity createNewTileEntity(World worldIn, int meta) {
//        return null;
//    }
//}
