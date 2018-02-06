package com.gmail.zendarva.parachronology.handlers;

import com.gmail.zendarva.parachronology.capability.ITimeless;
import com.gmail.zendarva.parachronology.item.TimelessWand;
import com.gmail.zendarva.parachronology.network.PacketHandler;
import com.gmail.zendarva.parachronology.network.SendWandBlockPacket;
import com.gmail.zendarva.parachronology.utility.TimelessUtility;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by James on 8/11/2017.
 */
public class WandEquipHandler {
    @SubscribeEvent
    public void LivingEquipmentChangeEvent(LivingEquipmentChangeEvent event) {
        if (!(event.getEntity() instanceof EntityPlayer))
            return;

        EntityPlayer player = (EntityPlayer) event.getEntity();
        ItemStack wand = player.getHeldItem(EnumHand.MAIN_HAND);
        if (!(wand.getItem() instanceof TimelessWand))
            return;

        ITimeless timeless = TimelessUtility.getTimeless(wand);
        ItemStack stack = TimelessUtility.getSelectedStorageSlotStack(DimensionManager.getWorld(timeless.getWorldId()),wand);
        SendWandBlockPacket packet = new SendWandBlockPacket(stack);
        PacketHandler.INSTANCE.sendTo(packet, (EntityPlayerMP) event.getEntity());
    }
}
