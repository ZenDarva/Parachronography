package com.darva.parachronology.items;

import com.darva.parachronology.Parachronology;
import net.minecraft.block.Block;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

/**
 * Created by James on 9/12/2015.
 */
public class Upgrade extends Item {


    @Override
    public boolean getHasSubtypes() {
        return true;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        switch (stack.getItemDamage()) {
            case 0:
                return "parachronology:tier2upgrade";
            case 1:
                return "parachronology:tier3upgrade";
        }
        return "nothing";
    }

    public Upgrade() {
        this.setMaxStackSize(1);

        this.setMaxDamage(0);
        this.setCreativeTab(CreativeTabs.tabMaterials);
        this.setRegistryName("upgrade");
        GameRegistry.registerItem(this, "upgrade");
    }
    @SideOnly(Side.CLIENT)
    public void registerModel() {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(this.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(this, 1, new ModelResourceLocation("parachronology:upgrade2", "tier3"));
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void getSubItems(Item item, CreativeTabs tabs, List p_150895_3_) {
        p_150895_3_.add(new ItemStack(this, 1, 0));
        p_150895_3_.add(new ItemStack(this, 1, 1));
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
        IBlockState block = worldIn.getBlockState(pos);

        if (block.getBlock() != Parachronology.displacer)
            return false;
        if (stack.getItemDamage() == 0 && block.getBlock().getMetaFromState(block) == 0) {
            //Do some particly stuff here in the future.
            worldIn.setBlockState(pos, block.getBlock().getStateFromMeta(1));
            stack.stackSize--;
            return true;
        }
        if (stack.getItemDamage() == 1 && block.getBlock().getMetaFromState(block) == 1) {
            //Do some particly stuff here in the future.
            worldIn.setBlockState(pos, block.getBlock().getStateFromMeta(2));
            stack.stackSize--;
            return true;
        }
        return false;

    }

}
