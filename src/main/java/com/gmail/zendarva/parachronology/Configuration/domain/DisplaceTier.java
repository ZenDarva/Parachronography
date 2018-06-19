package com.gmail.zendarva.parachronology.Configuration.domain;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by James on 4/17/2018.
 */
public class DisplaceTier {
    private int tier;
    public Map<BaseBlockReference, List<DisplaceResult>> displacements = new HashMap<>();

    public int getTier() {
        return tier;
    }

    public DisplaceTier(int tier){
        this.tier = tier;
    }

    public void addDisplacement(BaseBlockReference from, DisplaceResult result){
        List<DisplaceResult> list;
        if (displacements.containsKey(from)){
            list = displacements.get(from);
        }
        else
        {
            list = new LinkedList<>();
            displacements.put(from,list);
        }
        list.add(result);
    }

    public List<BlockReference> getDisplacements(BaseBlockReference from) {

        List<BlockReference> results = new LinkedList<>();
        for (BaseBlockReference blockReference : displacements.keySet()) {
            if (blockReference.matches(from)){
                List<DisplaceResult> rawResults =displacements.get(blockReference);
                for (DisplaceResult rawResult : rawResults) {
                    for (int i = 0; i<rawResult.weight;i++){
                        results.add(rawResult.to);
                    }
                }
            }
        }

        return results;

    }
}
