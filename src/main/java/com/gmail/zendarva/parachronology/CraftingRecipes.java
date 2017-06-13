package com.gmail.zendarva.parachronology;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.ArrayList;
import java.util.Map;

import static com.gmail.zendarva.parachronology.DisplaceListBuilder.displaceRecipes;

/**
 * Created by James on 2/27/2016.
 */
public class CraftingRecipes {

	public static void buildBiases() {
		ItemStack towards;
		ItemStack against;
		ItemStack redstone = new ItemStack(Items.REDSTONE);
		ItemStack torch = new ItemStack(Blocks.REDSTONE_TORCH);
		ItemStack moment = new ItemStack(Parachronology.moment, 1, 1);
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

					GameRegistry.addRecipe(towards, "aba", "bcb", "aba", 'a', reference.getStack(), 'b', redstone, 'c',
							moment);
					GameRegistry.addRecipe(against, "aba", "bcb", "aba", 'a', reference.getStack(), 'b', torch, 'c',
							moment);
					Parachronology.bias.addSubItem(towards);
					Parachronology.bias.addSubItem(against);
				}
			}
		}
	}
}
