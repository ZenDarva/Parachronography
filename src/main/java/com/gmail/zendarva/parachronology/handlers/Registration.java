package com.gmail.zendarva.parachronology.handlers;

import com.gmail.zendarva.parachronology.Parachronology;
import com.gmail.zendarva.parachronology.block.PetrifiedWood;
import com.gmail.zendarva.parachronology.item.DiplacerItemBlock;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Created by James on 7/4/2017.
 */
public class Registration {

    @SubscribeEvent
    public void RegisterBlocks(RegistryEvent.Register<Block> event)
    {
        event.getRegistry().register(Parachronology.displacer);
        event.getRegistry().register(Parachronology.petrifiedWood);
    }

    @SubscribeEvent
    public void RegisterItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(Parachronology.basicMoment);
        event.getRegistry().register(Parachronology.moment);
        event.getRegistry().register(Parachronology.bias);
        event.getRegistry().register(Parachronology.capturedMoment);
        event.getRegistry().register(Parachronology.upgrade);
        event.getRegistry().register(new DiplacerItemBlock(Parachronology.displacer));
        ItemBlock PetrifiedBlock = new ItemBlock(Parachronology.petrifiedWood);
        PetrifiedBlock.setRegistryName("petrifiedwoodItem");
        event.getRegistry().register(PetrifiedBlock);

    }
}
