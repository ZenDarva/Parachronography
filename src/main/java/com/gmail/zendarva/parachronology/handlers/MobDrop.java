package com.gmail.zendarva.parachronology.handlers;

import java.util.Random;

import com.gmail.zendarva.parachronology.Configuration.ConfigManager;
import com.gmail.zendarva.parachronology.Configuration.domain.DropData;
import com.gmail.zendarva.parachronology.Parachronology;
import com.gmail.zendarva.parachronology.Configuration.ConfigurationHolder;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

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

		if (event.getSource().getTrueSource() != null && event.getSource().getTrueSource() instanceof EntityPlayer && !(event.getSource().getTrueSource() instanceof FakePlayer)) {
			dropChanceBonus = ConfigurationHolder.playerDropChanceMultiplier;
		}

		Entity entity = (EntityLiving) event.getEntityLiving();
		int value = EnchantmentHelper.getLootingModifier((EntityLiving)entity);
		int droppedAmount = 1;
		Random r = new Random(System.currentTimeMillis());
		if (r.nextInt(10) < value) {
			droppedAmount++;
		}
		ResourceLocation mobName = EntityList.getKey(entity);
		String rawMobName = mobName.toString();
		int totalDropped = 1;

		if (ConfigManager.mobDrops.containsKey(rawMobName)) {
			DropData data = ConfigManager.mobDrops.get(rawMobName);

			if (data.complexMomentChance != 0) {

				if (checkChance((data.complexMomentChance * dropChanceBonus) + (value * 2))) {
					entity.entityDropItem(new ItemStack(Parachronology.moment, droppedAmount, 2), 1);
					return;
				}
			}
			if (data.momentChance != 0) {
				if (checkChance((data.momentChance * dropChanceBonus) + (value * 2))) {
					entity.entityDropItem(new ItemStack(Parachronology.moment, droppedAmount, 1), 1);
					return;
				}
			}
			if (data.simpleMomentChance != 0) {
				if (checkChance((data.simpleMomentChance * dropChanceBonus) + (value * 2))) {
					entity.entityDropItem(new ItemStack(Parachronology.moment, droppedAmount, 0), 1);
					return;
				}
			}

		}

	}

	boolean checkChance(int chance) {
		Random r = new Random(System.currentTimeMillis());
		int i = r.nextInt(100);
		//System.out.println("Moment chance roll: " + i + " compared to " + chance);
		if (i < chance)
			return true;
		return false;
	}
}
