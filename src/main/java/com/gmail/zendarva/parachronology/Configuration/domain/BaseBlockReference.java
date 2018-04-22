package com.gmail.zendarva.parachronology.Configuration.domain;

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

    protected BaseBlockReference getExistingReference(BaseBlockReference reference){
        for (BaseBlockReference blockReference : references) {
            if (blockReference.matches(reference))
                return blockReference;
        }
        return null;
    }
}
