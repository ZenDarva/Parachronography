package com.gmail.zendarva.parachronology.handlers;

import com.gmail.zendarva.parachronology.Parachronology;
import com.gmail.zendarva.parachronology.tileEntity.DisplacerEntity;
import com.gmail.zendarva.parachronology.tileEntity.StorageEntity;
import com.gmail.zendarva.parachronology.item.DiplacerItemBlock;
import com.gmail.zendarva.parachronology.utility.ItemBlockOverride;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
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

        GameRegistry.registerTileEntity(DisplacerEntity.class, new ResourceLocation("parachronology","tileEntityDisplacer"));
        GameRegistry.registerTileEntity(StorageEntity.class,new ResourceLocation("parachronology","tileEntityStorage"));

    }

    @SubscribeEvent
    public void RegisterItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(Parachronology.basicMoment);
        event.getRegistry().register(Parachronology.moment);
        event.getRegistry().register(Parachronology.capturedMoment);
        event.getRegistry().register(Parachronology.upgrade);
        event.getRegistry().register(new DiplacerItemBlock(Parachronology.displacer));
        event.getRegistry().register(Parachronology.timeSyphon);

        ItemBlock PetrifiedBlock = new ItemBlock(Parachronology.petrifiedWood);
        PetrifiedBlock.setRegistryName("petrifiedwoodItem");
        event.getRegistry().register(PetrifiedBlock);
        ItemBlock enrichedDirt = new ItemBlock(Parachronology.enrichedDirt);
        enrichedDirt.setRegistryName("enricheddirt");
        event.getRegistry().register(enrichedDirt);

        ItemBlock storageItem = new ItemBlockOverride(Parachronology.storage);
        storageItem.setRegistryName("storage");

        event.getRegistry().register(storageItem);

        event.getRegistry().register(Parachronology.pickaxe);
        event.getRegistry().register(Parachronology.wand);

    }

}
