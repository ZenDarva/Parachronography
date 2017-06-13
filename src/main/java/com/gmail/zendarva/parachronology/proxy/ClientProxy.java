package com.gmail.zendarva.parachronology.proxy;

import com.gmail.zendarva.parachronology.Parachronology;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;

/**
 * Created by James on 9/12/2015.
 */
public class ClientProxy extends CommonProxy {

	//Add this to your ClientProxy:
	public void registerRenderThings() {

		Parachronology.capturedMoment.registerModel();
		Parachronology.petrifiedWood.registerModel();
		Parachronology.displacer.registerModel();
		Parachronology.upgrade.registerModel();
		Parachronology.moment.registerModel();
		//Parachronology.storage.registerModel();
		Parachronology.bias.registerModel();
		Parachronology.basicMoment.registerModel();
	}

}
