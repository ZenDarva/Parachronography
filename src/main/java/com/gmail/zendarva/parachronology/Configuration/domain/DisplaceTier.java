package com.gmail.zendarva.parachronology.Configuration.domain;

import java.util.List;
import java.util.Map;

/**
 * Created by James on 4/17/2018.
 */
public class DisplaceTier {
    private int tier;
    private Map<BaseBlockReference, List<DisplaceResult>> displacements;

    public int getTier() {
        return tier;
    }

    public DisplaceTier(int tier){
        this.tier = tier;
    }

//    public void addDisplacement(BaseBlockReference from, DisplaceResult){
//        //for displacements.
//    }
}
