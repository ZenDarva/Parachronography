package com.gmail.zendarva.parachronology.handlers;

import com.gmail.zendarva.parachronology.Configuration.ConfigurationHolder;
import com.gmail.zendarva.parachronology.Parachronology;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.player.EntityPlayer;
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

	@SubscribeEvent
	public void newDrop(LivingDropsEvent event) {
		if (!(event.getEntityLiving() instanceof EntityLiving))
			return;
		int dropChanceBonus = 1;

		if (event.getSource().getTrueSource() != null && event.getSource().getTrueSource() instanceof EntityPlayer) {
			dropChanceBonus = ConfigurationHolder.playerDropChanceMultiplier;
		}

		EntityLiving entity = (EntityLiving) event.getEntityLiving();
		int value = EnchantmentHelper.getLootingModifier(entity);
		int droppedAmount = 1;
		Random r = new Random(System.currentTimeMillis());
		if (r.nextInt(10) < value) {
			droppedAmount++;
		}
		String modifiedName = entity.getName().toLowerCase();
		if (entity instanceof EntitySkeleton) {
			//                if (((EntitySkeleton)entity).getSkeletonType() != 0)
			//                {
			//                    modifiedName="wither skeleton";
			//                }
		}
		int totalDropped = 1;

		if (ConfigurationHolder.mobDrops.containsKey(modifiedName)) {
			DropData data = ConfigurationHolder.mobDrops.get(modifiedName);

			if (data.complexMomentChance != 0) {
				if (r.nextInt(100) < (data.complexMomentChance * dropChanceBonus) + (value * 2)) {
					entity.entityDropItem(new ItemStack(Parachronology.moment, droppedAmount, 2), 1);
					System.out.println("Dropped");
					return;
				}
			}
			if (data.momentChance != 0) {
				if (r.nextInt(100) < (data.momentChance * dropChanceBonus) + (value * 2)) {
					entity.entityDropItem(new ItemStack(Parachronology.moment, droppedAmount, 1), 1);
					System.out.println("Dropped");
					return;
				}
			}
			if (data.simpleMomentChance != 0) {
				if (r.nextInt(100) < (data.simpleMomentChance * dropChanceBonus) + (value * 2)) {
					entity.entityDropItem(new ItemStack(Parachronology.moment, droppedAmount, 0), 1);
					return;
				}
			}

		}

	}
}
