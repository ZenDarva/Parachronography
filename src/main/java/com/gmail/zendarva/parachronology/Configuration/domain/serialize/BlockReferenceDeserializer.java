package com.gmail.zendarva.parachronology.Configuration.domain.serialize;

import com.gmail.zendarva.parachronology.Configuration.domain.BaseBlockReference;
import com.gmail.zendarva.parachronology.Configuration.domain.BlockReference;
import com.gmail.zendarva.parachronology.Configuration.domain.OreDictReference;
import com.google.gson.*;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;

import java.lang.reflect.Type;

/**
 * Created by James on 4/12/2018.
 */
public class BlockReferenceDeserializer implements JsonDeserializer<BaseBlockReference> {
    @Override
    public BaseBlockReference deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject objRef = json.getAsJsonObject();
        if (objRef.has("Block")) {
            String str = objRef.get("Block").getAsString();
            if (!str.contains(":"))
                return null;
            String[] chunks = str.split(":");
            BlockReference ref = new BlockReference();

            ref.domain = chunks[0];
            ref.blockName = chunks[1];
            if (chunks.length == 3) {
                ref.metadata = Integer.parseInt(chunks[2]);
            }

            if (objRef.has("compareNBT")) {
                ref.compareNBT = objRef.get("compareNBT").getAsBoolean();
            }

            if (objRef.has("NBT")) {
                JsonElement obj = objRef.get("NBT");
                try {
                    ref.compound = JsonToNBT.getTagFromJson(obj.toString());
                } catch (NBTException e) {
                    e.printStackTrace();
                }
            }
            ref.register();
            return ref;
        }
        if (objRef.has("oreDictionary")){
            OreDictReference ref = new OreDictReference();
            ref.oreDictName= objRef.get("oreDictionary").getAsString();
            ref.register();
            return ref;
        }
        return null;
    }
}
