package com.gmail.zendarva.parachronology.handlers;

import com.gmail.zendarva.parachronology.Parachronology;
import com.gmail.zendarva.parachronology.block.PetrifiedWood;
import com.gmail.zendarva.parachronology.entity.DisplacerEntity;
import com.gmail.zendarva.parachronology.entity.StorageEntity;
import com.gmail.zendarva.parachronology.item.DiplacerItemBlock;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by James on 7/4/2017.
 */
public class Registration {

    @SubscribeEvent
    public void RegisterBlocks(RegistryEvent.Register<Block> event)
    {
        event.getRegistry().register(Parachronology.displacer);
        event.getRegistry().register(Parachronology.petrifiedWood);
        event.getRegistry().register(Parachronology.enrichedDirt);
        event.getRegistry().register(Parachronology.storage);

        GameRegistry.registerTileEntity(DisplacerEntity.class, "tileEntityDisplacer");
        GameRegistry.registerTileEntity(StorageEntity.class,"tileEntityStorage");

    }

    @SubscribeEvent
    public void RegisterItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(Parachronology.basicMoment);
        event.getRegistry().register(Parachronology.moment);
        event.getRegistry().register(Parachronology.capturedMoment);
        event.getRegistry().register(Parachronology.upgrade);
        event.getRegistry().register(new DiplacerItemBlock(Parachronology.displacer));
        ItemBlock PetrifiedBlock = new ItemBlock(Parachronology.petrifiedWood);
        PetrifiedBlock.setRegistryName("petrifiedwoodItem");
        event.getRegistry().register(PetrifiedBlock);
        ItemBlock enrichedDirt = new ItemBlock(Parachronology.enrichedDirt);
        enrichedDirt.setRegistryName("enricheddirt");
        event.getRegistry().register(enrichedDirt);

        ItemBlock storageItem = new ItemBlock(Parachronology.storage);
        storageItem.setRegistryName("storage");
        event.getRegistry().register(storageItem);

        event.getRegistry().register(Parachronology.pickaxe);
        event.getRegistry().register(Parachronology.wand);

    }

}
