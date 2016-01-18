package com.darva.parachronology;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by James on 8/24/2015.
 */
public class DisplaceListBuilder {
    private static HashMap<BlockReference, List<BlockReference>> tier1;
    private static HashMap<BlockReference, List<BlockReference>> tier2;
    private static HashMap<BlockReference, List<BlockReference>> tier3;
    private static DisplaceListBuilder instance;

    private DisplaceListBuilder() {
        tier1 = new HashMap<BlockReference, List<BlockReference>>();
        tier2 = new HashMap<BlockReference, List<BlockReference>>();
        tier3 = new HashMap<BlockReference, List<BlockReference>>();
    }

    public static DisplaceListBuilder Instance() {
        if (instance == null) {
            instance = new DisplaceListBuilder();
        }
        return instance;
    }

    public void AddDisplacement(int tier, BlockReference from, String[] to) {
        HashMap<BlockReference, List<BlockReference>> target;
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
            list = new LinkedList<BlockReference>();
        }
        for (String str : to) {
            list.add(BlockReference.readBlockFromString(str));
        }
        target.put(from, list);
    }

    public HashMap<BlockReference, ArrayList<BlockReference>> getDisplacements(int tier) {
        HashMap<BlockReference, ArrayList<BlockReference>> result = new HashMap<BlockReference, ArrayList<BlockReference>>();

        if (tier >= 0) {
            for (BlockReference ref : tier1.keySet()) {
                ArrayList<BlockReference> array = new ArrayList<BlockReference>();
                for (BlockReference targ : tier1.get(ref)) {
                    array.add(targ);
                }
                result.put(ref, array);
            }
        }
        if (tier >= 1) {
            for (BlockReference block : tier2.keySet()) {
                ArrayList<BlockReference> array;

                if (result.containsKey(block)) {
                    array = result.get(block);
                } else {
                    array = new ArrayList<BlockReference>();
                }
                for (BlockReference targ : tier2.get(block)) {
                    array.add(targ);
                }
                result.put(block, array);
            }
        }
        if (tier >= 2) {
            for (BlockReference block : tier3.keySet()) {
                ArrayList<BlockReference> array;

                if (result.containsKey(block)) {
                    array = result.get(block);
                } else {
                    array = new ArrayList<BlockReference>();
                }
                for (BlockReference targ : tier3.get(block)) {
                    array.add(targ);
                }
                result.put(block, array);
            }
        }

        return result;
    }
}
