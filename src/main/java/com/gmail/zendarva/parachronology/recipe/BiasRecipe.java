package com.gmail.zendarva.parachronology.recipe;

import com.gmail.zendarva.parachronology.Parachronology;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.registries.IForgeRegistryEntry;

/**
 * Created by James on 7/4/2017.
 */
public class BiasRecipe extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {

    private ItemStack result = ItemStack.EMPTY;
    private ItemStack target = ItemStack.EMPTY;
    private boolean against = false;
    public boolean isHidden() {
        return false;
    }

    @Override
    public String getGroup() {
        return "parachronology";
   }

    @Override
    public boolean matches(InventoryCrafting inv, World worldIn) {
        return false;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv) {
        return result.copy();
    }

    @Override
    public boolean canFit(int width, int height) {
        return (width >=3 && height >= 3);
    }

    @Override
    public ItemStack getRecipeOutput() {
        return result;
    }

    public BiasRecipe(ItemStack output, ItemStack target, boolean against ){
        this.result=output.copy();
        this.target=target.copy();
        this.against=against;
        if (against)
            this.setRegistryName("biasagainst" + target.getDisplayName().replace(" ",""));
        else
            this.setRegistryName("biastowards" + target.getDisplayName().replace(" ",""));
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> inputs = NonNullList.create();
        if (against) {
            ItemStack torch = new ItemStack(Blocks.REDSTONE_TORCH,1);
            inputs.add(Ingredient.fromStacks(target));
            inputs.add(Ingredient.fromStacks(torch));
            inputs.add(Ingredient.fromStacks(target));
            inputs.add(Ingredient.fromStacks(torch));
            inputs.add(Ingredient.fromStacks(new ItemStack(Parachronology.moment,1,0)));
            inputs.add(Ingredient.fromStacks(torch));
            inputs.add(Ingredient.fromStacks(target));
            inputs.add(Ingredient.fromStacks(torch));
            inputs.add(Ingredient.fromStacks(target));

        }
        else {
            inputs.add(Ingredient.fromStacks(target));
            inputs.add(Ingredient.fromItem(Items.REDSTONE));
            inputs.add(Ingredient.fromStacks(target));
            inputs.add(Ingredient.fromItem(Items.REDSTONE));
            inputs.add(Ingredient.fromStacks(new ItemStack(Parachronology.moment,1,0)));
            inputs.add(Ingredient.fromItem(Items.REDSTONE));
            inputs.add(Ingredient.fromStacks(target));
            inputs.add(Ingredient.fromItem(Items.REDSTONE));
            inputs.add(Ingredient.fromStacks(target));
        }

        return inputs;
    }
}
