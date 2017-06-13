package com.gmail.zendarva.parachronology.item;

import com.gmail.zendarva.parachronology.Parachronology;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by James on 9/12/2015.
 */
public class Upgrade extends Item {

	@Override
	public boolean getHasSubtypes() {
		return true;
	}
	
	@Override
    public String getUnlocalizedName(ItemStack stack)
    {
        return getUnlocalizedName() + "." + stack.getMetadata();
    }

	public Upgrade() {
		String name = "upgrade";
		this.setMaxStackSize(1);

		this.setMaxDamage(0);
		this.setCreativeTab(Parachronology.TAB);
		this.setUnlocalizedName(Parachronology.MODID + "." + name);
		this.setRegistryName(name);
		GameRegistry.register(this);
	}

	@SideOnly(Side.CLIENT)
	public void registerModel() {
		ModelLoader.setCustomModelResourceLocation(this, 0,
				new ModelResourceLocation(this.getRegistryName(), "inventory"));
		ModelLoader.setCustomModelResourceLocation(this, 1,
				new ModelResourceLocation("parachronology:upgrade2", "tier3"));
	}

	@Override
	public void getSubItems(Item itemIn, CreativeTabs tab, NonNullList<ItemStack> subItems) {
		subItems.add(new ItemStack(this, 1, 0));
		subItems.add(new ItemStack(this, 1, 1));
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand,
			EnumFacing facing, float hitX, float hitY, float hitZ) {
		IBlockState block = worldIn.getBlockState(pos);
		ItemStack stack = player.getHeldItem(hand);

		if (block.getBlock() != Parachronology.displacer)
			return EnumActionResult.FAIL;
		if (stack.getItemDamage() == 0 && block.getBlock().getMetaFromState(block) == 0) {
			//Do some particly stuff here in the future.
			worldIn.setBlockState(pos, block.getBlock().getStateFromMeta(1));
			stack.shrink(1);
			return EnumActionResult.SUCCESS;
		}
		if (stack.getItemDamage() == 1 && block.getBlock().getMetaFromState(block) == 1) {
			//Do some particly stuff here in the future.
			worldIn.setBlockState(pos, block.getBlock().getStateFromMeta(2));
			stack.shrink(1);
			return EnumActionResult.SUCCESS;
		}
		return EnumActionResult.FAIL;
	}
}
