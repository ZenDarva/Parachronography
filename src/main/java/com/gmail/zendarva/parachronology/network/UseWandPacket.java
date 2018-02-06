package com.gmail.zendarva.parachronology.network;

import com.gmail.zendarva.parachronology.capability.ITimeless;
import com.gmail.zendarva.parachronology.entity.StorageEntity;
import com.gmail.zendarva.parachronology.utility.TimelessUtility;
import com.gmail.zendarva.parachronology.utility.WorldUtil;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * Created by James on 8/11/2017.
 */
public class UseWandPacket implements IMessage {
    BlockPos targBlock;
    int slotIndex;
    EnumFacing facing;

    @Override
    public void fromBytes(ByteBuf buf) {
        targBlock = BlockPos.fromLong(buf.readLong());
        slotIndex = buf.readInt();
        facing = EnumFacing.values()[buf.readByte()];
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeLong(targBlock.toLong());
        buf.writeInt(slotIndex);
        buf.writeByte(facing.ordinal());
    }

    public UseWandPacket( BlockPos targBlock, int slotIndex, EnumFacing facing){

        this.targBlock = targBlock;
        this.slotIndex = slotIndex;
        this.facing = facing;
    }

    public UseWandPacket(){

    }


    public static class Handler implements IMessageHandler<UseWandPacket, IMessage>{

        @Override
        public IMessage onMessage(UseWandPacket message, MessageContext ctx) {
            FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
            return null;
        }

        private void handle(UseWandPacket packet, MessageContext ctx) {
            EntityPlayerMP playerEntity = ctx.getServerHandler().player;
            ItemStack wand = playerEntity.getHeldItem(EnumHand.MAIN_HAND);
            ITimeless timeless= TimelessUtility.getTimeless(wand);
            World world = DimensionManager.getWorld(timeless.getWorldId());

            ItemStack stack = TimelessUtility.getSelectedStorageSlotStack(world, timeless.getTarget() ,packet.slotIndex);

            WorldUtil.UseItemStack((WorldServer) playerEntity.world,packet.targBlock,stack, packet.facing);
            if (stack.isEmpty()) {
                SendWandBlockPacket packetOut = new SendWandBlockPacket(stack);
                PacketHandler.INSTANCE.sendTo(packetOut,playerEntity);
            }

        }
    }
}
