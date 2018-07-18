package com.gmail.zendarva.parachronology.recipe;

import com.gmail.zendarva.parachronology.Configuration.ConfigurationHolder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraftforge.common.crafting.IConditionFactory;
import net.minecraftforge.common.crafting.JsonContext;

import java.util.function.BooleanSupplier;

/**
 * Created by James on 7/17/2018.
 */
public class isFlagSet implements IConditionFactory {
    @Override
    public BooleanSupplier parse(JsonContext jsonContext, JsonObject jsonObject) {
        return ()->{
            JsonElement flagName= jsonObject.get("name");
            if (flagName == null){
                return false;
            }
            return ConfigurationHolder.flags.get(flagName.getAsString());
        };
    }
}
