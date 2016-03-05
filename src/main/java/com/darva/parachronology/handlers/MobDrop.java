package com.darva.parachronology.handlers;

import com.darva.parachronology.Configuration.ConfigurationHolder;
import com.darva.parachronology.Parachronology;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.monster.*;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Random;

/**
 * Created by James on 8/23/2015.
 */
public class MobDrop {

    public static double rand;
    private Random r = new Random();

//    @SubscribeEvent
//    public void onEntityDrop(LivingDropsEvent event) {
//        System.out.println(event.entityLiving.getName());
//        //Only player kills should drop Moments of any level.
////        if (!(event.entityLiving.getLastAttacker() instanceof EntityPlayer))
////            return;
//        //Simple moment drops.
//        newDrop((EntityLiving) event.entityLiving);
//        if (event.entityLiving instanceof EntitySkeleton) {
//            EntitySkeleton skele = (EntitySkeleton) event.entityLiving;
//            if (skele.getSkeletonType() == 0) {
//                if (r.nextInt(50) <= 1)
//                    event.entityLiving.entityDropItem(new ItemStack(Parachronology.moment, 1, 0), 1);
//            } else {
//                if (r.nextInt(50) <= 1)
//                    event.entityLiving.entityDropItem(new ItemStack(Parachronology.moment, 1, 1), 1);
//            }
//        }
//        if (event.entityLiving instanceof EntityZombie) {
//            if (r.nextInt(50) <= 1)
//                event.entityLiving.entityDropItem(new ItemStack(Parachronology.moment, 1, 0), 1);
//        }
//        if (event.entityLiving instanceof EntitySlime) {
//            if (r.nextInt(60) <= 1)
//                event.entityLiving.entityDropItem(new ItemStack(Parachronology.moment, 1, 0), 1);
//        }
//        if (event.entityLiving instanceof EntityCreeper) {
//            if (r.nextInt(20) <= 1)
//                event.entityLiving.entityDropItem(new ItemStack(Parachronology.moment, 1, 0), 1);
//        }
//        if (event.entityLiving instanceof EntityEnderman) {
//            if (r.nextInt(30) <= 1) {
//                if (r.nextInt(100) <= 1)
//                    event.entityLiving.entityDropItem(new ItemStack(Parachronology.moment, 1, 1), 1);
//                else
//                    event.entityLiving.entityDropItem(new ItemStack(Parachronology.moment, 1, 0), 1);
//            }
//
//        }
//        if (event.entityLiving instanceof EntityBlaze) {
//            if (r.nextInt(20) <= 1)
//                event.entityLiving.entityDropItem(new ItemStack(Parachronology.moment, 1, 1), 1);
//        }
//        if (event.entityLiving instanceof EntityPigZombie) {
//            if (r.nextInt(30) <= 1)
//                event.entityLiving.entityDropItem(new ItemStack(Parachronology.moment, 1, 1), 1);
//        }
//        if (event.entityLiving instanceof EntityMagmaCube) {
//            if (r.nextInt(40) <= 1)
//                event.entityLiving.entityDropItem(new ItemStack(Parachronology.moment, 1, 1), 1);
//        }
//
//        if (event.entityLiving instanceof EntityGhast)
//        {
//            if (r.nextInt(10) <=1)
//            {
//                if (r.nextInt(100) <=1)
//                {
//                    event.entityLiving.entityDropItem(new ItemStack(Parachronology.moment,1,2),1);
//                }
//                event.entityLiving.entityDropItem(new ItemStack(Parachronology.moment,1,1),1);
//            }
//        }
//
//        if (event.entityLiving instanceof EntityWither)
//        {
//            event.entityLiving.entityDropItem(new ItemStack(Parachronology.moment,1,2),1);
//        }
//        if (event.entityLiving instanceof EntityDragon)
//        {
//            event.entityLiving.entityDropItem(new ItemStack(Parachronology.moment,5,2),1);
//        }
//
//
//    }
    @SubscribeEvent
    public void newDrop(LivingDropsEvent event)
    {
        EntityLiving entity = (EntityLiving) event.entityLiving;
        int value = EnchantmentHelper.getLootingModifier(entity);
        int droppedAmount = 1;
        Random r = new Random(System.currentTimeMillis());
        //System.out.println(event.entityLiving.getName());
        if (r.nextInt(10) < value)
        {
            droppedAmount++;
        }
        String modifiedName = entity.getName().toLowerCase();
        if (entity instanceof EntitySkeleton)
            {
                if (((EntitySkeleton)entity).getSkeletonType() != 0)
                {
                    modifiedName="wither skeleton";
                }
            }
        int totalDropped = 1;

        if (ConfigurationHolder.mobDrops.containsKey(modifiedName)) {
            DropData data = ConfigurationHolder.mobDrops.get(modifiedName);

            if (data.complexMomentChance != 0) {
                if (r.nextInt(100) < data.complexMomentChance + value*2) {
                    entity.entityDropItem(new ItemStack(Parachronology.moment, droppedAmount, 2), 1);
                    System.out.println("Dropped");
                    return;
                }
            }
            if (data.momentChance != 0) {
                if (r.nextInt(100) < data.momentChance + value*2) {
                    entity.entityDropItem(new ItemStack(Parachronology.moment, droppedAmount, 1), 1);
                    System.out.println("Dropped");
                    return;
                }
            }
            if (data.simpleMomentChance != 0) {
                if (r.nextInt(100) < data.simpleMomentChance + value*2) {
                    entity.entityDropItem(new ItemStack(Parachronology.moment, droppedAmount, 0), 1);
                    System.out.println("Dropped");
                    return;
                }
            }


        }

    }
}
