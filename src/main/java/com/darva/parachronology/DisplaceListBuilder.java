package com.darva.parachronology;

import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by James on 8/24/2015.
 */
public class DisplaceListBuilder{
    private static HashMap<Block, List<Block>> tier1;
    private static HashMap<Block, List<Block>> tier2;
    private static HashMap<Block, List<Block>> tier3;
    private static DisplaceListBuilder instance;

    private DisplaceListBuilder()
    {
        tier1 = new HashMap<Block,List<Block>>();
        tier2 = new HashMap<Block,List<Block>>();
        tier3 = new HashMap<Block,List<Block>>();
    }

    public static DisplaceListBuilder Instance()
    {
        if (instance==null)
        {
            instance = new DisplaceListBuilder();
        }
        return instance;
    }

    public void AddDisplacement(int tier, Block from, String[]to) {
        HashMap<Block, List<Block>> target;
        switch (tier) {
            case 1:
                target = tier1;
                break;
            case 2:
                target = tier2;
                break;
            case 3:
                target = tier3;
                break;
            default:
                return;
        }

        LinkedList list;
        if (target.containsKey(from)) {
            list = (LinkedList) target.get(from);
            target.remove(from);
        } else {
            list = new LinkedList<Block>();
        }
        for (String str : to)
        {
            list.add(Block.getBlockFromName(str));
        }
        target.put(from,list);
    }

    public HashMap<Block, ArrayList<Block>> getDisplacements(int tier)
    {
        HashMap<Block, ArrayList<Block>> result = new HashMap<Block,ArrayList<Block>>();

        if (tier >= 1)
        {
            for(Block block : tier1.keySet())
            {
                ArrayList<Block> array = new ArrayList<Block>();
                for (Block targ : tier1.get(block))
                {
                    array.add(targ);
                }
                result.put(block,array);
            }
        }
        if (tier >=2)
        {
            for(Block block : tier2.keySet())
            {
                ArrayList<Block> array;

                if (result.containsKey(block)){
                    array = result.get(block);
                }
                else{
                    array = new ArrayList<Block>();
                }
                for (Block targ : tier2.get(block))
                {
                    array.add(targ);
                }
                result.put(block,array);
            }
        }

        return result;
    }
}
