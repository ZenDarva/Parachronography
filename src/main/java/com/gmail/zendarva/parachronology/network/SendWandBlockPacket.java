package com.gmail.zendarva.parachronology.network;

import com.gmail.zendarva.parachronology.Parachronology;
import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.io.*;

/**
 * Created by James on 8/11/2017.
 */
public class SendWandBlockPacket implements IMessage {

    protected ItemStack stack;

    @Override
    public void fromBytes(ByteBuf buf) {
            NBTTagCompound tag = ByteBufUtils.readTag(buf);
            stack = new ItemStack(tag);
    }

    @Override
    public void toBytes(ByteBuf buf) {
            ByteBufUtils.writeTag(buf, stack.serializeNBT());
    }

    public SendWandBlockPacket(ItemStack stack) {
        this.stack = stack;
    }
    public SendWandBlockPacket(){}

    public static class Handler implements IMessageHandler<SendWandBlockPacket, IMessage> {

        @Override
        public IMessage onMessage(SendWandBlockPacket message, MessageContext ctx) {
            FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
            return null;
        }

        private void handle(SendWandBlockPacket packet, MessageContext ctx){
            Parachronology.proxy.wandStack = packet.stack;
        }
    }
}
