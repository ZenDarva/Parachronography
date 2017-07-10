package com.gmail.zendarva.parachronology.recipe;

import static com.gmail.zendarva.parachronology.DisplaceListBuilder.displaceRecipes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.gmail.zendarva.parachronology.BlockReference;
import com.gmail.zendarva.parachronology.DisplaceRecipe;
import com.gmail.zendarva.parachronology.Parachronology;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by James on 2/27/2016.
 */
public class CraftingRecipes {

	@SubscribeEvent
	public void registerEvent(RegistryEvent.Register<IRecipe> event) {

		List<IRecipe> recipes = buildBiases();
		for (IRecipe recipe: recipes) {

			event.getRegistry().register(recipe);
		}
	}

	private List<IRecipe> buildBiases() {
		ItemStack towards;
		ItemStack against;
		ItemStack redstone = new ItemStack(Items.REDSTONE);
		ItemStack torch = new ItemStack(Blocks.REDSTONE_TORCH);
		ItemStack moment = new ItemStack(Parachronology.moment, 1, 1);
		ArrayList<IRecipe> results = new ArrayList<>();
		//EWWW... at least it's only an initilization step.
		for (Map.Entry<BlockReference, DisplaceRecipe> entry : displaceRecipes.entrySet()) {
			for (Map.Entry<Integer, ArrayList<BlockReference>> recipe : entry.getValue().to.entrySet()) {
				for (BlockReference reference : recipe.getValue()) {
					if (reference.targBlock == null)
						continue;
					//System.out.println("Biasing " + reference.targBlock.getLocalizedName());
					towards = new ItemStack(Parachronology.bias);
					towards.setTagCompound(new NBTTagCompound());
					towards.getTagCompound().setString("towards", reference.toString());

					against = new ItemStack(Parachronology.bias);
					against.setTagCompound(new NBTTagCompound());
					against.getTagCompound().setString("against", reference.toString());

					results.add(new BiasRecipe(against,reference.getStack(),true));
					results.add(new BiasRecipe(towards,reference.getStack(),false));

					Parachronology.bias.addSubItem(towards);
					Parachronology.bias.addSubItem(against);
				}
			}
		}
		return results;
	}
}
