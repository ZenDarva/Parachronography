package com.gmail.zendarva.parachronology.recipe;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.crafting.IConditionFactory;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.function.BooleanSupplier;

/**
 * Created by James on 7/17/2018.
 */
public class isModLoaded implements IConditionFactory {
    @Override
    public BooleanSupplier parse(JsonContext jsonContext, JsonObject jsonObject) {
        return () ->{
            JsonElement mod = jsonObject.get("mod");
            if (mod == null)
                return false;
            return Loader.isModLoaded(mod.getAsString());
        };

    }
}
