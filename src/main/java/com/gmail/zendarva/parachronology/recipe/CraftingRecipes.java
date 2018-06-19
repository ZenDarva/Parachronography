package com.gmail.zendarva.parachronology.recipe;


import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Created by James on 2/27/2016.
 */
public class CraftingRecipes {

	@SubscribeEvent
	public void registerEvent(RegistryEvent.Register<IRecipe> event) {

		event.getRegistry().register(new ShapelessTimelessRecipe().setRegistryName("rechargetimeless"));
	}


}
