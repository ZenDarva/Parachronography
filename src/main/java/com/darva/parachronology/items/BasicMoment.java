package com.darva.parachronology.items;

import com.darva.parachronology.Parachronology;
import net.minecraft.block.Block;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Random;

/**
 * Created by James on 3/22/2016.
 */
public class BasicMoment extends Item {

    public BasicMoment() {
        this.setMaxStackSize(64);
        this.setRegistryName("basicmoment");
        GameRegistry.registerItem(this, "b");
        this.setUnlocalizedName("parachronology:basicmoment");
        this.setHasSubtypes(false);
        this.setMaxDamage(0);
    }

    @SideOnly(Side.CLIENT)
    public void registerModel() {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation("parachronology:basicmoment", "inventory"));

    }

    private int spread(World world, BlockPos from, int times) {

        if (times == 0)
            return 0;
        if (OreDictionary.itemMatches(new ItemStack(Blocks.log), new ItemStack(world.getBlockState(from.add(0, -1, 0)).getBlock()), false)) {
            world.destroyBlock(from.add(0, -1, 0), false);
            world.setBlockState(from.add(0,-1,0), Parachronology.petrifiedWood.getDefaultState());
            world.markBlockForUpdate(from);
            times--;
            times = spread(world, from.add(0,-1,0), times);
        }
        if (times == 0)
            return 0;
        if (OreDictionary.itemMatches(new ItemStack(Blocks.log), new ItemStack(world.getBlockState(from.add(0,1,0)).getBlock()), false)) {
            world.destroyBlock(from.add(0,1,0), false);
            world.setBlockState(from.add(0, 1, 0), Parachronology.petrifiedWood.getDefaultState());
            world.markBlockForUpdate(from);
            times--;
            times = spread(world, from.add(0,1,0), times);
        }
        if (times == 0)
            return 0;
        if (OreDictionary.itemMatches(new ItemStack(Blocks.log), new ItemStack(world.getBlockState(from.add(-1,0,0)).getBlock()), false)) {
            world.destroyBlock(from.add(-1, 0, 0), false);
            world.setBlockState(from.add(-1, 0, 0), Parachronology.petrifiedWood.getDefaultState());
            world.markBlockForUpdate(from);
            times--;
            times = spread(world, from.add(-1,0,0), times);
        }
        if (times == 0)
            return 0;
        if (OreDictionary.itemMatches(new ItemStack(Blocks.log), new ItemStack(world.getBlockState(from.add(1,0,0)).getBlock()), false)) {
            world.destroyBlock(from.add(1, 0, 0), false);
            world.setBlockState(from.add(1, 0, 0), Parachronology.petrifiedWood.getDefaultState());
            world.markBlockForUpdate(from);
            times--;
            times = spread(world, from.add(1,0,0), times);
        }
        if (times == 0)
            return 0;
        if (OreDictionary.itemMatches(new ItemStack(Blocks.log), new ItemStack(world.getBlockState(from.add(0,0,-1)).getBlock()), false)) {
            world.destroyBlock(from.add(0,0,-1), false);
            world.setBlockState(from.add(0,0,-1), Parachronology.petrifiedWood.getDefaultState());
            world.markBlockForUpdate(from);
            times--;
            times = spread(world, from.add(0,0,-1), times);
        }
        if (times == 0)
            return 0;
        if (OreDictionary.itemMatches(new ItemStack(Blocks.log), new ItemStack(world.getBlockState(from.add(0,0,1)).getBlock()), false)) {
            world.destroyBlock(from.add(0, 0, 1), false);
            world.setBlockState(from.add(0, 0, 1), Parachronology.petrifiedWood.getDefaultState());
            world.markBlockForUpdate(from);
            times--;
            times = spread(world, from.add(0,0,1), times);
        }
        return times;

    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
        Block block =worldIn.getBlockState(pos).getBlock();
        Random rand = new Random();


        int amount = 2;

        return transformUse(playerIn, worldIn, stack, pos, amount);
    }

    private boolean transformUse(EntityPlayer player, World world, ItemStack stack, BlockPos targ, int amount) {
        Random r = new Random();
        Block block = world.getBlockState(targ).getBlock();

        if (OreDictionary.itemMatches(new ItemStack(Blocks.log), new ItemStack(block), false)) {
            world.destroyBlock(targ, false);
            world.setBlockState(targ, Parachronology.petrifiedWood.getDefaultState());
            world.markBlockForUpdate(targ);
            stack.stackSize--;
            spread(world, targ, amount);
            return true;
        }
        return false;
    }
}
