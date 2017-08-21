package com.gmail.zendarva.parachronology.network;

import com.gmail.zendarva.parachronology.capability.ITimeless;
import com.gmail.zendarva.parachronology.item.TimelessWand;
import com.gmail.zendarva.parachronology.utility.TimelessUtility;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * Created by James on 8/20/2017.
 */
public class UpdateWandSlotPacket implements IMessage {
    private int slot;
    @Override
    public void fromBytes(ByteBuf buf) {
        slot = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(slot);
    }

    public UpdateWandSlotPacket(){}
    public UpdateWandSlotPacket(int slot){
        this.slot = slot;
    }

    public static class Handler implements IMessageHandler<UpdateWandSlotPacket, IMessage>{

        @Override
        public IMessage onMessage(UpdateWandSlotPacket message, MessageContext ctx) {
            FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
            return null;
        }

        public void handle(UpdateWandSlotPacket packet, MessageContext ctx){
            EntityPlayer player = ctx.getServerHandler().player;
            ItemStack stack = player.getHeldItem(EnumHand.MAIN_HAND);
            if (stack.getItem() instanceof TimelessWand) {
                ITimeless timeless = TimelessUtility.getTimeless(stack);
                timeless.setSelectedSlot(packet.slot);
                ItemStack targ = TimelessUtility.getSelectedStorageSlotStack(player.world,stack);
                SendWandBlockPacket packetOut = new SendWandBlockPacket(targ);
                PacketHandler.INSTANCE.sendTo(packetOut, (EntityPlayerMP) player);
            }

        }
    }
}
