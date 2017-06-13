package com.gmail.zendarva.parachronology.block;

import com.gmail.zendarva.parachronology.BlockReference;
import com.gmail.zendarva.parachronology.DisplaceListBuilder;
import com.gmail.zendarva.parachronology.Parachronology;
import com.gmail.zendarva.parachronology.entity.DisplacerEntity;
import com.gmail.zendarva.parachronology.item.DiplacerItemBlock;
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
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import scala.xml.PrettyPrinter;

import java.util.ArrayList;
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
		if(!world.isRemote)world.scheduleUpdate(pos, this, 20);
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
		this.setSoundType(SoundType.ANVIL);
		setDefaultState(blockState.getBaseState().withProperty(TIER, EnumTier.TIER1));
		this.setUnlocalizedName(Parachronology.MODID + ".displacer");
		GameRegistry.register(this);
		GameRegistry.register(new DiplacerItemBlock(this), getRegistryName());
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

		bb = new AxisAlignedBB(pos.getX() - 3, pos.getY() - 3, pos.getZ() - 3, pos.getX() + 3, pos.getY() + 3,
				pos.getZ() + 3);

		List<EntityItem> items = world.getEntitiesWithinAABB(EntityItem.class, bb);

		for (EntityItem item : items) {
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

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(Item itemIn, CreativeTabs tab, NonNullList<ItemStack> list) {
		for (int ix = 0; ix < 3; ix++) {
			list.add(new ItemStack(this, 1, ix));
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
					Block block = world.getBlockState(tempPos).getBlock();
					BlockReference ref = BlockReference.readBlockFromString(Block.REGISTRY.getNameForObject(block) + ":"
							+ block.getMetaFromState(world.getBlockState(tempPos)));
					ArrayList<BlockReference> transforms = DisplaceListBuilder.Instance().getDisplacements(tier, ref);
					if (transforms != null && transforms.size() > 0) {
						BlockReference to = getDisplaceTo(transforms, world, pos);

						TransformTask tranform = new TransformTask(world, tempPos.getX(), tempPos.getY(),
								tempPos.getZ(), to);
						CommonProxy.getScheduler().schedule(r.nextInt(15), tranform, Side.SERVER);
					}
				}
			}
		}
	}

	private BlockReference getDisplaceTo(ArrayList<BlockReference> transforms, World world, BlockPos pos) {
		DisplacerEntity entity = (DisplacerEntity) world.getTileEntity(pos);
		Random r = new Random();
		BlockReference to = transforms.get(r.nextInt(transforms.size()));
		if (entity.getTowards() != null) {
			BlockReference ref = entity.getTowards();
			if (transforms.contains(ref) && to != ref && r.nextInt(100) < 40) {
				to = ref;
			}
		}
		if (entity.getAgainst() != null && to == entity.getAgainst()) {
			BlockReference ref = entity.getTowards();
			if (r.nextInt(100) < 30) {
				to = getDisplaceTo(transforms, world, pos);
			}
		}
		return to;
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
