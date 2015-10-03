package com.darva.parachronology.handlers;

import com.darva.parachronology.Parachronology;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.monster.*;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDropsEvent;

import java.util.Random;

/**
 * Created by James on 8/23/2015.
 */
public class MobDrop {

    public static double rand;
    private Random r = new Random();

    @SubscribeEvent
    public void onEntityDrop(LivingDropsEvent event) {

        //Only player kills should drop Moments of any level.
//        if (!(event.entityLiving.getLastAttacker() instanceof EntityPlayer))
//            return;
        //Simple moment drops.
        if (event.entityLiving instanceof EntitySkeleton) {
            EntitySkeleton skele = (EntitySkeleton) event.entityLiving;
            if (skele.getSkeletonType() == 0) {
                if (r.nextInt(50) <= 1)
                    event.entityLiving.entityDropItem(new ItemStack(Parachronology.moment, 1, 0), 1);
            } else {
                if (r.nextInt(50) <= 1)
                    event.entityLiving.entityDropItem(new ItemStack(Parachronology.moment, 1, 1), 1);
            }
        }
        if (event.entityLiving instanceof EntityZombie) {
            if (r.nextInt(50) <= 1)
                event.entityLiving.entityDropItem(new ItemStack(Parachronology.moment, 1, 0), 1);
        }
        if (event.entityLiving instanceof EntitySlime) {
            if (r.nextInt(60) <= 1)
                event.entityLiving.entityDropItem(new ItemStack(Parachronology.moment, 1, 0), 1);
        }
        if (event.entityLiving instanceof EntityCreeper) {
            if (r.nextInt(20) <= 1)
                event.entityLiving.entityDropItem(new ItemStack(Parachronology.moment, 1, 0), 1);
        }
        if (event.entityLiving instanceof EntityEnderman) {
            if (r.nextInt(30) <= 1) {
                if (r.nextInt(100) <= 1)
                    event.entityLiving.entityDropItem(new ItemStack(Parachronology.moment, 1, 1), 1);
                else
                    event.entityLiving.entityDropItem(new ItemStack(Parachronology.moment, 1, 0), 1);
            }

        }
        if (event.entityLiving instanceof EntityBlaze) {
            if (r.nextInt(20) <= 1)
                event.entityLiving.entityDropItem(new ItemStack(Parachronology.moment, 1, 1), 1);
        }
        if (event.entityLiving instanceof EntityPigZombie) {
            if (r.nextInt(30) <= 1)
                event.entityLiving.entityDropItem(new ItemStack(Parachronology.moment, 1, 1), 1);
        }
        if (event.entityLiving instanceof EntityMagmaCube) {
            if (r.nextInt(40) <= 1)
                event.entityLiving.entityDropItem(new ItemStack(Parachronology.moment, 1, 1), 1);
        }

        if (event.entityLiving instanceof EntityGhast)
        {
            if (r.nextInt(10) <=1)
            {
                if (r.nextInt(100) <=1)
                {
                    event.entityLiving.entityDropItem(new ItemStack(Parachronology.moment,1,2),1);
                }
                event.entityLiving.entityDropItem(new ItemStack(Parachronology.moment,1,1),1);
            }
        }

        if (event.entityLiving instanceof EntityWither)
        {
            event.entityLiving.entityDropItem(new ItemStack(Parachronology.moment,1,2),1);
        }
        if (event.entityLiving instanceof EntityDragon)
        {
            event.entityLiving.entityDropItem(new ItemStack(Parachronology.moment,5,2),1);
        }


    }
}
