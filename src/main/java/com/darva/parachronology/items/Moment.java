package com.darva.parachronology.items;

import com.darva.parachronology.Parachronology;
import com.darva.parachronology.TransformListBuilder;
import com.darva.parachronology.utility.BlockVector;
import com.darva.parachronology.utility.MultiBlockHelper;
import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;

import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by James on 8/23/2015.
 */
public class Moment extends Item {



    public Moment() {
        this.setMaxStackSize(64);
        GameRegistry.registerItem(this, "base-moment");
        this.setMaxDamage(0);
        this.setCreativeTab(CreativeTabs.tabMaterials);

    }
    @SideOnly(Side.CLIENT)
    public void registerModel() {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation("parachronology:simplemoment", "inventory"));
        ModelLoader.setCustomModelResourceLocation(this, 1, new ModelResourceLocation("parachronology:moment", "moment"));
        ModelLoader.setCustomModelResourceLocation(this, 2, new ModelResourceLocation("parachronology:complexmoment", "complexmoment"));
    }


    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
        Block block = worldIn.getBlockState(pos).getBlock();
        Random rand = new Random();

        if (block instanceof IGrowable) {

            BonemealEvent event = new BonemealEvent(playerIn, worldIn, pos, worldIn.getBlockState(pos));
            if (MinecraftForge.EVENT_BUS.post(event)) {
                return false;
            }

            if (event.getResult() == Event.Result.ALLOW) {
                if (!worldIn.isRemote) {
                    stack.stackSize--;
                    Moment.func_150918_a(worldIn, pos.getX(),pos.getY(),pos.getZ(), 15);
                    System.out.println("Spawned particles");
                }
                return true;
            }

            if (block instanceof IGrowable) {
                IGrowable igrowable = (IGrowable) block;
                int amount = stack.getItemDamage() + 5;

                for (int i = 0; i < amount; i++) {
                    if (igrowable.canGrow(worldIn, pos, worldIn.getBlockState(pos), worldIn.isRemote)) {
                        if (!worldIn.isRemote) {
                            if (igrowable.canGrow(worldIn, pos, worldIn.getBlockState(pos),worldIn.isRemote)) {
                                igrowable.grow(worldIn, worldIn.rand, pos, worldIn.getBlockState(pos));
                            }

                            if (block.isOpaqueCube())
                                worldIn.playAuxSFX(2005, pos.add(0,-1,0), 0);
                            else
                                worldIn.playAuxSFX(2005, pos, 0);
                            --stack.stackSize;
                        }


                    }
                }
                return true;
            }

            return false;
        }
        int amount = 7;
        switch (stack.getItemDamage()) {

            case 1:
                amount = 15;
                break;
            case 2:
                amount = 31;
        }
        return transformUse(playerIn, worldIn, stack, pos, amount);
    }


    @Override
    public String getUnlocalizedName(ItemStack stack) {
        switch (stack.getItemDamage()) {
            case 0:
                return "parachronology:simplemoment";
            case 1:
                return "parachronology:moment";
            case 2:
                return "parachronology:complexmoment";

        }
        return null;
    }


    @Override
    public boolean getHasSubtypes() {
        return true;
    }



    private boolean transformUse(EntityPlayer player, World world, ItemStack stack, BlockPos targ, int amount) {
        Random r  = new Random();
        Block block = world.getBlockState(targ).getBlock();

        if (OreDictionary.itemMatches(new ItemStack(Blocks.log), new ItemStack(block), false)) {
            world.destroyBlock(targ, false);
            world.setBlockState(targ, Parachronology.petrifiedWood.getDefaultState());
            world.markBlockForUpdate(targ);
            stack.stackSize--;
            spread(world, targ, amount);
            return true;
        }


        if (block == Blocks.end_stone && stack.getItemDamage() == 2) {
            BlockVector corner = MultiBlockHelper.findSouthWestCorner(world, targ.getX(), targ.getY(), targ.getZ());
            if (corner != null) {
                if (MultiBlockHelper.checkMultiblock(corner)) {
                    corner = corner.East().North();
                    corner.setBlock(Blocks.end_portal);
                    corner.North().setBlock(Blocks.end_portal);
                    corner.East().setBlock(Blocks.end_portal);
                    corner.East().North().setBlock(Blocks.end_portal);
                }
            }
            stack.stackSize--;
        }
        return false;


    }

    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase entity) {

        Random r = player.worldObj.rand;
        if (stack.getItemDamage() < 1)
            return false;
        if (player.isSneaking()) {
            if (player.worldObj.isRemote) {
                return true;
            }
            ItemStack captured = new ItemStack(Parachronology.capturedMoment, 1);

            if (Parachronology.capturedMoment.setStored(captured, entity, player)) {
                stack.stackSize--;
                player.inventory.addItemStackToInventory(captured);
                return true;
            }
        }
        ArrayList<String> transforms = TransformListBuilder.Instance().getTransforms(stack.getItemDamage(), EntityList.getEntityString(entity));
        if (transforms.size() == 0)
            return false;
        for (int i = 0;i<25;i++) {
            entity.worldObj.spawnParticle(EnumParticleTypes.PORTAL, entity.posX -.5 + r.nextFloat(), entity.posY, entity.posZ -.5 + +r.nextFloat(), r.nextFloat() *.5, r.nextFloat()*.5, r.nextFloat()*.5);
        }
        if (player.worldObj.isRemote)
            return true;
        stack.stackSize--;


        EntityLiving newEntity = (EntityLiving) EntityList.createEntityByName(transforms.get(r.nextInt(transforms.size())), player.getEntityWorld());
        newEntity.setPosition(entity.posX, entity.posY, entity.posZ);
        //newEntity.setRotationYawHead(entity.getRotationYawHead());
        player.worldObj.spawnEntityInWorld(newEntity);


        entity.setDead();


        return false;
    }


    private int spread(World world, BlockPos from, int times) {

        if (times == 0)
            return 0;
        if (OreDictionary.itemMatches(new ItemStack(Blocks.log), new ItemStack(world.getBlockState(from.add(0,-1,0)).getBlock()), false)) {
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

    @SideOnly(Side.CLIENT)
    @Override
    public void getSubItems(Item item, CreativeTabs tabs, List p_150895_3_) {
        p_150895_3_.add(new ItemStack(this, 1, 0));
        p_150895_3_.add(new ItemStack(this, 1, 1));
        p_150895_3_.add(new ItemStack(this, 1, 2));
    }


    //@SideOnly(Side.CLIENT)
    private static void func_150918_a(World p_150918_0_, int p_150918_1_, int p_150918_2_, int p_150918_3_, int p_150918_4_) {
        if (p_150918_4_ == 0) {
            p_150918_4_ = 15;
        }
        BlockPos targ = new BlockPos(p_150918_1_, p_150918_2_, p_150918_3_);
        Block block = p_150918_0_.getBlockState(targ).getBlock();

        if (block.getMaterial() != Material.air) {
            block.setBlockBoundsBasedOnState(p_150918_0_, targ);

            for (int i1 = 0; i1 < p_150918_4_; ++i1) {
                double d0 = itemRand.nextGaussian() * 0.02D;
                double d1 = itemRand.nextGaussian() * 0.02D;
                double d2 = itemRand.nextGaussian() * 0.02D;
                p_150918_0_.spawnParticle(EnumParticleTypes.VILLAGER_HAPPY, (double) ((float) p_150918_1_ + itemRand.nextFloat()), (double) p_150918_2_ + (double) itemRand.nextFloat() * block.getBlockBoundsMaxY(), (double) ((float) p_150918_3_ + itemRand.nextFloat()), d0, d1, d2);
            }
        } else {
            for (int i1 = 0; i1 < p_150918_4_; ++i1) {
                double d0 = itemRand.nextGaussian() * 0.02D;
                double d1 = itemRand.nextGaussian() * 0.02D;
                double d2 = itemRand.nextGaussian() * 0.02D;
                p_150918_0_.spawnParticle(EnumParticleTypes.VILLAGER_HAPPY, (double) ((float) p_150918_1_ + itemRand.nextFloat()), (double) p_150918_2_ + (double) itemRand.nextFloat() * 1.0f, (double) ((float) p_150918_3_ + itemRand.nextFloat()), d0, d1, d2);
            }
        }
    }
}
