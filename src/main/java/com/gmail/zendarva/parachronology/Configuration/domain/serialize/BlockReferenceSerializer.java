package com.gmail.zendarva.parachronology.Configuration.domain.serialize;

import com.gmail.zendarva.parachronology.Configuration.domain.BaseBlockReference;
import com.gmail.zendarva.parachronology.Configuration.domain.BlockReference;
import com.gmail.zendarva.parachronology.Configuration.domain.OreDictReference;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import scala.xml.dtd.impl.Base;

import java.lang.reflect.Type;

/**
 * Created by James on 4/12/2018.
 */
public class BlockReferenceSerializer implements JsonSerializer<BaseBlockReference> {
    @Override
    public JsonElement serialize(BaseBlockReference src, Type typeOfSrc, JsonSerializationContext context) {
        if (src instanceof BlockReference) {
            BlockReference ref = (BlockReference) src;
            JsonObject refObject = new JsonObject();
            refObject.addProperty("Block", ref.toString());
            refObject.addProperty("compareNBT", ref.compareNBT);
            if (ref.compound != null) {
                refObject.add("NBT", context.serialize(ref.compound));
            }
            return refObject;
        }
        if (src instanceof OreDictReference){
            OreDictReference ref = (OreDictReference) src;
            JsonObject refObject = new JsonObject();
            refObject.addProperty("oreDictionary",ref.oreDictName);
            return refObject;
        }
        return new JsonObject();
    }
}
