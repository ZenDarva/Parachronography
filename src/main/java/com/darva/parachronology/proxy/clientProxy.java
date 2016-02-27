package com.darva.parachronology.proxy;

import com.darva.parachronology.Parachronology;
import com.darva.parachronology.entity.DisplacerEntity;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import scala.xml.PrettyPrinter;

/**
 * Created by James on 9/12/2015.
 */
public class clientProxy extends commonProxy {

    //Add this to your ClientProxy:
    public void registerRenderThings() {

        Parachronology.capturedMoment.registerModel();
        Parachronology.petrifiedWood.registerModel();
        Parachronology.displacer.registerModel();
        Parachronology.upgrade.registerModel();
        Parachronology.moment.registerModel();
        //Parachronology.storage.registerModel();
    }

    public static void reg(Item item, String name) {
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
                .register(item, 0, new ModelResourceLocation(Parachronology.MODID + ":" + name, "inventory"));
    }
    public static void reg(Block block, String name)
    {
        Item item = Item.getItemFromBlock(block);
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new  ModelResourceLocation(Parachronology.MODID + ":" + name,"inventory"));

    }
}
