package com.gmail.zendarva.parachronology.Configuration;

import com.gmail.zendarva.parachronology.BlockReference;
import com.gmail.zendarva.parachronology.Configuration.domain.BaseBlockReference;
import com.gmail.zendarva.parachronology.Configuration.domain.DisplaceTier;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by James on 4/17/2018.
 */
public class DisplaceManager {
    protected static List<DisplaceTier> displacements= new ArrayList();

    public static void addDisplacement(int tier, BaseBlockReference from, BlockReference to, int weight){
        DisplaceTier targetTier = getTier(tier);

    }


    private static DisplaceTier getTier(int tier){
        for (DisplaceTier displacement : displacements) {
            if (displacement.getTier()==tier)
                return displacement;
        }
        DisplaceTier displaceTier = new DisplaceTier(tier);
        displacements.add(displaceTier);
        return displaceTier;
    }
}
