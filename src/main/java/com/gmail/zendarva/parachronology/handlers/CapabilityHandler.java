package com.gmail.zendarva.parachronology.handlers;

import com.gmail.zendarva.parachronology.capability.Timeless;
import com.gmail.zendarva.parachronology.capability.TimelessProvider;
import com.gmail.zendarva.parachronology.item.TimelessPickaxe;
import com.gmail.zendarva.parachronology.item.TimelessWand;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Created by James on 7/30/2017.
 */
public class CapabilityHandler {

    public static final ResourceLocation timeless = new ResourceLocation("parachronology", "timeless");

    @SubscribeEvent
    public void AttachItemStack(AttachCapabilitiesEvent<ItemStack> event) {
        if ((event.getObject() instanceof ItemStack)
             && (event.getObject().getItem() instanceof TimelessPickaxe ||
                event.getObject().getItem() instanceof TimelessWand)) {
            event.addCapability(timeless, new TimelessProvider());
        }
    }
}
