package com.gmail.zendarva.parachronology.Configuration.domain;

import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by James on 6/18/2018.
 */
public class Transform {
    private final int tier;
    public Map<String, Integer> results;
    static Random random = new Random(System.currentTimeMillis());
    public void addTransform(String mobName, int weight){
        results.put(mobName,weight);
    }

    public Transform(int tier){
        this.tier = tier;
        results = new HashMap<>();
    }

    public EntityLiving getResult(World world){
        ArrayList<String> rawResult = new ArrayList<>();
        for (String s : results.keySet()) {
            {
                for(int x = 0; x< results.get(s);x++){
                    rawResult.add(s);
                }
            }
        }
        if (rawResult.isEmpty())
            return null;
        if (rawResult.size() == 1)
            return (EntityLiving) EntityList.createEntityByIDFromName(new ResourceLocation(rawResult.get(0)), world);
        int index = random.nextInt(rawResult.size()-1);
        return (EntityLiving) EntityList.createEntityByIDFromName(new ResourceLocation(rawResult.get(index)), world);

    }

    public int getTier() {
        return tier;
    }
}
