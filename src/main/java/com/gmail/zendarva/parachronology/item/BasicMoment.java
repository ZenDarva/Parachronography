package com.gmail.zendarva.parachronology.item;

import java.util.List;
import java.util.Random;

import com.gmail.zendarva.parachronology.Configuration.ConfigManager;
import com.gmail.zendarva.parachronology.Configuration.domain.BaseBlockReference;
import com.gmail.zendarva.parachronology.Configuration.domain.BlockReference;
import com.gmail.zendarva.parachronology.Configuration.domain.OreDictReference;
import com.gmail.zendarva.parachronology.Configuration.domain.serialize.BlockReferenceSerializer;
import com.gmail.zendarva.parachronology.Parachronology;

import com.gmail.zendarva.parachronology.utility.TreeStructure;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by James on 3/22/2016.
 */
public class BasicMoment extends Item {

	public BasicMoment() {
		String name = "basicmoment";
		this.setMaxStackSize(64);
		this.setRegistryName(name);
		this.setCreativeTab(Parachronology.TAB);
		this.setUnlocalizedName(Parachronology.MODID + "." + name);
		this.setHasSubtypes(false);
		this.setMaxDamage(0);
	}

	@SideOnly(Side.CLIENT)
	public void registerModel() {
		ModelLoader.setCustomModelResourceLocation(this, 0,
				new ModelResourceLocation(getRegistryName(), "inventory"));

	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand,
			EnumFacing facing, float hitX, float hitY, float hitZ) {
		Block block = worldIn.getBlockState(pos).getBlock();
		Random rand = new Random();
		ItemStack stack = player.getHeldItem(hand);



		BlockReference target = BlockReference.fromBlockWorld(pos,worldIn);

		if (checkTree(target)){
			if (!worldIn.isRemote) {
				TreeStructure ts = new TreeStructure(worldIn, pos);
				ts.poof();
			}
			return EnumActionResult.SUCCESS;
		}



		return EnumActionResult.FAIL;
	}

	private void eatTree(World worldIn, BlockPos pos) {

	}

	private boolean checkTree(BlockReference target) {
		BaseBlockReference wood = BlockReference.getReference("logWood");
		BaseBlockReference leaves = BlockReference.getReference("treeLeaves");

		return (wood.matches(target) || leaves.matches(target));

	}

	

//	private static int spread(World world, BlockPos target, int amount){
//		Random r = new Random(System.currentTimeMillis());
//
//		for (EnumFacing face : EnumFacing.values()){
//			if (amount == 0)
//				return 0;
//			BlockReference ref = BlockReference.fromBlockWorld(target.offset(face), world);
//			List<BlockReference> targets = ConfigManager.getDislocates(ref);
//			if (!targets.isEmpty()){
//				targets.get(r.nextInt(targets.size())).setBlockInWorld(world,target.offset(face));
//				amount--;
//				amount = spread(world,target.offset(face),amount);
//			}
//		}
//		return amount;
//	}
}
