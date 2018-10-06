package com.gmail.zendarva.parachronology.item;

import com.gmail.zendarva.parachronology.Configuration.domain.BaseBlockReference;
import com.gmail.zendarva.parachronology.Configuration.domain.BlockReference;
import com.gmail.zendarva.parachronology.Parachronology;
import com.gmail.zendarva.parachronology.utility.TreeStructure;
import net.minecraft.block.Block;
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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class TimeSyphon extends Item {

    public TimeSyphon() {
        String name = "timesyphon";
        this.setMaxStackSize(1);
        this.setRegistryName(name);
        this.setCreativeTab(Parachronology.TAB);
        this.setUnlocalizedName(Parachronology.MODID + "." + name);
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (tab == Parachronology.TAB) {
            items.add(new ItemStack(this, 1, 0));
            items.add(new ItemStack(this, 1, 1));
        }
    }

    @SideOnly(Side.CLIENT)
    public void registerModel() {
        ModelLoader.setCustomModelResourceLocation(this, 0,
                new ModelResourceLocation(this.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(this, 1,
                new ModelResourceLocation("parachronology:timesyphon2", "tier2"));
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand,
                                      EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack stack = player.getHeldItem(hand);



        BlockReference target = BlockReference.fromBlockWorld(pos,worldIn);

        if (checkTree(target)){
            if (!worldIn.isRemote) {
                ItemStack result;

                if (this.getDamage(stack) ==0) {
                    result = new ItemStack(Parachronology.moment, 1, 1);
                    TreeStructure ts = new TreeStructure(worldIn, pos, result);
                    ts.poof(9);

                }
                else {
                    result = new ItemStack(Parachronology.moment, 1, 2);
                    TreeStructure ts = new TreeStructure(worldIn, pos, result);
                    ts.poof(6);
                }


            }
            return EnumActionResult.SUCCESS;
        }



        return EnumActionResult.FAIL;
    }


    private boolean checkTree(BlockReference target) {
        BaseBlockReference wood = BlockReference.getReference("logWood");
        BaseBlockReference leaves = BlockReference.getReference("treeLeaves");

        return (wood.matches(target) || leaves.matches(target));

    }
}
