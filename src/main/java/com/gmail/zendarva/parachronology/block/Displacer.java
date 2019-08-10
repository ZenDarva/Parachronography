package com.gmail.zendarva.parachronology.block;

import com.gmail.zendarva.parachronology.Configuration.ConfigManager;
import com.gmail.zendarva.parachronology.Configuration.domain.BlockReference;
import com.gmail.zendarva.parachronology.Parachronology;
import com.gmail.zendarva.parachronology.entity.DisplacerEntity;
import com.gmail.zendarva.parachronology.item.Moment;
import com.gmail.zendarva.parachronology.proxy.CommonProxy;
import com.gmail.zendarva.parachronology.utility.tasks.TransformTask;
import com.google.common.primitives.Ints;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

/**
 * Created by James on 8/23/2015.
 */
public class Displacer extends Block implements ITileEntityProvider {

	private String[] spawnableList = { "Enderman", "Zombie", "Skeleton", "Spider", "Blaze" };
	public static final PropertyEnum<EnumTier> TIER = PropertyEnum.create("tier", Displacer.EnumTier.class);

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer,
			ItemStack stack) {
	        if (stack.hasTagCompound())
            {
                DisplacerEntity entity = (DisplacerEntity) world.getTileEntity(pos);
                entity.readFromNBT(stack.getTagCompound());
            }
			world.scheduleUpdate(pos, this, 20);
	        super.onBlockPlacedBy(world,pos,state,placer,stack);
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { TIER });
	}
	

	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT;
	}

	public Displacer() {
		super(Material.ROCK);
		this.setHardness(1);
		this.setLightOpacity(1);
		this.setTickRandomly(true);
		this.setRegistryName("displacer");
		this.setCreativeTab(Parachronology.TAB);
		this.setSoundType(SoundType.ANVIL);
		setDefaultState(blockState.getBaseState().withProperty(TIER, EnumTier.TIER1));
		this.setUnlocalizedName(Parachronology.MODID + ".displacer");
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isFullBlock(IBlockState state) {
		return false;
	}

	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		AxisAlignedBB bb;

		if (world.isRemote)
		    return;

		bb = new AxisAlignedBB(pos.getX() - 3, pos.getY() - 3, pos.getZ() - 3, pos.getX() + 3, pos.getY() + 3,
				pos.getZ() + 3);

		List<EntityItem> items = world.getEntitiesWithinAABB(EntityItem.class, bb);

		for (EntityItem item : items) {
			if (item.getItem().isEmpty())
					continue;
			if (item.getItem().getItem() instanceof Moment) {
				item.getItem().shrink(1);
				this.transform(world, pos);
				world.scheduleUpdate(pos, world.getBlockState(pos).getBlock(), 20);
				return;
			}


			if (Ints.contains(OreDictionary.getOreIDs(item.getItem()), OreDictionary.getOreID("logWood"))) {
				ItemStack petrified = new ItemStack(Parachronology.petrifiedWood, item.getItem().getCount());
				EntityItem newItem = new EntityItem(world, item.posX, item.posY, item.posZ, petrified);
				world.spawnEntity(newItem);
				newItem.motionX = 0;
				newItem.motionY = 0;
				item.setDead();
			}
		}

		world.scheduleUpdate(pos, world.getBlockState(pos).getBlock(), 20);

	}

	@SideOnly(Side.CLIENT)
	public void registerModel() {
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0,
				new ModelResourceLocation(getRegistryName(), "tier=tier1"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 1,
				new ModelResourceLocation(getRegistryName(), "tier=tier2"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 2,
				new ModelResourceLocation(getRegistryName(), "tier=tier3"));
	}

	@Override
	public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> items) {
		if (tab == Parachronology.TAB || tab.getBackgroundImageName()=="item_search.png") {
			for (int ix = 0; ix < 3; ix++) {
				items.add(new ItemStack(this, 1, ix));
			}
		}
	}

	@Override
	public int damageDropped(IBlockState state) {
		return getMetaFromState(state);
	}


	private void transform(World world, BlockPos pos) {
		if (world.isRemote)
			return;
		int tier = getMetaFromState(world.getBlockState(pos));
		Random r = new Random();
		for (int x = -1; x <= 1; x++) {
			for (int y = -1; y <= 1; y++) {
				for (int z = -1; z <= 1; z++) {
					BlockPos tempPos = pos.add(x, y, z);
						BlockReference to = getDisplaceTo(tempPos,world,tier+1);

						if (to == null)
							continue;

						TransformTask tranform = new TransformTask(world, tempPos.getX(), tempPos.getY(),
								tempPos.getZ(), to);
						CommonProxy.getScheduler().schedule(r.nextInt(15), tranform, Side.SERVER);

				}
			}
		}
	}

	private BlockReference getDisplaceTo(BlockPos pos, World world, int tier){

		BlockReference block= BlockReference.fromBlockWorld(pos,world);
		List<BlockReference> results = ConfigManager.getDisplacements(block,tier);

		if (results.isEmpty())
			return null;
		if (results.size() == 1)
			return results.get(0);

		int selected = world.rand.nextInt(results.size());
		return results.get(selected);
	}

	@Override
	public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {

		TileEntity t = world.getTileEntity(pos);

		if (t instanceof DisplacerEntity) {
			DisplacerEntity tile = (DisplacerEntity) t;
			int meta = world.getBlockState(pos).getBlock().getMetaFromState(world.getBlockState(pos));

			ItemStack stack = new ItemStack(Parachronology.displacer, 1, meta);
			stack.setTagCompound(tile.getTileData());

			drops.add(stack);
		}

	}

	@Override
	public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
		if (willHarvest)
			return true;
		return super.removedByPlayer(state,world,pos,player,willHarvest);
	}

	@Override
	public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack) {
		super.harvestBlock(worldIn, player, pos, state, te, stack);
		worldIn.setBlockToAir(pos);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new DisplacerEntity();
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(TIER, EnumTier.values()[meta]);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(TIER).ordinal();
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
