package com.gmail.zendarva.parachronology.Configuration.domain.serialize;

import com.gmail.zendarva.parachronology.Configuration.domain.BaseBlockReference;
import com.gmail.zendarva.parachronology.Configuration.domain.OreDictReference;
import com.google.gson.*;

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
            //BlockReference ref = new BlockReference();

            String domain = chunks[0];
            String blockName = chunks[1];
            int metadata = 0;
            if (chunks.length == 3) {
                metadata = Integer.parseInt(chunks[2]);
            }

            String compound = null;
            if (objRef.has("NBT")) {
                JsonElement obj = objRef.get("NBT");
                    compound = obj.toString().replaceAll("^\"|\"$", "");;
            }

            return BaseBlockReference.getReference(metadata,blockName,domain,compound);
        }
        if (objRef.has("oreDictionary")){
            OreDictReference ref = (OreDictReference) BaseBlockReference.getReference(objRef.get("oreDictionary").getAsString());
            return ref;
        }
        return null;
    }
}
