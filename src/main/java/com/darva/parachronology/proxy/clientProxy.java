package com.darva.parachronology.proxy;

import com.darva.parachronology.Parachronology;
import com.darva.parachronology.entity.DisplacerEntity;
import com.darva.parachronology.renderer.DisplaceISBRH;
import com.darva.parachronology.renderer.DisplacerRenderer;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;

/**
 * Created by James on 9/12/2015.
 */
public class clientProxy extends commonProxy {

    //Add this to your ClientProxy:
    public void registerRenderThings() {
        Parachronology.renderId = RenderingRegistry.getNextAvailableRenderId();

        ClientRegistry.registerTileEntity(DisplacerEntity.class, "Displacer", new DisplacerRenderer());
        RenderingRegistry.registerBlockHandler(new DisplaceISBRH());
    }

//And add this to the @EventHanlder load(FMLInitializationEvent event) method of your main mod file:


//You also need a GameRegistry and everything else you'd do to add a normal block (which varies depending on if you have a config or not.)
}
