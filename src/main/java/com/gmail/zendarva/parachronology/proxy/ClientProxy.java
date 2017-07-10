package com.gmail.zendarva.parachronology.proxy;

import com.gmail.zendarva.parachronology.Parachronology;

/**
 * Created by James on 9/12/2015.
 */
public class ClientProxy extends CommonProxy {

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
