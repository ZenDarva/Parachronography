package com.gmail.zendarva.parachronology.handlers;

import com.gmail.zendarva.parachronology.capability.ITimeless;
import com.gmail.zendarva.parachronology.capability.TimelessStorage;
import com.gmail.zendarva.parachronology.item.TimelessWand;
import com.gmail.zendarva.parachronology.network.PacketHandler;
import com.gmail.zendarva.parachronology.network.SendWandBlockPacket;
import com.gmail.zendarva.parachronology.network.UpdateWandSlotPacket;
import com.gmail.zendarva.parachronology.proxy.ClientProxy;
import com.gmail.zendarva.parachronology.utility.TimelessUtility;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by James on 8/20/2017.
 */
public class KeyPressHandler {

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void HandleKeyPress(InputEvent.KeyInputEvent event)
    {
        EntityPlayer player = Minecraft.getMinecraft().player;
        ItemStack wand = player.getHeldItem(EnumHand.MAIN_HAND);
        if (wand.getItem() instanceof TimelessWand){
            if (ClientProxy.keyBindings[1].isPressed()) {
                ITimeless timeless = TimelessUtility.getTimeless(wand);
                int slot = timeless.getSelectedSlot();
                slot++;
                if (slot > 26)
                    slot=0;
                timeless.setSelectedSlot(slot);
                UpdateWandSlotPacket packetOut = new UpdateWandSlotPacket(slot);
                PacketHandler.INSTANCE.sendToServer(packetOut);
            }
            if (ClientProxy.keyBindings[0].isPressed()) {
                ITimeless timeless = TimelessUtility.getTimeless(wand);
                int slot = timeless.getSelectedSlot();
                slot--;
                if (slot < 0)
                    slot=26;
                timeless.setSelectedSlot(slot);
                UpdateWandSlotPacket packetOut = new UpdateWandSlotPacket(slot);
                PacketHandler.INSTANCE.sendToServer(packetOut);
            }
        }
    }
}
