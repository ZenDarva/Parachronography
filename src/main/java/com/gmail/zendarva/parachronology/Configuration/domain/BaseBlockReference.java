package com.gmail.zendarva.parachronology.Configuration.domain;

import com.gmail.zendarva.parachronology.Parachronology;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by James on 4/13/2018.
 */
public abstract class BaseBlockReference {
    protected static List<BaseBlockReference> references = new LinkedList<>();
    public abstract boolean matches(BaseBlockReference target);

    public boolean compareNBT = false;

    public void register(){
        if (!references.contains(this))
            references.add(this);
    }

    protected BaseBlockReference(){};

    public BaseBlockReference getReference(BaseBlockReference reference){
        for (BaseBlockReference blockReference : references) {
            if (blockReference.matches(reference))
                return blockReference;
        }
        return null;
    }

    public abstract String getDisplayName();

    public static BaseBlockReference getReference(int metaData, String blockName, String domain) {
        return getReference(metaData,blockName,domain,null);
    }
    public static BaseBlockReference getReference(int metaData, String blockName, String domain, String nbtTagCompound) {
        BlockReference ref;
        for (BaseBlockReference blockReference : references){
            if (blockReference instanceof OreDictReference)
                continue;

            ref = (BlockReference) blockReference;
            if (ref.metadata == metaData && blockName.equals(ref.blockName) && domain.equals(ref.domain)){
                if (nbtTagCompound== null && ref.compound==null)
                    return ref;
                try {
                    NBTTagCompound tag = JsonToNBT.getTagFromJson(nbtTagCompound);
                    if (tag.equals(ref.compound))
                        return ref;
                } catch (NBTException e) {
                    //Log this.
                }
            }
        }
        ref = new BlockReference();
        ref.metadata=metaData;
        ref.blockName=blockName;
        ref.domain=domain;
        if (nbtTagCompound != null)
        try {
            ref.compound= JsonToNBT.getTagFromJson(nbtTagCompound);
        } catch (NBTException e) {
            e.printStackTrace();
        }
        ref.register();
        return ref;

    }

    public static BaseBlockReference getReference(String oreDictName){
        OreDictReference ref;
        for (BaseBlockReference blockReference : references){
            if (blockReference instanceof BlockReference)
                continue;

            ref = (OreDictReference) blockReference;
            if (ref.oreDictName.equals(oreDictName)){
                return ref;
            }


        }
        ref = new OreDictReference(oreDictName);
        ref.register();
        return ref;
    }


}
