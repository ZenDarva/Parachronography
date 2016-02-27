package com.darva.parachronology;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by James on 1/18/2016.
 */
public class DisplaceRecipe {

    public BlockReference from;
    public HashMap<Integer, ArrayList<BlockReference>> to;

    public DisplaceRecipe(BlockReference from)
    {
        this.from = from;
        this.to = new HashMap<Integer, ArrayList<BlockReference>>();
    }
    public void addDisplacement(int tier, String[] result)
    {
        ArrayList<BlockReference> target;
        BlockReference ref;
        if (to.containsKey(tier))
        {
            target = to.get(tier);
        }
        else
            target = new ArrayList<BlockReference>();

        for (String str : result)
        {
            ref = BlockReference.readBlockFromString(str);
            if (!target.contains(ref))
                target.add(ref);
        }
        to.put(tier, target);
    }

    public ArrayList<BlockReference> getDisplacement(int Tier)
    {
        ArrayList<BlockReference> result = new ArrayList<BlockReference>();
        for (int i = Tier+1; i>0; i--) {
            if (to.containsKey(i))
                result.addAll(to.get(i));
        }
        return result;
    }

    public boolean matchesBlock(BlockReference ref)
    {
        if (from == ref ||
                (from.metadata == -1 && ref.targBlock == from.targBlock))
            return true;
        return false;
    }
}
