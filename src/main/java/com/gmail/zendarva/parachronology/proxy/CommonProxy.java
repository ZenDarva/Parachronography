package com.gmail.zendarva.parachronology.proxy;

import com.gmail.zendarva.parachronology.utility.Scheduler;
import net.minecraft.nbt.NBTTagCompound;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by James on 9/12/2015.
 */
public class CommonProxy {

	private static Scheduler scheduler;

	private static final Map<String, NBTTagCompound> extendedEntityData = new HashMap<String, NBTTagCompound>();

	public void registerRenderThings() {
	}

	public static void storeEntityData(String id, NBTTagCompound compound) {
		extendedEntityData.put(id, compound);
	}

	public static NBTTagCompound getEntityData(String name) {
		return extendedEntityData.remove(name);
	}

	public static Scheduler getScheduler() {
		if (scheduler == null) {
			scheduler = new Scheduler();
		}
		return scheduler;
	}

}
