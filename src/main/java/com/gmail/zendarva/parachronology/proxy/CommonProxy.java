package com.gmail.zendarva.parachronology.proxy;

import com.gmail.zendarva.parachronology.Parachronology;
import com.gmail.zendarva.parachronology.capability.ITimeless;
import com.gmail.zendarva.parachronology.capability.Timeless;
import com.gmail.zendarva.parachronology.capability.TimelessStorage;
import com.gmail.zendarva.parachronology.handlers.GuiProxy;
import com.gmail.zendarva.parachronology.handlers.WandEquipHandler;
import com.gmail.zendarva.parachronology.network.PacketHandler;
import com.gmail.zendarva.parachronology.utility.Scheduler;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.network.NetworkRegistry;

/**
 * Created by James on 9/12/2015.
 */
public class CommonProxy extends BaseProxy {

	public static Scheduler getScheduler() {
		if (scheduler == null) {
			scheduler = new Scheduler();
		}
		return scheduler;
	}

	@Override
	public void init() {
			NetworkRegistry.INSTANCE.registerGuiHandler(Parachronology.instance,new GuiProxy());
	}

	@Override
	public void preInit(){
		MinecraftForge.EVENT_BUS.register(new WandEquipHandler());
		PacketHandler.registerMessages("parachronology");
	}
}
