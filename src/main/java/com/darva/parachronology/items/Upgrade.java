package com.darva.parachronology.items;

import com.darva.parachronology.Parachronology;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.List;

/**
 * Created by James on 9/12/2015.
 */
public class Upgrade extends Item {

    private IIcon icons[];

    @Override
    public void registerIcons(IIconRegister register) {

        icons[0] = register.registerIcon("parachronology:upgrade1");
        icons[1] = register.registerIcon("parachronology:upgrade2");
        super.registerIcons(register);
    }

    @Override
    public IIcon getIconFromDamage(int p_77617_1_) {
        return icons[p_77617_1_];
    }

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
        GameRegistry.registerItem(this, "base-upgrade");
        this.setMaxDamage(0);
        this.setCreativeTab(CreativeTabs.tabMaterials);
        icons = new IIcon[2];
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void getSubItems(Item item, CreativeTabs tabs, List p_150895_3_) {
        p_150895_3_.add(new ItemStack(this, 1, 0));
        p_150895_3_.add(new ItemStack(this, 1, 1));
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float p_77648_8_, float p_77648_9_, float p_77648_10_) {
        Block block = world.getBlock(x, y, z);

        if (block != Parachronology.displacer)
            return false;
        if (stack.getItemDamage() == 0 && world.getBlockMetadata(x, y, z) == 0) {
            //Do some particly stuff here in the future.
            world.setBlock(x, y, z, block, 1, 3);
            stack.stackSize--;
            return true;
        }
        if (stack.getItemDamage() == 1 && world.getBlockMetadata(x, y, z) == 1) {
            //Do some particly stuff here in the future.
            world.setBlock(x, y, z, block, 2, 3);
            stack.stackSize--;
            return true;
        }
        return false;
    }
}
