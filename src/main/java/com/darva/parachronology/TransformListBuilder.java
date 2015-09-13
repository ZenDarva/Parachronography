package com.darva.parachronology;

import java.util.*;

/**
 * Created by James on 9/2/2015.
 */
public class TransformListBuilder {

    private static HashMap<String, List<String>> tier2;
    private static HashMap<String, List<String>> tier3;
    private static TransformListBuilder instance;

    private TransformListBuilder()
    {
        tier2 = new HashMap<String, List<String>>();
        tier3 = new HashMap<String, List<String>>();

    }

    public static TransformListBuilder Instance()
    {
        if (instance == null)
            instance = new TransformListBuilder();
        return instance;
    }

    public void addTransform(int tier, String from, String[] to)
    {
        HashMap<String,List<String>> targ;
        List<String> results;
        switch (tier+1)
        {
            default:
                return;
            case 2:
                targ = tier2;
                break;
            case 3:
                targ = tier3;

        }

        if (targ.containsKey(from))
        {
            results = targ.get(from);
        }
        else
            results = new LinkedList<String>();

        Collections.addAll(results, to);
        targ.put(from, results);
    }

    public ArrayList<String> getTransforms(int tier, String name)
    {

        ArrayList<String> result = new ArrayList<String>();
        if (tier >=1 && tier2.containsKey(name))
        {
            for (String str : tier2.get(name))
            {
                result.add(str);
            }
        }
        if (tier >=2 && tier3.containsKey(name))
        {
            for (String str : tier3.get(name))
            {
                result.add(str);
            }
        }

        return result;
    }
}
