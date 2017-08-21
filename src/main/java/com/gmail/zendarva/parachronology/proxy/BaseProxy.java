package com.gmail.zendarva.parachronology.proxy;

import com.gmail.zendarva.parachronology.Parachronology;
import com.gmail.zendarva.parachronology.capability.ITimeless;
import com.gmail.zendarva.parachronology.capability.Timeless;
import com.gmail.zendarva.parachronology.capability.TimelessStorage;
import com.gmail.zendarva.parachronology.handlers.GuiProxy;
import com.gmail.zendarva.parachronology.network.PacketHandler;
import com.gmail.zendarva.parachronology.utility.Scheduler;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.network.NetworkRegistry;

/**
 * Created by James on 8/11/2017.
 */
public abstract class BaseProxy {
    protected static Scheduler scheduler;
    public ItemStack wandStack;



    public void registerRenderThings() {
    }

    public abstract void init();

    public abstract void preInit();
}
