package com.gmail.zendarva.parachronology.Configuration.domain;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

import java.util.List;

/**
 * Created by James on 4/12/2018.
 */
public class DisplaceResult {
    public BlockReference to;
    public int weight =1;


    public DisplaceResult(BlockReference to, int weight){
        this.to = to;
        this.weight = weight;
    }
}
