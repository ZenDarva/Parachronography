package com.darva.parachronology.items;

import com.darva.parachronology.Parachronology;
import com.darva.parachronology.TransformListBuilder;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by James on 8/23/2015.
 */
public class Moment extends Item {

    private IIcon[] icons;

    public Moment()
    {
        this.setMaxStackSize(64);
        GameRegistry.registerItem(this, "base-moment");
        this.setMaxDamage(0);
        this.setCreativeTab(CreativeTabs.tabMaterials);
        icons = new IIcon[3];
    }

    @Override
    public boolean onItemUse(ItemStack Stack, EntityPlayer Player, World World, int x, int y, int z, int side, float p_77648_8_, float p_77648_9_, float p_77648_10_) {

        Block block = World.getBlock(x,y,z);

        if (block instanceof IGrowable)
        {

            BonemealEvent event = new BonemealEvent(Player, World, block, x, y, z);
            if (MinecraftForge.EVENT_BUS.post(event))
            {
                return false;
            }

            if (event.getResult() == Event.Result.ALLOW)
            {
                if (!World.isRemote)
                {
                    Stack.stackSize--;
                    Moment.func_150918_a(World,x,y,z-1,15);
                    System.out.println("Spawned particles");
                }
                return true;
            }

            if (block instanceof IGrowable) {
                IGrowable igrowable = (IGrowable) block;
                for (int i = 0; i < Stack.getItemDamage() + 2; i++) {
                    if (igrowable.func_149851_a(World, x, y, z, World.isRemote)) {
                        if (!World.isRemote) {
                            if (igrowable.func_149852_a(World, World.rand, x, y, z)) {
                                igrowable.func_149853_b(World, World.rand, x, y, z);
                            }

                            //TODO: Figure out why particles aren't spawning.
                            --Stack.stackSize;
                        }

                        return true;
                    }
                }
            }

            return false;
        }

        int amount = 7;
        switch(Stack.getItemDamage())
        {

            case 1:
                amount = 15;
                break;
            case 2:
                amount = 31;
        }
        return transformUse(Player, World, Stack, x, y, z, amount);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        switch (stack.getItemDamage())
        {
            case 0:
                return "parachronograph:simple-moment";
            case 1:
                return "parachronograph:moment";
            case 2:
                return "parachronograph:complex-moment";

        }
        return null;
    }





    @Override
    public boolean getHasSubtypes() {
        return true;
    }

    @Override
    public void registerIcons(IIconRegister register) {

        icons[0] = register.registerIcon("parachronology:simplemoment");
        icons[1] = register.registerIcon("parachronology:moment");
        icons[2] = register.registerIcon("parachronology:complexmoment");
        super.registerIcons(register);
    }

    @Override
    public IIcon getIconFromDamage(int p_77617_1_) {
        return icons[p_77617_1_];
    }

    private boolean transformUse(EntityPlayer player, World world, ItemStack stack, int x, int y, int z, int amount)
    {
        Block block = world.getBlock(x,y,z);

        if (OreDictionary.itemMatches(new ItemStack(Blocks.log), new ItemStack(block), false))
        {
            world.setBlock(x,y,z, Parachronology.petrifiedWood);
            world.markBlockForUpdate(x, y, z);
            stack.stackSize--;
            spread(world,x,y,z,amount);
            return true;
        }
        return false;
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase entity) {

        Random r = new Random();
        if (stack.getItemDamage() <1)
            return false;
        if (player.isSneaking())
        {
            if (player.worldObj.isRemote)
            {
                return true;
            }
            ItemStack captured = new ItemStack(Parachronology.capturedMoment,1);

            if (Parachronology.capturedMoment.setStored(captured,entity,player))
            {
                    stack.stackSize--;
                    player.inventory.addItemStackToInventory(captured);
                    return true;
            }
        }
        ArrayList<String> transforms =TransformListBuilder.Instance().getTransforms(stack.getItemDamage(), EntityList.getEntityString(entity));
        if (transforms.size() == 0)
            return false;
        if (player.worldObj.isRemote)
            return true;
        stack.stackSize--;
        Vec3 loc = entity.getPosition(1.0f);

        EntityLiving newEntity = (EntityLiving) EntityList.createEntityByName(transforms.get(r.nextInt(transforms.size())), player.getEntityWorld());
        newEntity.setPosition(loc.xCoord, loc.yCoord, loc.zCoord);
        newEntity.setRotationYawHead(entity.getRotationYawHead());
        player.worldObj.spawnEntityInWorld(newEntity);
        entity.setDead();

        return false;
    }



    private int spread(World world, int x, int y, int z, int times)
    {
        if (times == 0)
            return 0;
        if (OreDictionary.itemMatches(new ItemStack(Blocks.log), new ItemStack(world.getBlock(x,y-1,z) ), false))
        {
            world.setBlock(x,y-1,z, Parachronology.petrifiedWood);
            world.markBlockForUpdate(x,y-1,z);
            times--;
            times = spread(world,x,y-1,z,times);
        }
        if (times == 0)
            return 0;
        if (OreDictionary.itemMatches(new ItemStack(Blocks.log), new ItemStack(world.getBlock(x,y+1,z)), false) )
        {
            world.setBlock(x,y+1,z, Parachronology.petrifiedWood);
            world.markBlockForUpdate(x, y+1, z);
            times--;
            times = spread(world,x,y+1,z,times);
        }
        if (times == 0)
            return 0;
        if (OreDictionary.itemMatches(new ItemStack(Blocks.log), new ItemStack(world.getBlock(x-1,y,z)), false))
        {
            world.setBlock(x-1,y,z, Parachronology.petrifiedWood);
            world.markBlockForUpdate(x-1,y,z);
            times--;
            times = spread(world,x-1,y,z,times);
        }
        if (times == 0)
            return 0;
        if (OreDictionary.itemMatches(new ItemStack(Blocks.log), new ItemStack(world.getBlock(x+1,y,z)), false))
        {
            world.setBlock(x+1,y,z, Parachronology.petrifiedWood);
            world.markBlockForUpdate(x+1, y, z);
            times--;
            times = spread(world,x+1,y,z,times);
        }
        if (times == 0)
            return 0;
        if (OreDictionary.itemMatches(new ItemStack(Blocks.log), new ItemStack(world.getBlock(x,y,z-1)), false))
        {
            world.setBlock(x,y,z-1, Parachronology.petrifiedWood);
            world.markBlockForUpdate(x,y,z-1);
            times--;
            times = spread(world,x,y,z-1,times);
        }
        if (times == 0)
            return 0;
        if (OreDictionary.itemMatches(new ItemStack(Blocks.log), new ItemStack(world.getBlock(x,y,z+1)), false))
        {
            world.setBlock(x,y,z+1, Parachronology.petrifiedWood);
            world.markBlockForUpdate(x, y, z+1);
            times--;
            times = spread(world,x,y,z+1,times);
        }
        return times;

    }

    @SideOnly(Side.CLIENT)
    @Override
    public void getSubItems(Item item, CreativeTabs tabs, List p_150895_3_) {
        p_150895_3_.add(new ItemStack(this, 1,0));
        p_150895_3_.add(new ItemStack(this, 1,1));
        p_150895_3_.add(new ItemStack(this, 1,2));
    }


    //@SideOnly(Side.CLIENT)
    private static void func_150918_a(World p_150918_0_, int p_150918_1_, int p_150918_2_, int p_150918_3_, int p_150918_4_)
    {
        if (p_150918_4_ == 0)
        {
            p_150918_4_ = 15;
        }

        Block block = p_150918_0_.getBlock(p_150918_1_, p_150918_2_, p_150918_3_);

        if (block.getMaterial() != Material.air)
        {
            block.setBlockBoundsBasedOnState(p_150918_0_, p_150918_1_, p_150918_2_, p_150918_3_);

            for (int i1 = 0; i1 < p_150918_4_; ++i1)
            {
                double d0 = itemRand.nextGaussian() * 0.02D;
                double d1 = itemRand.nextGaussian() * 0.02D;
                double d2 = itemRand.nextGaussian() * 0.02D;
                p_150918_0_.spawnParticle("happyVillager", (double)((float)p_150918_1_ + itemRand.nextFloat()), (double)p_150918_2_ + (double)itemRand.nextFloat() * block.getBlockBoundsMaxY(), (double)((float)p_150918_3_ + itemRand.nextFloat()), d0, d1, d2);
            }
        }
        else
        {
            for (int i1 = 0; i1 < p_150918_4_; ++i1)
            {
                double d0 = itemRand.nextGaussian() * 0.02D;
                double d1 = itemRand.nextGaussian() * 0.02D;
                double d2 = itemRand.nextGaussian() * 0.02D;
                p_150918_0_.spawnParticle("happyVillager", (double)((float)p_150918_1_ + itemRand.nextFloat()), (double)p_150918_2_ + (double)itemRand.nextFloat() * 1.0f, (double)((float)p_150918_3_ + itemRand.nextFloat()), d0, d1, d2);
            }
        }
    }
}
